package com.cskaoyan.hackernews.service.impl;

import com.cskaoyan.hackernews.dao.NewsDAO;
import com.cskaoyan.hackernews.model.User;
import com.cskaoyan.hackernews.service.HacknewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HacknewsServiceImpl implements HacknewsService {
    @Autowired
    private NewsDAO newsDao;
    @Override
    public User selectUserByName(User user) {
        return newsDao.selectByUsername(user);
    }

    @Override
    public int addUser(User user) {
        return newsDao.addUser(user);
    }
}
