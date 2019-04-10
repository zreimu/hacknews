package com.cskaoyan.hackernews.controller;

import com.cskaoyan.hackernews.model.User;
import com.cskaoyan.hackernews.model.Vos;
import com.cskaoyan.hackernews.service.HacknewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class hackcontorller {
    @Autowired
    private HacknewsService hacknewsService;
    List<Vos> list=new ArrayList<>();
    @RequestMapping("/")

    public String home (Model model, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath", contextPath);
        model.addAttribute("vos",list);
        return "home";
    }
     @RequestMapping("/reg")
     @ResponseBody
       public Map<String, Object> addUser(User user, String username, HttpSession session) {
       User b = hacknewsService.selectUserByName(user);
         System.out.println("b"+b);
         Map map=new HashMap();
         if (b == null) {
             hacknewsService.addUser(user);
             session.setAttribute("user",user);
             map.put("code",0);
             map.put("msgname","注册成功");
         } else {
             map.put("code",1);
             map.put("msgname","用户名已存在");
         }
         return map;
     }
    @RequestMapping("/login")

    public String login(User user){

        return "home";
    }
    @RequestMapping("/logout")

    public String logout(Model model, HttpServletRequest request,HttpSession session){
          session.removeAttribute("user");
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath", contextPath);
        model.addAttribute("vos",list);
        return "home";
    }
    }


