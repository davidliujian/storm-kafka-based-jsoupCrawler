package bean;

import com.google.gson.JsonObject;
import utils.HttpRequestUtil;

/**
 * Created by david on 17-7-12.
 */
public class Xpath {
    static JsonObject json = null;
    static String xpath = null;
    public Xpath(String url){
        this.json = HttpRequestUtil.getXpath(url);
        this.xpath = this.json.toString();
    }


    public static  String  getXpath(){
        return xpath;
    }

}
