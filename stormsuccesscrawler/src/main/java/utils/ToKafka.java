package utils;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class ToKafka{

    private static kafka.javaapi.producer.Producer<Integer, String> producer;
    private final Properties props = new Properties();
    private String topic = "test12";

    public ToKafka(String topic) {
        Properties pro = new Properties(); // 定义相应的属性保存
        //    pro.setProperty("zookeeper.connect", "localhost:2181"); //这里根据实际情况填写你的zk连接地址
        pro.setProperty("metadata.broker.list", "localhost:9092"); //根据自己的配置填写连接地址
        pro.setProperty("serializer.class", "kafka.serializer.StringEncoder"); //填写刚刚自定义的Encoder类
        producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(pro));
        this.topic = topic;
    }

    public void send(String str){
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, str);
        producer.send(data);
    }

    public void close(){
        producer.close();
    }

}
