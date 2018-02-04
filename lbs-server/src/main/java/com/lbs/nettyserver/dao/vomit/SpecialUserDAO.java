package com.lbs.nettyserver.dao.vomit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.pojo.SpecialUser;

/**
 * 吐槽专题成员
 * @author visual
 *
 */
@Transactional
@Repository("specialUserDAO")
public class SpecialUserDAO {
	
	@Autowired 
    private BaseDao baseDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存专题成员
	 * @param specialUser
	 */
	public void saveSpecialUser(SpecialUser specialUser){
		this.sessionFactory.getCurrentSession().save(specialUser);
	}
	/**
	 * 根据专题ID和用户成员ID查询
	 * @param specialId
	 * @param userId
	 * @return
	 */
    public List<SpecialUser> getSpecialUserListBySpecialIdAndUserId(String specialId,String userId){
    	String hql = "from SpecialUser where specialId = :specialId and userId=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("specialId", specialId);
		query.setParameter("userId", userId); 
		
		@SuppressWarnings("unchecked")
		List<SpecialUser> list = query.list();  
		this.sessionFactory.getCurrentSession().flush();  
		
		return (list!=null && list.size()>0)?list:null;
	}
    
    /**
     * 根据专题id判断该专题成员是否已到达最大值
     * @param specialId
     * @return
     * @throws Exception
     */
    public boolean getSpecialUsersIsMax(String specialId) throws Exception{
    	

    	String sql="SELECT\r\n" + 
    			"    	(tsu.pCount < ts.pMax) as \"isCountMax\"\r\n" + 
    			"    FROM\r\n" + 
    			"    	(\r\n" + 
    			"    		SELECT\r\n" + 
    			"    			COUNT (*) AS pCount\r\n" + 
    			"    		FROM\r\n" + 
    			"    			t_special_users\r\n" + 
    			"    		WHERE\r\n" + 
    			"    			t_special_users.special_id = :specialId\r\n" + 
    			"    	) tsu,\r\n" + 
    			"    	(\r\n" + 
    			"    		SELECT\r\n" + 
    			"    			t_special.person_count_limit AS pMax\r\n" + 
    			"    		FROM\r\n" + 
    			"    			t_special\r\n" + 
    			"    		WHERE\r\n" + 
    			"    			t_special.special_id = :specialId\r\n" + 
    			"    	) ts";
    	Map<String,Object> params=new HashMap<String,Object>();
		params.put("specialId", specialId);
    	return (boolean)baseDao.selectNativeSqlUniqueResult(sql, params, "isCountMax");
	}
}
