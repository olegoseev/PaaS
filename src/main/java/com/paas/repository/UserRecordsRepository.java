package com.paas.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paas.PaaSApplicationException;
import com.paas.config.AppConfig;
import com.paas.model.User;
import com.paas.repository.dao.UserRecordsDataReader;

@Repository
public class UserRecordsRepository implements BaseRepository<Integer, User> {


	@Autowired
	UserRecordsDataReader reader;
	
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
	public List<User> findAny(User user) throws PaaSApplicationException {

		List<User> users = reader.readData();

		try {
			users = applyFilter(user, users);
		} catch (NullPointerException e) {
			throw new PaaSApplicationException(UserRecordsRepository.class, "Null pointer exception encountered");
		}
		return users;
	}

	/**
	 * Apply filter to the stream
	 * 
	 * @param user  - object with fields that will be used as a filter
	 * @param users - list to filter to be applied
	 * @return list of users matching to given criteria
	 */
	private List<User> applyFilter(User user, List<User> users) {
		// Find a user which match to all given fields
		if (!user.getName().isEmpty()) {
			List<User> _users = users.stream().filter(u -> u.getName().equals(user.getName()))
					.collect(Collectors.toList());
			users = _users;
		}

		if (user.getUid() != AppConfig.UID_NOT_DEFINED) {
			List<User> _users = users.stream().filter(u -> u.getUid() == user.getUid()).collect(Collectors.toList());
			users = _users;
		}

		if (user.getGid() != AppConfig.GID_NOT_DEFINED) {
			List<User> _users = users.stream().filter(u -> u.getGid() == user.getGid()).collect(Collectors.toList());

			users = _users;
		}

		if (!user.getComment().isEmpty()) {
			List<User> _users = users.stream().filter(u -> u.getComment().equals(user.getComment()))
					.collect(Collectors.toList());
			users = _users;
		}

		if (!user.getHome().isEmpty()) {
			List<User> _users = users.stream().filter(u -> u.getHome().equals(user.getHome()))
					.collect(Collectors.toList());
			users = _users;
		}

		if (!user.getShell().isEmpty()) {
			List<User> _users = users.stream().filter(u -> u.getShell().equals(user.getShell()))
					.collect(Collectors.toList());
			users = _users;
		}
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
	public User findBy(Integer id) throws PaaSApplicationException {

		List<User> users = reader.readData();

		Optional<User> user = users.stream().filter(u -> u.getUid() == id).findFirst();

		// Make sure something found
		if (user.isPresent()) {
			return user.get();
		}

		return null;
	}

}
