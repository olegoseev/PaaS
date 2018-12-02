package com.paas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paas.model.User;
import com.paas.repository.dao.UserRecordsDataReader;
import com.paas.services.UserRecordsFilter;

@Repository
public class UserRecordsRepository implements BaseRepository<Integer, User> {

	@Autowired
	UserRecordsDataReader reader;

	@Autowired
	UserRecordsFilter filter;

	/**
	 * All users
	 * 
	 * @return list of User objects if found an empty list if not
	 */
	@Override
	public List<User> findAll() {
		return reader.readData();
	}

	/**
	 * Find all users matching to input object.
	 * 
	 * @param user partially defined user object
	 * 
	 * @return list of User object if found an empty list if not
	 */
	@Override
	public List<User> findAny(User user) {
		List<User> users = reader.readData();
		filter.setCriteria(user);
		users = filter.appplyFor(users);
		return users;
	}

	/**
	 * Find user by Id
	 * 
	 * @param id user Id
	 * 
	 * @return User object if found null if not found
	 */
	@Override
	public User findBy(Integer id) {

		List<User> users = reader.readData();
		return findById(id, users);
	}

	private User findById(Integer id, List<User> users) {
		Optional<User> user = users.stream().filter(u -> u.getUid() == id).findFirst();

		// Make sure something found
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

}
