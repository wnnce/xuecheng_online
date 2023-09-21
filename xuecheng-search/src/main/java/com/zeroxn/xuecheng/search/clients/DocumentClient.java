package com.zeroxn.xuecheng.search.clients;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import com.zeroxn.xuecheng.search.annotations.Document;
import com.zeroxn.xuecheng.search.annotations.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @Author: lisang
 * @DateTime: 2023-09-20 17:53:09
 * @Description:
 */
public class DocumentClient {
    private static final Logger logger = LoggerFactory.getLogger(DocumentClient.class);
    private final ElasticsearchClient client;

    public DocumentClient(ElasticsearchClient client) {
        this.client = client;
    }

    public <T> String save(T entity) {
        String indexName = getEntityIndexName(entity.getClass());
        String id = getEntityId(entity);
        IndexRequest.Builder<T> builder = new IndexRequest.Builder<T>()
                .index(indexName).document(entity);
        if (id != null && !id.isEmpty()){
            builder.id(id);
        }
        IndexRequest<T> request = builder.build();
        return sendSaveDocumentRequest(request);
    }

    public <T> String save(T entity, String id) {
        String indexName = getEntityIndexName(entity.getClass());
        IndexRequest<T> request = new IndexRequest.Builder<T>().index(indexName).id(id).document(entity).build();
        return sendSaveDocumentRequest(request);
    }

    public <T> String save(T entity, String id, String indexName) {
        IndexRequest<T> request = new IndexRequest.Builder<T>().index(indexName).id(id).document(entity).build();
        return sendSaveDocumentRequest(request);
    }

    public <T> T get(String indexName, String id, Class<T> clazz) {
        return sendGetDocumentRequest(indexName, id, clazz);
    }

    public <T> T get(String id, Class<T> clazz) {
        String indexName = getEntityIndexName(clazz);
        return sendGetDocumentRequest(indexName, id, clazz);
    }

    public <T> String update(T entity) {
        return this.save(entity);
    }

    public <O, N> String partUpdate(String id, N newData, Class<O> entity) {
        String indexName = getEntityIndexName(entity);
        UpdateRequest<O, N> request = new UpdateRequest.Builder<O, N>().index(indexName).id(id).doc(newData).build();
        try{
            UpdateResponse<O> response = client.update(request, entity);
            return response.id();
        }catch (IOException e) {
            logger.error("elasticsearch局部修改错误，错误消息：{}", e.getMessage());
        }
        return null;
    }
    public <T> String delete(String id, String indexName) {
        return sendDeleteDocumentRequest(indexName, id);
    }

    public <T> String delete(String id, Class<T> clazz) {
        String indexName = getEntityIndexName(clazz);
        return sendDeleteDocumentRequest(indexName, id);
    }

    private <T> String sendDeleteDocumentRequest(String indexName, String id) {
        try{
            DeleteResponse response = client.delete(request -> request.index(indexName).id(id));
            return response.id();
        }catch (IOException e) {
            logger.error("elasticsearch删除文档错误，错误消息：{}", e.getMessage());
        }
        return null;

    }
    private <T> T sendGetDocumentRequest(String indexName, String id , Class<T> clazz){
        try {
            GetResponse<T> response = client.get(request -> request.index(indexName).id(id), clazz);
            return response.source();
        }catch (IOException e) {
            logger.error("elasticsearch获取文档错误，错误消息：{}", e.getMessage());
        }
        return null;
    }
    private <T> String sendSaveDocumentRequest(IndexRequest<T> request) {
        try {
            IndexResponse response = client.index(request);
            return response.id();
        }catch (IOException e) {
            logger.error("elasticsearch保存文档错误，错误消息：{}", e.getMessage());
        }
        return null;
    }
    private <T> String getEntityIndexName(Class<T> clazz) {
        Document annotation = clazz.getAnnotation(Document.class);
        Assert.notNull(annotation, "实体类型的Document注解不能为空");
        return annotation.indexName();
    }

    private <T> String getEntityId(T entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Id annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                field.setAccessible(true);
                try{
                    Object value = field.get(entity);
                    return value.toString();
                }catch (Exception e) {
                    logger.error("获取类型ID值错误，错误消息：{}", e.getMessage());
                }
            }
        }
        return null;
    }
}