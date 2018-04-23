package com.founder.bussiness.building.service.impl;

import com.founder.bussiness.building.dao.BuildingDao;
import com.founder.bussiness.building.service.IBuildingService;
import com.founder.bussiness.system.service.impl.SystemService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@Scope("prototype")
public class BuildingService implements IBuildingService {
    //log4j日志
    Logger logger = Logger.getLogger(SystemService.class.getName());
    @Resource
    private BuildingDao buildingDao;


    @Override
    public Map selectAllBuildingInfo(Map<String, String> param) {
        //返回
        Map returnMap = new HashMap();
        List buildingList = new ArrayList();

        try {
            //分页
            buildingList = buildingDao.selectAllBuildingInfo(param);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "EC01");
            returnMap.put("msg", "操作失败");
            return returnMap;
        }

        returnMap.put("rows", buildingList);
        returnMap.put("code", "EC00");
        returnMap.put("msg", "操作成功");
        return returnMap;
    }


    @Override
    public Map selectDicdataByTypeOrName(Map<String, String> param) {
        //返回
        Map returnMap = new HashMap();
        List rowlist = new ArrayList();

        try {
            //分页
            rowlist = buildingDao.selectDicdataByTypeOrName(param);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "EC01");
            returnMap.put("msg", "操作失败");
            return returnMap;
        }

        returnMap.put("rows", rowlist);
        returnMap.put("code", "EC00");
        returnMap.put("msg", "操作成功");
        return returnMap;
    }
}
