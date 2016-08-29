package com.sfexpress.tools.mysql.tbstructck.utils;

import org.slf4j.helpers.MessageFormatter;

public class StringFormatter {
	
	public static String format(String str, Object... objs) {
		return MessageFormatter.arrayFormat(str, objs).getMessage();
	}

}
