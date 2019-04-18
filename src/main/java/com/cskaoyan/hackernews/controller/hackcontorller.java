package com.cskaoyan.hackernews.controller;

import com.cskaoyan.hackernews.model.*;
import com.cskaoyan.hackernews.service.HacknewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@Controller
public class hackcontorller {
    @Autowired
    private HacknewsService hacknewsService;
    List<Vos> list=new ArrayList<>();
    @RequestMapping("/")

    public String home (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<Vos> newsList = hacknewsService.selectAllNews();
        session.setAttribute("vos",newsList);
        return "home";
    }
     @RequestMapping("/reg")
     @ResponseBody
       public Map<String, Object> addUser(User user,HttpServletRequest request,  HttpSession session) {
         String name=request.getParameter("username");
           user.setName(name);
       User b = hacknewsService.selectByName(user);
         Map map=new HashMap();
         if (b == null) {
             hacknewsService.addUser(user);
             session.setAttribute("user",user);
             map.put("code",0);
             map.put("msg","注册成功");
             return map;
         } else {
             map.put("code",1);
             map.put("msgname","用户名已存在");
             return map;
         }

     }
    @RequestMapping("login")
    @ResponseBody
    public Map login(User user,HttpSession session,HttpServletRequest request){
        String name=request.getParameter("username");
        user.setName(name);
        User user1 = hacknewsService.selectUser(user);
        System.out.println("b"+user1);
        Map map=new HashMap();
        if (user1 == null) {
            map.put("code",1);
            map.put("msgname","用户名或密码错误");
            return map;
        } else {
            session.setAttribute("user",user1);
           session.setAttribute("vos",list);
            map.put("code",0);
            map.put("msg","登录成功");
            return map;
        }

    }
    @RequestMapping("logout")

    public String logout(Model model, HttpServletRequest request,HttpSession session){
          session.removeAttribute("user");
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath", contextPath);
        model.addAttribute("vos",list);
        return "redirect:/";
    }

    @RequestMapping("user/{id}/")

    public String userInfo(@PathVariable("id") String id,HttpSession session,User user,HttpServletRequest request,Model model){
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
          user= hacknewsService.selectUserinfo(id);
          /*session.setAttribute("user",user);*/
         model.addAttribute("user",user);
          return "personal";
    }
      @RequestMapping("uploadImage")
      @ResponseBody
     public Map<Object,Object> uploadImage(@RequestParam(value="file") MultipartFile file, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws IOException {

          String fileName=file.getOriginalFilename();
          String suffixName=fileName.substring(fileName.lastIndexOf("."));
          URL resource=hackcontorller.class.getClassLoader().getResource("static/images");
          String filePath=resource.getPath();
          fileName=UUID.randomUUID()+suffixName;
          File dir=new File(filePath+"/"+fileName);
          if(!dir.getParentFile().exists()){
              dir.getParentFile().mkdirs();
          }
          file.transferTo(dir);
          Map<Object,Object> map=new HashMap<>();
          map.put("code",0);
          map.put("msg","http://localhost/images/"+fileName);
          session.setAttribute("filepath","http://localhost/images/"+fileName);
          return map;

      }
    @RequestMapping("news/{id}")

    public String newsInfo(@PathVariable("id") String id,HttpSession session,News news,HttpServletRequest request,Model model){
        String contextPath = request.getContextPath();
        session.setAttribute("newsId",id);

        session.setAttribute("contextPath", contextPath);

        News news1= hacknewsService.selectNewsinfo(id);
        User user=hacknewsService.selectUserinfo(String.valueOf(news1.getUserId()));
       List<CommentVo> comment=hacknewsService.selectComments(id);
        /*session.setAttribute("user",user);*/
        model.addAttribute("news",news1);
        model.addAttribute("owner",user);
        model.addAttribute("like",0);
        model.addAttribute("comments",comment);
        return "detail";
    }
    @RequestMapping("user/addNews")
    @ResponseBody
    public Map addNews(News news, HttpServletRequest request, HttpSession session){
        String imagepath = (String) session.getAttribute("filepath");
         User user= (User) session.getAttribute("user");
        int userId = user.getId();
          news.setUserId(userId);

        Date created_date=new Date();
        news.setCreatedDate(created_date);
        System.out.println(new Date());
        hacknewsService.addNews(news);
        Map<Object, Object> hashMap = new HashMap<>();
        session.removeAttribute("filepath");
        return hashMap;
    }
    @RequestMapping("/addComment")

    public String addComment( String id,News news,Model model,Comment comment, HttpServletRequest request, HttpSession session){
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);

            id= (String) session.getAttribute("newsId");
            comment.setEntityId(Integer.parseInt(id));
            News news1= hacknewsService.selectNewsinfo(id);
            User user1=hacknewsService.selectUserinfo(String.valueOf(news1.getUserId()));
            User user= (User) session.getAttribute("user");
            int userId = user.getId();
            comment.setUserId(userId);

            Date created_date=new Date();
            comment.setCreatedDate(created_date);
            System.out.println(new Date());
            hacknewsService.addComment(comment);
            hacknewsService.updateCommentCount(id);
            model.addAttribute("owner",user1);
            model.addAttribute("news",news1);
            List<CommentVo> comments=hacknewsService.selectComments(id);
            /*session.setAttribute("user",user);*/
            model.addAttribute("comments",comments);


        return "detail";
    }
    @RequestMapping("like")
    @ResponseBody
    public Map like(String newsId,HttpSession session,HttpServletRequest request){
        HashMap map=new HashMap();
        User user = (User)session.getAttribute("user");
        int id = user.getId();
        if(id==0){
            map.put("code",1);
            map.put("msg","登录后才能评价");
            return map;
        }else {
            String i = hacknewsService.like(id, newsId);
            map.put("code", 0);
            map.put("msg", i);
            return map;
        }
    }
    @RequestMapping("dislike")
    @ResponseBody
    public Map dislike(String newsId,HttpSession session,HttpServletRequest request) {
        HashMap map=new HashMap();
        User user = (User)session.getAttribute("user");
        int id = user.getId();
        String i =hacknewsService.dislike(id,newsId);
        if(id==0){
            map.put("code",1);
            map.put("msg","登录后才能评价");
            return  map;
        }else {
            map.put("code", 0);
            map.put("msg", i);
            return map;
        }
    }
    @RequestMapping("/test")
    @ResponseBody
    public List <Vos> test(){
         List<Vos> vos=hacknewsService.selectAllNews();

        return vos;
    }
    }


