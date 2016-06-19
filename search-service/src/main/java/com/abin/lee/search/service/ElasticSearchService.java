package com.abin.lee.search.service;

import com.abin.lee.search.model.Medicine;
import com.abin.lee.search.model.Order;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.QueryBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-30
 * Time: 下午11:20
 * To change this template use File | Settings | File Templates.
 */
public interface ElasticSearchService {

    public void createCluster(String indices);

    public void createMapping(String indices,String mappingType)throws Exception;

    BulkResponse createBlukIndex(String indexname, String type, Order order) throws IOException;

    IndexResponse createIndex(String indexname, String type,String jsondata);

    void deleteIndex(String index);

    List<Medicine> search(QueryBuilder queryBuilder, String indexname, String type) throws IOException;

    public List<Medicine> pageByFrom(QueryBuilder queryBuilder, String index, String type, Integer pageNo, Integer pageSize);

    public List<Medicine> pageByScroll(QueryBuilder queryBuilder, String index, String type, Integer pageNo, Integer pageSize);

    public List<Medicine> sortByFrom(QueryBuilder queryBuilder, String index, String type, Integer pageNo, Integer pageSize, String sort);

}
