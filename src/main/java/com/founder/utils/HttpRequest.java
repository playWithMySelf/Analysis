package com.founder.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @类名: HttpRequest
 * @描述:(类描述)
 * @作者: jin_wei@founder.com
 * @日期: 2017年12月7日 上午9:49:12
 *
 */

public class HttpRequest {
	/**
	 * GET请求
	 * @param url：请求地址
	 * @param param：请求参数
	 * @return
	 */
	public static String sendGet(String url, String param) {
		StringBuffer result = new StringBuffer();
		BufferedReader br = null;
		try {
			String urlName = url;
			URL realUrl = new URL(urlName);
			// 打开URL之间的链接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
			// 建立实际链接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			br = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return java.net.URLDecoder.decode(result.toString(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---------http发送get请求异常。-----");
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	/**
	 * POST请求
	 * @param uri：请求地址
	 * @param params：请求参数
	 * @return
	 */
	public static String doPost(String uri,String params) {
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("connection", "Keep-Alive");
		httppost.setHeader("accept", "*/*");
		if(params!=null&&!params.equals("")){
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
		System.out.println(response.getStatusLine());
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
	//代理POST请求
    public static String doPost(String uri, Map<String,String> params) {
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


	/**
	 * @Title: sendPost
	 * @描述:post请求，控制参数编码
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: String    返回类型
	 * @throws
	 */
	public static String sendPost(String url, String param){
		OutputStreamWriter  out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try{
			URL realUrl = new URL(url);
			//打开URL链接
			URLConnection conn = realUrl.openConnection();
			//设置通用属性
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//发送POST请求必须设置如下属性
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");
			//获取URLConnection对象输出流
			out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
			out.write(param);

			out.flush();
			//定义BufferedReader输入流读取请求的响应

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while((line = in.readLine()) != null){
				result.append(line);
			}
			return java.net.URLDecoder.decode(result.toString(), "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("---------http发送post请求异常。-----");
			return null;
		}finally{
			try{
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
	}

    /**
     * @Title: sendPost
     * @描述:post请求，控制参数编码
     * @作者: jin_wei@founder.com
     * @参数: 传入参数定义
     * @返回值: String    返回类型
     * @throws
     */
    public static String sendPost2(String url, String param){
        OutputStreamWriter  out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try{
            URL realUrl = new URL(url);
            //打开URL链接
            URLConnection conn = realUrl.openConnection();
            //设置通用属性
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //发送POST请求必须设置如下属性
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            //获取URLConnection对象输出流
            out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            out.write(param);

            out.flush();
            //定义BufferedReader输入流读取请求的响应

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            return java.net.URLDecoder.decode(result.toString(), "UTF-8");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("---------http发送post请求异常。-----");
            return null;
        }finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }

	public static String httpPost(String url, String param){
		StringBuffer res = new StringBuffer();
		try{
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "GBK");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			OutputStream out = conn.getOutputStream();
			DataOutputStream dout = new DataOutputStream(out);
			dout.write(param.getBytes("GBK"));
			dout.flush();
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
			String line;
			while((line = in.readLine()) != null){
				res.append(line);
			}
			in.close();
			return java.net.URLDecoder.decode(res.toString(), "utf-8");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断URL地址是否可用
	 * @param url
	 * @return
	 */
	public static String isConnect(String url){
		try{
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			int state = conn.getResponseCode();
			if(state == 200){
				return url;
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}


	/**
	 * @Title: translateResult
	 * @描述:解析简单查询接口返回的数据
	 * @作者: jin_wei@founder.com
	 * @参数: res : 请求大数据接口返回的结果
	 * @参数: description : 请求大数据接口查询的内容描述
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public static Map translateResult(String res,String description){
		Map map = new HashMap();
		List rowlist = new ArrayList();
		String total = "0";
		if(res.indexOf("<Response>")>=0 && res.indexOf("Records")>=0 && res.indexOf("<Record>")>=0){
			try {
				String resJson = Xml2JsonUtil.xml2JSON(res);
				JSONObject jb = JSONObject.fromObject(resJson);
				JSONArray ja = ((JSONObject) jb.get("Response")).getJSONArray("Method");
				if(ja.size()>0){
					JSONArray resultArr = ((JSONObject) ja.get(0)).getJSONArray("Items");
					if(resultArr.size()>0){
						JSONArray result = ((JSONObject) resultArr.get(0)).getJSONArray("Item");
						JSONArray columnArr =((JSONObject) ((JSONObject) result.get(0)).getJSONArray("Value").get(0)).getJSONArray("Data");
						JSONArray rowsArrTemp = ((JSONObject) ((JSONObject) result.get(1)).getJSONArray("Value").get(0)).getJSONArray("Records");
						JSONArray rowsArr = ((JSONObject)rowsArrTemp.get(0)).getJSONArray("Record");
						total = (String) ((JSONObject) result.get(2)).getJSONArray("Value").get(0);

						if(columnArr.size()>0){
							for(int j=0 ; j<rowsArr.size(); j++){
								Map rowMap = new HashMap();
								for(int i=0;i<columnArr.size();i++){
									rowMap.put(columnArr.get(i), ((JSONObject) rowsArr.get(j)).getJSONArray("Data").get(i));
								}
								rowlist.add(rowMap);
							}
						}
					}

				}
				map.put("code", "EC00");
				map.put("msg", description+"成功!");
				map.put("total", total);
				map.put("rowlist", rowlist);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "EC01");
				map.put("msg", description+"失败!");
			}

		}else{
			System.out.println("无结果");
			map.put("code", "EC02");
			map.put("msg", "未请求到数据!");
		}
		return map;
	}

	/**
	 * @Title: translateResult
	 * @描述:解析复杂接口返回的数据
	 * @作者: jin_wei@founder.com
	 * @参数: res : 请求大数据接口返回的结果
	 * @参数: description : 请求大数据接口查询的内容描述
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public static Map translatComplieResult(String res,String description){
		Map map = new HashMap();
		List rowlist = new ArrayList();
		String total = "0";
		if(res.indexOf("<Response>")>=0 && res.indexOf("Records")>=0 && res.indexOf("<Record>")>=0){
			try {
				String resJson = Xml2JsonUtil.xml2JSON(res);
				JSONObject jb = JSONObject.fromObject(resJson);
				JSONArray ja = ((JSONObject) jb.get("Response")).getJSONArray("Method");
				if(ja.size()>0){
					JSONArray resultArr = ((JSONObject) ja.get(0)).getJSONArray("Items");
					if(resultArr.size()>0){
						JSONArray result = ((JSONObject) resultArr.get(0)).getJSONArray("Item");
						JSONArray columnArr =((JSONObject) ((JSONObject) result.get(0)).getJSONArray("Value").get(0)).getJSONArray("Data");
						JSONArray rowsArrTemp = ((JSONObject) ((JSONObject) result.get(1)).getJSONArray("Value").get(0)).getJSONArray("Records");
						JSONArray rowsArr = ((JSONObject)rowsArrTemp.get(0)).getJSONArray("Record");

						if(columnArr.size()>0){
							for(int j=0 ; j<rowsArr.size(); j++){
								Map rowMap = new HashMap();
								for(int i=0;i<columnArr.size();i++){
									rowMap.put(columnArr.get(i), ((JSONObject) rowsArr.get(j)).getJSONArray("Data").get(i));
								}
								rowlist.add(rowMap);
							}
						}
					}

				}
				map.put("code", "EC00");
				map.put("msg", description+"成功!");
				map.put("rowlist", rowlist);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "EC01");
				map.put("msg", description+"失败!");
			}

		}else{
			System.out.println("无结果");
			map.put("code", "EC02");
			map.put("msg", "未请求到数据!");
		}
		return map;
	}

    public static void main(String[] args) {
	    String par = "{\n" +
            "  \"query\": {\n" +
            "    \"bool\": {\n" +
            "      \"must\": [\n" +
            "        {\n" +
            "          \"term\": {\n" +
            "            \"city.keyword\": \"wuhan\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"range\": {\n" +
            "            \"endtime\": {\n" +
            "              \"gte\": \"2011-04-23T15:26:50.000Z\",\n" +
            "              \"lt\": \"2018-04-23T15:26:50.000Z\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"from\": 0,\n" +
            "  \"size\": 250,\n" +
            "  \"sort\": [\n" +
            "    {\n" +
            "      \"id\": {\n" +
            "        \"order\": \"desc\"\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"aggs\": {\n" +
            "    \"balance_stats\": {\n" +
            "      \"stats\": {\n" +
            "        \"field\": \"@timestamp\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
	    String res = sendPost2("http://127.0.0.1:9200/examinfo_type/doc/_search/",par);
        System.out.println(res);
    }

}
