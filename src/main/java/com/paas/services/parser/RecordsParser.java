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

import com.paas.PaaSApplicationException;

public interface RecordsParser<T> {
	/**
	 * Parse a file
	 * 
	 * @param records
	 * @return the list of objects
	 * @throws PaaSApplicationException
	 */
	List<T> parseRecords(List<String> records) throws PaaSApplicationException;
}
