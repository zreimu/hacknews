package com.zhandi.hackernews.controller;

import com.zhandi.hackernews.model.*;
import com.zhandi.hackernews.service.HacknewsService;
import com.zhandi.hackernews.util.JedisUtils;
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

    public String selectNews (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<ItNewsVos> list=hacknewsService.selectItNews();
        session.setAttribute("itvos",list);
        return "news";
    }
    @RequestMapping("/luntan")

    public String home (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<Vos> newsList = hacknewsService.selectAllNews();
        session.setAttribute("vos",newsList);
        return "home";
    }
    @RequestMapping("/java")

    public String java (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<ItNewsVos> newsList = hacknewsService.selectjNews();
        session.setAttribute("itvos",newsList);
        return "news";
    }
    @RequestMapping("/python")

    public String python (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<ItNewsVos> newsList = hacknewsService.selectpNews();
        session.setAttribute("itvos",newsList);
        return "news";
    }
    @RequestMapping("/c++")

    public String c (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<ItNewsVos> newsList = hacknewsService.selectcNews();
        session.setAttribute("itvos",newsList);
        return "news";
    }
    @RequestMapping("/more")

    public String more (HttpSession session, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);
        List<ItNewsVos> newsList = hacknewsService.selectmNews();
        session.setAttribute("itvos",newsList);
        return "news";
    }
     @RequestMapping("/reg")
     @ResponseBody
       public Map<String, Object> addUser(User user,HttpServletRequest request,  HttpSession session) {
         String name=request.getParameter("username");
           user.setName(name);
           user.setNickname(name);
           user.setHeadUrl("http://localhost/images/touxiang.jpg");
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
    @RequestMapping("admin")
    public String admin(){
        return "index";
    }
    @RequestMapping("admin/main")
    public String main(){
        return "main";
    }

    @RequestMapping("admin/login")
    public String login(Admin admin,HttpSession session,HttpServletRequest request) {
        String name=request.getParameter("username");
        String password=request.getParameter("password");
        admin.setName(name);
        admin.setPassword(password);
        Admin admin1 =hacknewsService.selectAdmin(admin);
        if (admin1 == null) {
            return "/";
        } else {
            session.setAttribute("admin",admin1);
            return "main";
        }


    }
    @RequestMapping("admin/toaddnews")
    public String addnew(Admin admin,Model model,HttpSession session,HttpServletRequest request) {
       String contextPath=request.getContextPath();
        model.addAttribute("contextPath", contextPath);
            return "addnews";

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
          session.setAttribute("user",user);
        List<Vos> newsList = hacknewsService.queryAllNewsOfUserById(Integer.parseInt(id));
          /*session.setAttribute("user",user);*/
         model.addAttribute("user",user);
        Jedis jedis = JedisUtils.getJedisFromPool();
        //这时user已经登陆了
        if (user!=null){
            //模糊查询所有与新闻相关的key
            Set<String> userset = jedis.keys("usersetlike*");
            for (String key : userset) {
                //查询该id是否存在于其中
                //如果存在就返回like值为1
                Boolean sismember = jedis.sismember(key, ""+user.getId());
                if (sismember) {
                    for (Vos vo : newsList
                    ) {
                        //这个摘录出来新闻的id
                        String s = key.substring(11);
                        int i = Integer.parseInt(s);
                        int id1 = vo.getNews().getId();
                        if (id1 == i) {
                            vo.setLike(1);
                        }
                    }
                }
            }
            Set<String> userset2 = jedis.keys("usersetdislike*");
            for (String key : userset2) {
                //查询该id是否存在于其中
                Boolean sismember = jedis.sismember(key, ""+user.getId());
                //如果存在就返回like值为1
                if (sismember) {
                    for (Vos vo : newsList
                    ) {
                        String s = key.substring(14);
                        int i = Integer.parseInt(s);
                        int id1 = vo.getNews().getId();
                        if (id1 == i) {
                            vo.setLike(-1);
                        }
                    }
                }
            }
        }
        model.addAttribute("vos",newsList);
        jedis.close();
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
    @RequestMapping("itnews/{id}")

    public String itnewsInfo(@PathVariable("id") String id,HttpSession session,News news,HttpServletRequest request,Model model){
        String contextPath = request.getContextPath();
        session.setAttribute("itnewsId",id);
        session.setAttribute("contextPath", contextPath);

        ItNews news1= hacknewsService.selectitNewsinfo(id);
        Type type= hacknewsService.selectitTypeinfo(String.valueOf(news1.getTid()));
        List<ItCommentVo> comments=hacknewsService.selectitComments(id);
        /*session.setAttribute("user",user);*/
        model.addAttribute("itnews",news1);
        model.addAttribute("like",0);
        model.addAttribute("type",type);
        model.addAttribute("comments",comments);
        return "itnewsdetail";
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
            model.addAttribute("like",0);
            model.addAttribute("comments",comments);


        return "detail";
    }
    @RequestMapping("/additComment")

    public String additComment( String id,ItNews itNews,Model model,ItComment itComment, HttpServletRequest request, HttpSession session){
        String contextPath = request.getContextPath();
        session.setAttribute("contextPath", contextPath);

        id= (String) session.getAttribute("itnewsId");
        itComment.setEntityId(Integer.parseInt(id));
        ItNews news1= hacknewsService.selectitNewsinfo(id);
        Type type= hacknewsService.selectitTypeinfo(String.valueOf(news1.getTid()));
        User user= (User) session.getAttribute("user");
        int userId = user.getId();
        itComment.setUserId(userId);

        Date created_date=new Date();
        itComment.setCreatedDate(created_date);
        System.out.println(new Date());
        hacknewsService.additComment(itComment);
        hacknewsService.updateitCommentCount(id);
        model.addAttribute("type",type);
        model.addAttribute("itnews",news1);
        List<ItCommentVo> comments=hacknewsService.selectitComments(id);
        /*session.setAttribute("user",user);*/
        model.addAttribute("like",0);
        model.addAttribute("comments",comments);


        return "itnewsdetail";
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
    @RequestMapping("personal/delete")
    public String userMess(HttpServletRequest request, Model model, HttpSession session) {
        String op = request.getParameter("op");
        //获取需要删除的新闻id
        int id2 = Integer.parseInt(op);
        hacknewsService.removeUserById(id2);
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath",contextPath);
        //获取当前用户id
        User user = (User) session.getAttribute("user");
        user= hacknewsService.selectUserinfo(String.valueOf(user.getId()));
        model.addAttribute("user",user);

        List<Vos> newsList = hacknewsService.queryAllNewsOfUserById(user.getId());
        Jedis jedis = JedisUtils.getJedisFromPool();
        //这时user已经登陆了
        if (user!=null){
            //模糊查询所有与新闻相关的key
            Set<String> userset = jedis.keys("usersetlike*");
            for (String key : userset) {
                //查询该id是否存在于其中
                //如果存在就返回like值为1
                Boolean sismember = jedis.sismember(key, ""+user.getId());
                if (sismember) {
                    for (Vos vo : newsList
                    ) {
                        //这个摘录出来新闻的id
                        String s = key.substring(11);
                        int i = Integer.parseInt(s);
                        int id = vo.getNews().getId();
                        if (id == i) {
                            vo.setLike(1);
                        }
                    }
                }
            }
            Set<String> userset2 = jedis.keys("usersetdislike*");
            for (String key : userset2) {
                //查询该id是否存在于其中
                Boolean sismember = jedis.sismember(key, ""+user.getId());
                //如果存在就返回like值为1
                if (sismember) {
                    for (Vos vo : newsList
                    ) {
                        String s = key.substring(14);
                        int i = Integer.parseInt(s);
                        int id = vo.getNews().getId();
                        if (id == i) {
                            vo.setLike(-1);
                        }
                    }
                }
            }
        }
        model.addAttribute("vos",newsList);
        jedis.close();
        return "personal";
    }
    }


