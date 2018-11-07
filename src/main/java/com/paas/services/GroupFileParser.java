/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 *  Parsing /etc/group file and create a list with Group objects
 */
package com.paas.services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.Group;
import com.paas.utils.ReadFileInList;

@Service
public class GroupFileParser implements FileParseService<Group> {

	/**
	 * etc/group file parser
	 * 
	 * @param path
	 * 			  path to the file location
	 * 
	 * @return list of Group objects
	 */
	@Override
	public List<Group> parse(Path path) throws PaaSApplicationException {

		// List of User objects found in the file
		List<Group> groups = new ArrayList<>();

		try {
			// get list with file lines
			List<String> lines = ReadFileInList.readFileInList(path);
			
			if (!lines.isEmpty()) {
		
			// Parsing the file and store records in the list 
				for(String line : lines) {
					String[] parts = line.split(":");
					Group group = new Group();
					group.setName(parts[0]);
					group.setGid(Integer.parseInt(parts[2]));
					
					// check if group members are present
					if (parts.length > 3) {
						group.setMembers(getMembers(parts[3]));
					}
					
					groups.add(group);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PaaSApplicationException("File " + path.toString() + " error. Array index is out of bounds");
		}
	
		return groups;
	}
	
	/**
	 * 
	 * @param record
	 * 				a string of group members. members separated by "," (comma)
	 * @return List of string objects
	 */
	private List<String> getMembers(String record) {
		
		/**
		 * if record is empty return an empty list
		 */
		if(record.isEmpty()) {
			return Collections.emptyList();
		}
		
		String[] parts = record.split(",");
		
		List<String> members = new ArrayList<>(Arrays.asList(parts));
		
		return members;
	}

}
