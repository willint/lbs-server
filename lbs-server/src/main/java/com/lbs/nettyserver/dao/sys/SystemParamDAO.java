package com.lbs.nettyserver.dao.sys;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.SystemParam;

@Transactional
@Repository("systemParamDAO")
public class SystemParamDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void storeSystemParam(SystemParam e){
		this.sessionFactory.getCurrentSession().saveOrUpdate(e);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * @return List<SystemParam>
	 */
	@SuppressWarnings("unchecked")
	public List<SystemParam> getSystemParamList(){
		String hql = "from SystemParam "; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		List<SystemParam> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		return list;  
	}
	
	/**
	 * @param paramName
	 * @return SystemParam
	 */
	@SuppressWarnings("unchecked")
	public SystemParam getSystemParamByName(String paramName){
		SystemParam systemParam = null;
		String hql = "from SystemParam where param_name = :paramName"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameter("paramName", paramName);  
		List<SystemParam> list = query.list();  
		if(null !=list && list.size() > 0){
			systemParam = list.get(0);
		}
		this.sessionFactory.getCurrentSession().flush();  
		return systemParam;  
		
	}

}
