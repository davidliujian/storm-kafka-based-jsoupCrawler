package bolt;

import com.google.gson.JsonObject;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;
import utils.parseUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by david on 17-7-12.
 */
public class JsoupParserBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String url = (String) input.getValueByField("url");
        String xpath = (String) input.getValueByField("xpath");
        JsonObject json = parseUtil.parse(xpath);

        String titleTag= String.valueOf(json.get("titleTag")).replace("\"","");
        String publishtimeTag= String.valueOf(json.get("publishtimeTag")).replace("\"","");
        String sourceTag= String.valueOf(json.get("sourceTag")).replace("\"","");
        String contentTag= String.valueOf(json.get("contentTag")).replace("\"","");
        String identifier = String.valueOf(json.get("identifier"));

        System.out.println(titleTag+"   "+publishtimeTag+"   "+sourceTag+"   "+contentTag);
        //    String hrefTag="//a[href~=^http://news.ifeng.com/a/2017.*0.shtml$]";

        String title="";
        String publishTime="";
        String source="";
        String allContent="";
        StringBuffer Content = new StringBuffer();

        try {
            Document doc = Jsoup.connect(url).get();

            title = Xsoup.compile(titleTag).evaluate(doc).get();

            publishTime = Xsoup.compile(publishtimeTag).evaluate(doc).get();
            source = Xsoup.compile(sourceTag).evaluate(doc).get();
            List<String> contents= Xsoup.compile(contentTag).evaluate(doc).list();
            for(String s : contents){
                Content.append(s+"\n");
            }
            allContent = Content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(url+"  "+title+" "+publishTime+" "+source+" ");
        System.out.println(allContent);

        collector.emit(new Values(url, title, publishTime, source,allContent,identifier));

        Utils.sleep(500);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("url", "title", "publishTime", "source","allContent","identifier"));
    }
}
