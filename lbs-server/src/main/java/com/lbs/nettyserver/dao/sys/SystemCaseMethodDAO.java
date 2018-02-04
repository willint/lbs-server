package com.lbs.nettyserver.dao.sys;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.SystemCaseMethod;

@Transactional
@Repository("systemCaseMethodDAO")
public class SystemCaseMethodDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * @return List<SystemParam>
	 */
	@SuppressWarnings("unchecked")
	public List<SystemCaseMethod> getSystemCaseMethodList(){
		String hql = "from SystemCaseMethod "; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		List<SystemCaseMethod> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		return list;  
	}
}
