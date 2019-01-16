package com.paas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.paas.controller.rest.GroupRestController;
import com.paas.controller.rest.UserRestController;
import com.paas.repository.GroupRecordsRepository;
import com.paas.repository.UserRecordsRepository;
import com.paas.services.parser.GroupRecordsParser;
import com.paas.services.parser.PasswdRecordsParser;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SmokeTest {
	@Autowired
	private UserRestController userController;

	@Autowired
	private GroupRestController groupController;

	@Autowired
	private GroupRecordsParser groupParser;

	@Autowired
	private PasswdRecordsParser userParser;

	@Autowired
	private GroupRecordsRepository groupRepo;

	@Autowired
	private UserRecordsRepository userRepo;

	@Test
	public void contextLoads() {
		Assertions.assertNotNull(userController);
		Assertions.assertNotNull(groupController);
		Assertions.assertNotNull(groupParser);
		Assertions.assertNotNull(userParser);
		Assertions.assertNotNull(groupRepo);
		Assertions.assertNotNull(userRepo);
	}
}
