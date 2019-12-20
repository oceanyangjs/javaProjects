import mapper.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.Category;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Main {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testAdd() {
        Category category = new Category();
        category.setName("new Category");
        categoryMapper.add(category);
    }

    @Test
    public void testList() {
        System.out.println(categoryMapper);
        List<Category> cs=categoryMapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
