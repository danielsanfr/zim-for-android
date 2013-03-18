package com.danielsanfr.zimandroidwiki.controller;

public class PageControl {
	
	public static String getNameFile(String title) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < title.length(); i++) {
			if (title.charAt(i) == ' ') {
				stringBuffer.append('_');
			} else
				stringBuffer.append(title.charAt(i));
		}
		return stringBuffer.toString() + ".txt";
	}
	
	public static String getNameDirectory(String title) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < title.length(); i++) {
			if (title.charAt(i) == ' ') {
				stringBuffer.append('_');
			} else
				stringBuffer.append(title.charAt(i));
		}
		return stringBuffer.toString() + ".txt";
	}
	
}
