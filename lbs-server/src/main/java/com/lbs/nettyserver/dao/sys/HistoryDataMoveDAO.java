package com.lbs.nettyserver.dao.sys;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;

/**
 * 历史数据搬移数据库操作类
 * @author visual
 *
 */
@Transactional
@Repository("historyDataMoveDAO")
public class HistoryDataMoveDAO {

	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 调用存储过程搬移现场相关历史数据
	 * @return true-成功，false-失败
	 * @throws Exception
	 */
	public Object moveLiveHistoryDataByProc() throws Exception{
		return baseDao.selectNativeSqlUniqueResult("select history_live_data_move(2) as \"isSuccess\";", null, "isSuccess");
	}
}
