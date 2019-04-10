package com.cskaoyan.hackernews.model;

public class Vos {
    User user;
    News news;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "Vos{" +
                "user=" + user +
                ", news=" + news +
                '}';
    }
}
