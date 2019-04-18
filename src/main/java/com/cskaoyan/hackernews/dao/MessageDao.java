package com.cskaoyan.hackernews.dao;

import com.cskaoyan.hackernews.model.Message;
import com.cskaoyan.hackernews.model.MessageVo;
import com.cskaoyan.hackernews.model.Msg;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

public interface MessageDao {
    int addMessage(@Param("id") int id, @Param("toId")int toId, @Param("content") String content, @Param("date") Date date);
    List<Message> findAllMessage();
    List<MessageVo> findUserMessageByUserId(@Param("id")int id);

    List<Msg> findUserMessageByconversationId(@Param("conversationId")String conversationId, @Param("conversationId2") String conversationId2);

}
