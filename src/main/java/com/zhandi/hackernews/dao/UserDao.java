package com.zhandi.hackernews.dao;

import com.zhandi.hackernews.model.News;
import com.zhandi.hackernews.model.User;
import com.zhandi.hackernews.model.Vos;
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

    void removeUserById(int id2);

    List<Vos> queryAllNewsOfUserById(int id);

    void updateuser(String id, User user);
}
