package com.sfexpress.tools.mysql.tbstructck.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.sfexpress.tools.mysql.tbstructck.controller.CmptCheckController;
import com.sfexpress.tools.mysql.tbstructck.entity.Meta;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CmptCheckControllerTest {
	
	@Autowired
	private CmptCheckController controller;
	
	@Test
	public void test() throws Exception {
		
		Gson gson = new Gson();
		Meta meta = new Meta();
		meta.setPvHost("localhost");
		meta.setPvPort("3306");
		meta.setPvDb("db2");
		meta.setPvUser("root");
		meta.setPvPwd("mysql");
		
		meta.setGvHost("localhost");
		meta.setGvPort("3306");
		meta.setGvDb("db1");
		meta.setGvUser("root");
		meta.setGvPwd("mysql");
		
		meta.setTables("hotnews");
		
		
		MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
		String result = mock
			.perform(post("/checkCmpt")
					.content(gson.toJson(meta))
					.contentType("application/json;charset=UTF-8"))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}

}
