package com.sfexpress.tools.mysql.tbstructck.utils;

import java.util.Set;
import java.util.TreeSet;

public class SetUtil {
	
	/**
	 * 求set1中set2没有的元素
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static <E> Set<E> getDiffSet(Set<E> set1, Set<E> set2) {
		Set<E> result = new TreeSet<E>();
		result.addAll(set1);
		result.removeAll(set2);
		return result;
	}
	
	/**
	 * 求set1和set2的交集
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static <E> Set<E> getInterSet(Set<E> set1, Set<E> set2) {
		Set<E> result = new TreeSet<E>();
		result.addAll(set1);
		result.retainAll(set2);
		return result;
	}

}
