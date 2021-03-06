package com.paas.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paas.PaaSApplicationException;
import com.paas.model.Group;
import com.paas.model.User;
import com.paas.repository.dao.GroupRecordsDataReader;
import com.paas.services.filter.GroupFilter;
import com.paas.services.filter.GroupFilter.Builder;
import com.paas.utils.Utils;

@Repository
public class GroupRecordsRepository extends BaseRepositoryImpl<Integer, Group> {

	@Autowired
	GroupRecordsDataReader reader;

	@Override
	protected List<Group> applyFilter(List<Group> records, Group criteria) {
		GroupFilter gf = Builder.newInstance().setCriteria(criteria).build();
		return gf.apply(records);
	}

	@Override
	Group filterByPk(Integer id, List<Group> groups) {
		Optional<Group> group = groups.stream().filter(g -> g.getGid() == id).findFirst();
		// Make sure we found something
		if (group.isPresent()) {
			return group.get();
		}
		return null;
	}

	public List<Group> findAllGroupsForUser(User user) throws PaaSApplicationException {
		List<Group> groups = findAll();
		String name = user.getName();

		List<Group> userMemberOf = findGroupsUserMemberOf(groups, name);
		List<Group> usersGroup = findGroupForUser(groups, name);

		return Utils.concatenateTwoLists(userMemberOf, usersGroup);
	}

	private List<Group> findGroupForUser(List<Group> groups, String name) {
		return groups.stream().filter(g -> (g.getName().equals(name))).collect(Collectors.toList());
	}

	private List<Group> findGroupsUserMemberOf(List<Group> groups, String name) {
		return groups.stream().filter(g -> (g.getMembers().contains(name))).collect(Collectors.toList());
	}

	@Override
	public List<Group> findAll() throws PaaSApplicationException {
		return reader.readData();
	}

}
