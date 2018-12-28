package com.paas.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.paas.PaaSApplicationException;
import com.paas.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "user.records = src/test/resources/passwd",
		"dummy.file = src/test/resources/missing" })
public class TestUserRecordsRepository {

	@Autowired
	private UserRecordsRepository repo;

	@Value("${dummy.file}")
	private String dummyFile;

	@Value("${user.records}")
	private String realFile;

	@Test
	public void testFindAll() throws PaaSApplicationException {
		List<User> list = repo.findAll();
		Assertions.assertEquals(10, list.size(), "findAll user records");
	}

	@Test
	public void testFindAny() throws PaaSApplicationException {
		User user = new User();

		user.setName("tstnews");
		user.setHome("/var/spool/news");

		List<User> list = repo.findAny(user);
		Assertions.assertEquals(1, list.size(), "Find a single user by given username and home directory");
		Assertions.assertEquals("tstnews", list.get(0).getName(), "Find user 'tstnews'");
		Assertions.assertEquals("/var/spool/news", list.get(0).getHome(), "User 'tstnews' home directory");
	}

	@Test
	public void testFindBy() throws PaaSApplicationException {
		// let's check user games
		// games:x:5:60:games:/usr/games:/usr/sbin/nologin
		User actualUser = repo.findBy(5);
		User expectedUser = new User();
		expectedUser.setName("tstgames");
		expectedUser.setUid(5);
		expectedUser.setGid(60);
		expectedUser.setHome("/usr/games");
		expectedUser.setShell("/usr/sbin/nologin");
		expectedUser.setComment("tstgames");

		Assertions.assertEquals(actualUser, expectedUser, "Find user by Id");
	}
}
