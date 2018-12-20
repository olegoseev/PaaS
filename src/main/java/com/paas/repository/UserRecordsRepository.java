package com.paas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paas.model.User;
import com.paas.repository.dao.UserRecordsDataReader;
import com.paas.services.filter.UserRecordsFilter;

@Repository
public class UserRecordsRepository extends BaseRepositoryImpl<Integer, User> {

	@Autowired
	public UserRecordsRepository(UserRecordsDataReader reader, UserRecordsFilter filter) {
		super(reader, filter);
	}

	User filterByPk(Integer id, List<User> users) {
		Optional<User> user = users.stream().filter(u -> u.getUid() == id).findFirst();

		// Make sure something found
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

}
