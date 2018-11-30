/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
/**
 *  Parsing /etc/passwd file and create a list with User records
 */
package com.paas.services;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.User;
import com.paas.utils.FileReader;

@Service
public class PasswdFileParser implements FileParseService<User> {
	
	private static FileReader fileReader;

	@PostConstruct
	private static void init() {
		if(fileReader == null) {
			fileReader = new FileReader();
		}
	}
	
		
	/**
	 * etc/password file parser
	 * 
	 * @param path to the file location
	 * 
	 * @return list of Group objects
	 */
	@Override
	public List<User> parse(Path path) throws PaaSApplicationException {
		
		List<String> records = getRecords(path);
		
		if (records.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<User> users = parseRecords(records);
		
		return users;
	}
	
	private List<User> parseRecords(List<String> records) throws PaaSApplicationException {
		List<User> users = new LinkedList<>();
		
		try {
			// Parsing the file and store records in the list 
			for(String record : records) {
				
				String[] parts = record.split(":");
				User user = new User();
				user.setName(parts[0]);
				user.setUid(Integer.parseInt(parts[2]));
				user.setGid(Integer.parseInt(parts[3]));
				user.setComment(parts[4]);
				user.setHome(parts[5]);
				user.setShell(parts[6]);
				users.add(user);
			}
		} catch (PatternSyntaxException pe) {
			throw new PaaSApplicationException(PasswdFileParser.class, "Regular expression syntax error");
		} catch (NumberFormatException ne) {
			throw new PaaSApplicationException(PasswdFileParser.class, "Fail to parse group id");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PaaSApplicationException(PasswdFileParser.class, "File record parse error. Array index is out of bounds");
		}
		
		return users;
	}
	
	private List<String> getRecords(Path path) {
		List<String> records = fileReader.readFileInList(path);
		return records;
	}

}
