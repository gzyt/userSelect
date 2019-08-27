package com.example.demo;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSelectPostApplicationTests {

	@Test
	public void contextLoads() {
		String postUrl = "http://localhost:80/testDemo";
		String reqJson = "{'name':'zhnagsan','age':'你好'}";
		UserSelectPostController userSelectPostController = new UserSelectPostController();
		try
		{
			userSelectPostController.sendPost(postUrl,reqJson,"UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
