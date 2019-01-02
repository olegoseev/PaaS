package com.paas.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.paas.model.User;
import com.paas.services.filter.UserFilter;


@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TestUserFilter {

	private static List<User> records = new ArrayList<>();
	
	@BeforeAll
	public static void init() {
		User userA = new User();
		userA.setName("userA");
		userA.setUid(1);
		userA.setGid(1);
		userA.setHome("homeA");
		userA.setShell("shellA");
		userA.setComment("commentsA");
		records.add(userA);
		
		User userB = new User();
		userB.setName("userB");
		userB.setUid(2);
		userB.setGid(2);
		userB.setHome("homeB");
		userB.setShell("shellB");
		userB.setComment("commentsB");
		records.add(userB);
		
		User userC = new User();
		userC.setName("userC");
		userC.setUid(3);
		userC.setGid(3);
		userC.setHome("homeC");
		userC.setShell("shellC");
		userC.setComment("commentsC");
		records.add(userC);
		
		User userD = new User();
		userD.setName("userD");
		userD.setUid(4);
		userD.setGid(4);
		userD.setHome("homeD");
		userD.setShell("shellD");
		userD.setComment("commentsD");
		records.add(userD);
		
		User userE = new User();
		userE.setName("userE");
		userE.setUid(5);
		userE.setGid(5);
		userE.setHome("homeE");
		userE.setShell("shellE");
		userE.setComment("commentsE");
		records.add(userE);
	}
	
	
	@Test
	public void testUserNameFilter() {
		
		User criteria = new User();
		criteria.setName("userC");
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userC");
		user.setUid(3);
		user.setGid(3);
		user.setHome("homeC");
		user.setShell("shellC");
		user.setComment("commentsC");
		
		Assertions.assertEquals(user, result.get(0), "Should get userC");
	}
	
	@Test
	public void testUserUidFilter() {
		
		User criteria = new User();
		criteria.setUid(4);;
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userD");
		user.setUid(4);
		user.setGid(4);
		user.setHome("homeD");
		user.setShell("shellD");
		user.setComment("commentsD");
		
		Assertions.assertEquals(user, result.get(0), "Should get userD");
	}
	
	@Test
	public void testUserGidFilter() {
		
		User criteria = new User();
		criteria.setGid(2);;
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userB");
		user.setUid(2);
		user.setGid(2);
		user.setHome("homeB");
		user.setShell("shellB");
		user.setComment("commentsB");
		
		Assertions.assertEquals(user, result.get(0), "Should get userB");
	}
	
	@Test
	public void testUserCommentsFilter() {
		
		User criteria = new User();
		criteria.setComment("commentsE");
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userE");
		user.setUid(5);
		user.setGid(5);
		user.setHome("homeE");
		user.setShell("shellE");
		user.setComment("commentsE");
		
		Assertions.assertEquals(user, result.get(0), "Should get userE");
	}
	
	@Test
	public void testUserHomeFilter() {
		
		User criteria = new User();
		criteria.setHome("homeA");
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userA");
		user.setUid(1);
		user.setGid(1);
		user.setHome("homeA");
		user.setShell("shellA");
		user.setComment("commentsA");
		
		Assertions.assertEquals(user, result.get(0), "Should get userA");
	}
	
	@Test
	public void testUserShellFilter() {
		
		User criteria = new User();
		criteria.setShell("shellC");
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userC");
		user.setUid(3);
		user.setGid(3);
		user.setHome("homeC");
		user.setShell("shellC");
		user.setComment("commentsC");
		
		Assertions.assertEquals(user, result.get(0), "Should get userC");
	}
	
	@Test
	public void testUserAllFiledsFilter() {
		
		User criteria = new User();
		criteria.setName("userD");
		criteria.setUid(4);
		criteria.setGid(4);
		criteria.setHome("homeD");
		criteria.setShell("shellD");
		criteria.setComment("commentsD");
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userD");
		user.setUid(4);
		user.setGid(4);
		user.setHome("homeD");
		user.setShell("shellD");
		user.setComment("commentsD");
		
		
		Assertions.assertEquals(user, result.get(0), "Should get userD");
	}
	
	@Test
	public void testUserSomeFiledsFilter() {
		
		User criteria = new User();
		criteria.setName("userD");
		criteria.setGid(4);
		criteria.setHome("homeD");
		
		UserFilter uf = UserFilter.Builder.newInstance().setCriteria(criteria).build();
		List<User> result = uf.apply(records);
		
		Assertions.assertEquals(1, result.size(), "Should be only one record");
		
		User user = new User();
		user.setName("userD");
		user.setUid(4);
		user.setGid(4);
		user.setHome("homeD");
		user.setShell("shellD");
		user.setComment("commentsD");
		
		
		Assertions.assertEquals(user, result.get(0), "Should get userD");
	}
}
