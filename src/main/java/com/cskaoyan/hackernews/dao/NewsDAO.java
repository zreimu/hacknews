package com.cskaoyan.hackernews.dao;

import com.cskaoyan.hackernews.model.Comment;
import com.cskaoyan.hackernews.model.News;
import com.cskaoyan.hackernews.model.Vos;
import org.apache.ibatis.annotations.Param;

public interface NewsDAO {
    News selectNews(@Param("news")News news);
   News selectNewsinfoByid(@Param("id")String id);
    void updataCommentCount(@Param("id") String id);
}
