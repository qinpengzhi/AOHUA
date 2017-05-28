package com.aohua.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



@SuppressWarnings("deprecation")
public class HttpUtil {
	public static final String BASE_URL="http://172.18.14.1:8080/AOHUAServlet/";
	public static String getRequest(String action) throws InterruptedException, ExecutionException{
				String url=BASE_URL+action;
				HttpClient httpClient=new DefaultHttpClient();
				HttpGet get=new HttpGet(url);
				HttpResponse httpResponse;
				try {
					httpResponse = httpClient.execute(get);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						String result=EntityUtils.toString(httpResponse.getEntity());
						return result;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}				
				return null;			
	}
	public static String postRequest(String url,Map<String,String> rawParams) throws InterruptedException, ExecutionException{
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost post=new HttpPost(url);
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				for(String key:rawParams.keySet()){
					params.add(new BasicNameValuePair(key, rawParams.get(key)));
				}
				//设置请求参数
				try {
					post.setEntity(new UrlEncodedFormEntity(params,"gbk"));
					HttpResponse httpResponse=httpClient.execute(post);
					if(httpResponse.getStatusLine().getStatusCode()==200){;
						String result=EntityUtils.toString(httpResponse.getEntity());
						return result;
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
		
	}
}
