/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  Response messages for rest controllers
 */
package com.paas.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;



public class AppResponse {

	/**
	 *  User not found
	 * @return
	 * 			map
	 */
	public static Map<String, Object> errorUserNotFound() {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("error", HttpStatus.NOT_FOUND);
		map.put("message", "User not found");
		
		return map;
	}
	
	/**
	 *  Group not found
	 * @return
	 * 			map
	 */
	public static Map<String, Object> errorGroupNotFound() {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("error", HttpStatus.NOT_FOUND);
		map.put("message", "Group not found");
		
		return map;
	}
	
	/**
	 *  General error
	 * @return
	 * 			map
	 */
	public static Map<String, Object> appError(String message) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("message", message);
		
		return map;
	}

	/**
	 *  Internal server error
	 * @return
	 * 			map
	 */
	public static Map<String, Object> internalServerError() {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
		map.put("message", "Internal Server Error");
		
		return map;
	}

	/**
	 *  Success
	 * @param Object that will be return back to client
	 * @return
	 * 			map
	 */
	public static Map<String, Object> successResult(Object obj) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "OK");
		map.put("success", HttpStatus.OK);
		map.put("data", obj);
		
		return map;
	}
}
