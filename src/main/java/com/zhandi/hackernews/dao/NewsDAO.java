package com.zhandi.hackernews.dao;

import com.zhandi.hackernews.model.News;
import org.apache.ibatis.annotations.Param;

public interface NewsDAO {
    News selectNews(@Param("news")News news);
   News selectNewsinfoByid(@Param("id")String id);
    void updateCommentCount(@Param("id") String id);
    void updateLikeCount(String newsId);
    void updateDisLikeCount(String newsId);
    int selectLikeCount(String newsId);
}
