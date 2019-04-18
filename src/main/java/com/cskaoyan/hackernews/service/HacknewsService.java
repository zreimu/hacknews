package com.cskaoyan.hackernews.service;

import com.cskaoyan.hackernews.model.*;

import java.util.List;

public interface HacknewsService {
    //用户模块
    User selectByName(User user);
     int addUser(User user) ;
    User selectUser(User user);
    User selectUserinfo(String id);
    //新闻模块
    int addNews(News news);
    List<Vos> selectAllNews();
   News selectNewsinfo(String id);
   //评论模块
    int addComment(Comment comment);
   List<CommentVo> selectComments(String id);
    void updateCommentCount(String id);
    //站内私信模块
    public int addMessage(int id, String toName, String content);
    List<MessageVo> findUserMessageByUserId(int id);
    List<Msg> findMessageByconversationId(String conversationId);
    //点赞功能
     String like(int id,String newsId);
     String dislike(int id, String newsId);
     int selectLikeCount(String newsId);
}
