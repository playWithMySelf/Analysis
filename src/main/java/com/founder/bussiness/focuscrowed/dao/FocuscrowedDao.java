package com.founder.bussiness.focuscrowed.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
@Repository
public class FocuscrowedDao extends SqlSessionDaoSupport {

    /**
     * 查询数量
     *
     * @return
     * @throws Exception
     */
    public List selectFocuscrowedLocation(Map param) throws Exception {
        try {
            return this.getSqlSessionTemplate().selectList("focuscrowed.selectFocuscrowedLocation", param);
        } catch (Exception e) {
            throw e;
        }
    }
    public List selectBjzbzByZzjgdm(Map param) throws Exception {
        try {
            return this.getSqlSessionTemplate().selectList("focuscrowed.selectBjzbzByZzjgdm", param);
        } catch (Exception e) {
            throw e;
        }
    }

    public List selectZzjglx(Map param) throws Exception {
        try {
            return this.getSqlSessionTemplate().selectList("focuscrowed.selectZzjglx", param);
        } catch (Exception e) {
            throw e;
        }
    }

    public List selectGzrqLx(Map param) throws Exception {
        try {
            return this.getSqlSessionTemplate().selectList("focuscrowed.selectGzrqLx", param);
        } catch (Exception e) {
            throw e;
        }
    }
}
