package com.paas.utils;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.paas.model.User;
import com.paas.repository.dao.UserRecordsDataReader;
import com.paas.services.UserRecordsFilter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"user.records = src/test/resources/passwd"})
public class TestUserRecordsFilter {
	
	
	@Autowired
	UserRecordsDataReader reader;
	
	@Test
	public void testApplyFor() {
		
		// man:x:6:12:man:/var/cache/man:/usr/sbin/nologin
		User user = new User();
		
		user.setName("tstman");
		user.setUid(6);
		user.setGid(12);
		user.setComment("tstman");
		user.setHome("/var/cache/man");
		user.setShell("/usr/sbin/nologin");
		
		List<User> users = reader.readData();
		UserRecordsFilter filter = new UserRecordsFilter();
		filter.setCriteria(user);
		users = filter.appplyFor(users);
		Assertions.assertEquals(1, users.size(), "Should return only one user");
		Assertions.assertEquals(user, users.get(0), "Found user should match");
	}
}
