package com.abin.lee.search.service.elasticsearch.test;

import com.abin.lee.search.model.Medicine;
import com.abin.lee.search.model.Order;
import com.abin.lee.search.service.ElasticSearchService;
import com.alibaba.dubbo.common.json.JSON;
import com.google.common.primitives.Longs;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/elasticsearch-service.xml" })
public class CreateIndexTest {
    final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Resource
    ElasticSearchService elasticSearchService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateIndex() throws IOException, InterruptedException {
        LOGGER.info("-------------start-------------");
//        List<String> jsondata = ElasticSearchFactory.getInitJsonData();
//        LOGGER.info("jsondata=" + JSON.json(jsondata));
        for(int i=0;i<10;i++){
//            Medicine medicine = new Medicine(i,"银花"+i,"功能：银花感冒颗粒"+i);
//            Medicine medicine = new Medicine(i,"银花"+i,"主治：银花感冒颗粒"+i);
            Medicine medicine = new Medicine(i,"针灸"+i,"浑身疼痛"+i);
            String indexName = "hospital";
            String type = "medicine";
            IndexResponse indexResponse = elasticSearchService.createIndex(indexName, type, JSON.json(medicine));
            LOGGER.info("indexResponse.getId()=" + indexResponse.getId());
            LOGGER.info("indexResponse.getIndex()=" + indexResponse.getIndex());
            LOGGER.info("indexResponse.getType()=" + indexResponse.getType());
            LOGGER.info("indexResponse.getVersion()=" + indexResponse.getVersion());
//        LOGGER.info("indexResponse=" + indexResponse.getId());
            LOGGER.info("-------------end-------------");
        }
        Thread.sleep(1_0000L);

    }
    @Test
    public void testCreateBlukIndex() throws IOException, InterruptedException {
        LOGGER.info("-------------start-------------");
        for(int i=0;i<100;i++){
            Order order = new Order(i,"酒店"+i,"入住大床房"+i,new Date());
            String indexName = "business";
            String type = "order";
            BulkResponse blukResponse = elasticSearchService.createBlukIndex(indexName, type, order);
            LOGGER.info("blukResponse.getItems()=" + blukResponse.getItems());
            LOGGER.info("blukResponse.getTook()=" + blukResponse.getTook());
            LOGGER.info("blukResponse.getTookInMillis()=" + blukResponse.getTookInMillis());
//        LOGGER.info("indexResponse=" + indexResponse.getId());
            LOGGER.info("-------------end-------------");
        }
        Thread.sleep(1_0000L);

    }
	

}
