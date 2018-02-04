package com.lbs.nettyserver.dao.sys;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.FuncSet;

/**
 * 技能数据库操作类
 * @author visual
 *
 */
@Transactional
@Repository("funcSetDAO")
public class FuncSetDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 获取吐槽技能列表
	 * @return 无数据则返回null
	 */
	@SuppressWarnings("unchecked")
	public List<FuncSet> getFuncSetList(){
		String hql = "from FuncSet "; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		List<FuncSet> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		return (list==null || list.size()<=0)?null:list;  
	}
}
