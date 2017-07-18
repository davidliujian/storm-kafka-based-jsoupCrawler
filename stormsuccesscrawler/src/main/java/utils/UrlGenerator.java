package utils;

import com.digitalpebble.stormcrawler.protocol.ProtocolFactory;
import com.digitalpebble.stormcrawler.util.StringTabScheme;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 31357 on 2017/7/6.
 */
public class UrlGenerator {

    public static Map<Integer, String> urlGet(String url, String hrefTag) {
        Map<Integer, String> slinks;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select(hrefTag);
            slinks = new HashMap<Integer, String>();
            int i = 0;
            for (Element link : links) {
                String u = link.attr("abs:href");
                if (!slinks.containsValue(u) && exists(u))
                    slinks.put(++i, u);
            }
        } catch (Throwable e) {
            String errorMessage = "Exception while parsing " + url + ": " + e;
            System.out.println(errorMessage);
            return null;
        }
        return slinks;
    }

    static boolean exists(String URLName) {

        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(URLName)
                    .openConnection();

           /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            if (con.getResponseCode() != 200) return false;
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
//            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        UrlGenerator urlg = new UrlGenerator();
        Map<Integer, String> urllists = urlg.urlGet("http://news.ifeng.com/mil/", "a[href~=^http://news.ifeng.com/a/2017.*0.shtml$]");
        System.out.println("output"+urllists.size());
        for (int i = 1; i <= urllists.size(); i++) {
            System.out.println("urls are these " + urllists.get(i));
        }

    }


}

