package com.lbs.nettyserver.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.pojo.PowerValue;

@Transactional
@Repository("powerValueDAO")
public class PowerValueDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 保存用户能量体力实体到数据库
	 * @param powerValue
	 */
	public void savePowerValue(PowerValue powerValue){
		this.sessionFactory.getCurrentSession().save(powerValue);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 调用存储过程重置所有用户的体力和能量值
	 * @return
	 * @throws Exception
	 */
	public Object userPowerValueResetByProc() throws Exception{
		return baseDao.selectNativeSqlUniqueResult("select user_power_value_reset() as \"isSuccess\";", null, "isSuccess");
	}
	
	/**
	 * 根据用户Id获取用户体力能量实体类
	 * @param userId
	 * @return
	 */
	public PowerValue getPowerValueByUserId(String userId){
		String hql = "from PowerValue where user_id = :userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId);  
		@SuppressWarnings("unchecked")
		List<PowerValue> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}

}
