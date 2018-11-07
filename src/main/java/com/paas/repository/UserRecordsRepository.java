package com.paas.repository;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.paas.PaaSApplicationException;
import com.paas.config.AppConfig;
import com.paas.model.User;
import com.paas.services.PasswdFileParser;
import com.paas.utils.Utils;


@Repository
public class UserRecordsRepository implements BaseRepository<Integer, User> {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserRecordsRepository.class);
	

	/**
	 * Users records will be stored in LinkedList.
	 * in case if needed to store a numerous of records
	 * probably would be better to store them in database
	 */
	private List<User> userList = new ArrayList<>();
	
	// path to file
	@Value("${user.records}")
	private String userFile;
	
	@Autowired
	PasswdFileParser parser;
	
	/**
	 * Update user records
	 */
	public void updateRepository()  throws PaaSApplicationException {
	
		 // Clear the list before updating it
		if(!userList.isEmpty()) {
			userList.clear();
		}
		
		try {
			// Converting path string to path
			Path path = Paths.get(userFile);
			
			System.out.println(path.toString());
			
			// parse file and fill the list with new data
			userList = parser.parse(path);
		
		} catch (InvalidPathException e) {
			LOG.error("Failed to resolve path", e);
			throw new PaaSApplicationException("Invalid Path Exception");
		} catch (UnsupportedOperationException e) {
			LOG.error(e.toString());
			throw new PaaSApplicationException("Unsupported Operation Exception");
		} catch (NullPointerException e) {
			LOG.error(e.toString());
			throw new PaaSApplicationException("NullPointer Exception");
		} catch (ClassCastException e) {
			LOG.error(e.toString());
			throw new PaaSApplicationException("Class Cast Exception");
		} catch (IllegalArgumentException e) {
			LOG.error(e.toString());
			throw new PaaSApplicationException("Illegal Argument Exception");
		}
	}

    /**
     * All users
     * 
     * @return 
     * 			list of User objects if found
     * 			an empty list if not
     */
	@Override
	public List<User> findAll() {
		
		updateRepository();
		
		return userList;
	}
	
    /**
     * Find all users matching to input object.
     * 
     * @param user
     * 			 partially defined user object
     *            
     * @return 
     * 			list of User object if found
     * 			an empty list if not
     */
	@Override
	public List<User> findAny(User user) throws PaaSApplicationException {
		
		updateRepository();
		
		List<User> users =  Collections.emptyList();
		
		users = Utils.concatenateTwoLists(users, userList);
		
		try {
			// Find a user which match to all given fields
			if(!user.getName().isEmpty()) {
				List<User> _users = users.stream()
						.filter(u -> u.getName().equals(user.getName()))
						.collect(Collectors.toList());
				users = _users;
			}
			
			if(user.getUid() != AppConfig.UID_NOT_DEFINED) {
				List<User> _users = users.stream()
						.filter(u -> u.getUid() == user.getUid())
						.collect(Collectors.toList());
				users = _users;
			}
			
			if(user.getGid() != AppConfig.GID_NOT_DEFINED) {
				List<User> _users = users.stream()
						.filter(u -> u.getGid() == user.getGid())
						.collect(Collectors.toList());
				
				users = _users;
			}
			
			if(!user.getComment().isEmpty()) {
				List<User> _users = users.stream()
						.filter(u -> u.getComment().equals(user.getComment()))
						.collect(Collectors.toList());
				users = _users;
			}
			
			if(!user.getHome().isEmpty()) {
				List<User> _users = users.stream()
						.filter(u -> u.getHome().equals(user.getHome()))
						.collect(Collectors.toList());
				users = _users;
			}
			
			if(!user.getShell().isEmpty()) {
				List<User> _users = users.stream()
						.filter(u -> u.getShell().equals(user.getShell()))
						.collect(Collectors.toList());
				users = _users;
			}
			
		} catch (NullPointerException e) {
			LOG.error("Null pointer exception");
			throw new PaaSApplicationException("Null pointer exception encountered");
			
		}
		return users;
	}
	
	
    /**
     * Find user by Id
     * 
     * @param id
     * 			user Id
     *            
     * @return 
     * 			User object if found
     * 			null if not found
     */
	@Override
	public User findBy(Integer id) throws PaaSApplicationException {
		
		updateRepository();
		
		Optional<User> user = userList.stream()
								.filter(u -> u.getUid() == id)
								.findFirst();

		// Make sure something found
		if(user.isPresent()) {
			return user.get();
		}
		
		return null;
	}

}
