package com.example.biz.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class SqlSessionFactoryBean {
    private static SqlSessionFactory sessionFactory = null;

    static {
        try {
            if (sessionFactory == null) {
                Reader reader = Resources.getResourceAsReader("sql-map-config.xml");
                sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSessionFactory() {
        return sessionFactory.openSession();
    }
}
