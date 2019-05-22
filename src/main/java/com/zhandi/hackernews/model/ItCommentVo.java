package com.zhandi.hackernews.model;

public class ItCommentVo {
    User user;
    ItComment itComment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItComment getItComment() {
        return itComment;
    }

    public void setItComment(ItComment itComment) {
        this.itComment = itComment;
    }

    @Override
    public String toString() {
        return "ItCommentVo{" +
                "user=" + user +
                ", itComment=" + itComment +
                '}';
    }
}
