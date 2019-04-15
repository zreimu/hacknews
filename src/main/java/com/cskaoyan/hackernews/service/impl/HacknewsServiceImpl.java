package com.cskaoyan.hackernews.service.impl;

import com.cskaoyan.hackernews.dao.CommentDao;
import com.cskaoyan.hackernews.dao.NewsDAO;
import com.cskaoyan.hackernews.dao.UserDao;
import com.cskaoyan.hackernews.model.*;
import com.cskaoyan.hackernews.service.HacknewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

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
        return userDao.selectAllNews();
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
    public void updataCommentCount(String id) {
        newsDao.updataCommentCount(id);
    }
}
