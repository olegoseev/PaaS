package com.paas.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paas.PaaSApplicationException;
import com.paas.config.AppConfig;
import com.paas.model.Group;
import com.paas.model.User;
import com.paas.repository.dao.GroupRecordsDataReader;
import com.paas.utils.Utils;

@Repository
public class GroupRecordsRepository implements BaseRepository<Integer, Group> {


	@Autowired
	GroupRecordsDataReader reader;
	
    /**
     * All groups
     * 
     * @return list of Group objects if found
     * 			an empty list if not
     */
	@Override
	public List<Group> findAll() throws PaaSApplicationException {
		return reader.readData();
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
		
		List<Group> groups = reader.readData();
		
		try {
			
			groups = applyFilter(group, groups);
		} catch (NullPointerException e) {
			throw new PaaSApplicationException(GroupRecordsRepository.class, "Null pointer exception encountered");
			
		}
		
		return groups;
	}

	/**
	 * Apply filter to the stream
	 * @param group - object with fields that will be used as a filter
	 * @param groups - list to filter to be applied
	 * @return list of groups matching to given criteria
	 */
	private List<Group> applyFilter(Group group, List<Group> groups) {
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
		return groups;
	}
	
    /**
     * Find group by Id
     * 
     * @param group Id
     *            
     * @return Group object if found
     * 			null if not found
     */
	@Override
	public Group findBy(Integer id) throws PaaSApplicationException {

		List<Group> groups = reader.readData();
		
		Optional<Group> group = groups.stream()
									.filter(g -> g.getGid() == id)
									.findFirst();

		 // Make sure we found something
		if (group.isPresent()) {
			return group.get();
		}
		return null;
	}

	
	public List<Group> findAllGroupsForUser(User user) throws PaaSApplicationException {
		
		List<Group> groups = reader.readData();
		
		String name = user.getName();
		
		List<Group> groupA = groups.stream()
										.filter(g -> (g.getMembers().contains(name)))
										.collect(Collectors.toList());
		
		List<Group> groupB = groups.stream()
										.filter(g -> (g.getName().equals(name)))
										.collect(Collectors.toList());
		
		List<Group> combinedGroups = Utils.concatenateTwoLists(groupA, groupB);
		
		return combinedGroups;
	}
}
