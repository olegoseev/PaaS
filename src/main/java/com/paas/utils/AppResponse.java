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
