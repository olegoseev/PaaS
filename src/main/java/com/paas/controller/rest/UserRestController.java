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
import com.paas.model.Group;
import com.paas.model.User;
import com.paas.repository.GroupRecordsRepository;
import com.paas.repository.UserRecordsRepository;
import com.paas.utils.AppResponse;

import static com.paas.model.ModelDefaults.*;

@RestController
public class UserRestController {

	private static final Logger LOG = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private UserRecordsRepository userRepository;

	@Autowired
	private GroupRecordsRepository groupRepository;

	/**
	 * Entry point to get all users
	 * 
	 * @return JSON success/error
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllUsers() {
		try {
			// Get user entities from repository
			List<User> users = userRepository.findAll();

			// In case of empty list returned sending NO.CONTENT to client
			if (users == null || users.isEmpty()) {
				return new ResponseEntity<>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(AppResponse.successResult(users), HttpStatus.OK);

		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Entry point to get a user record by user Id
	 * 
	 * @param id UID
	 * @return JSON success/error
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getUser(@PathVariable("id") Integer id) {

		try {

			User user = userRepository.findBy(id);
			if (user == null) {
				return new ResponseEntity<>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(AppResponse.successResult(user), HttpStatus.OK);

		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Entry point to get all the groups for a user by user id
	 * 
	 * @param id UID
	 * @return JSON success/error
	 */
	@RequestMapping(value = "/users/{id}/groups", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllGroupsForUser(@PathVariable("id") Integer id) {

		try {
			User user = userRepository.findBy(id);

			if (user == null) {
				return new ResponseEntity<>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}

			List<Group> groups = groupRepository.findAllGroupsForUser(user);

			if (groups == null) {
				return new ResponseEntity<>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(AppResponse.successResult(groups), HttpStatus.OK);

		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Entry point to get all the users based on query parameters. Query string
	 * should have at least one parameter.
	 * 
	 * @param queryMap name, uid, gid, comment, home, shell
	 * @return JSON success/error
	 */
	@RequestMapping(value = "/users/query", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> queryUser(@RequestParam Map<String, String> queryMap) {

		try {

			User user = makeUserFromMap(queryMap);

			List<User> users = userRepository.findAny(user);

			if (users.isEmpty()) {
				return new ResponseEntity<>(AppResponse.errorUserNotFound(), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(AppResponse.successResult(users), HttpStatus.OK);

		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Helper function. create User object from Map object where some keys may not
	 * available
	 * 
	 * @param map
	 * @return User object
	 * @throws PaaSApplicationException 
	 */
	private User makeUserFromMap(Map<String, String> map) throws PaaSApplicationException {

		try {
			User user = new User();

			String name = extractUserName(map);
			user.setName(name);

			int uid = extractUserId(map);
			user.setUid(uid);

			int gid = extractUserGroupId(map);
			user.setGid(gid);

			String comments = extractUserComments(map);
			user.setComment(comments);

			String home = extractUserHomeDir(map);
			user.setHome(home);

			String shell = extractUserShell(map);
			user.setShell(shell);

			return user;

		} catch (NullPointerException ne) {
			throw new PaaSApplicationException(UserRestController.class, "Null pointer exception");
		} catch (ClassCastException cce) {
			throw new PaaSApplicationException(UserRestController.class, "Class cast exception");
		}
	}

	private String extractUserName(Map<String, String> map) {
		return map.containsKey(USER_NAME) ? (String) map.get(USER_NAME) : EMPTY_STRING;
	}

	private int extractUserId(Map<String, String> map) {
		return map.containsKey(USER_UID) ? Integer.parseInt((String) map.get(USER_UID)) : UID_NOT_DEFINED;
	}

	private int extractUserGroupId(Map<String, String> map) {
		return map.containsKey(USER_GID) ? Integer.parseInt((String) map.get(USER_GID)) : GID_NOT_DEFINED;
	}

	private String extractUserComments(Map<String, String> map) {
		return map.containsKey(USER_COMMENT) ? (String) map.get(USER_COMMENT) : EMPTY_STRING;
	}

	private String extractUserHomeDir(Map<String, String> map) {
		return map.containsKey(USER_HOME) ? (String) map.get(USER_HOME) : EMPTY_STRING;
	}

	private String extractUserShell(Map<String, String> map) {
		return map.containsKey(USER_SHELL) ? (String) map.get(USER_SHELL) : EMPTY_STRING;
	}
}
