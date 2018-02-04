package com.lbs.nettyserver.dao.sys;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;

/**
 * 用户现场综合得分计算
 * @author visual
 *
 */
@Transactional
@Repository("userLiveScoreCalculateDAO")
public class UserLiveScoreCalculateDAO {
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 调用存储过程计算用户现场综合得分
	 * @return true-成功，false-失败
	 * @throws Exception
	 */
	public Object calculateUserLiveScoreByProc() throws Exception{
		return baseDao.selectNativeSqlUniqueResult("select user_live_score_calculate();", null, "user_live_score_calculate");
	}

}
