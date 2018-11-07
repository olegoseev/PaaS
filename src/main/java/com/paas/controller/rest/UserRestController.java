package com.paas.controller.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paas.PaaSApplicationException;
import com.paas.config.AppConfig;
import com.paas.model.Group;
import com.paas.model.User;
import com.paas.repository.GroupRecordsRepository;
import com.paas.repository.UserRecordsRepository;
import com.paas.utils.AppResponse;

@RestController
public class UserRestController {

	private static final Logger LOG = LoggerFactory.getLogger(UserRestController.class);
	// User repository
	@Autowired
	UserRecordsRepository userRepository;
	
	// Group repository
	@Autowired
	GroupRecordsRepository groupRepository;
	
	/**
	 * Entry point to get all users
	 * @return 
	 * 		  json success/error
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllUsers() {
		try {		
			 // Get user entities from repository
			List<User> users = userRepository.findAll();
			
			 // In case of empty list returned sending NO.CONTENT to client
			if (users == null || users.isEmpty()) {
				return new ResponseEntity<Object>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(users), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Entry point to get a user record by user Id
	 * @param id
	 * 			UID
	 * @return 
	 * 		  json success/error
	 */
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getUser(@PathVariable("id") Integer id) {
		
		try {
			
			User user = userRepository.findBy(id);
			if(user == null) {
				return new ResponseEntity<Object>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(user), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Entry point to get all the groups for a user by user id
	 * @param id
	 * 			UID
	 * @return 
	 * 		  json success/error
	 */
	@RequestMapping(value="/users/{id}/groups", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllGroupsForUser(@PathVariable("id") Integer id) {
		
		try {
			User user = userRepository.findBy(id);
			
			if(user == null) {
				return new ResponseEntity<Object>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}
			
			List<Group> groups = groupRepository.findAllGroupsForUser(user);
			
			if(groups == null) {
				return new ResponseEntity<Object>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(groups), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Helper function. create User object from Map object where some keys may not available
	 * @param map
	 * @return User object
	 */
	private User makeUserFromMap(Map<String, String> map) {
		
		User user = new User();
		
		try {
			
			user.setName(map.containsKey(AppConfig.USER_NAME) ? (String)map.get(AppConfig.USER_NAME) : AppConfig.EMPTY_STRING);
			user.setUid(map.containsKey(AppConfig.USER_UID) ? Integer.parseInt((String)map.get(AppConfig.USER_UID)) : AppConfig.UID_NOT_DEFINED);
			user.setGid(map.containsKey(AppConfig.USER_GID) ? Integer.parseInt((String)map.get(AppConfig.USER_GID)) : AppConfig.GID_NOT_DEFINED);
			user.setComment(map.containsKey(AppConfig.USER_COMMENT) ? (String)map.get(AppConfig.USER_COMMENT) : AppConfig.EMPTY_STRING);
			user.setHome(map.containsKey(AppConfig.USER_HOME) ? (String)map.get(AppConfig.USER_HOME) : AppConfig.EMPTY_STRING);
			user.setShell(map.containsKey(AppConfig.USER_SHELL) ? (String)map.get(AppConfig.USER_SHELL) : AppConfig.EMPTY_STRING);
			
		} catch (NullPointerException ne) {
			LOG.error(ne.getMessage());
			ne.printStackTrace();
			return null;
		} catch (ClassCastException cce) {
			LOG.error(cce.getMessage());
			cce.printStackTrace();
			return null;
		}

		return user;
	}
	
	/**
	 * Entry point to get all the users based on query parameters. 
	 * Query string should have at least one parameter.
	 * 
	 * @param queryMap
	 * 		  name, uid, gid, comment, home, shell 
	 * @return 
	 * 		  json success/error
	 */
	@RequestMapping(value="/users/query", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> queryUser(@RequestParam Map<String, String> queryMap) {
		
		try {
			
			User user = makeUserFromMap(queryMap);
			
			if (user == null) {
				return new ResponseEntity<Object>(AppResponse.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			List<User> users = userRepository.findAny(user);
			
			if(users.isEmpty()) {
				return new ResponseEntity<Object>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(users), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
