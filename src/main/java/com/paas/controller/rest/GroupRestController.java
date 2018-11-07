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
import com.paas.config.AppConfig;
import com.paas.model.Group;
import com.paas.repository.GroupRecordsRepository;
import com.paas.utils.AppResponse;

@RestController
public class GroupRestController {

	private static final Logger LOG = LoggerFactory.getLogger(GroupRestController.class);

	 // Group repository
	@Autowired
	GroupRecordsRepository groupRepository;
	
	/**
	 * Entry point to get all the groups
	 * @return
	 * 		  json success/error
	 */
	@RequestMapping(value="/groups", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllGroups() {
		
		try {
			// get group entities from repository
			List<Group> groups = groupRepository.findAll();
			
			// In case of empty list returned sending NO.CONTENT to client
			if(groups == null || groups.isEmpty()) {
				return new ResponseEntity<Object>(AppResponse.errorGroupNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(groups), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("Group Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Entry point to get a record for a group by group id
	 * @param id
	 * 			gid
	 * @return 
	 * 		  json success/error
	 */
	@RequestMapping(value="/groups/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getGroup(@PathVariable Integer id) {
		
		try {
			
			Group group = groupRepository.findBy(id);
			
			if(group == null) {
				return new ResponseEntity<Object>(AppResponse.errorGroupNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(group), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("Group Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Helper function. create User object from Map object where some keys may not available
	 * @param map
	 * @return User object
	 */
	private Group makeGroupFromMap(MultiValueMap<String, String> map) {
		
		Group group = new Group();
		
		try {
			
			group.setName(map.containsKey(AppConfig.GROUP_NAME) ? (String)map.get(AppConfig.GROUP_NAME).get(0) : AppConfig.EMPTY_STRING);
			group.setGid(map.containsKey(AppConfig.GROUP_GID) ? Integer.parseInt((String)map.get(AppConfig.GROUP_GID).get(0)) : AppConfig.GID_NOT_DEFINED);
			group.setMembers(map.containsKey(AppConfig.GROUP_MEMBER) ? map.get(AppConfig.GROUP_MEMBER) : Collections.emptyList());
			
		} catch (NullPointerException ne) {
			LOG.error(ne.getMessage());
			ne.printStackTrace();
			return null;
		} catch (ClassCastException cce) {
			LOG.error(cce.getMessage());
			cce.printStackTrace();
			return null;
		}
		return group;
	}
	
	/**
	 * Entry point to get all the groups based on query parameters. 
	 * Query string should have at least one parameter.
	 * 
	 * @param queryMap
	 * 		  name, gid, member(repeated) 
	 * @return 
	 * 		  json success/error
	 */
	@RequestMapping(value="/groups/query", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> queryUser(@RequestParam MultiValueMap<String, String> queryMap) {
		
	
		try {
			
			Group group = makeGroupFromMap(queryMap);
			
			if(group == null) {
				return new ResponseEntity<Object>(AppResponse.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			List<Group> groups = groupRepository.findAny(group);
			
			if(groups.isEmpty()) {
				return new ResponseEntity<Object>(AppResponse.errorGroupNotFound(), HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Object>(AppResponse.successResult(groups), HttpStatus.OK);
			
		} catch (PaaSApplicationException e) {
			LOG.error("User Repository encounter an error", e);
			return new ResponseEntity<Object>(AppResponse.appError(e.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
