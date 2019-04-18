package com.cskaoyan.hackernews.service.impl;

import com.cskaoyan.hackernews.dao.CommentDao;
import com.cskaoyan.hackernews.dao.MessageDao;
import com.cskaoyan.hackernews.dao.NewsDAO;
import com.cskaoyan.hackernews.dao.UserDao;
import com.cskaoyan.hackernews.model.*;
import com.cskaoyan.hackernews.service.HacknewsService;
import com.cskaoyan.hackernews.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class HacknewsServiceImpl implements HacknewsService {
    @Autowired
    private NewsDAO newsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private MessageDao messageDao;
    @Override
    public User selectByName(User user) {
        return userDao.selectByName(user);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User selectUser(User user) {
        return userDao.selectUser(user);
    }

    @Override
    public User selectUserinfo(String id) {
        return userDao.selectUserinfoByid(id);
    }

    @Override
    public int addNews(News news) {
        return userDao.addNews(news);
    }

    @Override
    public List<Vos> selectAllNews() {

        List<Vos> vos= userDao.selectAllNews();
        Jedis jedis=JedisUtils.getJedisFromPool();
        for(Vos vo:vos){
            int newsId = vo.getNews().getId();
            String likeNews = "likeNews" + newsId;
            vo.setLike(vo.getNews().getLikeCount());
            Long scard = jedis.scard(likeNews);

        }
        return  vos;
    }

    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public News selectNewsinfo(String id) {
        return newsDao.selectNewsinfoByid(id);
    }

    @Override
    public List<CommentVo> selectComments(String id) {
        LinkedList<CommentVo> commentVos=new LinkedList<>();
        List<Comment> comments=commentDao.selectComments(id);
        for(Comment comment: comments){
            CommentVo commentVo=new CommentVo();
            commentVo.setUser(userDao.selectUserinfoByid(String.valueOf(comment.getUserId())));
            commentVo.setComment(comment);
            commentVos.addLast(commentVo);

        }

        return commentVos ;
    }

    @Override
    public void updateCommentCount(String id) {
        newsDao.updateCommentCount(id);
    }

    @Override
    public int addMessage(int id, String toName, String content) {
        Date date = new Date();
        User toUser= userDao.findUserByname(toName);
        if(toUser!=null) {
            int toId = toUser.getId();
            if (id <= toId) {
                int i = messageDao.addMessage(id, toId, content, date);
            }else if(toId<id){
                int i = messageDao.addMessage(toId, id, content, date);
            }
        }
        return 0;

    }

    @Override
    public List<MessageVo> findUserMessageByUserId(int id) {
        List<MessageVo> userMessageByUserId = messageDao.findUserMessageByUserId(id);
        return userMessageByUserId;
    }

    @Override
    public List<Msg> findMessageByconversationId(String conversationId) {
        String[] split = conversationId.split("\\_");
        String conversationId2=split[1]+"_"+split[0];
        List<Msg> messageList= messageDao.findUserMessageByconversationId(conversationId,conversationId2);
        for (Msg msg : messageList) {
            msg.setUserId(msg.getUser().getId());
            msg.setHeadUrl(msg.getUser().getHeadUrl());
        }
        return messageList;
    }

    @Override
    public String like(int id, String newsId) {
        Jedis jedis= JedisUtils.getJedisFromPool();
        String sid=String.valueOf(id);
        String like="like"+newsId;
        String dislike="dislike"+newsId;
        Boolean dislike1=jedis.sismember(like,sid);
        if(!dislike1){
            jedis.sadd(like,sid);
        }

        Boolean sismember=jedis.sismember(dislike,sid);
        if(sismember){
            jedis.srem(dislike,sid);
        }
        long scard= jedis.scard(like);
        String s=String.valueOf(jedis.scard(like));
        jedis.close();
        newsDao.updateLikeCount(newsId);
        return s;
    }

    @Override
    public String dislike(int id, String newsId) {
        Jedis jedis= JedisUtils.getJedisFromPool();
        String sid=String.valueOf(id);
        String like="like"+newsId;
        String dislike="dislike"+newsId;
        Boolean dislike1=jedis.sismember(dislike,sid);
        if(!dislike1){
            jedis.sadd(dislike,sid);
        }
        Boolean sismember=jedis.sismember(like,sid);
        if(sismember){
            jedis.srem(like,sid);
        }
        String s=String.valueOf(jedis.scard(like));
        jedis.close();
        newsDao.updateDisLikeCount(newsId);
      return s;
    }

    @Override
    public int selectLikeCount(String newsId) {
        return newsDao.selectLikeCount(newsId);
    }

}
