package com.founder.bussiness.rktj.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class RktjDao extends SqlSessionDaoSupport {


	/**
	 * 人口类型统计
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> getRktj(Map param) throws Exception {
		try{
			return this.getSqlSessionTemplate().selectList("rktj.getRktj", param);
		}catch(Exception e){
			throw e;
		}
	}

    /**
     * 单位类型统计
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> getDwtj(Map param) throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("rktj.getDwtj", param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 单位类型统计
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> exportDwtj(Map param) throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("rktj.exportDwtj", param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 人口类型统计
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> exportRktj(Map param) throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("rktj.exportRktj", param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 房屋类型统计
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> exportFwtj(Map param) throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("rktj.exportFwtj", param);
        }catch(Exception e){
            throw e;
        }
    }


}
