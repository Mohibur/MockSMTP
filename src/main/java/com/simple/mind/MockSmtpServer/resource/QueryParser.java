package com.simple.mind.MockSmtpServer.resource;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryParser {
	HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();

	public QueryParser(String query) {

		String[] strs = query.split("&");
		for (String s : strs) {
			String var, value;
			int pos = s.indexOf("=");
			var = s.substring(pos);
			value = s.substring(pos + 2, s.length());
			ArrayList<String> al = hashMap.get(var);
			if (al == null) {
				al = new ArrayList<String>();
				hashMap.put(var, al);
			}
			al.add(value);
		}
	}

	public ArrayList<String> getAllString(String value) {
		return hashMap.get(value);
	}

	public String getString(String value) {
		ArrayList<String> al = hashMap.get(value);
		if (al == null) {
			return null;
		}
		return al.get(0);
	}

	public String getString(String value, String def) {
		ArrayList<String> al = hashMap.get(value);
		if (al == null) {
			return def;
		}
		return al.get(0);
	}

	public Integer getInt(String value) {
		ArrayList<String> al = hashMap.get(value);
		if (al == null) {
			return null;
		}
		return Integer.valueOf(al.get(0));
	}

	public Integer getInt(String value, Integer def) {
		ArrayList<String> al = hashMap.get(value);
		if (al == null) {
			return def;
		}
		return Integer.valueOf(al.get(0));
	}
}
