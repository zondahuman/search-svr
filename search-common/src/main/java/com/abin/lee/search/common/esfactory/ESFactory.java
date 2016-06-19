package com.abin.lee.search.common.esfactory;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-11-23
 * Time: 下午10:43
 * To change this template use File | Settings | File Templates.
 */
public class ESFactory {
    private static JestHttpClient client;

    private ESFactory() {

    }

    public synchronized static JestHttpClient getClient() {
        if (client == null) {
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(new HttpClientConfig.Builder(
                    "http://172.30.11.240:9200").multiThreaded(true).build());
            client = (JestHttpClient) factory.getObject();
        }
        return client;
    }

    public static void main(String[] args) {
        JestHttpClient client = ESFactory.getClient();
        System.out.println(client.getAsyncClient());
        System.out.println(client.getHttpClient());
        client.shutdownClient();
    }
}
