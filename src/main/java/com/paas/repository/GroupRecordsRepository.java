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
import com.paas.model.Group;
import com.paas.model.User;
import com.paas.services.GroupFileParser;
import com.paas.utils.Utils;

@Repository
public class GroupRecordsRepository implements BaseRepository<Integer, Group> {

	private static final Logger LOG = LoggerFactory.getLogger(GroupRecordsRepository.class);
	/**
	 * Users records will be stored in LinkedList.
	 * in case if needed to store a numerous of records
	 * probably would be better to store them in database
	 */
	private static List<Group> groupList = new ArrayList<>();
	
	// Path to the file
	@Value("${group.records}")
	private String groupFile;
	
	@Autowired
	GroupFileParser parser;
	
	public void updateRepository() throws PaaSApplicationException {

		 // Clear the list before updating it
		if(!groupList.isEmpty()) {
			groupList.clear();
		}
		
		try {
			 // Converting path string to path
			Path path = Paths.get(groupFile);

			 // Fill the list with new data
			groupList.addAll(parser.parse(path));
			
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
     * All groups
     * 
     * @return 
     * 			list of Group objects if found
     * 			an empty list if not
     */
	@Override
	public List<Group> findAll() throws PaaSApplicationException {
		
		updateRepository();
		
		// Simply return the list
		return groupList;
	}

    /**
     * Find all groups matching to input object.
     * 
     * @param group
     * 			 partially defined group object
     *            
     * @return 
     * 			list of Group object if found
     * 			an empty list if not
     */
	@Override
	public List<Group> findAny(Group group) throws PaaSApplicationException {
		
		updateRepository();
		
		List<Group> groups = Collections.emptyList();
		
		groups = Utils.concatenateTwoLists(groups, groupList);
		
		try {
			
			// Find a group which match to all given fields
			if(!group.getName().isEmpty()) {
				List<Group> _groups = groups.stream()
						.filter(g -> g.getName().equals(group.getName()))
						.collect(Collectors.toList());

				groups = _groups;
			}
			if(group.getGid() != AppConfig.GID_NOT_DEFINED) {
				List<Group> _groups = groups.stream()
						.filter(g -> g.getGid() == group.getGid())
						.collect(Collectors.toList());

				groups = _groups;
			}		
			if(!group.getMembers().isEmpty()) {
				List<Group> _groups = groups.stream()
						.filter(g -> g.getMembers().containsAll(group.getMembers()))
						.collect(Collectors.toList());
				groups = _groups;
			}
		} catch (NullPointerException e) {
			LOG.error("Null pointer exception");
			throw new PaaSApplicationException("Null pointer exception encountered");
			
		}
		
		return groups;
	}
	
    /**
     * Find group by Id
     * 
     * @param id
     * 			group Id
     *            
     * @return 
     * 			Group object if found
     * 			null if not found
     */
	@Override
	public Group findBy(Integer id) throws PaaSApplicationException {
		
		updateRepository();
		
		Optional<Group> group = groupList.stream()
									.filter(g -> g.getGid() == id)
									.findFirst();

		 // Make sure we found something
		if (group.isPresent()) {
			return group.get();
		}
		return null;
	}

	
	public List<Group> findAllGroupsForUser(User user) throws PaaSApplicationException {
		
		updateRepository();
		
		String name = user.getName();
		
		List<Group> groupA = groupList.stream()
										.filter(g -> (g.getMembers().contains(name)))
										.collect(Collectors.toList());
		
		List<Group> groupB = groupList.stream()
										.filter(g -> (g.getName().equals(name)))
										.collect(Collectors.toList());
		
		List<Group> groups = Utils.concatenateTwoLists(groupA, groupB);
		
		return groups;
	}
}
