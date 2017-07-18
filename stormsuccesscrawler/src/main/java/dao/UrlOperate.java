package dao;

import bean.Urls;
import org.apache.ibatis.session.SqlSession;
import utils.MyBatisUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 17-7-12.
 */
public class UrlOperate {
    public static void insert(Urls url){
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        String statement = "mapping.urlmapper.addUrl";

        //执行插入
        int retResult = sqlSession.insert(statement,url);
        sqlSession.close();
    }

    public static List<Urls> get(){
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        String statement = "mapping.urlmapper.getUrl";//映射sql的标识字符串
        //执行查询操作，将查询结果自动封装成List<User>返回
        List<Urls> lstUrls = sqlSession.selectList(statement);
        //使用SqlSession执行完SQL之后需要关闭SqlSession
        sqlSession.close();
        return lstUrls;
    }
}
