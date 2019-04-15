package com.cskaoyan.hackernews;

import com.cskaoyan.hackernews.dao.UserDao;
import com.cskaoyan.hackernews.model.User;
import com.cskaoyan.hackernews.model.Vos;
import com.cskaoyan.hackernews.service.HacknewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HackernewsApplicationTests {
	@Autowired
	private HacknewsService hacknewsService;
	@Test
	public void contextLoads() {
		User user = new User();
		user.setName("123456");
		user.setPassword("123456");
		User user1 = hacknewsService.selectUser(user);
		System.out.println(user1);
	}

}
