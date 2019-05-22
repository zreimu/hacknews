package com.zhandi.hackernews.service;

import com.zhandi.hackernews.model.*;

import java.util.List;

public interface HacknewsService {
    //用户模块
    User selectByName(User user);
     int addUser(User user) ;
    User selectUser(User user);
    User selectUserinfo(String id);
    //帖子模块
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
     //新闻模块
    ItNews selectitNewsinfo(String id);
    List<ItNewsVos> selectItNews();

    Type selectitTypeinfo(String id);

    int additComment(ItComment itComment);

    List<ItCommentVo> selectitComments(String id);

    void updateitCommentCount(String id);

    List<ItNewsVos> selectjNews();

    List<ItNewsVos> selectpNews();

    List<ItNewsVos> selectcNews();
    List<ItNewsVos> selectmNews();

    void removeUserById(int id2);

    List<Vos> queryAllNewsOfUserById(int id);

    void updateUser(String id, User user);

    Admin selectAdmin(Admin admin);
}
