package bolt;

import bean.Urls;
import dao.UrlOperate;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 17-7-12.
 */
public class CheckRepeatBolt extends BaseBasicBolt{
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String xpath = (String) input.getValueByField("xpath");
        String url = (String) input.getValueByField("url");
        List<Urls> list =   UrlOperate.get();

        if(!list.contains(new Urls(url))){
    //        System.out.println(list.size()+"        "+url);
            collector.emit(new Values(url,xpath));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("url","xpath"));
    }
}
