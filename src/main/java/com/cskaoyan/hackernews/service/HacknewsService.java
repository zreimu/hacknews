package com.cskaoyan.hackernews.service;

import com.cskaoyan.hackernews.model.User;

public interface HacknewsService {
    User selectUserByName(User user);
     int addUser(User user) ;
}
