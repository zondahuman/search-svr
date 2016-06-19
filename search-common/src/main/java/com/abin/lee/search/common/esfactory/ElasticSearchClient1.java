package com.abin.lee.search.common.esfactory;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-27
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class ElasticSearchClient1 {
    static TransportClient client;

    static{
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch").put("client.transport.sniff", true).build();
        Client client = null;
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.75"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.240"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.77"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("client="+client);
    }

    public static void close() {
        client.close();
    }

}
