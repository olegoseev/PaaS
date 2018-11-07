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
		List<T> list = Stream.of(list1, list2)
						.flatMap(List::stream)
						.distinct()
						.collect(Collectors.toList());
		return list;
	}
}
