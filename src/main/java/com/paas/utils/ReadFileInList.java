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

//@Component
public class ReadFileInList {

	/**
	 * Read whole file and returns a list with file lines
	 * 
	 * @param path
	 * 				path to file
	 */
	public static List<String> readFileInList(Path path) throws PaaSApplicationException {
		
		List<String> lines = Collections.emptyList();
		
		try {
			lines = Files.readAllLines(path, StandardCharsets.US_ASCII);
		} catch (IOException e) {
			throw new PaaSApplicationException("Error to read file " + path.toString());
		} catch (SecurityException se) {
			throw new PaaSApplicationException("Security exception occured during attempt to read the file " + path.toString());
		}
		
		return lines;
	}
}


