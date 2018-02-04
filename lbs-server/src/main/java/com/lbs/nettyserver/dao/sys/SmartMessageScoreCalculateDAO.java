package com.lbs.nettyserver.dao.sys;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;

/**
 * 智囊消息得分计算
 * @author visual
 *
 */
@Transactional
@Repository("smartMessageScoreCalculateDAO")
public class SmartMessageScoreCalculateDAO {

	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 调用存储过程计算智囊消息质量得分
	 * @return true-成功，false-失败
	 * @throws Exception
	 */
	public Object calculateLiveSmartMessageScoreByProc() throws Exception{
		return baseDao.selectNativeSqlUniqueResult("select smart_message_score_calculate();", null, "smart_message_score_calculate");
	}
	
}
