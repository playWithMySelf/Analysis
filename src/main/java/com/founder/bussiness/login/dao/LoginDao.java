package com.founder.bussiness.login.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class LoginDao extends SqlSessionDaoSupport {

	/**
	 * 用户登陆
	 * @param param
	 * @return
	 */
	public Map UserLoginDb(Map param)  throws Exception {
		try{
			return (Map)this.getSqlSessionTemplate().selectOne("login.UserLoginDb",param);
		}catch(Exception e){
            throw e;
		}
	}

    /**
     * 查询用户
     * @param param
     * @return
     */
    public String QueryUserCount(Map param)  throws Exception {
        try{
            return (String) this.getSqlSessionTemplate().selectOne("login.QueryUserCount",param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 查询用户
     * @param param
     * @return
     */
    public List QueryUser(Map param)  throws Exception {
        try{
            return this.getSqlSessionTemplate().selectList("login.QueryUser",param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 添加用户
     * @param param
     * @return
     */
    public int AddUser(Map param)  throws Exception {
        try{
            return this.getSqlSessionTemplate().update("login.AddUser",param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 更新用户
     * @param param
     * @return
     */
    public int UppUser(Map param)  throws Exception {
        try{
            return this.getSqlSessionTemplate().update("login.UppUser",param);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 删除用户
     * @param param
     * @return
     */
    public int DelUser(Map param)  throws Exception {
        try{
            return this.getSqlSessionTemplate().update("login.DelUser",param);
        }catch(Exception e){
            throw e;
        }
    }


}
