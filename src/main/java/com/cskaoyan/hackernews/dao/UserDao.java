package com.cskaoyan.hackernews.dao;

import com.cskaoyan.hackernews.model.News;
import com.cskaoyan.hackernews.model.User;
import com.cskaoyan.hackernews.model.Vos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User selectByName(@Param("user") User user);
    int addUser(@Param("user") User user);
    User selectUser(@Param("user")User user);
    int addNews(@Param("news") News news);
    List<Vos> selectAllNews();
    User findUserByname(@Param("name") String toName);
    User selectUserinfoByid(@Param("id")String id);

}
