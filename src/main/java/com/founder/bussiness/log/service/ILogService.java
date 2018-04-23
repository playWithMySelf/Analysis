package com.founder.bussiness.log.service;

import com.founder.bussiness.log.entity.LogEntity;

import java.util.Map;

/**
 * Created by playWithMyself on 2018/1/11.
 */
public interface ILogService {

    public Map selectLogList(Map<String,String> param);

    public void  addLog(LogEntity logEntity);

    public Map selectOptionTypeList(Map param);

    public Map selectModuleList(Map param);

    public Map exportLog(Map param);
}
