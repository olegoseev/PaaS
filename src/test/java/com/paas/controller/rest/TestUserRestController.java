package com.paas.controller.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.paas.repository.UserRecordsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource (properties = {"user.records = src/test/resources/passwd", "userFile = src/test/resources/passwd"})
public class TestUserRestController {
	
	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private UserRecordsRepository userRepo;
	
	@Value("${user.records}")
	private String realFile;
	
	@Test
	public void getAllUsers() throws Exception {
		
		ReflectionTestUtils.setField(userRepo, "userFile", realFile);
		
		this.mockMvc.perform(get("/users"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void getUser() throws Exception {
		
		
		this.mockMvc.perform(get("/users/5"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(content().json("{'status': 'OK','success':'OK','data':{'name':'games','uid':5,'gid':60,'comment':'games','home':'/usr/games'}}"));
	}
}
