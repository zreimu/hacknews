package com.cskaoyan.hackernews.service;

import com.cskaoyan.hackernews.model.*;

import java.util.List;

public interface HacknewsService {
    User selectByName(User user);
     int addUser(User user) ;
    User selectUser(User user);
    User selectUserinfo(String id);
    int addNews(News news);
    List<Vos> selectAllNews();
    int addComment(Comment comment);
   News selectNewsinfo(String id);
    List<CommentVo> selectComments(String id);

    void updataCommentCount(String id);
}
