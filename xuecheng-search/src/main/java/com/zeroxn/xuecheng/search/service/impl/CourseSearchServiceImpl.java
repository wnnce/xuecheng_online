package com.zeroxn.xuecheng.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.search.annotations.Document;
import com.zeroxn.xuecheng.search.dto.SearchCourseParamDto;
import com.zeroxn.xuecheng.search.dto.SearchPageResultDto;
import com.zeroxn.xuecheng.search.entity.CourseIndex;
import com.zeroxn.xuecheng.search.service.CourseSearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:49:38
 * @Description:
 */
@Service
public class CourseSearchServiceImpl implements CourseSearchService {
    private static final Logger logger = LoggerFactory.getLogger(CourseSearchServiceImpl.class);
    private final ElasticsearchClient elasticsearchClient;

    public CourseSearchServiceImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }
    @Override
    public SearchPageResultDto<CourseIndex> queryCourseIndex(PageParams params, SearchCourseParamDto courseDto) {
        String indexName = CourseIndex.class.getAnnotation(Document.class).indexName();
        // 创建bool查询体
        BoolQuery.Builder queryBuilder = new BoolQuery.Builder();
        // 创建查询请求体
        SearchRequest.Builder builder = new SearchRequest.Builder();
        // 全文检索
        if (StringUtils.isNotEmpty(courseDto.getKeywords())) {
            queryBuilder.must(q -> q.multiMatch(m -> m
                    .fields(Arrays.asList("name", "description"))
                    .query(courseDto.getKeywords())
                    .minimumShouldMatch("70%")));
        }
        // 按照条件过滤
        if (StringUtils.isNotEmpty(courseDto.getMt())) {
            queryBuilder.filter(f -> f.term(t -> t.field("mtName").value(courseDto.getMt())));
        }
        if (StringUtils.isNotEmpty(courseDto.getSt())) {
            queryBuilder.filter(f -> f.term(t -> t.field("stName").value(courseDto.getSt())));
        }
        if (StringUtils.isNotEmpty(courseDto.getGrade())) {
            queryBuilder.filter(f -> f.term(t -> t.field("grade").value(courseDto.getGrade())));
        }
        // 分页
        long page = params.getPageNo();
        long pageSize = params.getPageSize();
        int start = (int)((page - 1) * pageSize);
        builder.query(q -> q.bool(queryBuilder.build()));
        builder.from(start);
        builder.size(Math.toIntExact(pageSize));

        // 字段高亮
        builder.highlight(h -> h
                .requireFieldMatch(false)
                .fields("name", hf -> hf
                        .preTags("<font class='eslight'>")
                        .postTags("</font>")));
        // 按照大分类和小分类进行数据聚合
        builder.aggregations("mtAgg", a -> a.terms(t -> t.field("mtName").size(100)));
        builder.aggregations("stAgg", a -> a.terms(t -> t.field("stName").size(100)));

        SearchRequest request = builder.index(indexName).build();
        SearchResponse<CourseIndex> response = null;
        try {
            response = elasticsearchClient.search(request, CourseIndex.class);
        }catch (IOException e) {
            logger.error("elasticsearch搜索错误，错误消息：{}", e.getMessage());
            return new SearchPageResultDto<>(new ArrayList<>(), 0L, 0L, 0L);
        }
        SearchPageResultDto<CourseIndex> resultDto = responseMakeSearchPageResultDto(response, page, pageSize);
        List<String> mtList = getResponseAggregations(response, "mtAgg");
        List<String> stList = getResponseAggregations(response, "stAgg");
        resultDto.setMtList(mtList);
        resultDto.setStList(stList);
        return resultDto;
    }

    private SearchPageResultDto<CourseIndex> responseMakeSearchPageResultDto(SearchResponse<CourseIndex> response, long page, long pageSize) {
        List<Hit<CourseIndex>> hitList = response.hits().hits();
        List<CourseIndex> courseIndexList = new ArrayList<>();
        hitList.forEach(hit -> {
            CourseIndex courseIndex = hit.source();
            if (courseIndex == null){
                return;
            }
            List<String> highlightList = hit.highlight().get("name");
            if (highlightList != null && !highlightList.isEmpty()) {
                StringBuilder stf = new StringBuilder();
                highlightList.forEach(stf::append);
                courseIndex.setName(stf.toString());
            }
            courseIndexList.add(courseIndex);
        });
        return new SearchPageResultDto<>(courseIndexList, (long) hitList.size(), page, pageSize);
    }

    private <T> List<String> getResponseAggregations(SearchResponse<T> response, String aggregationsName) {
        Aggregate aggregate = response.aggregations().get(aggregationsName);
        List<StringTermsBucket> termsBuckets = aggregate.sterms().buckets().array();
        return termsBuckets.stream().map(bucket -> bucket.key().stringValue()).toList();
    }
}
