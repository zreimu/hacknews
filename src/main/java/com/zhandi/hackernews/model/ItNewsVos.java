package com.zhandi.hackernews.model;

public class ItNewsVos {

    ItNews itnews;
    int like;
    Type type;
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ItNews getItnews() {
        return itnews;
    }

    public void setItnews(ItNews itnews) {
        this.itnews = itnews;
    }

    @Override
    public String toString() {
        return "ItNewsVos{" +
                "itnews=" + itnews +
                ", like=" + like +
                ", type=" + type +
                '}';
    }
}
