package com.paas.controller.rest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class TestGroupRestController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAllGroups() throws Exception {
		
		this.mockMvc.perform(get("/groups"))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK'}"));;
	}

	
	@Test
	public void getGroup() throws Exception {
		// trying to get group - tty:x:5:
		
		this.mockMvc.perform(get("/groups/5"))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK','data':{'name':'tty','gid':5}}"));

	}
	
	@Test
	public void queryGroups() throws Exception {
		// looking for a group with name:sys and gid:3
		this.mockMvc.perform(get("/groups/query?gid=3&name=sys"))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK','data':[{'name':'sys','gid':3}]}"));

	}
}
