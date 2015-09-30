package com.whcard.net;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {
	public static String httpClient(String actionName, String jsonString) {
		Log.d("post请求内容：", jsonString);
		String returnValue = null;
		HttpPost httpPost = new HttpPost(GetUrl.PreUrl + actionName);
		MultipartEntity reqEntity = new MultipartEntity();
		
		StringBody dataBody=null;
		try {
			dataBody = new StringBody(jsonString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		reqEntity.addPart("data", dataBody);
		httpPost.setEntity(reqEntity);
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,3000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
		HttpResponse rsp=null;
		try {
			rsp = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			returnValue="webserver is stop:ClientProtocolException";
			Log.d("returnValue", returnValue);
			return returnValue;
		} catch (IOException e) {
			returnValue="webserver is stop:IOException"+e.getLocalizedMessage();
			Log.d("returnValue", returnValue);
			return returnValue;
		}
		Log.d(actionName + "服务器状态码为：", rsp.getStatusLine().getStatusCode()+"");
		if (rsp.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
			HttpEntity httpEntity = rsp.getEntity();
			try {
				returnValue = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			returnValue="webserver is error";
			Log.d("服务器错误：", "服务器异常！");
		}
		Log.d(actionName + "服务器状态码为：", rsp.getStatusLine().getStatusCode()+"");
		Log.d("服务器返回值为：", returnValue);
		return returnValue;
	}

	public static String httpClient(String actionName, String jsonString,
			File imageFile) {
		Log.d("post请求内容：", jsonString);
		String returnValue = null;
		HttpPost httpPost = new HttpPost(GetUrl.PreUrl + actionName);
		MultipartEntity reqEntity = new MultipartEntity();
		
	    FileBody fileBody = new FileBody(imageFile);
		StringBody dataBody=null;
		try {
			dataBody = new StringBody(jsonString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		reqEntity.addPart("upload", fileBody);
		reqEntity.addPart("data", dataBody);
		httpPost.setEntity(reqEntity);
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,3000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);
		HttpResponse rsp=null;
		try {
			rsp = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			returnValue="webserver is stop";
			Log.d("returnValue", returnValue);
			return returnValue;
		} catch (IOException e) {
			returnValue="webserver is stop";
			Log.d("returnValue", returnValue);
			return returnValue;
		}
		if (rsp.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
			HttpEntity httpEntity = rsp.getEntity();
			try {
				returnValue = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else {
			returnValue="webserver is error";
		}
		Log.d(actionName + "服务器错误，服务器状态码为：", rsp.getStatusLine().getStatusCode()+"");
		Log.d("服务器返回值为：", returnValue);
		return returnValue;
	}

	public static String httpClient(String actionName, String jsonString,
			List<File> fileList) {
		Log.d("post请求内容：", jsonString);
		String returnValue = null;

		HttpPost httpPost = new HttpPost(GetUrl.PreUrl + actionName);
		MultipartEntity reqEntity = new MultipartEntity();
		
		for (File file : fileList) {
			FileBody fileBody = new FileBody(file);
			reqEntity.addPart("upload", fileBody);
		}    
		StringBody dataBody=null;
		try {
			dataBody = new StringBody(jsonString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		reqEntity.addPart("data", dataBody);
		httpPost.setEntity(reqEntity);
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,3000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,8000);
		HttpResponse rsp=null;
		try {
			rsp = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			returnValue="webserver is stop";
			Log.d("returnValue", returnValue);
			return returnValue;
		} catch (IOException e) {
			returnValue="webserver is stop";
			Log.d("returnValue", returnValue);
			return returnValue;
		}
		if (rsp.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
			HttpEntity httpEntity = rsp.getEntity();
			try {
				returnValue = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else {
			returnValue="webserver is error";
		}
		Log.d(actionName + "服务器错误，服务器状态码为：", rsp.getStatusLine().getStatusCode()+"");
		Log.d("服务器返回值为：", returnValue);
		return returnValue;
	}

}
