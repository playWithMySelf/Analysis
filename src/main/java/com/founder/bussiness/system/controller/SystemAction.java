package com.founder.bussiness.system.controller;

import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.system.service.impl.SystemService;
import com.founder.utils.PrintExcel;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller("SystemAction")
@Scope("prototype")
@RequestMapping("/system")
/**
 *
 * @类名: RktjAction
 * @描述:(类描述)
 * @作者: jin_wei@founder.com
 * @日期: 2017年12月22日 下午2:58:47
 *
 */
public class SystemAction extends BaseAction {
	/*服务实现类*/
	@Resource
	private SystemService systemService;


	/**
	 * 查询日志
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/selectDicdataByTypeOrName")
	public ModelAndView selectDicdataByTypeOrName(HttpServletRequest req, HttpServletResponse res) {
		//页面参数
		Map<String, String> param  = this.getParameterMap(req);

		//查询日志
		Map data = this.systemService.selectDicdataByTypeOrName(param);

		// 写回客户端
		this.WriteJsonObjToPage(data, res,"","");

		// 返回
		return null;
	}


}
