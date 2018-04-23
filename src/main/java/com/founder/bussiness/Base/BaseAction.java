package com.founder.bussiness.Base;

/**
 * Created by playWithMyself on 2018/1/9.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * action请求基类
 * @author yangm
 */
public class BaseAction extends MultiActionController{
    /**
     * 装换数据为json对象,并写道客户端.
     * @param obj 数据对象
     * @param response 响应对象
     */
    public void WriteJsonObjToPage(Object obj,HttpServletResponse response){


        // 把文件数据json格式化
        String jsonString = "";
        /*JSONArray resultJson = JSONArray.fromObject(obj);
        if (resultJson != null) {
            jsonString = resultJson.toString();
        }*/
        JSONObject resultJson = JSONObject.fromObject(obj);
        if (resultJson != null) {
            jsonString = resultJson.toString();
        }

        // 写到客户端,页面展示.
        try {
			/* 数据库编码为GBK 取出数据后要显示转码 */
            response.getOutputStream().write(jsonString.getBytes("UTF-8"));
            response.flushBuffer();
        } catch (IOException e) {}

        return;
    }

    /**
     * 装换数据为json对象,并写道客户端.
     * @param obj 数据对象
     * @param response 响应对象
     * @param codeType 设置编码类型
     */
    public void WriteJsonObjToPage(Object obj,HttpServletResponse response,String codeType){

        if(!"UTF-8".equals(codeType)&&!"gbk".equals(codeType)){
            codeType = "UTF-8";
        }

        // 把文件数据json格式化
        String jsonString = "";
        JSONArray resultJson = JSONArray.fromObject(obj);
        if (resultJson != null) {
            jsonString = resultJson.toString();
        }

        // 写到客户端,页面展示.
        try {
			/* 数据库编码为GBK 取出数据后要显示转码 */
            response.getOutputStream().write(jsonString.getBytes(codeType));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    /**
     * 装换数据为json对象,并写道客户端.
     * @param obj 数据对象
     * @param response 响应对象
     * @param codeType 设置编码类型
     */
    public void WriteJsonObjToPage(Object obj,HttpServletResponse response,String codeType,String type){

        if(!"UTF-8".equals(codeType)&&!"gbk".equals(codeType)){
            codeType = "UTF-8";
        }

        // 把文件数据json格式化
        String jsonString = "";
        if("jsonarr".equals(type)){
            JSONArray resultJson = JSONArray.fromObject(obj);
            if (resultJson != null) {
                jsonString = resultJson.toString();
            }
        }else{
            JSONObject resultJson = JSONObject.fromObject(obj);
            if (resultJson != null) {
                jsonString = resultJson.toString();
            }
        }

        // 写到客户端,页面展示.
        try {
			/* 数据库编码为GBK 取出数据后要显示转码 */
            response.getOutputStream().write(jsonString.getBytes(codeType));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    /**
     * 向客户端写xml或text内容
     * @param textOrXml xml或text内容
     * @param response 响应对象
     */
    public void wirteTextOrXml(String textOrXml,HttpServletResponse response){
        // 写道客户端,页面展示.
        try {
            response.setContentType("text/xml");
            response.setCharacterEncoding("GBK");
            response.getOutputStream().write(textOrXml.getBytes());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取客户端参数通用方法
     *    将前台参数装载到一个泛型Map里面
     * @param req
     * @return  Map<String,Object>
     */
    @SuppressWarnings("unchecked")
    public Map<String,String> getParameterMap(HttpServletRequest req){
        // 待返回的客户端参数
        Map<String,String> clientMap = new HashMap<String,String>();
        clientMap.put("messageCode", "");
        clientMap.put("messageContent", "");

        // 获取客户端请求参数Map
        Map<String,String[]> parameterMap = req.getParameterMap();

        //生成迭代器
        Iterator<String> paramIterator = parameterMap.keySet().iterator();

        //遍历客户端参数以装载至待返回的客户端参数
        while(paramIterator.hasNext()){
            String key = paramIterator.next();
            if("method".equals(key)){
                //continue;
            }

            clientMap.put(key, parameterMap.get(key)[0]);
        }
        return clientMap;
    }

    /**
     * 获取客户端参数通用方法
     *    将前台参数装载到一个泛型Map里面
     * @param req
     * @return  Map<String,Object>
     */
    public Map<String,Object> getParameterMap2(HttpServletRequest req){
        // 待返回的客户端参数
        Map<String,Object> clientMap = new HashMap<String,Object>();
        clientMap.put("messageCode", "");
        clientMap.put("messageContent", "");

        // 获取客户端请求参数Map
        Map<String,String[]> parameterMap = req.getParameterMap();

        //生成迭代器
        Iterator<String> paramIterator = parameterMap.keySet().iterator();

        //遍历客户端参数以装载至待返回的客户端参数
        while(paramIterator.hasNext()){
            String key = paramIterator.next();
            if("method".equals(key)){
                //continue;
            }

            clientMap.put(key, parameterMap.get(key)[0]);
        }
        return clientMap;
    }
}
