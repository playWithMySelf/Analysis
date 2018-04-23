package com.founder.bussiness.rktj.controller;

import com.founder.bussiness.Base.BaseAction;
import com.founder.bussiness.rktj.service.impl.RktjService;
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

@Controller("RktjAction")
@Scope("prototype")
@RequestMapping("/rktj")
/**
 *
 * @类名: RktjAction
 * @描述:(类描述)
 * @作者: jin_wei@founder.com
 * @日期: 2017年12月22日 下午2:58:47
 *
 */
public class RktjAction extends BaseAction {
	/*服务实现类*/
	@Resource
	private RktjService rktjService;


	/**
	 * 查询日志
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getRktj")
	public ModelAndView getRktj(HttpServletRequest req, HttpServletResponse res) {
		//页面参数
		Map<String, String> param  = this.getParameterMap(req);

		//查询日志
		Map data = this.rktjService.getRktj(param);

		// 写回客户端
		this.WriteJsonObjToPage(data, res,"","");

		// 返回
		return null;
	}

    /**
     * 查询日志
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/getDwtj")
    public ModelAndView getDwtj(HttpServletRequest req, HttpServletResponse res) {
        //页面参数
        Map<String, String> param  = this.getParameterMap(req);

        //查询日志
        Map data = this.rktjService.getDwtj(param);

        // 写回客户端
        this.WriteJsonObjToPage(data, res,"","");

        // 返回
        return null;
    }


    /**
     * 导出单位统计日志
     * @param req
     * @param res
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportDwtj")
    public ModelAndView exportDwtj(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");
        res.addHeader("Cache-Control", "no-cache");
        //页面参数
        Map param  = this.getParameterMap(req);

        System.out.println("导出到excel--开始:"+param);
        //获取数据
        res.setContentType("octets/stream");
        String filename = req.getParameter("filename");
        String excelName = "单位统计表";
        if(!"".equals(filename) && filename != null){
            excelName = filename;
        }
        res.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("UTF-8"), "ISO8859-1") +".xls");

        String paramString1 = "ZZJGMC,ZDDWLX_D01,ZDDWLX_D02,ZDDWLX_D03,ZDDWLX_D04,ZDDWLX_D05,ZDDWLX_D06,ZDDWLX_D07,ZDDWLX_D08,ZDDWLX_D09,ZDDWLX_D10,ZDDWLX_D11,DWLBDM_100,DWLBDM_200,DWLBDM_300,DWLBDM_900";
        String paramString2 = "单位,重要机关单位,国防尖端企事业单位,重点建设工程,重要科研单位,重要新闻单位,重要邮电通讯单位,重要动力单位,重要金融单位,重要物资仓储,重要文博单位,其他重点单位,机关团体,事业单位,企业单位,其它";

        //查询统计
        Map data = this.rktjService.exportDwtj(param);
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

    /**
     * 导出人口统计日志
     * @param req
     * @param res
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportRktj")
    public ModelAndView exportRktj(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");
        res.addHeader("Cache-Control", "no-cache");
        //页面参数
        Map param  = this.getParameterMap(req);

        System.out.println("导出到excel--开始:"+param);
        //获取数据
        res.setContentType("octets/stream");
        String filename = req.getParameter("filename");
        String excelName = "人口统计表";
        if(!"".equals(filename) && filename != null){
            excelName = filename;
        }
        res.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("UTF-8"), "ISO8859-1") +".xls");

        String paramString1 = "ZZJGMC,ZFLX_KCLR,ZFLX_LSET,ZFLX_SYDX,ZFLX_PKJT,ZFLX_JGDX,ZFLX_JYZT,ZFLX_SKSW,ZFLX_XDRQ,ZFLX_SFRQ,ZFLX_JSBQ,ZFLX_XSRQ";
        String paramString2 = "单位,空巢老人,留守儿童,生育对象,贫困家庭,监管对象,经营主体,涉恐涉稳,吸毒人群,涉访人群,精神病群,刑释人群";

        //查询统计
        Map data = this.rktjService.exportRktj(param);
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

    /**
     * 导出房屋统计日志
     * @param req
     * @param res
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportFwtj")
    public ModelAndView exportFwtj(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");
        res.addHeader("Cache-Control", "no-cache");
        //页面参数
        Map param  = this.getParameterMap(req);

        System.out.println("导出到excel--开始:"+param);
        //获取数据
        res.setContentType("octets/stream");
        String filename = req.getParameter("filename");
        String excelName = "房屋统计表";
        if(!"".equals(filename) && filename != null){
            excelName = filename;
        }
        res.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("UTF-8"), "ISO8859-1") +".xls");
        String paramString1 = "ZZJGMC,SFWF_1,SFWF_0,FWYTDM_10,FWYTDM_20,FWYTDM_30,FWYTDM_40,FWYTDM_50,FWYTDM_60,FWYTDM_70,FWYTDM_90,SFCZFW_1,SFCZFW_0";
        String paramString2 = "单位,危房,非危房,住宅,工业交通仓储,商业金融信息,教育医疗卫生科研,文化娱乐体育,办公,军事,其他,出租房,非出租房";


        //查询统计
        Map data = this.rktjService.exportFwtj(param);
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
