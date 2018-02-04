package com.lbs.nettyserver.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.User;

@Transactional
@Repository("userDAO")
public class UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveUser(User user){
		this.sessionFactory.getCurrentSession().save(user);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 根据电话号码和密码查询用户信息
	 * @param phone
	 * @param password
	 * @return List<User>
	 */
	@SuppressWarnings("unchecked")
	public User getUserByPhoneAndPassword(String phone,String password){
		String hql = "from User where phone = :phone and password=:password"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("phone", phone);
		query.setParameter("password", password); 
		
		List<User> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param phone
	 * @param password
	 * @return List<User>
	 */
	@SuppressWarnings("unchecked")
	public User getUserByUserId(String userId){
		String hql = "from User where userId = :userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId);
		
		List<User> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据电话号码查询用户信息
	 * @param phone
	 * @return List<User>
	 */
	@SuppressWarnings("unchecked")
	public User getUserByPhone(String phone){

		String hql = "from User where phone = :phone"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("phone", phone);  
		List<User> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据用户id获取用户密码
	 * @param userId
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getUserPwsByUserId(String userId){
		String hql = "from User where userId = :userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId);
		
		List<User> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		
		return (list!=null && list.size()>0)?list.get(0).getPassword():null;
	}

}
