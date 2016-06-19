package com.abin.lee.search.service.elasticsearch.test;

import com.abin.lee.search.model.Medicine;
import com.abin.lee.search.service.ElasticSearchService;
import com.alibaba.dubbo.common.json.JSON;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/elasticsearch-service.xml" })
public class DeleteIndexTest {
    final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Resource
    ElasticSearchService elasticSearchService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDeleteIndex() throws IOException {
        LOGGER.info("-------------start-------------");
        //查询条件
        String index = "indexmedicine";
        elasticSearchService.deleteIndex(index);
//        LOGGER.info("medicineList=" + JSON.json(medicineList));
//        LOGGER.info("indexResponse=" + indexResponse.getId());
        LOGGER.info("-------------end-------------");
    }
	

}
