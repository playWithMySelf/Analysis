package com.founder.bussiness.focuscrowed.service.impl;

import com.founder.bussiness.focuscrowed.dao.FocuscrowedDao;
import com.founder.bussiness.focuscrowed.service.IFocuscrowedService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
@SuppressWarnings("unchecked")
@Service
@Scope("prototype")
public class FocuscrowedService implements IFocuscrowedService {
    /*数据库连接类*/
    @Resource
    private FocuscrowedDao focuscrowedDao;

    /**
     *
     * @param param
     * @return
     */
    public Map selectFocuscrowedLocation(Map<String, String> param) {
        //返回
        Map returnMap = new HashMap();

        //查询数量
        List rowlist = new ArrayList();
        try {
            List lxList = this.focuscrowedDao.selectZzjglx(param);
            String zzjgdm = param.get("zzjgdm").toString();
            if(lxList.size() > 0){
                Map lxMap = (Map)lxList.get(0);
                String zzjglx = (String)lxMap.get("ZZJGLX");
                String ssdw = "";
                if(zzjglx.equals("11")){
                    ssdw = "1 = 1";
                }else if(zzjglx.equals("12")){
                    ssdw = "t2.gxfjdm = '" + zzjgdm + "'";
                }else if(zzjglx.equals("13")){
                    ssdw = "t2.gxpcsdm = '" + zzjgdm + "'";
                }else if(zzjglx.equals("14")){
                    ssdw = "t2.gxzrqdm = '" + zzjgdm + "'";
                }
                param.put("ssdw", ssdw);
                //分页
                rowlist = this.focuscrowedDao.selectFocuscrowedLocation(param);
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "EC01");
            returnMap.put("msg", "查询失败");
            return returnMap;
        }

        returnMap.put("rows", rowlist);
        returnMap.put("code", "EC00");
        returnMap.put("msg", "查询成功");
        return returnMap;
    }
    public Map selectBjzbzByZzjgdm(Map<String, String> param) {
        //返回
        Map returnMap = new HashMap();

        //查询数量
        List rowlist = new ArrayList();
        try {
            //分页
            rowlist = this.focuscrowedDao.selectBjzbzByZzjgdm(param);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "EC01");
            returnMap.put("msg", "查询失败");
            return returnMap;
        }

        returnMap.put("rows", rowlist);
        returnMap.put("code", "EC00");
        returnMap.put("msg", "查询成功");
        return returnMap;
    }
    public Map selectGzrqLx(Map<String, String> param) {
        //返回
        Map returnMap = new HashMap();

        //查询数量
        List rowlist = new ArrayList();
        try {
            //分页
            rowlist = this.focuscrowedDao.selectGzrqLx(param);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "EC01");
            returnMap.put("msg", "查询失败");
            return returnMap;
        }

        returnMap.put("rows", rowlist);
        returnMap.put("code", "EC00");
        returnMap.put("msg", "查询成功");
        return returnMap;
    }
}