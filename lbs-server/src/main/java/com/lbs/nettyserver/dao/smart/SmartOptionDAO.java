package com.lbs.nettyserver.dao.smart;

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
import com.lbs.nettyserver.model.common.SmartOneUserIsAgainstAndAgree;
import com.lbs.nettyserver.model.pojo.SmartAgainst;
import com.lbs.nettyserver.model.pojo.SmartAgree;
import com.lbs.nettyserver.model.pojo.SmartMessageComment;
import com.lbs.nettyserver.model.request.smart.SmartGetOneMessageCommentListRequest;
import com.lbs.nettyserver.model.request.smart.SmartGetSmartMessageListRequest;
import com.lbs.nettyserver.model.response.smart.SmartGetOneMessageCommentListResponse;
import com.lbs.nettyserver.model.response.smart.SmartGetSmartMessageListResponse;

/**
 * 智囊数据库操作类
 * @author visual
 *
 */
@Transactional
@Repository("smartOptionDAO")
public class SmartOptionDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired 
    private BaseDao baseDao;
	
	/**
	 * 根据经纬度、接收范围获取智囊消息列表并按照时间从大到小排序
	 * @param smartGetSmartMessageListRequest
	 * @return List<SmartGetSmartMessageListResponse>
	 */
	public List<SmartGetSmartMessageListResponse> getSmartMessageListByJdAndWd(SmartGetSmartMessageListRequest smartGetSmartMessageListRequest){
		String sql="SELECT\r\n" + 
				"	smgc.*, tsrc.read_count AS \"readCount\"\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			smg.*, tsac.against_count AS \"againstCount\",\r\n" + 
				"			tsac.agree_count AS \"agreeCount\"\r\n" + 
				"		FROM\r\n" + 
				"			(\r\n" + 
				"				SELECT\r\n" + 
				"					tsm.sm_id AS \"smId\",\r\n" + 
				"					tsm.jd,\r\n" + 
				"					tsm.wd,\r\n" + 
				"					tsm.location_name AS \"locationName\",\r\n" + 
				"					tsm. CONTENT AS \"content\",\r\n" + 
				"					tsm.smart_score AS \"smartScore\",\r\n" + 
				"					tsm.send_time AS \"sendTime\",\r\n" + 
				"					tu.user_id AS \"userId\",\r\n" + 
				"					tu.nickname AS \"nickName\",\r\n" + 
				"					tu.headimg AS \"headImg\",\r\n" + 
				"					tu.sex\r\n" + 
				"				FROM\r\n" + 
				"					t_smart_message tsm,\r\n" + 
				"					t_user tu\r\n" + 
				"				WHERE\r\n" + 
				"					tsm.user_id <> :userId\r\n" + 
				"				AND tsm.is_delete = '0'\r\n" + 
				"				AND tsm.user_id = tu.user_id\r\n" + 
				"				AND round(\r\n" + 
				"					earth_distance (\r\n" + 
				"						ll_to_earth (:wd, :jd),\r\n" + 
				"						ll_to_earth (tsm.wd, tsm.jd)\r\n" + 
				"					)\r\n" + 
				"				) <= :smartRange\r\n" + 
				"				ORDER BY\r\n" + 
				"					\"sendTime\" DESC\r\n" + 
				"				LIMIT :limit OFFSET :offset\r\n" + 
				"			) smg\r\n" + 
				"		LEFT JOIN t_smart_agree_against_count tsac ON smg.\"smId\" = tsac.sm_id\r\n" + 
				"	) smgc\r\n" + 
				"LEFT JOIN t_smart_read_count tsrc ON smgc.\"smId\" = tsrc.sm_id\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", smartGetSmartMessageListRequest.getLocationWd());
		params.put("jd", smartGetSmartMessageListRequest.getLocationJd());
		params.put("userId", smartGetSmartMessageListRequest.getUserId());
		params.put("limit", smartGetSmartMessageListRequest.getLimit());
		params.put("offset", smartGetSmartMessageListRequest.getOffset());
		params.put("smartRange", smartGetSmartMessageListRequest.getSmartRange());

		List<SmartGetSmartMessageListResponse> list=new ArrayList<SmartGetSmartMessageListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, SmartGetSmartMessageListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 查询某一用户是否同意或者反对过智囊消息
	 * @param userId
	 * @param smId
	 * @return SmartOneUserIsAgainstAndAgree
	 */
	public SmartOneUserIsAgainstAndAgree checkOneUserIsAgainstOrAgree(String userId,String smId){
		
		String sql="SELECT\r\n" + 
				"	*\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"isAgainst\"\r\n" + 
				"		FROM\r\n" + 
				"			t_smart_against_detail tsad\r\n" + 
				"		WHERE\r\n" + 
				"			tsad.user_id = :userId\r\n" + 
				"		AND tsad.sm_id = :smId\r\n" + 
				"	) against,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"isAgree\"\r\n" + 
				"		FROM\r\n" + 
				"			t_smart_agree_detail tsa\r\n" + 
				"		WHERE\r\n" + 
				"			tsa.user_id = :userId\r\n" + 
				"		AND tsa.sm_id = :smId\r\n" + 
				"	) agree\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("smId",smId);
		params.put("userId", userId);
		
        List<SmartOneUserIsAgainstAndAgree> list=new ArrayList<SmartOneUserIsAgainstAndAgree>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, SmartOneUserIsAgainstAndAgree.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据smId获取某一条智囊消息得到的评论总数
	 * @param smId
	 * @return
	 * @throws Exception
	 */
	public Object getOneSmartMessageCommentTotalBySmId(String smId) throws Exception{
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"smartCommentCount\"\r\n" + 
				"FROM\r\n" + 
				"	t_smart_message_comment tsmc\r\n" + 
				"WHERE\r\n" + 
				"	tsmc.sm_id = :smId\r\n" + 
				"";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("smId", smId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "smartCommentCount");
	}
	
	/**
	 * 获取某一条智囊消息的评论（不包含评论中的评论，按时间从近到远排序）
	 * @param smartGetOneMessageCommentListRequest
	 * @return List<SmartGetOneMessageCommentListResponse>
	 */
	public List<SmartGetOneMessageCommentListResponse> getOneMessageCommentListBySmId(SmartGetOneMessageCommentListRequest smartGetOneMessageCommentListRequest){
		String sql="SELECT\r\n" + 
				"	tsmc.comment_id AS \"commentId\",\r\n" + 
				"	tsmc.sm_id AS \"smId\",\r\n" + 
				"	tsmc.\"content\",\r\n" + 
				"	tsmc.jd,\r\n" + 
				"	tsmc.wd,\r\n" + 
				"   tsmc.location_name AS \"locationName\",\r\n" + 
				"	tsmc.comment_time AS \"commentTime\",\r\n" + 
				"	tsmc.comment_score AS \"commentScore\",\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.sex\r\n" + 
				"FROM\r\n" + 
				"	t_smart_message_comment tsmc,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tsmc.sm_id = :smId\r\n" + 
				"AND (tsmc.pid IS NULL OR tsmc.pid = '')\r\n" + 
				"AND tsmc.user_id = tu.user_id\r\n" + 
				"ORDER BY\r\n" + 
				"	\"commentTime\" DESC\r\n" + 
				"LIMIT :limit OFFSET :offset\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("smId", smartGetOneMessageCommentListRequest.getSmId());
		params.put("limit", smartGetOneMessageCommentListRequest.getLimit());
		params.put("offset", smartGetOneMessageCommentListRequest.getOffset());


		List<SmartGetOneMessageCommentListResponse> list=new ArrayList<SmartGetOneMessageCommentListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, SmartGetOneMessageCommentListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 保存同意
	 * @param smartAgree
	 */
	public void saveSmartAgree(SmartAgree smartAgree){
		this.sessionFactory.getCurrentSession().save(smartAgree);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 保存反对
	 * @param smartAgainst
	 */
	public void saveSmartAgainst(SmartAgainst smartAgainst){
		this.sessionFactory.getCurrentSession().save(smartAgainst);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 根据智囊消息id和点同意的人的用户id获取同意的信息
	 * @param smId
	 * @param userId
	 * @return
	 */
	public SmartAgree getSmartAgreeBySmIdAndUserId(String smId,String userId){
		String hql = "from SmartAgree where sm_id = :smId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId); 
		query.setParameter("smId", smId); 
		@SuppressWarnings("unchecked")
		List<SmartAgree> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据智囊消息id和点反对的人的用户id获取反对的信息
	 * @param smId
	 * @param userId
	 * @return
	 */
	public SmartAgainst getSmartAgainstBySmIdAndUserId(String smId,String userId){
		String hql = "from SmartAgainst where sm_id = :smId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId); 
		query.setParameter("smId", smId); 
		@SuppressWarnings("unchecked")
		List<SmartAgainst> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 保存评论
	 * @param smartMessageComment
	 */
	public void saveSmartMessageComment(SmartMessageComment smartMessageComment){
		this.sessionFactory.getCurrentSession().save(smartMessageComment);
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * 根据智囊消息id和评论的用户id获取该用户对消息的评论
	 * @param smId
	 * @param userId
	 * @return
	 */
	public SmartMessageComment getSmartMessageCommentBySmIdAndUserId(String smId,String userId){
		String hql = "from SmartMessageComment where (pid is null OR pid='') AND sm_id = :smId AND user_id=:userId"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);  
		    
		query.setParameter("userId", userId); 
		query.setParameter("smId", smId); 
		@SuppressWarnings("unchecked")
		List<SmartMessageComment> list = query.list(); 
		this.sessionFactory.getCurrentSession().flush();  
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
}
