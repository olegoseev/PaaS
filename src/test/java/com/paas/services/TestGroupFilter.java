package com.paas.services;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.paas.model.Group;
import com.paas.services.filter.GroupFilter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TestGroupFilter {

	private static List<Group> records = new ArrayList<>();

	@BeforeAll
	public static void init() {

		String[] ma = { "member1" };

		Group groupA = new Group();
		groupA.setName("groupA");
		groupA.setGid(1);
		groupA.setMembers(new ArrayList<String>(Arrays.asList(ma)));

		records.add(groupA);

		String[] mb = { "member1", "member2" };

		Group groupB = new Group();
		groupB.setName("groupB");
		groupB.setGid(2);
		groupB.setMembers(new ArrayList<String>(Arrays.asList(mb)));

		records.add(groupB);

		String[] mc = { "member1", "member2", "member3" };

		Group groupC = new Group();
		groupC.setName("groupC");
		groupC.setGid(3);
		groupC.setMembers(new ArrayList<String>(Arrays.asList(mc)));

		records.add(groupC);

		String[] md = { "member1", "member2", "member3", "member4" };

		Group groupD = new Group();
		groupD.setName("groupD");
		groupD.setGid(4);
		groupD.setMembers(new ArrayList<String>(Arrays.asList(md)));

		records.add(groupD);
	}

	@Test
	public void testGroupNameFilter() {

		Group criteria = new Group();
		criteria.setName("groupA");

		GroupFilter gf = GroupFilter.Builder.newInstance().setCriteria(criteria).build();
		List<Group> result = gf.apply(records);

		Assertions.assertEquals(1, result.size(), "Should be only one record");

		String[] ma = { "member1" };

		Group group = new Group();
		group.setName("groupA");
		group.setGid(1);
		group.setMembers(new ArrayList<String>(Arrays.asList(ma)));

		Assertions.assertEquals(group, result.get(0), "Should get groupA");
	}

	@Test
	public void testGroupGidFilter() {
		Group criteria = new Group();
		criteria.setGid(3);

		GroupFilter gf = GroupFilter.Builder.newInstance().setCriteria(criteria).build();
		List<Group> result = gf.apply(records);

		Assertions.assertEquals(1, result.size(), "Should be only one record");

		String[] mc = { "member1", "member2", "member3" };

		Group group = new Group();
		group.setName("groupC");
		group.setGid(3);
		group.setMembers(new ArrayList<String>(Arrays.asList(mc)));

		Assertions.assertEquals(group, result.get(0), "Should get groupC");
	}

	@Test
	public void testGroupMembersFilter() {
		String[] md = { "member1", "member2", "member3", "member4" };

		Group criteria = new Group();
		criteria.setMembers(new ArrayList<String>(Arrays.asList(md)));

		GroupFilter gf = GroupFilter.Builder.newInstance().setCriteria(criteria).build();
		List<Group> result = gf.apply(records);

		Assertions.assertEquals(1, result.size(), "Should be only one record");

		Group group = new Group();
		group.setName("groupD");
		group.setGid(4);
		group.setMembers(new ArrayList<String>(Arrays.asList(md)));

		Assertions.assertEquals(group, result.get(0), "Should get groupD");
	}

	@Test
	public void testGroupAllFieldsFilter() {
		String[] mb = { "member1", "member2" };

		Group criteria = new Group();
		criteria.setName("groupB");
		criteria.setGid(2);
		criteria.setMembers(new ArrayList<String>(Arrays.asList(mb)));

		GroupFilter gf = GroupFilter.Builder.newInstance().setCriteria(criteria).build();
		List<Group> result = gf.apply(records);

		Assertions.assertEquals(1, result.size(), "Should be only one record");

		Group group = new Group();
		group.setName("groupB");
		group.setGid(2);
		group.setMembers(new ArrayList<String>(Arrays.asList(mb)));

		Assertions.assertEquals(group, result.get(0), "Should get groupB");
	}

	@Test
	public void testGroupSomeFiledsFilter() {
		String[] mc = { "member1", "member2", "member3" };

		Group criteria = new Group();
		criteria.setGid(3);
		criteria.setMembers(new ArrayList<String>(Arrays.asList(mc)));

		GroupFilter gf = GroupFilter.Builder.newInstance().setCriteria(criteria).build();
		List<Group> result = gf.apply(records);

		Assertions.assertEquals(1, result.size(), "Should be only one record");

		Group group = new Group();
		group.setName("groupC");
		group.setGid(3);
		group.setMembers(new ArrayList<String>(Arrays.asList(mc)));

		Assertions.assertEquals(group, result.get(0), "Should get groupC");
	}
}
