package com.founder.bussiness.log.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * 日志表entity
 * @类名: LogEntity
 * @描述:(类描述)
 * @作者: jin_wei@founder.com
 * @日期: 2017年12月4日 下午2:47:12
 *
 */
public class LogEntity implements Serializable{

    private static final long serialVersionUID = -1904417015481538034L;
    private String id ;
	private String userxm;
	private String userid;
	private String sszzjg;
	private String usedate;
	private String domainid;
	private String usemoudle;
	private String usefunction;
	private String userip;
	private String optiontype;
	private String optiondata;
	private String remark;

	public LogEntity(){
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.userxm = "";
		this.userid = "";
		this.sszzjg = "";
		this.usedate = "";
		this.domainid = "";
		this.usemoudle = "";
		this.usefunction = "";
		this.userip = "";
		this.optiontype = "";
		this.optiondata = "";
		this.remark = "";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserxm() {
		return userxm;
	}
	public void setUserxm(String userxm) {
		this.userxm = userxm;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSszzjg() {
		return sszzjg;
	}
	public void setSszzjg(String sszzjg) {
		this.sszzjg = sszzjg;
	}
	public String getUsedate() {
		return usedate;
	}
	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}
	public String getDomainid() {
		return domainid;
	}
	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}
	public String getUsemoudle() {
		return usemoudle;
	}
	public void setUsemoudle(String usemoudle) {
		this.usemoudle = usemoudle;
	}
	public String getUsefunction() {
		return usefunction;
	}
	public void setUsefunction(String usefunction) {
		this.usefunction = usefunction;
	}
	public String getUserip() {
		return userip;
	}
	public void setUserip(String userip) {
		this.userip = userip;
	}
	public String getOptiontype() {
		return optiontype;
	}
	public void setOptiontype(String optiontype) {
		this.optiontype = optiontype;
	}
	public String getOptiondata() {
		return optiondata;
	}
	public void setOptiondata(String optiondata) {
		this.optiondata = optiondata;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
