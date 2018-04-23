package com.founder.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 常用方法
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class DataUtil {
	//日志
	private static Logger logger = Logger.getLogger(DataUtil.class.getName());

	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUniqId() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 验证地址是否可用，成功返回EC00，失败返回失败原因
	 * @param Url
	 * @return
	 */
	public static String checkUrl(String Url) {
		String msg = "";
		int state = -1;

		long a = System.currentTimeMillis();
		URL url;
		HttpURLConnection con = null;
		try {
			url = new URL(Url);
			con = (HttpURLConnection) url.openConnection();
			state = con.getResponseCode();
			logger.debug("验证URL地址："+url+"，结果："+state);
			if (state == 200) {
				msg = "EC00";
			} else {
				msg = "URL地址验证失败："+state;
			}
			InputStream is = con.getInputStream();
			is.close();

		} catch (Exception e) {
			msg = "URL地址验证失败："+e.getMessage();
		} finally {
			try{con.disconnect();}catch(Exception e){}
		}

		return msg;
	}

	/**
	 * 验证IP是否可用，成功返回EC00，失败返回失败原因
	 * @param ip
	 * @return
	 */
	public static String checkIp(String ip) {
		BufferedReader in = null;

		Runtime r = Runtime.getRuntime();
	    //超时时间500ms
	    int timeout = 500;
	    String pingCommand = "ping " + ip + " -n 1 -w " + timeout;

	    int index = 0;
	    while(index<5){
	       try {
	    	   ++index;
	           Process p = r.exec(pingCommand);
	           if (p == null) {
	               continue;
	           }
	           in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	           String line = null;
	           while ((line = in.readLine()) != null) {
	               if (line.startsWith("Reply from")) {
	                   return "EC00";
	               }else if(line.startsWith("来自")){
	            	   return "EC00";
	               }
	           }
	       } catch (Exception ex) {
	           logger.info("验证IP："+ip+"，次数："+index+"，异常："+ex.getMessage());
	       } finally {
	           try {
	               in.close();
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
	       }
	    }
		return "IP地址验证失败，连接超时！";
	}

	/**
	 * oracle数据库验证
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String checkOracle(String url,String userName,String password){
		Connection conn=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String lj="jdbc:oracle:thin:@";
			int data=url.indexOf("/");
			//打开一个数据库连接
			if(data!=-1){
				String m=url.substring(0,url.indexOf("/"));
				String n=url.substring(url.indexOf("/")+1,url.length());
				url=m+":"+n;
				url=lj+url;
			}
			conn=DriverManager.getConnection(url,userName,password);

			//判断数据库是否处于打开状态,返回一个布尔值，数据库状态打开的就向控制台打印false,反之打印true
			if(conn.isClosed()==false){
				conn.close();
				return "EC00";
			}
			return "数据库连接失败！";
		}catch (Exception e) {
			logger.info("数据库连接异常："+e.getMessage());
			//连接失败返回失败
			return "数据库连接异常："+e.getMessage();
		}
	}

	/**
	 * MD5加密密码
	 * @param pwd
	 * @return 32位编码
	 */
	public static String getMD5PassWord(String pwd){
		MessageDigest msd;
		try {
			msd = MessageDigest.getInstance("MD5");
			msd.reset();
			msd.update(pwd.getBytes());
			byte[] res = msd.digest();
			StringBuffer stb = new StringBuffer(res.length * 2);
			for (byte b : res) {
				stb.append(Character.forDigit((b & 240) >> 4, 16));
				stb.append(Character.forDigit(b & 15, 16));
			}
			return stb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

}
