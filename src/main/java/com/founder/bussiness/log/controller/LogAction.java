package com.founder.bussiness.log.controller;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.log.service.impl.LogService;
import com.founder.utils.PrintExcel;
import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("LogAction")
@Scope("prototype")
@RequestMapping("/log")
/**
 *
 * @类名: RktjAction
 * @描述:(类描述)
 * @作者: jin_wei@founder.com
 * @日期: 2017年12月22日 下午2:58:47
 *
 */
public class LogAction extends BaseAction {
	/*服务实现类*/
	@Resource
	private LogService logService;


	/**
	 * 查询日志
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(params = "method=selectLogList")
	public ModelAndView selectLogList(HttpServletRequest req, HttpServletResponse res) {
		//页面参数
		Map<String, String> param  = this.getParameterMap(req);

		//查询日志
		Map data = this.logService.selectLogList(param);

		// 写回客户端
		this.WriteJsonObjToPage(data, res,"","");

		// 返回
		return null;
	}

	/**
	 * 获取操作类型字典
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(params = "method=selectOptionTypeList")
	public ModelAndView selectOptionTypeList(HttpServletRequest req, HttpServletResponse res) {
		//页面参数
		Map<String, String> param  = this.getParameterMap(req);

		//查询日志
		Map data = this.logService.selectOptionTypeList(param);

		// 写回客户端
		this.WriteJsonObjToPage(data, res,"","");

		// 返回
		return null;
	}

	/**
	 * 获取模块列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(params = "method=selectModuleList")
	public ModelAndView selectModuleList(HttpServletRequest req, HttpServletResponse res) {
		//页面参数
		Map<String, String> param  = this.getParameterMap(req);

		//查询日志
		Map data = this.logService.selectModuleList(param);

		// 写回客户端
		this.WriteJsonObjToPage(data, res,"","");

		// 返回
		return null;
	}

	/**
	 * 导出日志
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=exportLog")
	public ModelAndView exportLog(HttpServletRequest req, HttpServletResponse res) throws Exception {

		req.setCharacterEncoding("UTF-8");
		res.addHeader("Cache-Control", "no-cache");
		//页面参数
		Map param  = this.getParameterMap(req);

		System.out.println("导出到excel--开始:"+param);
		//获取数据
		res.setContentType("octets/stream");
		String filename = req.getParameter("filename");
		String excelName = "日志表";
		if(!"".equals(filename) && filename != null){
				excelName = filename;
		}
		res.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("UTF-8"), "ISO8859-1") +".xls");


		String paramString1 = "USERID,USERXM,SSZZJGMC,USEDATE,USEMOUDLE,OPTIONTYPEMC,USERIP";
		String paramString2 = "登录名,用户名,单位名称,操作时间,操作模块,操作类型,IP地址";

		//查询日志
		Map data = this.logService.selectLogList(param);
		List excelData = null;
		String code = (String) data.get("code");
		if("EC00".equalsIgnoreCase(code)){
			excelData = (List) data.get("rows");
		}
		JSONArray jd = JSONArray.fromObject(excelData);

		OutputStream out = res.getOutputStream();
		PrintExcel printExcel = new PrintExcel();
        printExcel.exportExcel(excelName, paramString1, paramString2, jd, out, "yyyy-MM-dd");
		// 返回
		return null;
	}
}
