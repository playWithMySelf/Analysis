package com.founder.bussiness.focuscrowed.controller;

import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.focuscrowed.service.impl.FocuscrowedService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
@Controller("FocuscrowedAction")
@Scope("prototype")
@RequestMapping("/focuscrowed")
public class FocuscrowedAction extends BaseAction {
    /*服务实现类*/

    @Resource
    private FocuscrowedService focuscrowedService;


    /**
     * 查询日志
     *
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/selectFocuscrowedLocation")
    public ModelAndView selectFocuscrowedLocation(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param = this.getParameterMap(req);

        //查询日志
        Map data = this.focuscrowedService.selectFocuscrowedLocation(param);

        // 写回客户端
        this.WriteJsonObjToPage(data, res, "", "");

        // 返回
        return null;
    }
    @RequestMapping("/selectBjzbzByZzjgdm")
    public ModelAndView selectBjzbzByZzjgdm(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param = this.getParameterMap(req);

        //查询日志
        Map data = this.focuscrowedService.selectBjzbzByZzjgdm(param);

        // 写回客户端
        this.WriteJsonObjToPage(data, res, "", "");

        // 返回
        return null;
    }
    @RequestMapping("/selectGzrqLx")
    public ModelAndView selectGzrqLx(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param = this.getParameterMap(req);

        //查询日志
        Map data = this.focuscrowedService.selectGzrqLx(param);

        // 写回客户端
        this.WriteJsonObjToPage(data, res, "", "");

        // 返回
        return null;
    }
}
