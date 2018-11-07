package com.paas.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;


public class AppResponse {

	public static Map<String, Object> errorUserNotFound() {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("error", HttpStatus.NOT_FOUND);
		map.put("message", "User not found");
		
		return map;
	}
	
	public static Map<String, Object> errorGroupNotFound() {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("error", HttpStatus.NOT_FOUND);
		map.put("message", "Group not found");
		
		return map;
	}
	
	
	public static Map<String, Object> appError(String message) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("message", message);
		
		return map;
	}

	public static Map<String, Object> internalServerError() {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "error");
		map.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
		map.put("message", "Internal Server Error");
		
		return map;
	}

	public static Map<String, Object> successResult(Object obj) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", "OK");
		map.put("success", HttpStatus.OK);
		map.put("data", obj);
		
		return map;
	}
}
