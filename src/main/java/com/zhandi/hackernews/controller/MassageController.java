package com.zhandi.hackernews.controller;

import com.zhandi.hackernews.model.MessageVo;
import com.zhandi.hackernews.model.Msg;
import com.zhandi.hackernews.model.User;
import com.zhandi.hackernews.service.HacknewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MassageController {

    @Autowired
    HacknewsService hacknewsService;
    @RequestMapping("/user/tosendmsg")
    public  String toSendMsg(HttpServletRequest request, Model model){
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath",contextPath);
        return "sendmsg";
    }

    @RequestMapping("/user/msg/addMessage")
    public  String addMessage(HttpServletRequest request, Model model, @RequestParam String toName, String content){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        int id = user.getId();
        int i =hacknewsService.addMessage(id,toName,content);
       return "redirect:/msg/list";
    }

    @RequestMapping("/msg/list")
    public  String msgList(HttpServletRequest request, Model model){

        String contextPath = request.getContextPath();
        model.addAttribute("contextPath",contextPath);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        List<MessageVo> messageVoList= hacknewsService.findUserMessageByUserId(user.getId());
        model.addAttribute("conversations" ,messageVoList);

        return "letter";
    }

    @RequestMapping("/msg/detail")
    public String letterDetail(HttpServletRequest request, Model model ,String conversationId){
        String contextPath = request.getContextPath();
        List<Msg> messageList= hacknewsService.findMessageByconversationId(conversationId);
        model.addAttribute("contextPath",contextPath);
        model.addAttribute("messages",messageList);
        return "letterDetail";
    }



}
