package com.founder.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest httpreq = (HttpServletRequest)req;
		HttpServletResponse httpres = (HttpServletResponse)res;

		HttpSession session = httpreq.getSession();
		String isCheckUser = "1";
		String url=((HttpServletRequest) req).getRequestURI();
		if(!url.endsWith("login.jsp") && !url.endsWith("login.html") && !url.endsWith("home.html") && !url.endsWith("help.html")){
			//对首页不过滤
			if("1".equals(isCheckUser)){
				if(session.getAttribute("user")==null){
					String header = httpreq.getHeader("x-requested-with");
					if("XMLHttpRequest".equals(header)){
						//ajax类请求，标识timeout异常
						httpres.setHeader("sessionstatus", "timeout");
						httpres.setStatus(403);
						return;
					}else{
						//一般请求，直接跳转
						res.setContentType("text/html");
						res.setCharacterEncoding("UTF-8");
						PrintWriter out = res.getWriter();
						out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
						out.println("<HTML>");
						out.println(" <HEAD><TITLE>退回登录页面</TITLE></HEAD>");
						out.println("<BODY><div style='display:none;'><form action='' id='navigateForm'></form></div></BODY>");
						out.println("</HTML>");
						out.println("<SCRIPT type='text/javascript'>alert('登录用户未操作超时，请重新登录！');navigateForm.submit();</SCRIPT>");
						out.flush();
						out.close();
						return;
					}
				}
			}
		}
		filter.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
