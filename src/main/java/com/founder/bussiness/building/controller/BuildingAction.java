package com.founder.bussiness.building.controller;

import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.building.service.impl.BuildingService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller("BuildingAction")
@Scope("prototype")
@RequestMapping("/building")
public class BuildingAction extends BaseAction{
    @Resource
    private BuildingService buildingService;

    @RequestMapping("/selectDicdataByTypeOrName")
    public ModelAndView selectDicdataByTypeOrName(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);

        //查询日志
        Map data = buildingService.selectDicdataByTypeOrName(param);

        // 写回客户端
        this.WriteJsonObjToPage(data, res,"","");

        // 返回
        return null;
    }

    @RequestMapping("/selectAllBuildingInfo")
    public ModelAndView selectAllBuildingInfo(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);
        //查询日志
        Map data = buildingService.selectAllBuildingInfo(param);
        // 写回客户端
        this.WriteJsonObjToPage(data, res,"","");

        //返回
        return  null;
    }
}
