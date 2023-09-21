package com.zeroxn.xuecheng.search.test;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.zeroxn.xuecheng.search.annotations.Document;
import com.zeroxn.xuecheng.search.annotations.Id;
import com.zeroxn.xuecheng.search.clients.DocumentClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lisang
 * @DateTime: 2023-09-21 19:55:41
 * @Description:
 */
@SpringBootTest
public class SearchTest {

    @Autowired
    private DocumentClient documentClient;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void testAddDocument() {
        Student student = new Student(1, 18, "小明");
        String save = documentClient.save(student);
        System.out.println(save);
    }

    @Test
    public void testQueryDocument() {
        Student student = documentClient.get("2", Student.class);
        System.out.println(student);
    }

    @Test
    public void testUpdateDocument() {
        Student student = new Student(1, 19, "李小羿");
        String update = documentClient.update(student);
        System.out.println(update);
        student = documentClient.get("1", Student.class);
        System.out.println(student);
    }

    @Test
    public void testPrUpdateDocument() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 20);
        String result = documentClient.partUpdate("1", map, Student.class);
        System.out.println(result);
        Student student = documentClient.get("1", Student.class);
        System.out.println(student);
    }

    @Test
    public void testDeleteDocument() {
        String delete = documentClient.delete("1", Student.class);
        System.out.println(delete);
        Student student = documentClient.get("1", Student.class);
        System.out.println(student);
    }

    @Test
    public void testSearch() throws IOException {
        SearchResponse<Student> search = elasticsearchClient.search(s -> s.index("student").query(q -> q.match(m -> m.field("name").query("小"))), Student.class);
        List<Hit<Student>> hits = search.hits().hits();
        hits.forEach(hit -> System.out.println(hit.source()));
    }
}

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "student")
class Student {
    @Id
    private Integer id;
    private Integer age;
    private String name;
}
