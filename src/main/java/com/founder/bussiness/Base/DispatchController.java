package com.founder.bussiness.Base;

import com.founder.bussiness.login.service.impl.LoginService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: inwei
 * @create: 2018-04-11 15:05
 * @description: Spring分发请求调度控制器
 */

@SuppressWarnings("unchecked")
@Controller("DispatchController")
@Scope("prototype")

public class DispatchController extends BaseAction{

    //log4j日志
    Logger logger = Logger.getLogger(LoginService.class.getName());

    @RequestMapping("/dispatch")
    public String dispatch(HttpServletRequest req, HttpServletResponse res,RedirectAttributesModelMap redirect) {
        //页面参数
        Map<String, Object> param  = this.getParameterMap2(req);
        String controller = (String) param.get("controller");
        String method = (String) param.get("method");
        //获取controller和method
        Map dataMap = new HashMap();
        dataMap.put("code","EC99");
        if(controller == null || controller.length()<=0){
            logger.debug("****DispatchController中获取controller失败***");
            dataMap.put("mag","请输入正确的controller");
            this.WriteJsonObjToPage(dataMap, res);
            return null;
        }
        if(method == null || method.length()<=0){
            logger.debug("****DispatchController中获取method失败***");
            dataMap.put("mag","请输入正确的method");
            this.WriteJsonObjToPage(dataMap, res);
            return null;
        }

        //加载参数
        for(Map.Entry<String,Object> entry : param.entrySet()){
            redirect.addAttribute(entry.getKey() , entry.getValue());
        }
        //重定向
        return "redirect:/mvc/"+controller+"/"+method;

    }


}
