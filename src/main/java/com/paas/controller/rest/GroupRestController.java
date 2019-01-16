package com.paas.controller.rest;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paas.PaaSApplicationException;
import com.paas.model.Group;
import com.paas.repository.GroupRecordsRepository;
import com.paas.utils.AppResponse;

import static com.paas.model.ModelDefaults.*;

@RestController
public class GroupRestController {

	private static final Logger LOG = LoggerFactory.getLogger(GroupRestController.class);

	@Autowired
	GroupRecordsRepository groupRepository;

	/**
	 * Entry point to get all the groups
	 * 
	 * @return JSON success/error
	 */
	@RequestMapping(value = "/groups", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllGroups() {

		try {
			// get group entities from repository
			List<Group> groups = groupRepository.findAll();

			// In case of empty list returned sending NO.CONTENT to client
			if (groups == null || groups.isEmpty()) {
				return new ResponseEntity<>(AppResponse.errorGroupNotFound(), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(AppResponse.successResult(groups), HttpStatus.OK);

		} catch (PaaSApplicationException e) {
			LOG.error("Group Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Entry point to get a record for a group by group id
	 * 
	 * @param id gid
	 * @return json success/error
	 */
	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getGroup(@PathVariable Integer id) {

		try {

			Group group = groupRepository.findBy(id);

			if (group == null) {
				return new ResponseEntity<>(AppResponse.errorGroupNotFound(), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(AppResponse.successResult(group), HttpStatus.OK);

		} catch (PaaSApplicationException e) {
			LOG.error("Group Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Entry point to get all the groups based on query parameters. Query string
	 * should have at least one parameter.
	 * 
	 * @param queryMap name, gid, member(repeated)
	 * @return json success/error
	 */
	@RequestMapping(value = "/groups/query", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> queryGroups(@RequestParam MultiValueMap<String, String> queryMap) {

		try {
			Group group = makeGroupFromMap(queryMap);
			List<Group> groups = groupRepository.findAny(group);

			if (groups.isEmpty()) {
				return new ResponseEntity<>(AppResponse.errorGroupNotFound(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(AppResponse.successResult(groups), HttpStatus.OK);
		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

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
	private Group makeGroupFromMap(MultiValueMap<String, String> map) throws PaaSApplicationException {
		try {
			Group group = new Group();
			String name = extractGroupName(map);
			group.setName(name);

			int gid = extractGroupId(map);
			group.setGid(gid);

			List<String> members = extractGroupMembers(map);
			group.setMembers(members);

			return group;
		} catch (NullPointerException ne) {
			throw new PaaSApplicationException(GroupRestController.class, "Null pointer exception");
		} catch (ClassCastException cce) {
			throw new PaaSApplicationException(GroupRestController.class, "Class cast exception");
		}
	}

	private String extractGroupName(MultiValueMap<String, String> map) {
		return map.containsKey(GROUP_NAME) ? (String) map.get(GROUP_NAME).get(0) : EMPTY_STRING;
	}

	private int extractGroupId(MultiValueMap<String, String> map) {
		return map.containsKey(GROUP_GID) ? Integer.parseInt((String) map.get(GROUP_GID).get(0)) : GID_NOT_DEFINED;
	}

	private List<String> extractGroupMembers(MultiValueMap<String, String> map) {
		return map.containsKey(GROUP_MEMBER) ? map.get(GROUP_MEMBER) : Collections.emptyList();
	}
}
