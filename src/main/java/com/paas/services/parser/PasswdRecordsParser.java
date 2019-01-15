/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
/**
 *  Parsing /etc/passwd file and create a list with User records
 */
package com.paas.services.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.User;

@Service
public class PasswdRecordsParser implements RecordsParser<User> {

	private final int NAME = 0;
	private final int UID = 2;
	private final int GID = 3;
	private final int COMMENTS = 4;
	private final int HOME = 5;
	private final int SHELL = 6;

	@Override
	public List<User> parseRecords(List<String> records) throws PaaSApplicationException {

		if (records.isEmpty()) {
			return Collections.emptyList();
		}
		List<User> users = new LinkedList<>();

		try {
			// Parsing the file and store records in the list
			records.stream().map((record) -> record.split(":")).map((parts) -> {
				User user = new User();
				user.setName(parts[NAME]);
				user.setUid(Integer.parseInt(parts[UID]));
				user.setGid(Integer.parseInt(parts[GID]));
				user.setComment(parts[COMMENTS]);
				user.setHome(parts[HOME]);
				user.setShell(parts[SHELL]);
				return user;
			}).forEachOrdered((user) -> {
				users.add(user);
			});

		} catch (PatternSyntaxException pe) {
			throw new PaaSApplicationException(PasswdRecordsParser.class, "Regular expression syntax error");
		} catch (NumberFormatException ne) {
			throw new PaaSApplicationException(PasswdRecordsParser.class, "Fail to parse group id");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PaaSApplicationException(PasswdRecordsParser.class,
					"File record parse error. Array index is out of bounds");
		}
		return users;
	}
}
