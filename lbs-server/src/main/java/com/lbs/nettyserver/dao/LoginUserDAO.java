package com.lbs.nettyserver.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.pojo.LoginUser;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;

@Transactional
@Repository("loginUserDAO")
public class LoginUserDAO {
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * 保存或者更新用户登录信息
	 * @param loginUser
	 */
	public void saveOrUpdateLoginUser(LoginUser loginUser){
		this.sessionFactory.getCurrentSession().saveOrUpdate(loginUser);
		this.sessionFactory.getCurrentSession().flush();
	}
	/**
	 * 根据用户的id获取对应的登录信息
	 * @param userId
	 * @return List<LoginUser>
	 */
	@SuppressWarnings("unchecked")
	public LoginUser getLoginUserByUserId(String userId){
		
		String hql = "from LoginUser where user_id = :userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId);  
		List<LoginUser> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
		
	}
	
	/**
	 * 根据token(手机唯一标识码)获取对应的未注册用户登录信息
	 * @param userId
	 * @return List<LoginUser>
	 */
	@SuppressWarnings("unchecked")
	public LoginUser getIsNoRegistLoginUserByToken(String token){
		
		String hql = "from LoginUser where token = :token and is_regist='0'"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("token", token);  
		List<LoginUser> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
		
	}
	
	/**
	 * 根据用户登录id更新用户在线状态为false
	 * @param userId
	 * @param newPws
	 */
	public int updateLoginUserStateByLoginId(String loginId,String userId){
		
		int result=0;
		Query query = this.sessionFactory.getCurrentSession().createQuery("update LoginUser set isOnline=:isOnline,lastTime=:lastTime where loginId=:loginId and userId=:userId");
		query.setParameter("isOnline", false);
		query.setParameter("loginId", loginId); 
		query.setParameter("userId", userId);
		query.setParameter("lastTime", TimeUtil.getChinaLocalDateTimeNow());
		result=query.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		return result;
	}
	
	/**
	 * 计算推送用户的范围
	 * @param jd
	 * @param wd
	 * @return
	 */
	public List<LoginUser> getBroadcastUser(BigDecimal jd, BigDecimal wd){
		
		String hql = "from LoginUser AS lu where earth_distance(ll_to_earth(:jd,:wd),ll_to_earth(lu.jd,lu.wd))<= lu.vomitRange"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameter("jd", jd);
		query.setParameter("wd", wd);
		List<LoginUser> list = query.list();
		
		return list;
	}

}
