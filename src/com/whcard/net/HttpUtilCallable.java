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

			Log.d("服务器返回值", returnValue);

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

	
	
  /* //传递一个url,返回网页内容
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
			//BufferRead理解为对Read(字符输入流)的封装，功能比Read更强，性能更优，其构造函数只能以Read作为参数传入
			bufferedReader=new BufferedReader(new InputStreamReader(is));
			result = bufferedReader.readLine();
			Log.v("HttpUtil", result);	
			}else {
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.v("HttpUtil", "网络访问错误1！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.v("HttpUtil", "网络访问错误2！");			
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
   //get请求，有文件长度大小限制   
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
   //post请求，无文件长度大小限制   
   public static boolean doPost(String urlPath,Map<String,String> map) throws Exception  
   {  
       StringBuilder builder=new StringBuilder(); //拼接字符   
       //拿出键值   
       if(map!=null && !map.isEmpty())  
       {  
           for(Map.Entry<String, String> param:map.entrySet())  
           {  
               builder.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "utf-8")).append('&');  
           }  
           builder.deleteCharAt(builder.length()-1); 
       }  
       //下面的Content-Length: 是这个URL的二进制数据长度   
       byte b[]=builder.toString().getBytes();
      
       URL url=new URL(urlPath);  
       HttpURLConnection con=(HttpURLConnection)url.openConnection();  
       
       con.setRequestMethod("POST");  
       con.setReadTimeout(5*1000);  
       con.setDoOutput(true);//打开向外输出  
       con.setDoInput(true);
       con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//内容类型   
       con.setRequestProperty("Content-Length",String.valueOf(b.length));//长度   
       con.connect();
       
       OutputStream outStream=con.getOutputStream();
       outStream.write(b);//写入数据   
       outStream.flush();//刷新内存   
       outStream.close();  
       
       
       //状态码是不成功   
       if(con.getResponseCode()==200)  
       {  
           return true;  
       }  
       return false;   
         
   }  
   
   public static boolean doPost(String actingPath,JSONObject jsonObject) throws Exception{
       //下面的Content-Length: 是这个URL的二进制数据长度   
       byte b[]=jsonObject.toString().getBytes();
       URL url=new URL(actingPath);  
       HttpURLConnection con=(HttpURLConnection)url.openConnection();  
       
       con.setRequestMethod("POST");  
       con.setReadTimeout(5*1000);  
       con.setDoOutput(true);//打开向外输出  
       con.setDoInput(true);
       con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//内容类型   
       con.setRequestProperty("Content-Length",String.valueOf(b.length));//长度   
       con.connect();
       
       OutputStream outStream=con.getOutputStream();
       outStream.write(b);//写入数据 
       outStream.flush();//刷新内存   
       outStream.close();  
       
       //状态码是不成功   
       if(con.getResponseCode()==200)  
       {  
           return false;  
       }  
       return true;
	}*/
}
