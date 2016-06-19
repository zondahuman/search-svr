package com.abin.lee.search.service.impl;

import com.abin.lee.search.common.esfactory.ElasticSearchClient;
import com.abin.lee.search.model.Medicine;
import com.abin.lee.search.model.Order;
import com.abin.lee.search.service.ElasticSearchService;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.fieldstats.FieldStats;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-30
 * Time: 下午11:20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{

    @Resource
    ElasticSearchClient client;

    public void createCluster(String indices){
        client.getTransportClient().admin().indices().prepareCreate(indices).execute().actionGet();
        client.getTransportClient().close();
    }

    public void createMapping(String indices,String mappingType)throws Exception{
        new XContentFactory();
        XContentBuilder builder=XContentFactory.jsonBuilder()
                .startObject()
                .startObject(indices)
                .startObject("properties")
                .startObject("id").field("type", "integer").field("store", "false").endObject()
                .startObject("orderName").field("type", "string").field("store", "false").endObject()
//                .startObject("orderName").field("type", "string").field("store", "yes").field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").endObject()
                .startObject("createTime").field("type", "date").field("store", "false").endObject()
                .startObject("desc").field("type", "string").field("store", "false").endObject()
//                .startObject("desc").field("type", "string").field("store", "yes").field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").endObject()
                .endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(indices).type(mappingType).source(builder);
        client.getTransportClient().admin().indices().putMapping(mapping).actionGet();
//        client.getTransportClient().close();
    }

    @Override
    public BulkResponse createBlukIndex(String index, String type, Order order) throws IOException {
        BulkRequestBuilder bulkRequest = client.getTransportClient().prepareBulk();
        bulkRequest.add(client.getTransportClient().prepareIndex(index, type)
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id", order.getId())
                        .field("orderName", order.getOrderName())
                        .field("createTime", new Date())
                        .field("desc", order.getDesc())
                        .endObject()
                )
        );
        BulkResponse response = client.getTransportClient().bulk(bulkRequest.request())
                .actionGet();
        return response;
    }

    @Override
    public IndexResponse createIndex(String index, String type,String jsondata) {
        IndexResponse response = client.getTransportClient().prepareIndex(index, type)
                .setSource(jsondata)
                .execute()
                .actionGet();
        return response;
    }

    @Override
    public void deleteIndex(String index) {
        DeleteRequest deleteRequest = new DeleteRequest(index);
        DeleteRequestBuilder builder = client.getTransportClient().prepareDelete();
        client.getTransportClient().delete(deleteRequest);
        client.close();
    }

    @Override
    public List<Medicine> search(QueryBuilder queryBuilder, String indexname, String type) throws IOException {
        List<Medicine> list = new ArrayList<Medicine>();
        SearchResponse searchResponse = client.getTransportClient().prepareSearch(indexname).setTypes(type)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if(searchHists.length>0){
            for(SearchHit hit:searchHists){
                Integer id = (Integer)hit.getSource().get("id");
                String name =  (String) hit.getSource().get("name");
                String function =  (String) hit.getSource().get("funciton");
                list.add(new Medicine(id, name, function));
            }
        }
        return list;
    }


    @Override
    public List<Medicine> pageByFrom(QueryBuilder queryBuilder, String index, String type, Integer pageNo, Integer pageSize) {
        List<Medicine> list = new ArrayList<Medicine>();
        SearchResponse searchResponse = client.getTransportClient().prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder)
                .setFrom(pageNo)
                .setSize(pageSize)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if(searchHists.length>0){
            for(SearchHit hit:searchHists){
                Integer id = (Integer)hit.getSource().get("id");
                String name =  (String) hit.getSource().get("name");
                String function =  (String) hit.getSource().get("function");
                list.add(new Medicine(id, name, function));
            }
        }
        return list;
    }

    @Override
    public List<Medicine> pageByScroll(QueryBuilder queryBuilder, String index, String type, Integer pageNo, Integer pageSize) {
        List<Medicine> list = new ArrayList<Medicine>();
        // 设置Scroll参数,执行查询并返回结果
        SearchResponse scrollResp = client.getTransportClient().prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.SCAN)
                .setScroll(new TimeValue(20000))
                .setSize(100).execute().actionGet();  //100 hits per shard will be returned for each scroll
        //Scroll until no hits are returned
        while (true) {
            long count = scrollResp.getHits().getTotalHits();//第一次不返回数据
            for(int i=0,sum=0; sum<count; i++){
                scrollResp = client.getTransportClient().prepareSearchScroll(scrollResp.getScrollId())
                        .setScroll(TimeValue.timeValueMinutes(8))
                        .execute().actionGet();
                sum += scrollResp.getHits().hits().length;
                System.out.println("总量"+count+" 已经查到"+sum);
            }


            for (SearchHit hit : scrollResp.getHits()) {
                Integer id = (Integer)hit.getSource().get("id");
                String name =  (String) hit.getSource().get("name");
                String function =  (String) hit.getSource().get("function");
                list.add(new Medicine(id, name, function));
            }
            scrollResp = client.getTransportClient().prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }

        return list;
    }


    @Override
    public List<Medicine> sortByFrom(QueryBuilder queryBuilder, String index, String type, Integer pageNo, Integer pageSize, String sort) {
        List<Medicine> list = new ArrayList<Medicine>();
        SearchResponse searchResponse = client.getTransportClient().prepareSearch(index).setTypes(type)
                .addSort(SortBuilders.fieldSort(sort))
                .setSearchType(SearchType.DEFAULT)
                .setQuery(queryBuilder)
                .setFrom(pageNo)
                .setSize(pageSize)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if(searchHists.length>0){
            for(SearchHit hit:searchHists){
                Integer id = (Integer)hit.getSource().get("id");
                String name =  (String) hit.getSource().get("name");
                String function =  (String) hit.getSource().get("function");
                list.add(new Medicine(id, name, function));
            }
        }
        return list;
    }


}
