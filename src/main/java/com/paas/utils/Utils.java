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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

	/**
	 * Concatenate two lists
	 * 
	 * @param list1
	 * 				List object
	 * @param list2
	 * 				List object
	 * @return
	 * 		  List object containing elements from list1 and list2 
	 */
	public static <T> List<T> concatenateTwoLists (List<T> list1, List<T> list2) {
		
		if(list1.isEmpty()) {
			return newListFromList(list2);
		}
		
		if(list2.isEmpty()) {
			return newListFromList(list1);
		}
		
		List<T> list = Stream.of(list1, list2)
						.flatMap(List::stream)
						.distinct()
						.collect(Collectors.toList());
		return list;
	}
	
	private static <T> List<T> newListFromList(List<T> list) {
		List<T> al = new ArrayList<>();
		al.addAll(list);
		return al;
	}
}
