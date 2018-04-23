package com.founder.annotation;

import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.log.entity.LogEntity;
import com.founder.bussiness.log.service.impl.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 切点类
 *
 * @author jinwei
 * @since 2017-12-04 Pm 20:35
 * @version 1.0
 */
@Aspect
@Component
public class SystemLogAop {
	// 注入Service用于把日志保存数据库
	@Resource
	private LogService logService;
	// 本地异常日志记录对象
	private static final Logger logger = Logger
			.getLogger(SystemLogAop.class);


	public SystemLogAop(){
		System.out.println("*********************SystemLogAop*******************");
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 拦截com.founder.service包下所有以Action结尾的java文件
	 * @param joinPoint
	 *            切点
	 */
	@Before("execution(* com.founder.bussiness..*Action.*(..))")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("*********************SystemLogAop begin*******************");
		/*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		try {
			// *========控制台输出=========*//*/
			System.out.println("=====前置通知开始=====");
			System.out.println("请求方法:"
					+ (joinPoint.getTarget().getClass().getName() + "."
							+ joinPoint.getSignature().getName() + "()"));
			// *========数据库日志=========*//*/
			//获取请求的参数
			BaseAction baseAction = new BaseAction();
			Map<String, String> logParam = new HashMap();
			if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
				for ( int i = 0; i < joinPoint.getArgs().length; i++) {

	                String type = (joinPoint.getArgs()[i]).getClass().toString();
	                if(type.indexOf("RequestFacade")>=0){
	                	HttpServletRequest hq = (HttpServletRequest) joinPoint.getArgs()[i];
	                	//获取session
	                	HttpSession ss = hq.getSession();
	                	User userSession = null;
	                	if(ss!=null){
	                		userSession = (User) ss.getAttribute("user");
	                	}

	                	logParam = baseAction.getParameterMap(hq);
	                	System.out.println("Action请求的参数："+logParam);
	                	try {
	            			//获取参数记录日志
	                		String log_ip = getIp(hq);
	            			String log_usemoudle = (String) logParam.get("log_usemoudle");
	            			String log_usefunction = (String) logParam.get("log_usefunction");
	            			String log_optiontype = (String) logParam.get("log_optiontype");
	            			String log_optiondata = (String) logParam.get("log_optiondata");
	            			String log_remark = logParam.get("log_remark") ==null ? "" : (String)logParam.get("log_remark");
	            			String log_domainid = logParam.get("log_domainid")==null ? "2" : (String)logParam.get("log_domainid");
	            			//获取用户id
	            			String log_userid = "";
	            			//优先从post请求中读取，然后从session中读取
	            			if(logParam.containsKey("log_userid") && logParam.get("log_userid")!=null){
	            				log_userid = logParam.get("log_userid");
	            			}else{
	            				if(userSession!=null){
	            					log_userid = userSession.getId();
	            				}
	            			}

	            			if(!"".equalsIgnoreCase(log_usemoudle) && log_usemoudle!=null
	            				&& !"".equalsIgnoreCase(log_usefunction) && log_usefunction!=null
	            				&& !"".equalsIgnoreCase(log_optiontype) && log_optiontype!=null
	            				&& !"".equalsIgnoreCase(log_optiondata) && log_optiondata!=null
	            				&& !"".equalsIgnoreCase(log_userid) && log_userid!=null){
	            				//填充日志信息
	            				LogEntity logEntity = new LogEntity();
	            				logEntity.setDomainid(log_domainid);
	            				logEntity.setOptiondata(log_optiondata);
	            				logEntity.setOptiontype(log_optiontype);
	            				logEntity.setRemark(log_remark);
	            				logEntity.setUsefunction(log_usefunction);
	            				logEntity.setUsemoudle(log_usemoudle);
	            				logEntity.setUserid(log_userid);
	            				logEntity.setUserip(log_ip);
	            				//记录日志
	            				this.logService.addLog(logEntity);
	            			}
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}
	                }
	            }
			}

			System.out.println("=====前置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}",e);
		}*/
	}

	@After("execution(* com.founder.bussiness..*Action.*(..))")
	public void doAfter(){
		System.out.println("*********************SystemLogAop end*******************");

	}

	/**
	 * @Title: getIp
	 * @描述:获取客户端Ip
	 * @作者: jin_wei@founder.com
	 * @参数: 传入参数定义
	 * @返回值: void    返回类型
	 * @throws
	 */
	public String getIp(HttpServletRequest request){
		String ip = null;
		ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
        	ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
        	ip = request.getHeader("WL-Proxy-Client-IP");
        }
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getRemoteAddr();
        }
		return ip;
	}

}