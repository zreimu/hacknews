package com.cskaoyan.hackernews.model;

public class CommentVo {
    User user;
    Comment comment;

    @Override
    public String toString() {
        return "CommentVo{" +
                "user=" + user +
                ", comment=" + comment +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
