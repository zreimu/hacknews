package com.zhandi.hackernews.model;

public class Vos {
    User user;
    News news;
    int like;

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

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
                ", like=" + like +
                '}';
    }
}
