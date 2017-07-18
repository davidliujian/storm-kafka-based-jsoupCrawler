package dao;

import bean.Contents;
import org.apache.ibatis.session.SqlSession;
import utils.MyBatisUtil;

/**
 * Created by david on 17-7-12.
 */
public class ContentsOperate {
    public static void insert(Contents con){
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        String statement = "mapping.contentsmapper.addContent";
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(con.getContent());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        //执行插入
        int retResult = sqlSession.insert(statement,con);
        sqlSession.close();
    }
}
