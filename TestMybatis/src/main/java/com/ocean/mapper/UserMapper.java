package com.ocean.mapper;

import com.ocean.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {
    public List<User> listUser();
}
