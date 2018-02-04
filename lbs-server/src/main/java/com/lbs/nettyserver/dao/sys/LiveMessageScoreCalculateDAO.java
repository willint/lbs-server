package com.lbs.nettyserver.dao.sys;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;

/**
 * 现场消息质量得分计算
 * @author visual
 *
 */
@Transactional
@Repository("liveMessageScoreCalculateDAO")
public class LiveMessageScoreCalculateDAO {
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 调用存储过程计算现场消息质量得分
	 * @return true-成功，false-失败
	 * @throws Exception
	 */
	public Object calculateLiveMessageScoreByProc() throws Exception{
		return baseDao.selectNativeSqlUniqueResult("select live_message_score_calculate();", null, "live_message_score_calculate");
	}

}
