package com.example.student;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public final class MyBatisUtil {
    private static final SqlSessionFactory SQL_SESSION_FACTORY = buildSqlSessionFactory();

    private MyBatisUtil() {
    }

    private static SqlSessionFactory buildSqlSessionFactory() {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(reader, "development", DbConfig.myBatisProperties());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to build SqlSessionFactory", e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return SQL_SESSION_FACTORY;
    }
}
