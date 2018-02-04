package com.lbs.nettyserver.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.AttentionPerson;

/**
 * 用户关注操作类
 * @author visual
 *
 */
@Transactional
@Repository("attentionPersonDAO")
public class AttentionPersonDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存关注
	 * @param attentionPerson
	 */
	public void saveAttentionPerson(AttentionPerson attentionPerson){
		this.sessionFactory.getCurrentSession().save(attentionPerson);
		this.sessionFactory.getCurrentSession().flush();
	}
	/**
	 * 删除(取消)关注
	 * @param liveAttentionPerson
	 */
	public void deleteAttentionPerson(AttentionPerson attentionPerson){
		this.sessionFactory.getCurrentSession().delete(attentionPerson);
		this.sessionFactory.getCurrentSession().flush();
	}
	/**
	 * 根据id查询attentionPerson
	 * @param lapId
	 */
	public AttentionPerson selectAttentionPersonById(String lapId){
		String hql = "from AttentionPerson where lap_id = :lapId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("lapId", lapId);  
		@SuppressWarnings("unchecked")
		List<AttentionPerson> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	

	/**
	 * 根据被关注的用户id和关注的用户id查询attentionPerson
	 * @param beUserId
	 * @param userId
	 * @return
	 */
	public AttentionPerson selectAttentionPersonByIdAndUserId(String beUserId,String userId){
		String hql = "from AttentionPerson where be_user_id = :beUserId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("beUserId", beUserId); 
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<AttentionPerson> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
