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
import com.lbs.nettyserver.model.pojo.LiveCai;

/**
 * 现场踩表
 * @author visual
 *
 */
@Transactional
@Repository("liveCaiDAO")
public class LiveCaiDAO {
	
	@Autowired
	private BaseDao baseDao;

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存踩
	 * @param liveCai
	 */
	public void saveLiveCai(LiveCai liveCai){
		this.sessionFactory.getCurrentSession().save(liveCai);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 根据现场消息id和点踩人的用户id获取踩的信息
	 * @param lmId
	 * @param userId
	 * @return
	 */
	public LiveCai getLiveCaiBylmIdAndUserId(String lmId,String userId){
		String hql = "from LiveCai where lm_id = :lmId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId); 
		query.setParameter("lmId", lmId); 
		@SuppressWarnings("unchecked")
		List<LiveCai> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 获取某一用户被踩的总数caiTotal
	 * @param beUserId
	 * @return
	 * @throws Exception
	 */
	public Object getLiveZanTotalByBeUserId(String beUserId) throws Exception{
		String sql="SELECT\r\n" + 
				"		COUNT (*) AS caiTotal\r\n" + 
				"	FROM\r\n" + 
				"		t_live_cai\r\n" + 
				"	WHERE\r\n" + 
				"		be_user_id = :beUserId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("beUserId", beUserId);
		
		return baseDao.selectNativeSqlUniqueResult(sql, params, "caiTotal");
	}
}
