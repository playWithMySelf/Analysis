package com.founder.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2017/9/23.
 */
public class HttpUtil{
    //代理GET请求
    public static String doGet(String uri) {
        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(uri);

        // Execute the request
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = null;
            try {
                instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
                String temp = null;
                while((temp = reader.readLine())!=null){
                    sb.append(temp);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
//				httpget.abort();
//				httpclient.getConnectionManager().shutdown();
            }
        }
        httpclient.getConnectionManager().shutdown();
        return sb.toString();
    }

    //代理GET图片请求
    public static byte[] doGetImg(String url,String type) {
        if(type==null||"".equals(type))type="image/png";
        HttpURLConnection conn = null;
        // 发送数据
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置超时
            System.setProperty("sun.net.client.defultConnectionTimeout",
                    "10000");
            System.setProperty("sun.net.client.defultReadTimeout", "10000");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.connect();

            // 接收结果
            InputStream instream = conn.getInputStream();
            try {
                int len = 0;
                for(int a=1;a!=0;a++){
                    int leng = instream.available();
                    byte[] data = new byte[leng];
                    len = instream.read(data);
                    if(len!=-1){
                        return data;
                    }else{
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                instream.close();
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    //代理POST请求
    public static String doPost(String uri,Map<String,String> params) {
        HttpClient httpclient = new DefaultHttpClient();
        try{
            // Prepare a request object
            HttpPost httppost = new HttpPost(uri);
            httppost.setHeader("connection", "Keep-Alive");
            httppost.setHeader("accept", "*/*");
            System.setProperty("sun.net.client.defultConnectionTimeout", "10000");
            System.setProperty("sun.net.client.defultReadTimeout", "10000");

            if(params!=null&&params.size() > 0){
                //			HttpParams hp = new BasicHttpParams();
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Set<Map.Entry<String, String>> set = params.entrySet();
                Iterator<Map.Entry<String, String>> iterator = set.iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    nvps.add(nvp);
                }
                try {
                    if(nvps.size() > 0)
                        httppost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Execute the request
            HttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer sb = new StringBuffer();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = null;
                try {
                    instream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
                    String temp = null;
                    while((temp = reader.readLine())!=null){
                        sb.append(temp);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpclient.getConnectionManager().shutdown();
            return sb.toString();
        }catch(Exception e){
            return "";
        }
    }

    public static String doPost(String uri,String params) {
        HttpClient httpclient = new DefaultHttpClient();
        // Prepare a request object
        HttpPost httppost = new HttpPost(uri);
        httppost.setHeader("connection", "Keep-Alive");
        httppost.setHeader("accept", "*/*");
        if(params!=null&&!params.equals("")){
//				ContentType.create(ContentType.TEXT_XML, "utf-8");
//			HttpEntity entity = new StringEntity(params,ContentType.APPLICATION_FORM_URLENCODED);
            InputStream is = null;
            try {
                is = new ByteArrayInputStream(params.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            InputStreamEntity entity = new InputStreamEntity(is,-1);
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/x-www-form-urlencoded");
            httppost.setEntity(entity);
        }

//		HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 1000);
//		HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);
        // Execute the request
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = null;
            try {
                instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
                String temp = null;
                while((temp = reader.readLine())!=null){
                    sb.append(temp);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        httpclient.getConnectionManager().shutdown();
        return sb.toString();
    }
    /**
     * 发送请求
     * @param pControlURL
     * @param data
     * @return
     */
    public static Map sendReuest(String pControlURL,String data){
        Map remap = new HashMap();
        HttpURLConnection conn = null;
        // 发送数据
        try {
            conn = (HttpURLConnection) new URL(pControlURL).openConnection();
            //设置超时
            System.setProperty("sun.net.client.defultConnectionTimeout", "10000");
            System.setProperty("sun.net.client.defultReadTimeout", "10000");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            out.write(data);
            out.flush();
            out.close();

            // 接收结果
            InputStream is = conn.getInputStream();
            String result = inputStream2String(is);

            System.out.println(result);

            remap.put("result", result);
            remap.put("code", "success");
            return remap;
        } catch (SocketTimeoutException e){
            remap.put("code", "timeout");
            remap.put("msg", "请求超时！");
            return remap;
        } catch (Exception e) {
            e.printStackTrace();
            remap.put("code", "error");
            remap.put("msg", "请求失败！");
            return remap;
        }finally{
            conn.disconnect();
        }
    }

    //解析回参
    private static String inputStream2String(InputStream is) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return new String(baos.toByteArray(),"UTF-8");
    }

    public static String postRequestBody(String url, String content) throws ClientProtocolException, IOException{
	    HttpClient hc = new DefaultHttpClient();
		HttpResponse hr = null;
		HttpGet hget = null;
		HttpPost hpost = null;



		hpost = new HttpPost(url);
		StringEntity se = new StringEntity(content,"UTF-8");
		hpost.setEntity(se);
		hr = hc.execute(hpost);

		if(hr!=null){
			 InputStream instream = hr.getEntity().getContent();
		     try {
	    		 byte[] bytes = new byte[1024*4];
	    		 int size = 0;
	    		 BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
	    		 StringBuilder sb = new StringBuilder();
                 String temp = null;
                 while((temp = reader.readLine())!=null){
                     sb.append(temp);
                 }
                 return sb.toString();
		     } catch(IOException e){
		    	 e.printStackTrace();
		     } catch (RuntimeException ex) {
		         // In case of an unexpected exception you may want to abort
		         // the HTTP request in order to shut down the underlying
		         // connection and release it back to the connection manager.

		    	 if(hget!=null)
		    		 hget.abort();
		    	 else if(hpost!=null)
		    		 hpost.abort();
		         throw ex;
		     } finally {
		         // Closing the input stream will trigger connection release
		         instream.close();
		     }
		     // When HttpClient instance is no longer needed,
		     // shut down the connection manager to ensure
		     // immediate deallocation of all system resources
		     hc.getConnectionManager().shutdown();
		}
		return content;


	}

	// 处理GBK编码问题
	private boolean isEncodingValid(String str) {
		for (int f = 0; f < str.length(); ++f) {
			if (str.charAt(f) == 65533)
				return false;
		}
		return true;
	}

    public static void main(String[] args) {
    	String url = "http://10.60.148.54:8080/EZSPservice/QueryDataServlet";
		String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Request><SenderID>17a0fcdff0a642a3a6783548d37de5d3</SenderID><Method><Name>Query_V_GXST_130336175279687500</Name><Security Algorithm=\"\"></Security></Method><Items><Item><Name>DataObjectCode</Name><Value Type=\"string\"><Data>01020401071</Data></Value></Item><Item><Name>Condition</Name><Value Type=\"string\"><Data>CREATEDTIME>=TO_DATE('2014/1/8 10:59:38','yyyy/mm/dd hh24:mi:ss')  </Data></Value></Item><Item><Name>RequiredItems</Name><Value Type=\"arrayof_string\"> <Data>F05</Data> <Data>F03</Data> </Value> </Item> <Item> <Name>Order</Name> <Value Type=\"arrayof_string\"> <Data>CREATEDTIME</Data> </Value> </Item> <Item><Name>StartPosition</Name><Value Type=\"long\">  				<Data>0</Data>  			</Value>  		</Item>  		<Item> 			<Name>MaxResultCount</Name> 			<Value Type=\"long\"> 				<Data>100</Data> 			</Value> 		</Item> <Item> 			<Name> RequestResultCount</Name> <Value Type=\"boolean\"> 				<Data>false</Data> 			</Value>  	     </Item>  	</Items> </Request>";
		Map res = null;
			res = HttpUtil.sendReuest(url, content);
		System.out.println(res);
	}
}
