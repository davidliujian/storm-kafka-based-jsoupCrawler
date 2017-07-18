package com.digitalpebble.stormcrawler;

/**
 * Licensed to DigitalPebble Ltd under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * DigitalPebble licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import bolt.JsoupParserBolt;
import org.apache.storm.Config;
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

/**
 * Dummy topology to play with the spouts and bolts
 */
public class CrawlTopology extends ConfigurableTopology {

    private String topic="test2";
    private ToKafka tokafka = new ToKafka(topic);

    public static void main(String[] args) throws Exception {
        String[] argxx = {"-config", "crawler-conf.yaml", "-local"};
        ConfigurableTopology.start(new CrawlTopology(), argxx);
    }

    @Override
    protected int run(String[] args) {

        //    Xpath XPATH = new Xpath("");

        String URL = "http://news.ifeng.com/mil/";

        HashMap<String,HashMap<String,String>> urls=new HashMap<String,HashMap<String, String>>();

//        String titleTag="h1";
//        String publishtimeTag="span[itemprop=datePublished]";
//        String sourceTag="span[class=ss03]";
//        String contentTag="#main_content>p";

        String titleTag="//h1/text()";
        String publishtimeTag="//span[@itemprop=datePublished]/text()";
        String sourceTag="//span[@class=ss03]/text()";
        String contentTag="//div[@id=main_content]/p/text()";

        String hrefTag="a[href~=^http://news.ifeng.com/a/2017.*0.shtml$]";

        HashMap<String,String> xpath=new HashMap<String,String>();
        xpath.put("titleTag",titleTag);
        xpath.put("publishtimeTag",publishtimeTag);
        xpath.put("sourceTag",sourceTag);
        xpath.put("contentTag",contentTag);
        //       xpath.put("hrefTag",hrefTag);

        //     Date now = new Date();
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
        //    map.put("zookeeper.connect", "localhost:2181");
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig),5);
        builder.setBolt("bolt", new JsoupParserBolt(),5).shuffleGrouping("spout");
        conf.setMaxTaskParallelism(3);

        return submit("crawl", conf, builder);


    }
}