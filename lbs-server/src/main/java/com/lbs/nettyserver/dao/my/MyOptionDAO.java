package com.lbs.nettyserver.dao.my;

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
import com.lbs.nettyserver.model.request.my.MyGetAttentionPersonsListRequest;
import com.lbs.nettyserver.model.request.my.MyGetFansListRequest;
import com.lbs.nettyserver.model.request.my.MyGetGetCaiListRequest;
import com.lbs.nettyserver.model.request.my.MyGetGetCommentListRequest;
import com.lbs.nettyserver.model.request.my.MyGetGetZanListRequest;
import com.lbs.nettyserver.model.request.my.MyGetLiveMessageTodayListRequest;
import com.lbs.nettyserver.model.request.my.MyGetLiveMessageYesterdayListResquest;
import com.lbs.nettyserver.model.request.my.MyGetPutCaiListRequest;
import com.lbs.nettyserver.model.request.my.MyGetPutCommentListRequest;
import com.lbs.nettyserver.model.request.my.MyGetPutZanListRequest;
import com.lbs.nettyserver.model.response.my.MyGetAttentionPersonsListResponse;
import com.lbs.nettyserver.model.response.my.MyGetFansListResponse;
import com.lbs.nettyserver.model.response.my.MyGetGetCaiListResponse;
import com.lbs.nettyserver.model.response.my.MyGetGetCommentListResponse;
import com.lbs.nettyserver.model.response.my.MyGetGetZanListResponse;
import com.lbs.nettyserver.model.response.my.MyGetLiveMessageTodayListResponse;
import com.lbs.nettyserver.model.response.my.MyGetLiveMessageYesterdayListResponse;
import com.lbs.nettyserver.model.response.my.MyGetPutCaiListResponse;
import com.lbs.nettyserver.model.response.my.MyGetPutCommentListResponse;
import com.lbs.nettyserver.model.response.my.MyGetPutZanListResponse;
import com.lbs.nettyserver.model.response.my.MyGetUserBaseInfoResponse;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 用户中心数据操作类
 * @author visual
 *
 */
@Transactional
@Repository("myOptionDAO")
public class MyOptionDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private BaseDao baseDao;	
	
	/**
	 * 根据用户id更新用户头像路径
	 * @param userId
	 * @param headerImgSrc
	 * @return 受影响的行数
	 */
	public int updateHeaderImgByUserId(String userId,String headerImgSrc){
		int result=0;
		Query query = this.sessionFactory.getCurrentSession().createQuery("update User set headImg=:headImg where userId=:userId");
		query.setParameter("headImg", headerImgSrc);
		query.setParameter("userId", userId); 
		result=query.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		return result;
	}
	
	/**
	 * 根据用户id更新用户背景图片
	 * @param userId
	 * @param backImgSrc
	 * @return 受影响的行数
	 */
	public int updateBackImgByUserId(String userId,String backImgSrc){
		int result=0;
		Query query = this.sessionFactory.getCurrentSession().createQuery("update User set backImg=:backImg where userId=:userId");
		query.setParameter("backImg", backImgSrc);
		query.setParameter("userId", userId); 
		result=query.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		return result;
	}
	
	/**
	 * 根据用户id更新用户手机号码
	 * @param userId
	 * @param phone
	 * @return 受影响的行数
	 */
	public int updatePhoneByUserId(String userId,String phone){
		int result=0;
		Query query = this.sessionFactory.getCurrentSession().createQuery("update User set phone=:phone where userId=:userId");
		query.setParameter("phone", phone);
		query.setParameter("userId", userId); 
		result=query.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		return result;
	}
	
	/**
	 * 根据用户id更新用户昵称
	 * @param userId
	 * @param nickName
	 * @return 受影响的行数
	 */
	public int updateNickNameByUserId(String userId,String nickName){
		int result=0;
		Query query = this.sessionFactory.getCurrentSession().createQuery("update User set nickname=:nickName where userId=:userId");
		query.setParameter("nickName", nickName);
		query.setParameter("userId", userId); 
		result=query.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		return result;
	}
	
	
	
	/**
	 * 根据用户id获取用户基本信息
	 * @param userId
	 * @return
	 */
	public MyGetUserBaseInfoResponse getUserBaseInByUserId(String userId){
		
		String sql="SELECT\r\n" + 
				"	tu.email AS \"email\",\r\n" + 
				"	tu.phone AS \"phone\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.live_score AS \"liveScore\",\r\n" + 
				"	tu.levl_val AS \"lv\",\r\n" + 
				"	tu.sex AS \"sex\"\r\n" + 
				"FROM\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tu.user_id = :userId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		List<MyGetUserBaseInfoResponse> list=new ArrayList<MyGetUserBaseInfoResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetUserBaseInfoResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据用户id获取用户今天创建的专题数量
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getCreateSpecialTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"createSpecialTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_special\r\n" + 
				"WHERE\r\n" + 
				"	create_user_id = :userId\r\n" + 
				"AND CAST (creat_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"';";
		
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "createSpecialTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天加入的专题数量
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getJoinSpecialTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"joinSpecialTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_special_users tsu\r\n" + 
				"WHERE\r\n" + 
				"	tsu.user_id = :userId\r\n" + 
				"AND CAST (tsu.join_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "joinSpecialTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天发布的现场消息总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getLiveMessageTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"liveMessageTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	tlm.user_id = :userId\r\n" + 
				" tlm.is_delete='0'"+
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "liveMessageTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天得到的赞的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getGetZanTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"getZanTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_zan tlz\r\n" + 
				"WHERE\r\n" + 
				"	tlz.be_user_id = :userId\r\n" + 
				"AND CAST (tlz.zan_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "getZanTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天发送的赞的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getPutZanTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"putZanTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_zan tlz\r\n" + 
				"WHERE\r\n" + 
				"	tlz.user_id = :userId\r\n" + 
				"AND CAST (tlz.zan_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "putZanTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天得到的踩的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getGetCaiTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"getCaiTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_cai tlc\r\n" + 
				"WHERE\r\n" + 
				"	tlc.be_user_id = :userId\r\n" + 
				"AND CAST (tlc.cai_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "getCaiTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天发送的踩的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getPutCaiTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"putCaiTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_cai tlc\r\n" + 
				"WHERE\r\n" + 
				"	tlc.user_id = :userId\r\n" + 
				"AND CAST (tlc.cai_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "putCaiTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天发送的现场评论的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getPutCommentTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"putCommentTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message_comment tlmc\r\n" + 
				"WHERE\r\n" + 
				"	tlmc.user_id = :userId\r\n" + 
				"AND (\r\n" + 
				"	tlmc.pid IS NULL\r\n" + 
				"	OR tlmc.pid = ''\r\n" + 
				")\r\n" + 
				"AND CAST (tlmc.comment_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "putCommentTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户今天得到的现场评论的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getGetCommentTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"getCommentTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message_comment tlmc\r\n" + 
				"WHERE\r\n" + 
				"	tlmc.lm_id IN (\r\n" + 
				"		SELECT\r\n" + 
				"			tlm.lm_id\r\n" + 
				"		FROM\r\n" + 
				"			t_live_message tlm\r\n" + 
				"		WHERE\r\n" + 
				"			tlm.user_id = :userId\r\n" + 
				"		AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"	);\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "getCommentTodayTotal");
	}
	
	/**
	 * 根据用户id获取用户粉丝的总数（关注我的人）
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getFansTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	get_attentionperson_total AS \"getAttentionpersonTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_userinfo_count tuc\r\n" + 
				"WHERE\r\n" + 
				"	tuc.user_id = :userId\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "getAttentionpersonTotal");
	}
	
	/**
	 * 根据用户id获取用户关注的人的总数（我关注的人）
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getAttentionTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	put_attentionperson_total AS \"putAttentionpersonTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_userinfo_count tuc\r\n" + 
				"WHERE\r\n" + 
				"	 tuc.user_id=:userId\r\n" + 
				"";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "putAttentionpersonTotal");
	}
	
	/**
	 * 根据用户id获取用户今天发布的智囊消息总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public Object getSmartMessageTodayTotalByUserId(String userId) throws Exception{
		
		String sql="SELECT\r\n" + 
				"	COUNT (*) AS \"smartMessageTodayTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_smart_message tsm\r\n" + 
				"WHERE\r\n" + 
				"	tsm.user_id = :userId\r\n" + 
				"AND CAST (tsm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"';";

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return baseDao.selectNativeSqlUniqueResult(sql, params, "smartMessageTodayTotal");
	}
	
	/**
	 * 获取我今天发布的消息列表
	 * @param myGetLiveMessageTodayListRequest
	 * @return
	 */
	public List<MyGetLiveMessageTodayListResponse> getOneUserLiveMessageTodayList(MyGetLiveMessageTodayListRequest myGetLiveMessageTodayListRequest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.location_name AS \"locationName\",\r\n" + 
				"	tlm.send_time AS \"sendTime\",\r\n" + 
				"	tlm. CONTENT AS \"content\",\r\n" + 
				"	tlm.message_score AS \"messageScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	user_id = :userId\r\n" + 
				" AND tlm.is_delete='0'\r\n"+
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"' "+
				"ORDER BY\r\n" + 
				"	tlm.send_time DESC \r\n" + 
				"OFFSET :offset\r\n" + 
				" LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetLiveMessageTodayListRequest.getUserId());
		params.put("limit", myGetLiveMessageTodayListRequest.getLimit());
		params.put("offset", myGetLiveMessageTodayListRequest.getOffset());

		List<MyGetLiveMessageTodayListResponse> list=new ArrayList<MyGetLiveMessageTodayListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetLiveMessageTodayListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我昨天发布的消息列表
	 * @param myGetLiveMessageTodayListRequest
	 * @return
	 */
	public List<MyGetLiveMessageYesterdayListResponse> getOneUserLiveMessageYesterdayList(MyGetLiveMessageYesterdayListResquest myGetLiveMessageYesterdayListResquest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.location_name AS \"locationName\",\r\n" + 
				"	tlm.send_time AS \"sendTime\",\r\n" + 
				"	tlm. CONTENT AS \"content\",\r\n" + 
				"	tlm.message_score AS \"messageScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	user_id = :userId\r\n" + 
				" AND tlm.is_delete='0'\r\n"+
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeYesterday(), "yyyy-MM-dd")+"' "+
				"ORDER BY\r\n" + 
				"	tlm.send_time DESC \r\n" + 
				"OFFSET :offset\r\n" + 
				"LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetLiveMessageYesterdayListResquest.getUserId());
		params.put("limit", myGetLiveMessageYesterdayListResquest.getLimit());
		params.put("offset", myGetLiveMessageYesterdayListResquest.getOffset());

		List<MyGetLiveMessageYesterdayListResponse> list=new ArrayList<MyGetLiveMessageYesterdayListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetLiveMessageYesterdayListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我得到的赞的列表
	 * @param myGetGetZanListRequest
	 * @return
	 */
	public List<MyGetGetZanListResponse> getOneUserGetZanList(MyGetGetZanListRequest myGetGetZanListRequest){
		String sql="SELECT\r\n" + 
				"	tlz.zan_id AS \"zanId\",\r\n" + 
				"	tlz.lm_id AS \"lmId\",\r\n" + 
				"	tlm. CONTENT AS \"liveMessageContent\",\r\n" + 
				"   tlm.is_delete AS \"isDelete\",\r\n"+
				"	tlz.zan_time AS \"zanTime\",\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tu.headimg AS \"headImg\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_zan tlz,\r\n" + 
				"	t_user tu,\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	tlz.be_user_id = :userId\r\n" + 
				"AND tlz.user_id = tu.user_id\r\n" + 
				"AND tlz.lm_id = tlm.lm_id ORDER BY tlz.zan_time DESC OFFSET :offset LIMIT :limit\r\n" + 
				"";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetGetZanListRequest.getUserId());
		params.put("limit", myGetGetZanListRequest.getLimit());
		params.put("offset", myGetGetZanListRequest.getOffset());

		List<MyGetGetZanListResponse> list=new ArrayList<MyGetGetZanListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetGetZanListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我点出的赞的列表
	 * @param myGetPutZanListRequest
	 * @return
	 */
	public List<MyGetPutZanListResponse> getOneUserPutZanList(MyGetPutZanListRequest myGetPutZanListRequest){
		String sql="SELECT\r\n" + 
				"	tlz.zan_id AS \"zanId\",\r\n" + 
				"	tlz.lm_id AS \"lmId\",\r\n" + 
				"	tlm. CONTENT AS \"liveMessageContent\",\r\n" + 
				"   tlm.is_delete AS \"isDelete\",\r\n"+
				"	tlz.zan_time AS \"zanTime\",\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tu.headimg AS \"headImg\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_zan tlz,\r\n" + 
				"	t_user tu,\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	tlz.user_id = :userId\r\n" + 
				"AND tlz.be_user_id = tu.user_id\r\n" + 
				"AND tlz.lm_id = tlm.lm_id\r\n" + 
				"ORDER BY\r\n" + 
				"	tlz.zan_time DESC OFFSET :offset\r\n" + 
				"LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetPutZanListRequest.getUserId());
		params.put("limit", myGetPutZanListRequest.getLimit());
		params.put("offset", myGetPutZanListRequest.getOffset());

		List<MyGetPutZanListResponse> list=new ArrayList<MyGetPutZanListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetPutZanListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我得到的踩的列表
	 * @param myGetGetCaiListRequest
	 * @return
	 */
	public List<MyGetGetCaiListResponse> getOneUserGetCaiList(MyGetGetCaiListRequest myGetGetCaiListRequest){
		String sql="SELECT\r\n" + 
				"	tlc.cai_id AS \"caiId\",\r\n" + 
				"	tlc.lm_id AS \"lmId\",\r\n" + 
				"	tlm. CONTENT AS \" liveMessageContent \",\r\n" + 
				"   tlm.is_delete AS \"isDelete\",\r\n"+
				"	tlc.cai_time AS \"caiTime\",\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tu.headimg AS \"headImg\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_cai tlc,\r\n" + 
				"	t_user tu,\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	tlc.be_user_id = :userId\r\n" + 
				"AND tlc.user_id = tu.user_id\r\n" + 
				"AND tlc.lm_id = tlm.lm_id ORDER BY tlc.cai_time DESC OFFSET :offset LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetGetCaiListRequest.getUserId());
		params.put("limit", myGetGetCaiListRequest.getLimit());
		params.put("offset", myGetGetCaiListRequest.getOffset());

		List<MyGetGetCaiListResponse> list=new ArrayList<MyGetGetCaiListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetGetCaiListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我点出的踩的列表
	 * @param myGetGetCaiListRequest
	 * @return
	 */
	public List<MyGetPutCaiListResponse> getOneUserPutCaiList(MyGetPutCaiListRequest myGetPutCaiListRequest){
		String sql="SELECT\r\n" + 
				"	tlc.cai_id AS \"caiId\",\r\n" + 
				"	tlc.lm_id AS \"lmId\",\r\n" + 
				"	tlm. CONTENT AS \"liveMessageContent\",\r\n" + 
				"   tlm.is_delete AS \"isDelete\",\r\n"+
				"	tlc.cai_time AS \"caiTime\",\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tu.headimg AS \"headImg\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_cai tlc,\r\n" + 
				"	t_user tu,\r\n" + 
				"	t_live_message tlm\r\n" + 
				"WHERE\r\n" + 
				"	tlc.user_id = :userId\r\n" + 
				"AND tlc.be_user_id = tu.user_id\r\n" + 
				"AND tlc.lm_id = tlm.lm_id\r\n" + 
				" ORDER BY tlc.cai_time DESC OFFSET :offset LIMIT :limit";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetPutCaiListRequest.getUserId());
		params.put("limit", myGetPutCaiListRequest.getLimit());
		params.put("offset", myGetPutCaiListRequest.getOffset());

		List<MyGetPutCaiListResponse> list=new ArrayList<MyGetPutCaiListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetPutCaiListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	
	/**
	 * 获取我得到的评论的列表
	 * @param myGetGetCommentListRequest
	 * @return
	 */
	public List<MyGetGetCommentListResponse> getOneUserGetCommentList(MyGetGetCommentListRequest myGetGetCommentListRequest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm. CONTENT AS \"liveMessageContent\",\r\n" + 
				"   tlm.is_delete AS \"isDelete\",\r\n"+
				"	tlmc. CONTENT AS \"commentContent\",\r\n" + 
				"	tlmc.comment_id AS \"commentId\",\r\n" + 
				"	tlmc.comment_time AS \"commentTime\",\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tu.nickname AS \"nickName\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm,\r\n" + 
				"	t_live_message_comment tlmc,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tlm.user_id = :userId\r\n" + 
				"AND tlm.lm_id = tlmc.lm_id\r\n" + 
				"AND tlmc.user_id = tu.user_id\r\n" + 
				"ORDER BY tlmc.comment_time DESC OFFSET :offset LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetGetCommentListRequest.getUserId());
		params.put("limit", myGetGetCommentListRequest.getLimit());
		params.put("offset", myGetGetCommentListRequest.getOffset());

		List<MyGetGetCommentListResponse> list=new ArrayList<MyGetGetCommentListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetGetCommentListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我发表的评论的列表
	 * @param myGetPutCommentListRequest
	 * @return
	 */
	public List<MyGetPutCommentListResponse> getOneUserPutCommentList(MyGetPutCommentListRequest myGetPutCommentListRequest){
		String sql="SELECT\r\n" + 
				"	tlmc.lm_id AS \"lmId\",\r\n" + 
				"	tlmc. CONTENT AS \"commentContent\",\r\n" + 
				"	tlmc.comment_id AS \"commentId\",\r\n" + 
				"	tlmc.comment_time AS \"commentTime\",\r\n" + 
				"   tlm. CONTENT AS \"liveMessageContent\",\r\n" + 
				"   tlm.is_delete AS \"isDelete\",\r\n"+
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tu.nickname AS \"nickName\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message_comment tlmc,\r\n" + 
				"    t_live_message tlm,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tlmc.user_id = :userId\r\n" + 
				"AND tlmc.lm_id=tlm.lm_id\r\n" + 
				"AND tlm.user_id = tu.user_id\r\n" + 
				"ORDER BY tlmc.comment_time DESC OFFSET :offset LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetPutCommentListRequest.getUserId());
		params.put("limit", myGetPutCommentListRequest.getLimit());
		params.put("offset", myGetPutCommentListRequest.getOffset());

		List<MyGetPutCommentListResponse> list=new ArrayList<MyGetPutCommentListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetPutCommentListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我关注的人员的列表
	 * @param myGetAttentionPersonsListRequest
	 * @return
	 */
	public List<MyGetAttentionPersonsListResponse> getOneUserAttentionPersonsList(MyGetAttentionPersonsListRequest myGetAttentionPersonsListRequest){
		String sql="SELECT\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.sex AS \"sex\",\r\n" + 
				"	tap.attention_time AS \"attentionTime\"\r\n" + 
				"FROM\r\n" + 
				"	t_attention_person tap,\r\n" + 
				"	t_user tu	\r\n" + 
				"WHERE\r\n" + 
				"	tap.user_id = :userId\r\n" + 
				"AND tap.be_user_id = tu.user_id ORDER BY tap.attention_time DESC OFFSET :offset LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetAttentionPersonsListRequest.getUserId());
		params.put("limit", myGetAttentionPersonsListRequest.getLimit());
		params.put("offset", myGetAttentionPersonsListRequest.getOffset());

		List<MyGetAttentionPersonsListResponse> list=new ArrayList<MyGetAttentionPersonsListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetAttentionPersonsListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 获取我的粉丝的列表
	 * @param myGetFansListRequest
	 * @return
	 */
	public List<MyGetFansListResponse> getOneUserFansList(MyGetFansListRequest myGetFansListRequest){
		String sql="SELECT\r\n" + 
				"	fans.\"userId\",\r\n" + 
				"	fans.\"headImg\",\r\n" + 
				"	fans.\"nickName\",\r\n" + 
				"	fans.sex,\r\n" + 
				"	fans.\"attentionTime\",\r\n" + 
				"	CASE\r\n" + 
				"WHEN att.user_id IS NULL THEN\r\n" + 
				"	'0'\r\n" + 
				"ELSE\r\n" + 
				"	'1'\r\n" + 
				"END AS \"isAttention\"\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			tu.user_id AS \"userId\",\r\n" + 
				"			tu.headimg AS \"headImg\",\r\n" + 
				"			tu.nickname AS \"nickName\",\r\n" + 
				"			tu.sex AS \"sex\",\r\n" + 
				"			tap.attention_time AS \"attentionTime\",\r\n" + 
				"			tap.be_user_id AS \"beUserId\"\r\n" + 
				"		FROM\r\n" + 
				"			t_attention_person tap,\r\n" + 
				"			t_user tu\r\n" + 
				"		WHERE\r\n" + 
				"			tap.be_user_id = :userId\r\n" + 
				"		AND tap.user_id = tu.user_id\r\n" + 
				"	) fans\r\n" + 
				"LEFT JOIN (\r\n" + 
				"	SELECT\r\n" + 
				"		user_id,\r\n" + 
				"		be_user_id\r\n" + 
				"	FROM\r\n" + 
				"		t_attention_person\r\n" + 
				") att ON fans.\"userId\" = att.be_user_id\r\n" + 
				"AND fans.\"beUserId\" = att.user_id\r\n" + 
				" ORDER BY fans.\"attentionTime\" DESC OFFSET :offset LIMIT :limit";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", myGetFansListRequest.getUserId());
		params.put("limit", myGetFansListRequest.getLimit());
		params.put("offset", myGetFansListRequest.getOffset());

		List<MyGetFansListResponse> list=new ArrayList<MyGetFansListResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, MyGetFansListResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	public boolean deleteLiveMessage(String userId,String lmId){
		
		int liveMsgResult=0;
		boolean result=false;
		Query liveMessageQuery = this.sessionFactory.getCurrentSession().createQuery("update LiveMessage set isDelete='1' where lmId=:lmId");
		liveMessageQuery.setParameter("lmId", lmId);
		liveMsgResult=liveMessageQuery.executeUpdate();
		this.sessionFactory.getCurrentSession().flush();
		
		if(liveMsgResult>0){
			Query userInfoCountQuery = this.sessionFactory.getCurrentSession().createSQLQuery("update t_userinfo_count set put_livemsg_total=put_livemsg_total-1 where user_id=:userId");
			userInfoCountQuery.setParameter("userId", userId);
			userInfoCountQuery.executeUpdate();
			this.sessionFactory.getCurrentSession().flush();
			result=true;
		}		
		return result;
	}
	
}
