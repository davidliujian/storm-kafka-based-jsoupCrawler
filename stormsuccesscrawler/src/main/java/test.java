import bean.Urls;
import dao.UrlOperate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 17-7-12.
 */
public class test {
    public static void main(String args[]){
        List<Urls> list = UrlOperate.get();
//        ArrayList<Urls> arrlist = new ArrayList<Urls>();
//        arrlist.addAll(list);
        Urls url = new Urls("http://news.ifeng.com/a/20170712/51416395_0.shtml");
//        System.out.println(arrlist);

        if(list.contains(url)){
            System.out.println("包含！");
        }
    }
}
