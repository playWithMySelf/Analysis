package com.founder.bussiness.login.service;

import java.util.Map;

/**
 * Created by playWithMyself on 2018/1/10.
 */
public interface ILoginService {

    /**
     * 用户登陆
     * @param param
     * @return
     */
    Map UserLoginDb(Map param);

    /**
     * 用户登陆
     * @param param
     * @return
     */
    Map AddUser(Map param);

    /**
     * 用户登陆
     * @param param
     * @return
     */
    Map UppUser(Map param);

    /**
     * 用户登陆
     * @param param
     * @return
     */
    Map DelUser(Map param);
}
