package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by david on 17-7-12.
 */
public class MyBatisUtil {
    public static SqlSessionFactory getSqlSessionFactory(){
        String resource  = "mybatis-conf.xml";
        SqlSessionFactory factory = null;
        try {
            InputStream is = Resources.getResourceAsStream(resource);
            factory = new SqlSessionFactoryBuilder().build(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return factory;
    }

    public static SqlSession getSqlSession(){
        return getSqlSessionFactory().openSession();
    }

    public static SqlSession getSqlSession(boolean isAutoCommit) {
        return getSqlSessionFactory().openSession(isAutoCommit);
    }
}
