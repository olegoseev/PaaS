package com.paas.repository;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.paas.model.Group;
import com.paas.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "group.records = src/test/resources/group",
		"dummy.file = src/test/resources/missing" })
public class TestGroupRecordsRepository {

	@Value("${dummy.file}")
	private String dummyFile;

	@Value("${group.records}")
	private String realFile;

	@Autowired
	private GroupRecordsRepository repo;

	@Test
	public void testFindAll() throws PaaSApplicationException {
		List<Group> list = repo.findAll();
		// the list should have total 7 records
		Assertions.assertEquals(7, list.size(), "findAll user records");
	}

	private List<String> getMembers() {

		List<String> members = new ArrayList<>();
		members.add("member1");
		members.add("member2");
		members.add("member3");
		return members;
	}

	@Test
	public void testFindAny() throws PaaSApplicationException {
		// test for group: daemon:x:1:member1,member2,member3
		Group group = new Group();

		List<String> members = getMembers();

		group.setGid(1);
		group.setMembers(members);

		List<Group> list = repo.findAny(group);
		Assertions.assertEquals(1, list.size(), "Find a single user by given username and home directory");
		Assertions.assertEquals("tstdaemon", list.get(0).getName(), "Find user 'tstnews'");
		Assertions.assertEquals(members, list.get(0).getMembers(), "User 'news' home directory");
	}

	@Test
	public void testFindBy() throws PaaSApplicationException {
		// test for group: daemon:x:1:member1,member2,member3
		List<String> members = getMembers();

		Group expected = new Group();
		expected.setGid(1);
		expected.setName("tstdaemon");
		expected.setMembers(members);

		Group actual = repo.findBy(1);
		Assertions.assertEquals(expected, actual, "Find group by Id");
	}

	@Test
	public void testFindAllGroupsForUser() throws PaaSApplicationException {
		// user sys should appears in two groups
		// sys:x:3:sys
		// adm:x:4:sys

		User user = new User();
		user.setName("tstsys");

		List<Group> groups = repo.findAllGroupsForUser(user);

		List<String> names = groups.stream().map(g -> g.getName()).collect(Collectors.toList());

		Assertions.assertEquals(2, groups.size(), "Find all groups for a given user");
		assertThat(names, hasItems("tstsys", "tstadm"));
	}
}
