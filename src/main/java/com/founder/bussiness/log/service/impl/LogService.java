package com.founder.bussiness.log.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.founder.bussiness.log.dao.LogDao;
import com.founder.bussiness.log.entity.LogEntity;
import com.founder.bussiness.log.service.ILogService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@SuppressWarnings("unchecked")
@Service
@Scope("prototype")
public class LogService implements ILogService {
	//缓存
	private static List<Map> configTemp = new ArrayList<Map>();

	//log4j日志
	Logger logger = Logger.getLogger(LogService.class.getName());

	/*数据库连接类*/
	@Resource
	private LogDao logDao;

	/**
	 * 查询日志记录
	 * @Title: selectLogList
	 * @描述:(方法描述)
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public Map selectLogList(Map<String,String> param) {
		//返回
		Map returnMap = new HashMap();


		//获取数量
		String total = "";
		try {
			total = this.logDao.selectLogListCount(param);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", "EC01");
			returnMap.put("msg", "操作失败");
			return returnMap;
		}

		//查询数量
		List rowlist = new ArrayList();
		try {
			//分页
			rowlist = this.logDao.selectLogList(param);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", "EC01");
			returnMap.put("msg", "操作失败");
			return returnMap;
		}

		returnMap.put("rows", rowlist);
		returnMap.put("total", total);
		returnMap.put("code", "EC00");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/**添加系统操作日志
	 * @Title: addLog
	 * @描述:(方法描述)
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: void    返回类型
	 * @throws
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class, ArithmeticException.class}, timeout=1)
	public void  addLog(LogEntity logEntity){
		try {
			this.logDao.addLog(logEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: selectOptionTypeList
	 * @描述:获取操作类型字典
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public Map selectOptionTypeList(Map param){
		Map reMap = new HashMap();
		List<Map<String,String>> listMap = null;
		try {
			listMap = this.logDao.selectOptionTypeList(param);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put("code", "EC01");
			reMap.put("msg" , "获取字典失败!");
		}
		reMap.put("code", "EC00");
		reMap.put("msg" , "操作成功!");
		reMap.put("rowlist" , listMap);
		return reMap;
	}


	/**
	 * @Title: selectModuleList
	 * @描述:获取模块列表
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public Map selectModuleList(Map param){
		Map reMap = new HashMap();
		List<Map<String,String>> listMap = null;
		try {
			listMap = this.logDao.selectModuleList(param);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put("code", "EC01");
			reMap.put("msg" , "获取字典失败!");
		}
		reMap.put("code", "EC00");
		reMap.put("msg" , "操作成功!");
		reMap.put("rowlist" , listMap);
		return reMap;
	}

	/**
	 * @Title: exportLog
	 * @描述:获取模块列表
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public Map exportLog(Map param){
		Map reMap = new HashMap();
		List rowlist = new ArrayList();
		try {
			rowlist =this.logDao.selectLogList(param);

		} catch (Exception e) {
			e.printStackTrace();
			reMap.put("code", "EC00");
			reMap.put("msg" , "导出日志失败!");
		}
		reMap.put("code", "EC00");
		reMap.put("msg" , "导出日志成功!");
		return reMap;
	}
}
