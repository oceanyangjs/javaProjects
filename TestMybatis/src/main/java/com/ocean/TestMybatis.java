package com.ocean;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.ocean.pojo.Order;
import com.ocean.pojo.OrderItem;
import com.ocean.pojo.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ocean.pojo.Category;

public class TestMybatis {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session=sqlSessionFactory.openSession();
//        listCategory(session);
//        Category ct1 = new Category();
//        ct1.setName("test1");
//        session.insert("insertCategory",ct1);
//        listCategory(session);
//        Category ct2 = new Category();
//        ct2.setName("test2");
//        ct2.setId(3);
//        session.update("updateCategory",ct2);
//        listCategory(session);
//        Category ct3 = new Category();
//        ct3.setId(3);
//        session.delete("deleteCategory",ct3);
//        listCategory(session);
//        Category ct4 = new Category();
//        ct4.setId(2);
//        Category ct5 = session.selectOne("queryCategory",ct4);
//        System.out.println(ct5);
//        listCategory(session);
//        String str = "test";
//        List<Category> ls = session.selectList("queryListCategory",str);
//        for (Category c : ls) {
//            System.out.println(c.getName());
//        }
//        System.out.println("我是赤裸裸的分割线！");
//        Map<String,Object> mp = new HashMap<>();
//        mp.put("id",4);
//        mp.put("name","test");
//        Category ll = session.selectOne("queryListMulCategory",mp);
//        System.out.println(ll);

//        一对多查询
        List<Category> cs = session.selectList("listCategoryWithProduct");
        for (Category c : cs) {
            System.out.println(c);
            List<Product> ps = c.getProducts();
            for (Product p : ps) {
                System.out.println("\t"+p);
            }
        }

//        多对一查询
        List<Product> ps = session.selectList("listProduct");
        for (Product p : ps) {
            System.out.println(p+" 对应的分类是 \t "+ p.getCategory());
        }

//        多对多查询
        List<Order> os = session.selectList("listOrder");
        for (Order o : os) {
            System.out.println(o.getCode());
            List<OrderItem> ois= o.getOrderItems();
            for (OrderItem oi : ois) {
                System.out.format("\t%s\t%f\t%d%n", oi.getProduct().getName(),oi.getProduct().getPrice(),oi.getNumber());
            }
        }
        session.commit();
        session.close();
    }

    private static void listCategory(SqlSession session){
        List<Category> cs=session.selectList("listCategory");
        for (Category c : cs) {
            System.out.println(c.getName());
        }
        System.out.println("我是赤裸裸的分割线！");
    }
}
