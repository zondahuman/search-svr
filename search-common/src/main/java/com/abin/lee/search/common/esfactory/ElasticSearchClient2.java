package com.abin.lee.search.common.esfactory;

import com.google.common.collect.Maps;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-27
 * Time: 下午7:30
 * To change this template use File | Settings | File Templates.
 */
public class ElasticSearchClient2 {
    private static Client client;

    public static Client getInstance() throws UnknownHostException {
        // 配置你的es,现在这里只配置了集群的名,默认是elasticsearch,跟服务器的相同
        Map<String, String> settingMap = Maps.newHashMap();
//        settingMap.put("node.client", "false");
//        settingMap.put("node.data", "true");
//        settingMap.put("node.local", "true");
        settingMap.put("cluster.name", "elasticsearch");
        // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
        settingMap.put("client.transport.sniff", "true");
        Settings settings = Settings.settingsBuilder()
                .put(settingMap).build();
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.75"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.240"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.77"), 9300));
        return client;
    }

    public static void close() {
        client.close();
    }
    public static void main(String[] args) throws UnknownHostException {
        System.out.println("client="+ElasticSearchClient2.getInstance());
    }
}
