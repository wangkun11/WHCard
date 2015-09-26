package com.whcard.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtilCallable implements Callable{
	
	private String actionName;
	private String jsonString;
	
	
	public HttpUtilCallable(String actionName, String jsonString) {
		super();
		this.actionName = actionName;
		this.jsonString = jsonString;
	}
	@Override
	public Object call() throws Exception {
		String returnString=httpClient(actionName,jsonString);
		return returnString;
	}
	private String httpClient(String actionName, String jsonString) {
		String returnValue = null;
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("data", jsonString));

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(GetUrl.PreUrl+actionName);
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse rsp = httpClient.execute(post);

			HttpEntity httpEntity = rsp.getEntity();
			returnValue = EntityUtils.toString(httpEntity);

			Log.d("����������ֵ", returnValue);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	
	
  /* //����һ��url,������ҳ����
   public static String doGet(String url) {
		String result="";
		InputStream is=null;
		
		BufferedReader bufferedReader=null;
		try {
			URL urlNet=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) urlNet.openConnection();					
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			
			conn.connect();
			if(conn.getResponseCode()==200)
			{
			is= conn.getInputStream();
			//BufferRead���Ϊ��Read(�ַ�������)�ķ�װ�����ܱ�Read��ǿ�����ܸ��ţ��乹�캯��ֻ����Read��Ϊ��������
			bufferedReader=new BufferedReader(new InputStreamReader(is));
			result = bufferedReader.readLine();
			Log.v("HttpUtil", result);	
			}else {
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.v("HttpUtil", "������ʴ���1��");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.v("HttpUtil", "������ʴ���2��");			
			return "";
			
		}finally{
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
   //get�������ļ����ȴ�С����   
   public static boolean doPost(String urlPath) throws Exception  
   {  
       URL url=new URL(urlPath);  
       HttpURLConnection con=(HttpURLConnection)url.openConnection();  
       con.setRequestMethod("GET");  
       con.setReadTimeout(5*1000);  
       if(con.getResponseCode()==200)  
       {  
           return true;  
       }  
       return false;  
   }  
   //post�������ļ����ȴ�С����   
   public static boolean doPost(String urlPath,Map<String,String> map) throws Exception  
   {  
       StringBuilder builder=new StringBuilder(); //ƴ���ַ�   
       //�ó���ֵ   
       if(map!=null && !map.isEmpty())  
       {  
           for(Map.Entry<String, String> param:map.entrySet())  
           {  
               builder.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "utf-8")).append('&');  
           }  
           builder.deleteCharAt(builder.length()-1); 
       }  
       //�����Content-Length: �����URL�Ķ��������ݳ���   
       byte b[]=builder.toString().getBytes();
      
       URL url=new URL(urlPath);  
       HttpURLConnection con=(HttpURLConnection)url.openConnection();  
       
       con.setRequestMethod("POST");  
       con.setReadTimeout(5*1000);  
       con.setDoOutput(true);//���������  
       con.setDoInput(true);
       con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//��������   
       con.setRequestProperty("Content-Length",String.valueOf(b.length));//����   
       con.connect();
       
       OutputStream outStream=con.getOutputStream();
       outStream.write(b);//д������   
       outStream.flush();//ˢ���ڴ�   
       outStream.close();  
       
       
       //״̬���ǲ��ɹ�   
       if(con.getResponseCode()==200)  
       {  
           return true;  
       }  
       return false;   
         
   }  
   
   public static boolean doPost(String actingPath,JSONObject jsonObject) throws Exception{
       //�����Content-Length: �����URL�Ķ��������ݳ���   
       byte b[]=jsonObject.toString().getBytes();
       URL url=new URL(actingPath);  
       HttpURLConnection con=(HttpURLConnection)url.openConnection();  
       
       con.setRequestMethod("POST");  
       con.setReadTimeout(5*1000);  
       con.setDoOutput(true);//���������  
       con.setDoInput(true);
       con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//��������   
       con.setRequestProperty("Content-Length",String.valueOf(b.length));//����   
       con.connect();
       
       OutputStream outStream=con.getOutputStream();
       outStream.write(b);//д������ 
       outStream.flush();//ˢ���ڴ�   
       outStream.close();  
       
       //״̬���ǲ��ɹ�   
       if(con.getResponseCode()==200)  
       {  
           return false;  
       }  
       return true;
	}*/
}
