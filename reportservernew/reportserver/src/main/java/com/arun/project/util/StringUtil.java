package com.arun.project.util;

public class StringUtil {
	
	public static boolean isNullOrBlank(String s)
	{
	  return (s==null || s.trim().equals(""));
	}

}
