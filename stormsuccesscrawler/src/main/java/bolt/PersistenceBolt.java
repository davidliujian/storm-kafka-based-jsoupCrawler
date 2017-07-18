package bolt;

import bean.Contents;
import bean.Urls;
import dao.ContentsOperate;
import dao.UrlOperate;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * Created by david on 17-7-12.
 */
public class PersistenceBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String url = (String) input.getValueByField("url");
        System.out.println(url);
        //插入urls数据库
        UrlOperate.insert(new Urls(url));

        String pub_time = (String) input.getValueByField("publishTime");
        String source = (String) input.getValueByField("source");
        String content = (String) input.getValueByField("allContent");
        String identifier ="aB"; //(String) input.getValueByField("identifier");
        String title = (String) input.getValueByField("title");
    //    System.out.println(pub_time+"  "+source+"  "+content+ "  "+identifier+"   "+title);

        if(content==null){
            content=" ";
        }
        if(pub_time==null){
            pub_time=" ";
        }
        if(source==null){
            source=" ";
        }
        if(title==null){
            title=" ";
        }

        ContentsOperate.insert(new Contents(content,title, source, pub_time, url, identifier));

        collector.emit(new Values(url, title, pub_time, source,content,identifier));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("url", "title", "publishTime", "source","allContent","identifier"));
    }
}
