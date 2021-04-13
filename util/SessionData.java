package application.util;

import java.util.HashMap;
import java.util.Map;

public class SessionData {
	private static Map<String, Integer> data;
	private static Map<String, String> searchData;
	public static Map<String, Integer> getSessionData(){
		if(data == null) {
			data = new HashMap<String, Integer>();
		}
		return data;
	}
	public static Map<String, String> getSearchData(){
		if(searchData == null) {
			searchData = new HashMap<String, String>();
		}
		return searchData;
	}
	
}
