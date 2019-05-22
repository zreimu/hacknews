package com.zhandi.hackernews.dao;

import com.zhandi.hackernews.model.ItComment;
import com.zhandi.hackernews.model.ItNews;
import com.zhandi.hackernews.model.ItNewsVos;
import com.zhandi.hackernews.model.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItNewsDao {
    List<ItNewsVos> selectNews();
    ItNews selectitNewsinfoByid(@Param("id")String id);

    Type selectitNewsTypeByid(@Param("id")String id);

    Type selectitNewsType();
    int additComment(@Param("itComment") ItComment itComment);
    List<ItComment> selectitComments(@Param("id")String id);

    void updateitCommentCount(@Param("id")String id);
    void updateLikeCount(String ItNewsId);
    void updateDisLikeCount(String ItNewsId);

    List<ItNewsVos> selectjNews();

    List<ItNewsVos> selectpNews();

    List<ItNewsVos> selectcNews();

    List<ItNewsVos> selectmNews();
}
