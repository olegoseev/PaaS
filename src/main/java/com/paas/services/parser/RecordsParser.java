/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  File parse service interface
 */
package com.paas.services.parser;

import java.util.List;

public interface RecordsParser<MODEL> {
	/**
	 * Parse a file
	 * 
	 * @param value path to the file
	 * @return the list of objects
	 */
	List<MODEL> parseRecords(List<String> records);
}
