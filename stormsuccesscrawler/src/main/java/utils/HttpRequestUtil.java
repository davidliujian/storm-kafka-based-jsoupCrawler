package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by 31357 on 2017/7/9.
 */

public class HttpRequestUtil {

    /**
     * 发起http请求并获取结果
     * @param requestUrl 请求地址
     */
    public static JsonObject getXpath(String requestUrl){
        String res="";
        JsonObject object = null;
        StringBuffer buffer = new StringBuffer();
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
            if(200==urlCon.getResponseCode()){
                InputStream is = urlCon.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                while((str = br.readLine())!=null){
                    buffer.append(str);
                }
                br.close();
                isr.close();
                is.close();
                res = buffer.toString();
                JsonParser parse =new JsonParser();
                object = (JsonObject) parse.parse(res);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return object;
    }
    public static void main(String args [] ) {
        JsonObject res = null;
        res = getXpath("http://ip.taobao.com/service/getIpInfo.php?ip=63.223.108.42");
        System.out.println(res);
        String ss = String.valueOf(res.get("code"));
        System.out.println(res.get("code"));
        System.out.println(res.get("data"));
    }
}
