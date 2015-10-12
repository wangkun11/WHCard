package com.whcard.util;

import java.io.Serializable;

import org.json.JSONObject;

public class MyJsonObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
