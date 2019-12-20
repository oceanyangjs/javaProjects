package com.ocean.mapper;

import com.ocean.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {

//    @Select("select * from category_ ")
    public List<Category> findAll();

//    插入数据
//    @Insert("insert into category_ ( name ) values ( #{name} )")
    public int save(Category category);

//    @Delete(" delete from category_ where id= #{id} ")
    public void delete(int id);

//    @Select("select * from category_ where id= #{id} ")
    public Category get(int id);

//    @Update("update category_ set name=#{name} where id=#{id} ")
    public int update(Category category);
}
