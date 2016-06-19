package com.abin.lee.search.service.factory.elasticsearch;

import com.abin.lee.search.model.Medicine;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-30
 * Time: 下午11:32
 * To change this template use File | Settings | File Templates.
 */
public class ElasticSearchUtil {
    /**
     * 实现将实体对象转换成json对象
     * @param medicine    Medicine对象
     * @return
     */
    public static String obj2JsonData(Medicine medicine){
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
                    .field("id",medicine.getId())
                    .field("name", medicine.getName())
                    .field("funciton",medicine.getFunction())
                    .endObject();
            jsonData = jsonBuild.string();
            System.out.println(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
