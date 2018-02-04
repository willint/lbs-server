package com.lbs.nettyserver.dao.live;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.pojo.LiveMessageComment;
import com.lbs.nettyserver.model.request.live.LiveGetOneMessageCommentRequest;
import com.lbs.nettyserver.model.response.live.LiveGetOneMessageCommentResponse;

/**
 * 现场-现场消息评论
 * @author visual
 *
 */
@Transactional
@Repository("liveMessageCommentDAO")
public class LiveMessageCommentDAO {
	
	@Autowired 
    private BaseDao baseDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存现场消息评论
	 * @param liveMessage
	 */
	public void saveLiveMessageComment(LiveMessageComment liveMessageComment){
		this.sessionFactory.getCurrentSession().save(liveMessageComment);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 根据现场消息id获取该消息的评论，按照日期从早到晚排序
	 * @param liveGetCommentRequest
	 * @return
	 */
	public List<LiveGetOneMessageCommentResponse>  getLiveOneMessageCommentById(LiveGetOneMessageCommentRequest liveGetOneMessageCommentRequest){
		
		String sql="SELECT\r\n" + 
				"	lmc.comment_id as \"commentId\",\r\n" + 
				"lmc.lm_id as \"lmId\",\r\n" + 
				"lmc.\"content\",\r\n" + 
				"lmc.jd,\r\n" + 
				"lmc.wd,\r\n" + 
				"lmc.location_name AS \"locationName\",\r\n" + 
				"lmc.comment_time as \"commentTime\",\r\n" + 
				"lmc.comment_score as \"commentScore\",\r\n" + 
				"tu.user_id as \"userId\",\r\n" + 
				"tu.headimg as \"headImg\",\r\n" + 
				"tu.nickname as \"nickName\",\r\n" + 
				"tu.sex,\r\n" + 
				"tu.live_score as \"liveScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message_comment lmc,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	lmc.lm_id = :lmId\r\n" + 
				"AND (lmc.pid IS NULL OR lmc.pid = '')\r\n" + 
				"AND lmc.user_id = tu.user_id ORDER BY \"commentTime\" DESC LIMIT :limit OFFSET :offset";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("lmId", liveGetOneMessageCommentRequest.getLmId());
		params.put("limit",liveGetOneMessageCommentRequest.getLimit() );
		params.put("offset",liveGetOneMessageCommentRequest.getOffset());
		
		List<LiveGetOneMessageCommentResponse> list=new ArrayList<LiveGetOneMessageCommentResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveGetOneMessageCommentResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 根据现场消息id和评论的用户id获取该用户对消息的评论
	 * @param lmId
	 * @param userId
	 * @return
	 */
	public LiveMessageComment getSmartMessageCommentByLmIdAndUserId(String lmId,String userId){
		String hql = "from LiveMessageComment where (pid is null OR pid='') AND lm_id = :lmId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId); 
		query.setParameter("lmId", lmId); 
		@SuppressWarnings("unchecked")
		List<LiveMessageComment> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
