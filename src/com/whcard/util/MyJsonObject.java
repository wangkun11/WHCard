package com.whcard.util;

import org.json.JSONObject;

public class MyJsonObject {
	private JSONObject jsonObject;
	
	public MyJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getString(String key) {
	    return jsonObject.isNull(key) ? null : jsonObject.optString(key);
	}
	public String getInt(String key) {
	    return jsonObject.isNull(key) ? null : String.valueOf(jsonObject.optInt(key));
	}
}
