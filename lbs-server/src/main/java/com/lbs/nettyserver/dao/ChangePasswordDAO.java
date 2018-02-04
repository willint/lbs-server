package com.lbs.nettyserver.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.User;


@Transactional
@Repository("changePasswordDAO")
public class ChangePasswordDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 根据用户id修改密码
	 * @param userId
	 * @param newPws
	 */
	public int updatePasswordByUserId(String userId,String newPws){
		
		int result=0;
		Query query = this.sessionFactory.getCurrentSession().createQuery("update User set password=:password where userId=:userId");
		query.setParameter("password", newPws);
		query.setParameter("userId", userId); 
		result=query.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		return result;
	}
}
