package com.cskaoyan.hackernews.dao;

import com.cskaoyan.hackernews.model.User;
import org.apache.ibatis.annotations.Param;

public interface NewsDAO {
     User selectByUsername(@Param("user") User user);
     int addUser(@Param("user") User user);
}
