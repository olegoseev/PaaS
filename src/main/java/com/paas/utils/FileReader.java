/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 * Helper class. read all lines from a file and return a collection  
 * 
 */
package com.paas.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import com.paas.PaaSApplicationException;

public class FileReader {

	/**
	 * Read whole file and returns a list with file lines
	 * 
	 * @param path to file
        * @return 
        * @throws com.paas.PaaSApplicationException
	 */
	public List<String> readFileInList(Path path) throws PaaSApplicationException {

		List<String> lines = Collections.emptyList();

		try {
			lines = Files.readAllLines(path, StandardCharsets.US_ASCII);
		} catch (IOException e) {
			throw new PaaSApplicationException("IO exception occured during attempt to read file " + path.toString());
		} catch (SecurityException se) {
			throw new PaaSApplicationException(
					"Security exception occured during attempt to read the file " + path.toString());
		}
		return lines;
	}
}
