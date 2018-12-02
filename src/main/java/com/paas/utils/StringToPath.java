/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 * Helper class. Converts string path to file to path object  
 * 
 */
package com.paas.utils;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.paas.PaaSApplicationException;

public class StringToPath {

	public static Path getPath(String pathToFile) {

		try {
			Path path = Paths.get(pathToFile);
			return path;
		} catch (InvalidPathException e) {
			throw new PaaSApplicationException(StringToPath.class, "Invalid Path Exception");
		} catch (UnsupportedOperationException e) {
			throw new PaaSApplicationException(StringToPath.class, "Unsupported Operation Exception");
		} catch (NullPointerException e) {
			throw new PaaSApplicationException(StringToPath.class, "NullPointer Exception");
		} catch (ClassCastException e) {
			throw new PaaSApplicationException(StringToPath.class, "Class Cast Exception");
		} catch (IllegalArgumentException e) {
			throw new PaaSApplicationException(StringToPath.class, "Illegal Argument Exception");
		}
	}
}
