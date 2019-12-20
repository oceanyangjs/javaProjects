package com.ocean;

import com.ocean.mapper.CategoryMapper;
import com.ocean.mapper.UserMapper;
import com.ocean.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMybatisImproXml {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session=sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        List<User> ls = mapper.listUser();

        for (User user:ls){
            System.out.println(user.getName());
        }
    }
}
