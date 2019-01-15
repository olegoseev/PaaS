/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  Parsing /etc/group file and create a list with Group objects
 */
package com.paas.services.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.Group;

@Service
public class GroupRecordsParser implements RecordsParser<Group> {

	private final int NAME = 0;
	private final int GID = 2;
	private final int MEMBERS = 3;


        @Override
	public List<Group> parseRecords(List<String> records) throws PaaSApplicationException {

		if (records.isEmpty()) {
			return Collections.emptyList();
		}

		List<Group> groups = new LinkedList<>();

		try {
			for (String record : records) {

				String[] parts = record.split(":");
				Group group = new Group();
				group.setName(parts[NAME]);
				group.setGid(Integer.parseInt(parts[GID]));

				if (hasMembers(parts)) {
					group.setMembers(getMembers(parts[MEMBERS]));
				} else {
					group.setMembers(Collections.emptyList());
				}

				groups.add(group);
			}
		} catch (PatternSyntaxException pe) {
			throw new PaaSApplicationException(GroupRecordsParser.class, "Regular expression syntax error");
		} catch (NumberFormatException ne) {
			throw new PaaSApplicationException(GroupRecordsParser.class, "Fail to parse group id");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PaaSApplicationException(GroupRecordsParser.class,
					"File record parse error. Array index is out of bounds");
		}

		return groups;
	}

	// members in the group records is a third argument
	private boolean hasMembers(String[] ar) {
		return ar.length > 3;
	}

	/**
	 * @param record of a group members. members separated by ","
	 * @return List of string objects
	 */
	private List<String> getMembers(String record) throws PaaSApplicationException {

		List<String> members = Collections.emptyList();
		try {
			String[] parts = record.split(",");
			members = new LinkedList<>(Arrays.asList(parts));

		} catch (PatternSyntaxException pe) {
			throw new PaaSApplicationException(GroupRecordsParser.class, "Regular expression syntax error");
		} catch (NullPointerException ne) {
			throw new PaaSApplicationException(GroupRecordsParser.class, "Fail to create a members group list");
		}

		return members;
	}

}
