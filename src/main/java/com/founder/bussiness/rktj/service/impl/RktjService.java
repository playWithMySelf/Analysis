package com.founder.bussiness.rktj.service.impl;

import com.founder.bussiness.rktj.dao.RktjDao;
import com.founder.bussiness.rktj.service.IRktjService;
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
public class RktjService implements IRktjService {
	//缓存
	private static List<Map> configTemp = new ArrayList<Map>();

	//log4j日志
	Logger logger = Logger.getLogger(RktjService.class.getName());

	/*数据库连接类*/
	@Resource
	private RktjDao rktjDao;

	/**
	 * 查询日志记录
	 * @Title: selectLogList
	 * @描述:(方法描述)
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: Map    返回类型
	 * @throws
	 */
	public Map getRktj(Map<String,String> param) {
		//返回
		Map returnMap = new HashMap();
		List rowlist = new ArrayList();

		try {
			//分页
			rowlist = this.rktjDao.getRktj(param);
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

    /**
     * 查询日志记录
     * @Title: selectLogList
     * @描述:(方法描述)
     * @作者: jin_wei@founder.com
     * @参数: 传入参数定义
     * @返回值: Map    返回类型
     * @throws
     */
    public Map getDwtj(Map<String,String> param) {
        //返回
        Map returnMap = new HashMap();
        List rowlist = new ArrayList();

        try {
            //分页
            rowlist = this.rktjDao.getDwtj(param);
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

    /**
     * 导出单位统计数据
     * @Title: selectLogList
     * @描述:(方法描述)
     * @作者: jin_wei@founder.com
     * @参数: 传入参数定义
     * @返回值: Map    返回类型
     * @throws
     */
    public Map exportDwtj(Map<String,String> param) {
        //返回
        Map returnMap = new HashMap();
        List rowlist = new ArrayList();

        try {
            //分页
            rowlist = this.rktjDao.exportDwtj(param);
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

    /**
     * 导出人口统计数据
     * @Title: selectLogList
     * @描述:(方法描述)
     * @作者: jin_wei@founder.com
     * @参数: 传入参数定义
     * @返回值: Map    返回类型
     * @throws
     */
    public Map exportRktj(Map<String,String> param) {
        //返回
        Map returnMap = new HashMap();
        List rowlist = new ArrayList();

        try {
            //分页
            rowlist = this.rktjDao.exportRktj(param);
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

    /**
     * 导出房屋统计数据
     * @Title: exportFwtj
     * @描述:(方法描述)
     * @作者: jin_wei@founder.com
     * @参数: 传入参数定义
     * @返回值: Map    返回类型
     * @throws
     */
    public Map exportFwtj(Map<String,String> param) {
        //返回
        Map returnMap = new HashMap();
        List rowlist = new ArrayList();

        try {
            //分页
            rowlist = this.rktjDao.exportFwtj(param);
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
