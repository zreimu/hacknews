package com.zhandi.hackernews.model;



public class MessageVo {
    User user;
    Conversation conversation;

    public MessageVo(User user, Conversation conversation) {
        this.user = user;
        this.conversation = conversation;
    }

    public MessageVo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public String toString() {
        return "MessageVo{" +
                "user=" + user +
                ", conversation=" + conversation +
                '}';
    }
}
