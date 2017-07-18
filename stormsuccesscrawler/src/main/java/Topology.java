import bolt.CheckRepeatBolt;
import bolt.JsoupParserBolt;
import bolt.PersistenceBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import spout.MessageScheme;
import utils.ToKafka;
import utils.UrlGenerator;

import java.util.HashMap;
import java.util.Map;


public class Topology  {


    public static void main(String[] args) throws Exception {

        String topic="test908089";
        ToKafka tokafka = new ToKafka(topic);

        String URL = "http://finance.ifeng.com/money/";
//
//        String titleTag="//h1/text()";
//        String publishtimeTag="//span[@itemprop=datePublished]/text()";
//        String sourceTag="//span[@class=ss03]/text()";
//        String contentTag="//div[@id=main_content]/p/text()";
//
        String hrefTag="a[href~=^http://finance.ifeng.com/a/2017.*0.shtml$]";
//
//        HashMap<String,String> xpath=new HashMap<String,String>();
//        xpath.put("titleTag",titleTag);
//        xpath.put("publishtimeTag",publishtimeTag);
//        xpath.put("sourceTag",sourceTag);
//        xpath.put("contentTag",contentTag);

        Map<Integer,String> deriveFromStartingUrls = UrlGenerator.urlGet(URL,hrefTag);
        for (String u : deriveFromStartingUrls.values()) {
            System.out.println(u);
            tokafka.send(u);
        }
        tokafka.close();



        BrokerHosts brokerHosts = new ZkHosts("localhost:2181");
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, "/storm", "kafka");
        Config conf = new Config();
        Map<String, String> map = new HashMap<String, String>();

        map.put("metadata.broker.list", "localhost:9092");
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        //map.put("zookeeper.connect", "localhost:2181");
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig),5);
        builder.setBolt("checkrepeatbolt", new CheckRepeatBolt(),5).shuffleGrouping("spout");
        builder.setBolt("jsoupbolt", new JsoupParserBolt(),5).shuffleGrouping("checkrepeatbolt");
        builder.setBolt("persistentbolt", new PersistenceBolt(),5).shuffleGrouping("jsoupbolt");
        conf.setMaxTaskParallelism(3);
        conf.setDebug(true);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);

            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("crawler", conf, builder.createTopology());

//            Thread.sleep(10000);
//
//            cluster.shutdown();
        }
    }
}