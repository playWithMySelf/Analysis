package com.founder.bussiness.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.login.service.impl.LoginService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 登录模块
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
@Controller("LoginAction")
@Scope("prototype")
@RequestMapping("/login")
public class LoginAction extends BaseAction {
	/*服务实现类*/
	@Resource
	private LoginService loginService;

	//用户登陆
	@RequestMapping("/UserLoginDb")
	public ModelAndView UserLoginDb(HttpServletRequest req, HttpServletResponse res) {
		//页面参数
		Map<String, String> param  = this.getParameterMap(req);

		Long sessionTimeout = 1000*60*3L;
		//是否验证用户登录
		//获取数据
		Map dataMap = this.loginService.UserLoginDb(param);
		if("EC00".equals(dataMap.get("code"))){
			HttpSession session = req.getSession(true);
			session.setMaxInactiveInterval(sessionTimeout.intValue());//设置session超时时间
			session.setAttribute("user", dataMap.get("user"));
			session.setAttribute("auth", dataMap.get("auth"));
			System.out.println("登陆dataMap:"+dataMap+"登陆session:"+session.getAttribute("user"));
			dataMap.remove("user");
		}

		// 写回客户端
		this.WriteJsonObjToPage(dataMap, res);

		// 返回
		return null;
	}

    //查询用户
    @RequestMapping("/QueryUser")
    public ModelAndView QueryUser(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);
        //获取数据
        Map dataMap = this.loginService.QueryUser(param);

        // 写回客户端
        this.WriteJsonObjToPage(dataMap, res);

        // 返回
        return null;
    }

    //更新用户
    @RequestMapping("/UppUser")
    public ModelAndView UppUser(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);
        //获取数据
        Map dataMap = this.loginService.UppUser(param);

        // 写回客户端
        this.WriteJsonObjToPage(dataMap, res);

        // 返回
        return null;
    }

    //删除用户
    @RequestMapping("/DelUser")
    public ModelAndView DelUser(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);
        //获取数据
        Map dataMap = this.loginService.DelUser(param);
        // 写回客户端
        this.WriteJsonObjToPage(dataMap, res);
        // 返回
        return null;
    }

    //添加用户
    @RequestMapping("/AddUser")
    public ModelAndView AddUser(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);
        //获取数据
        Map dataMap = this.loginService.AddUser(param);
        // 写回客户端
        this.WriteJsonObjToPage(dataMap, res);
        // 返回
        return null;
    }

	/**
	 * 用户退出
	 * @param req
	 * @param res
	 * @return
	 */
    @RequestMapping("/UserLogout")
	public ModelAndView UserLogout(HttpServletRequest req, HttpServletResponse res) {
		//获取数据
		boolean flag = false;
		HttpSession session = req.getSession(true);
		if(session.getAttribute("user")!=null){
			session.setAttribute("user",null);
		}
        if(session.getAttribute("auth")!=null){
            session.setAttribute("auth",null);
        }
        flag = true;
		// 写回客户端
		this.WriteJsonObjToPage(flag, res);

		// 返回
		return null;
	}

	/**
	 * 获取登录用户
	 * @param req
	 * @param res
	 * @return
	 */
    @RequestMapping("/GetLoginUser")
	public ModelAndView GetLoginUser(HttpServletRequest req, HttpServletResponse res) {
		//获取用户信息
		HttpSession session = req.getSession(true);
		Map dataMap = new HashMap();
		if(session.getAttribute("user")!=null){
			dataMap.put("user", session.getAttribute("user"));
			dataMap.put("auth", session.getAttribute("auth"));
			dataMap.put("code", "EC00");
			dataMap.put("msg", "操作成功！");
		}else{
			dataMap.put("code", "EC01");
			dataMap.put("msg", "登录用户超时，请重新登录！");
		}

		// 写回客户端
		this.WriteJsonObjToPage(dataMap, res);

		// 返回
		return null;
	}

}
