package spout;


import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.nio.ByteBuffer;
import java.util.List;

public class MessageScheme implements Scheme {

    private String xpath  =" {\"titleTag\": \"//h1/text()\",\"publishtimeTag\": \"//span[@itemprop=datePublished]/text()\",\"sourceTag\": \"//span[@class=ss03]/allText()\",\"contentTag\": \"//div[@id=main_content]/p/allText()|//li/p[@class=photoDesc]/text()\"}";                 // Xpath.getXpath();

    public List<Object> deserialize(ByteBuffer byteBuffer) {
        System.out.println("执行到反序列化了");
        byte[] content = new byte[byteBuffer.limit()];//byteBuffer.array();
        System.out.println(content.length);
        // 从ByteBuffer中读取数据到byte数组中
        byteBuffer.get(content);

        String msg = new String(content);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(msg);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        if(msg != null){
            return new Values(msg,xpath);
        }
        else{
            return null;
        }

    }

    public Fields getOutputFields() {
        //return new Fields("msg");
        return new Fields("url","xpath");
    }
}
