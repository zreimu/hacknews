package com.cskaoyan.hackernews.dao;

import com.cskaoyan.hackernews.model.Comment;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface CommentDao {
    List<Comment> selectComments(@Param("id")String id);
    int addComment(@Param("comment") Comment comment);
    void updataCommentCount(@Param("id") String id);
}
