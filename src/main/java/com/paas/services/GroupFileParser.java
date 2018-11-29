/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  Parsing /etc/group file and create a list with Group objects
 */
package com.paas.services;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.Group;
import com.paas.utils.FileReader;

@Service
public class GroupFileParser implements FileParseService<Group> {

	private static FileReader fileReader;

	@PostConstruct
	private static void init() {
		if(fileReader == null) {
			fileReader = new FileReader();
		}
	}
	
	/**
	 * etc/group file parser
	 * 
	 * @param path to the file location
	 * @return list of Group objects
	 */
	@Override
	public List<Group> parse(Path path) throws PaaSApplicationException {

		// List of User objects found in the file
		List<Group> groups = Collections.emptyList();

		List<String> records = getRecords(path);
		
		if (records.isEmpty()) {
			return Collections.emptyList();
		}
		
		groups = parseRecords(records);

		return groups;
	}

	private List<Group> parseRecords(List<String> records) throws PaaSApplicationException {
		
		List<Group> groups = new LinkedList<>();
		
		try {
			// Parsing the file and store records in the list 
			for(String record : records) {
				
				String[] parts = record.split(":");
				Group group = new Group();
				group.setName(parts[0]);
				group.setGid(Integer.parseInt(parts[2]));
				
				// check if group members are present
				if (parts.length > 3) {
					group.setMembers(getMembers(parts[3]));
				} else {
					group.setMembers(Collections.emptyList());
				}
				
				groups.add(group);
			}			
		} catch (PatternSyntaxException pe) {
			throw new PaaSApplicationException(GroupFileParser.class, "Regular expression syntax error");
		} catch (NumberFormatException ne) {
			throw new PaaSApplicationException(GroupFileParser.class, "Fail to parse group id");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PaaSApplicationException(GroupFileParser.class, "File record parse error. Array index is out of bounds");
		}

		return groups;
	}

	private List<String> getRecords(Path path) {
		List<String> lines = fileReader.readFileInList(path);
		return lines;
	}
	
	/**
	 * 
	 * @param record of a group members. members separated by ","
	 * @return List of string objects
	 */
	private List<String> getMembers(String record) throws PaaSApplicationException {

		List<String> members = Collections.emptyList();
		try {
			String[] parts = record.split(",");
			members = new LinkedList<>(Arrays.asList(parts));
			
		} catch (PatternSyntaxException pe) {
			throw new PaaSApplicationException(GroupFileParser.class, "Regular expression syntax error");
		} catch (NullPointerException ne) {
			throw new PaaSApplicationException(GroupFileParser.class, "Fail to create a members group list");
		}
		
		return members;
	}

}
