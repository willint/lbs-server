package com.lbs.nettyserver.dao.live;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.pojo.LiveZan;

/**
 * 现场赞表
 * @author visual
 *
 */
@Transactional
@Repository("liveZanDAO")
public class LiveZanDAO {
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存赞
	 * @param liveZan
	 */
	public void saveLiveZan(LiveZan liveZan){
		this.sessionFactory.getCurrentSession().save(liveZan);
		this.sessionFactory.getCurrentSession().flush();
	}
	/**
	 * 根据现场消息id和点赞人的用户id获取赞的信息
	 * @param lmId
	 * @param userId
	 * @return
	 */
	public LiveZan getLiveZanBylmIdAndUserId(String lmId,String userId){
		String hql = "from LiveZan where lm_id = :lmId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId); 
		query.setParameter("lmId", lmId); 
		@SuppressWarnings("unchecked")
		List<LiveZan> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 获取某一用户被赞的总数zanTotal
	 * @param beUserId
	 * @return
	 * @throws Exception
	 */
	public Object getLiveZanTotalByBeUserId(String beUserId) throws Exception{
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS zanTotal\r\n" + 
				"FROM\r\n" + 
				"	t_live_zan\r\n" + 
				"WHERE\r\n" + 
				"	be_user_id = :beUserId ";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("beUserId", beUserId);
		
		return baseDao.selectNativeSqlUniqueResult(sql, params, "zanTotal");
	}
}
