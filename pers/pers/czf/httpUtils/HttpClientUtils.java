/**
 * 
 */
package pers.czf.httpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author Administrator
 *
 */
public class HttpClientUtils {
	private static final String _charset = "UTF-8";

	/**
	 * 发送一个Get 请求, 使用默认的字符集编码.  且无参数值
	 * @param url
	 * @param param
	 * @return
	 */
    public static String doGet(String url){
    	return doGet(url,null,_charset);
    }
    
	/**
	 * 发送一个 Get 请求, 使用默认的字符集编码.  
	 * @param url
	 * @param param
	 * @return
	 */
    public static String doGet(String url,Map<String,String[]> param){
    	return doGet(url,param,_charset);
    }
    
    /**
     * 发送一个 Get 请求, 使用指定的字符集编码.
     * @param url
     * @param param
     * @param charset
     * @return
     */
	public static String doGet(String url,Map<String,String[]> param,String charset){
		
		//创建参数队列
    	List<NameValuePair> formParams =paramDeal(param);
		
		// 创建默认的httpClient实例
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	CloseableHttpResponse response = null;
    	
		try {
			String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(formParams, charset));  
			
			//创建httpget
			if(paramStr!=null&&!paramStr.isEmpty()){
				url = url+"?"+paramStr;
			}
	    	HttpGet httpGet = new HttpGet(url);
	    	
			response = httpClient.execute(httpGet);
			return resultDeal(response,charset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}finally{
			close(response,httpClient);
		}
	}
	
	/**
	 * 发送一个 Post 请求, 使用默认的字符集编码.  且无参数值
	 * @param url
	 * @param param
	 * @return
	 */
    public static String doPost(String url){
    	return doPost(url,null,_charset);
    }
    
	/**
	 * 发送一个 Post 请求, 使用默认的字符集编码.  
	 * @param url
	 * @param param
	 * @return
	 */
    public static String doPost(String url,Map<String,String[]> param){
    	return doPost(url,param,_charset);
    }
    
    /**
     * 发送一个 Post 请求, 使用指定的字符集编码.  
     * @param url
     * @param param
     * @param charsets
     * @return
     */
    public static String doPost(String url,Map<String,String[]> param,String charset){
    	
    	//创建参数队列
    	List<NameValuePair> formParams =paramDeal(param);
    	
    	// 创建默认的httpClient实例
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	CloseableHttpResponse response = null;
    	
    	try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams,charset);
			//创建HttpPost
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			return resultDeal(response,charset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}finally{
			close(response,httpClient);
		}
    }  
    
    /**
     * 创建参数队列
     * @param param
     * @return
     */
    public static List<NameValuePair> paramDeal(Map<String,String[]> param){
    	//创建参数队列
    	List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    	
    	if(param!=null&&!param.isEmpty()){
    		for(String key:param.keySet()){
    			String[] values = param.get(key);
    			for(String value:values){
    				formParams.add(new BasicNameValuePair(key,value));
    			}
    		}
    	}
    	
    	return formParams;
    }
    
    private static String resultDeal(CloseableHttpResponse response,String charset){
    	int status = response.getStatusLine().getStatusCode();
		String res = null;
		try {
			switch(status){
				case 200:
					res = EntityUtils.toString(response.getEntity(),charset);
					break;
				case 404:
					throw new Exception("错误代码404，您访问的地址不存在。");
				case 405:
					throw new Exception("错误代码405，用来访问本页面的 HTTP 方法不被允许。 ");
				case 500:
					throw new Exception("错误代码500，您访问的地址不存在。");
				default:
					throw new Exception("错误代码"+status+"!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
    	return res;
    }
    
    private static void close(CloseableHttpResponse response,CloseableHttpClient httpClient){
    	try{
			if(response!=null){
				response.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
