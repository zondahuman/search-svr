package com.abin.lee.search.service.elasticsearch.test;

import com.abin.lee.search.service.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/elasticsearch-service.xml" })
public class CreateClusterTest {
    final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Resource
    ElasticSearchService elasticSearchService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateCluster() throws Exception {
        LOGGER.info("-------------start-------------");
        String index = "business";
        String type = "order";
        elasticSearchService.createCluster(index);
        LOGGER.info("-------------end-------------");
    }


}
