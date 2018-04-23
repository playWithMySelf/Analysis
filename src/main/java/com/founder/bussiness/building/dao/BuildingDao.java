package com.founder.bussiness.building.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BuildingDao extends SqlSessionDaoSupport {
    /**
     * 查询日志列表
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> selectDicdataByTypeOrName(Map param) throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("building.selectDicdataByTypeOrName", param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
            * 查询日志列表
     * @return
             * @throws Exception
     */
    public List<Map<String,String>> selectAllBuildingInfo(Map param) throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("building.selectAllBuildingInfo", param);
        }catch(Exception e){
            throw e;
        }
    }


}
