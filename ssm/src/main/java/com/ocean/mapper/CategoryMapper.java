package com.ocean.mapper;
 
import java.util.List;

import com.ocean.pojo.Category;
import com.ocean.util.Page;

public interface CategoryMapper {
 
      
    public int add(Category category);
       
      
    public void delete(int id);  
       
      
    public Category get(int id);  
     
      
    public int update(Category category);   
       
      
    public List<Category> list();

    List<Category> list(Page page);

    public int total();
      
}