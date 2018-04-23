package com.founder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 文件工具类
 * @类名: FileUtil
 * @描述:(类描述)
 * @作者: jin_wei@founder.com
 * @日期: 2018年1月4日 上午10:35:48
 *
 */
public class FileUtil {

	/**
	 * @Title: readFile
	 * @描述:读取本地文件
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: String    返回类型
	 * @throws
	 */
	public static String readFile(String path){
		String laststr = "";
		File file = new File(path);
		BufferedReader reader = null;
		try {
			FileInputStream in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in , "UTF-8"));
			String tempStr = null;
			while((tempStr = reader.readLine()) != null){
				laststr = laststr + tempStr;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	public static void main(String[] args) {
		/*String jsonPath = FileUtil.class.getResource("/").getPath()+"com/founder/config/locationConfig.json";
		System.out.println("path:"+jsonPath);
		String jsonRes = readFile(jsonPath);
		JSONObject jo = JSONObject.fromObject(jsonRes);
		System.out.println(jo);
		Iterator<String> sIterator =  (Iterator<String>) jo.keys();
		List jsonList = new ArrayList();
		while(sIterator.hasNext()){
			Map map = new HashMap();
			String key = sIterator.next();
			JSONObject vj = jo.getJSONObject(key);
			map.put("ename", key);
			map.put("x", vj.getString("x"));
			map.put("y", vj.getString("y"));
			jsonList.add(map);
		}
		System.out.println(jsonList);*/
	}


}
