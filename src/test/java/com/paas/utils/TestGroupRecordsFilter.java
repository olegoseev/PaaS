package com.paas.utils;

import java.util.LinkedList;
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

import com.paas.model.Group;
import com.paas.repository.dao.GroupRecordsDataReader;
import com.paas.services.filter.GroupRecordsFilter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "group.records = src/test/resources/group" })
public class TestGroupRecordsFilter {

	@Autowired
	GroupRecordsDataReader reader;

	@Test
	public void testApplyFor() {

		// daemon:x:1:member1,member2,member3
		Group group = new Group();
		group.setName("tstdaemon");
		group.setGid(1);
		List<String> members = new LinkedList<>();
		members.add("member1");
		members.add("member2");
		members.add("member3");
		group.setMembers(members);

		List<Group> groups = reader.readData();
		GroupRecordsFilter filter = new GroupRecordsFilter();
		filter.setCriteria(group);
		groups = filter.applyFor(groups);
		Assertions.assertEquals(1, groups.size(), "Should return only one user");
		Assertions.assertEquals(group, groups.get(0), "Found user should match");
	}
}
