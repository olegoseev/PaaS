package com.paas.controller.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource (properties = {"user.records = src/test/resources/passwd", "group.records = src/test/resources/group"})
@ContextConfiguration
public class TestUserRestController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAllUsers() throws Exception {
		
		this.mockMvc.perform(get("/users"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK'}"));
	}
	
	@Test
	public void getUser() throws Exception {
		// trying to get user - games:x:5:60:games:/usr/games:/usr/sbin/nologin
		this.mockMvc.perform(get("/users/5"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK','data':{'name':'games','uid':5,'gid':60,'comment':'games','home':'/usr/games'}}"));
	}

	@Test
	public void getUserNotFound() throws Exception {
		// trying to get user - games:x:5:60:games:/usr/games:/usr/sbin/nologin
		this.mockMvc.perform(get("/users/11"))
					.andDo(print())
					.andExpect(status().is4xxClientError())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'error','error':'NOT_FOUND'}"));
	}
	
	@Test
	public void getAllGroupsForUser() throws Exception {
		// requesting groups for user - sys:x:3:3:sys:/dev:/usr/sbin/nologin
		// the user should appear in two groups
		this.mockMvc.perform(get("/users/3/groups"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					// check some key : value fields in the response
					.andExpect(content().json("{'status': 'OK','success':'OK', 'data':[{'name':'sys','gid':3},{'name':'adm','gid':4}]}"));
	}
	
	
	@Test
	public void getAllGroupsForUserNotFound() throws Exception {
		// requesting groups for user - sys:x:3:3:sys:/dev:/usr/sbin/nologin
		// the user should appear in two groups
		this.mockMvc.perform(get("/users/11/groups"))
					.andDo(print())
					.andExpect(status().is4xxClientError())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					// check some key : value fields in the response
					.andExpect(content().json("{'status': 'error','error':'NOT_FOUND'}"));
	}
	
	@Test
	public void queryUser() throws Exception {
		// query user - mail:x:8:8:mail:/var/mail:/usr/sbin/nologin
		this.mockMvc.perform(get("/users/query?uid=8&name=mail"))
			        .andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK',"
							+ "'data':[{'name':'mail','uid':8,'gid':8,'comment':'mail','home':'/var/mail','shell':'/usr/sbin/nologin'}]}"));
	}
	
	@Test
	public void queryUserNotFound() throws Exception {
		// query user - mail:x:8:8:mail:/var/mail:/usr/sbin/nologin
		this.mockMvc.perform(get("/users/query?uid=11&name=unknown"))
			        .andDo(print())
					.andExpect(status().is4xxClientError())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'error','error':'NOT_FOUND'}"));
	}
}
