package com.founder.bussiness.log.dao;

import java.util.List;
import java.util.Map;

import com.founder.bussiness.log.entity.LogEntity;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;


@Repository
public class LogDao extends SqlSessionDaoSupport {

	/**
	 * 查询日志数量
	 * @return
	 * @throws Exception
	 */
	public String selectLogListCount(Map param) throws Exception {
		try{
			return (String)this.getSqlSessionTemplate().selectOne("log.selectLogListCount", param);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * 查询日志列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectLogList(Map param) throws Exception {
		try{
			return this.getSqlSessionTemplate().selectList("log.selectLogList", param);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * 添加日志信息
	 * @param logEntity
	 * @return
	 * @throws Exception
	 */
	public int addLog(LogEntity logEntity) throws Exception {
		try{
			int flag = 0;
			flag = this.getSqlSessionTemplate().insert("log.addLog",logEntity);
			return flag;
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * @Title: selectOptionTypeList
	 * @描述:操作类型字典
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: List<Map<String,String>>    返回类型
	 * @throws
	 */
	public List<Map<String,String>> selectOptionTypeList(Map param) throws Exception {
		try{
			return this.getSqlSessionTemplate().selectList("log.selectOptionTypeList", param);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * @Title: selectModuleList
	 * @描述:获取模块列表
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: List<Map<String,String>>    返回类型
	 * @throws
	 */
	public List<Map<String,String>> selectModuleList(Map param) throws Exception {
		try{
			return this.getSqlSessionTemplate().selectList("log.selectModuleList", param);
		}catch(Exception e){
			throw e;
		}
	}
}
