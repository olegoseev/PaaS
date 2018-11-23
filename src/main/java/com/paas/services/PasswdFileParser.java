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
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.User;
import com.paas.utils.ReadFileInList;

@Service
public class PasswdFileParser implements FileParseService<User> {

	/**
	 * etc/password file parser
	 * 
	 * @param path to the file location
	 * 
	 * @return list of Group objects
	 */
	@Override
	public List<User> parse(Path path) throws PaaSApplicationException {
		/**
		 * List of User objects found in the file
		 */
		List<User> users = new ArrayList<>();
		
		try {
			//  get list with file lines
			List<String> lines = ReadFileInList.readFileInList(path);
			if (!lines.isEmpty()) {

				// Parsing the file and store records in the list 
				for(String line : lines) {
					
					String[] parts = line.split(":");
					User user = new User();
					user.setName(parts[0]);
					user.setUid(Integer.parseInt(parts[2]));
					user.setGid(Integer.parseInt(parts[3]));
					user.setComment(parts[4]);
					user.setHome(parts[5]);
					user.setShell(parts[6]);
					users.add(user);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PaaSApplicationException("File " + path.toString() + " error. Array index is out of bounds");
		}
		
		return users;
	}

}
