package com.lbs.nettyserver.dao.live;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.common.LiveMessageZanCaiTotal;
import com.lbs.nettyserver.model.pojo.LiveMessage;
import com.lbs.nettyserver.model.request.live.LiveGetFixedPointMessageRequest;
import com.lbs.nettyserver.model.request.live.LiveGetPathMessageRequest;
import com.lbs.nettyserver.model.request.live.LiveGetTodayMessageRequest;
import com.lbs.nettyserver.model.request.live.LiveGetYesterdayMessageRequest;
import com.lbs.nettyserver.model.request.live.LiveRankRequest;
import com.lbs.nettyserver.model.response.live.LiveGetFixedPointMessageResponse;
import com.lbs.nettyserver.model.response.live.LiveGetOneMessageCommentTotalAndMessageScoreResponse;
import com.lbs.nettyserver.model.response.live.LiveGetPathMessageResponse;
import com.lbs.nettyserver.model.response.live.LiveGetTodayMessageResponse;
import com.lbs.nettyserver.model.response.live.LiveGetYesterdayMessageResponse;
import com.lbs.nettyserver.model.response.live.LiveOneUserZanAndCaiTodayTotalResponse;
import com.lbs.nettyserver.model.response.live.LiveOneUserZanAndCaiTotalResponse;
import com.lbs.nettyserver.model.response.live.LiveRankResponse;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 现场消息表
 * @author visual
 *
 */
@Transactional
@Repository("liveMessageDAO")
public class LiveMessageDAO {

	@Autowired 
    private BaseDao baseDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存现场发布的消息
	 * @param liveMessage
	 */
	public void saveLiveMessage(LiveMessage liveMessage){
		this.sessionFactory.getCurrentSession().save(liveMessage);
		this.sessionFactory.getCurrentSession().flush();
	}
	/**
	 * 获取今天现场消息，按照时间、距离从近到远排序
	 * @param liveGetTodayMessageRequest
	 * @return
	 */
	public List<LiveGetTodayMessageResponse> getTodayLiveMessage(LiveGetTodayMessageRequest liveGetTodayMessageRequest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.jd,\r\n" + 
				"	tlm.wd,\r\n" + 
				"	tlm.location_name AS \"locationName\",\r\n" + 
				"	tlm. CONTENT,\r\n" + 
				"	tlm.message_score AS \"messageScore\",\r\n" + 
				"	tlm.send_time AS \"sendTime\",\r\n" + 
				"	round(\r\n" + 
				"		earth_distance (\r\n" + 
				"			ll_to_earth (:wd, :jd),\r\n" + 
				"			ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"		)\r\n" + 
				"	) distance,\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.sex,\r\n" + 
				"	tu.live_score AS \"liveScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"  tlm.is_delete='0'\r\n"+
				" AND	tlm.user_id <> :userId\r\n" + 
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"AND tlm.user_id = tu.user_id\r\n" + 
				"AND round(\r\n" + 
				"	earth_distance (\r\n" + 
				"		ll_to_earth (:wd, :jd),\r\n" + 
				"		ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"	)\r\n" + 
				") <= :liveRange\r\n" + 
				"ORDER BY\r\n" + 
				"\"sendTime\" DESC,distance ASC limit :limit offset :offset";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", liveGetTodayMessageRequest.getWd());
		params.put("jd", liveGetTodayMessageRequest.getJd());
		params.put("liveRange", liveGetTodayMessageRequest.getLiveRange());
		params.put("userId", liveGetTodayMessageRequest.getUserId());
		params.put("limit", liveGetTodayMessageRequest.getLimit());
		params.put("offset", liveGetTodayMessageRequest.getOffset());

		List<LiveGetTodayMessageResponse> list=new ArrayList<LiveGetTodayMessageResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveGetTodayMessageResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	
	/**
	 * 获取昨天现场消息，按照时间、距离从近到远排序
	 * @param liveGetTodayMessageRequest
	 * @return
	 */
	public List<LiveGetYesterdayMessageResponse> getYesterdayLiveMessage(LiveGetYesterdayMessageRequest liveGetYesterdayMessageRequest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.jd,\r\n" + 
				"	tlm.wd,\r\n" + 
				"	tlm.location_name AS \"locationName\",\r\n" + 
				"	tlm. CONTENT,\r\n" + 
				"	tlm.message_score AS \"messageScore\",\r\n" + 
				"	tlm.send_time AS \"sendTime\",\r\n" + 
				"	round(\r\n" + 
				"		earth_distance (\r\n" + 
				"			ll_to_earth (:wd, :jd),\r\n" + 
				"			ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"		)\r\n" + 
				"	) distance,\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.sex,\r\n" + 
				"	tu.live_score AS \"liveScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tlm.user_id <> :userId\r\n" + 
				" AND tlm.is_delete='0'\r\n"+
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeYesterday(), "yyyy-MM-dd")+"'\r\n" + 
				"AND tlm.user_id = tu.user_id\r\n" + 
				"AND round(\r\n" + 
				"	earth_distance (\r\n" + 
				"		ll_to_earth (:wd, :jd),\r\n" + 
				"		ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"	)\r\n" + 
				") <= :liveRange\r\n" + 
				"ORDER BY\r\n" + 
				"\"sendTime\" DESC,distance ASC limit :limit offset :offset";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", liveGetYesterdayMessageRequest.getWd());
		params.put("jd", liveGetYesterdayMessageRequest.getJd());
		params.put("liveRange", liveGetYesterdayMessageRequest.getLiveRange());
		params.put("userId", liveGetYesterdayMessageRequest.getUserId());
		params.put("limit", liveGetYesterdayMessageRequest.getLimit());
		params.put("offset", liveGetYesterdayMessageRequest.getOffset());

		List<LiveGetYesterdayMessageResponse> list=new ArrayList<LiveGetYesterdayMessageResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveGetYesterdayMessageResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	
	/**
	 * 获取定点现场消息，按照时间、距离从近到远排序，默认只获取今天的
	 * @param liveGetTodayMessageRequest
	 * @return
	 */
	public List<LiveGetFixedPointMessageResponse> getFixedPointLiveMessage(LiveGetFixedPointMessageRequest liveGetFixedPointMessageRequest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.jd,\r\n" + 
				"	tlm.wd,\r\n" + 
				"	tlm.location_name AS \"locationName\",\r\n" +
				"	tlm. CONTENT,\r\n" + 
				"	tlm.message_score AS \"messageScore\",\r\n" + 
				"	tlm.send_time AS \"sendTime\",\r\n" + 
				"	round(\r\n" + 
				"		earth_distance (\r\n" + 
				"			ll_to_earth (:wd, :jd),\r\n" + 
				"			ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"		)\r\n" + 
				"	) distance,\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.sex,\r\n" + 
				"	tu.live_score AS \"liveScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tlm.user_id <> :userId\r\n" + 
				" AND tlm.is_delete='0'\r\n"+
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"AND tlm.user_id = tu.user_id\r\n" + 
				"AND round(\r\n" + 
				"	earth_distance (\r\n" + 
				"		ll_to_earth (:wd, :jd),\r\n" + 
				"		ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"	)\r\n" + 
				") <= :liveRange\r\n" + 
				"ORDER BY\r\n" + 
				"\"sendTime\" DESC,distance ASC limit :limit offset :offset";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", liveGetFixedPointMessageRequest.getWd());
		params.put("jd", liveGetFixedPointMessageRequest.getJd());
		params.put("liveRange", liveGetFixedPointMessageRequest.getLiveRange());
		params.put("userId", liveGetFixedPointMessageRequest.getUserId());
		params.put("limit", liveGetFixedPointMessageRequest.getLimit());
		params.put("offset", liveGetFixedPointMessageRequest.getOffset());

		List<LiveGetFixedPointMessageResponse> list=new ArrayList<LiveGetFixedPointMessageResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveGetFixedPointMessageResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	
	/**
	 * 获取轨迹现场消息，按照时间、距离从近到远排序，默认只获取今天的
	 * @param liveGetTodayMessageRequest
	 * @return
	 */
	public List<LiveGetPathMessageResponse> getPathLiveMessage(LiveGetPathMessageRequest LiveGetPathMessageRequest){
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.jd,\r\n" + 
				"	tlm.wd,\r\n" + 
				"  tlm.location_name AS \"locationName\",\r\n" + 
				"	tlm. CONTENT,\r\n" + 
				"	tlm.message_score AS \"messageScore\",\r\n" + 
				"	tlm.send_time AS \"sendTime\",\r\n" + 
				"	round(\r\n" + 
				"		earth_distance (\r\n" + 
				"			ll_to_earth (:userWd, :userJd),\r\n" + 
				"			ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"		)\r\n" + 
				"	) distance,\r\n" + 
				"	tu.user_id AS \"userId\",\r\n" + 
				"	tu.nickname AS \"nickName\",\r\n" + 
				"	tu.headimg AS \"headImg\",\r\n" + 
				"	tu.sex,\r\n" + 
				"	tu.live_score AS \"liveScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_live_message tlm,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tlm.user_id <> :userId\r\n" + 
				" AND tlm.is_delete='0'\r\n"+
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"'\r\n" + 
				"AND tlm.user_id = tu.user_id\r\n" + 
				"AND \r\n" + 
				"(tlm.jd>=:minJd AND tlm.jd<=:maxJd)\r\n" + 
				"ORDER BY \"sendTime\" DESC,\r\n" + 
				" distance ASC\r\n" + 
				"";
		//TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")
		
		double[] pathJdArray=LiveGetPathMessageRequest.getPathJdArray();
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userWd", LiveGetPathMessageRequest.getUserWd());
		params.put("userJd", LiveGetPathMessageRequest.getUserJd());
		params.put("userId", LiveGetPathMessageRequest.getUserId());
		params.put("minJd", pathJdArray[0]<pathJdArray[pathJdArray.length-1]?pathJdArray[0]:pathJdArray[pathJdArray.length-1]);
		params.put("maxJd", pathJdArray[0]<pathJdArray[pathJdArray.length-1]?pathJdArray[pathJdArray.length-1]:pathJdArray[0]);

		List<LiveGetPathMessageResponse> list=new ArrayList<LiveGetPathMessageResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveGetPathMessageResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	
	/**
	 * 获取某一条现场消息赞踩的总数以及该userId用户是否已经赞踩过
	 * @param lmId
	 * @param userId
	 */
	public LiveMessageZanCaiTotal getLiveMessageZanAnCaiTotal(String lmId,String userId){
		String sql="SELECT\r\n" + 
				"	tcai.\"caiTotal\",\r\n" + 
				"	cai.\"selfCai\" AS \"isCai\",\r\n" + 
				"	tzan.\"zanTotal\",\r\n" + 
				"	zan.\"selfZan\" AS \"isZan\"\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"caiTotal\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_cai\r\n" + 
				"		WHERE\r\n" + 
				"			t_live_cai.lm_id = :lmId\r\n" + 
				"	) tcai,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"selfCai\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_cai\r\n" + 
				"		WHERE\r\n" + 
				"			t_live_cai.lm_id = :lmId\r\n" + 
				"		AND t_live_cai.user_id = :userId\r\n" + 
				"	) cai,\r\n" + 
				"(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"zanTotal\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_zan\r\n" + 
				"		WHERE\r\n" + 
				"			t_live_zan.lm_id = :lmId\r\n" + 
				"	) tzan,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"selfZan\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_zan\r\n" + 
				"		WHERE\r\n" + 
				"			t_live_zan.lm_id = :lmId\r\n" + 
				"		AND t_live_zan.user_id = :userId\r\n" + 
				"	) zan";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("lmId",lmId);
		params.put("userId", userId);
		
        List<LiveMessageZanCaiTotal> list=new ArrayList<LiveMessageZanCaiTotal>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveMessageZanCaiTotal.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 现场-获取某一条消息的评论总数和消息质量得分
	 * @param lmId
	 */
	public LiveGetOneMessageCommentTotalAndMessageScoreResponse getOneMessageCommentTotalAndMessageScoreById(String lmId){
		String sql="SELECT\r\n" + 
				"	*\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"commentTotal\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_message_comment lmc\r\n" + 
				"		WHERE\r\n" + 
				"			lmc.lm_id = :lmId\r\n" + 
				"	) lmct,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			lm.message_score AS \"messageScore\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_message lm\r\n" + 
				"		WHERE\r\n" + 
				"			lm.lm_id = :lmId\r\n" + 
				"	) lms";
		
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("lmId",lmId);
		
        List<LiveGetOneMessageCommentTotalAndMessageScoreResponse> list=new ArrayList<LiveGetOneMessageCommentTotalAndMessageScoreResponse>();
		
		try {
			list=baseDao.selectNativeSqlList(sql, params, LiveGetOneMessageCommentTotalAndMessageScoreResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 现场-获取某一用户的当前排名及基本信息
	 * @param liveRankRequest
	 * @return
	 */
	public LiveRankResponse getLiveOneUserRank(LiveRankRequest liveRankRequest){
		
		String sql="SELECT * FROM (SELECT\r\n" + 
				"  row_number() OVER (ORDER BY tu.live_score DESC) as \"rank\",\r\n" + 
				"	tu.user_id as \"userId\",\r\n" + 
				"	tu.nickname as \"nickName\",\r\n" + 
				"	tu.sex,\r\n" + 
				"	tu.headimg as \"headImg\",\r\n" + 
				"	tu.live_score as \"liveScore\"\r\n" + 
				"FROM\r\n" + 
				"	t_login_user tlu,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"	tlu.is_online=TRUE AND tlu.user_id = tu.user_id\r\n" + 
				"AND earth_distance (\r\n" + 
				"	ll_to_earth (:wd, :jd),\r\n" + 
				"	ll_to_earth (tlu.wd, tlu.jd)\r\n" + 
				") <= :liveRange\r\n" + 
				"AND tlu.is_online = TRUE) t WHERE \"userId\"=:userId";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("jd",liveRankRequest.getJd());
		params.put("wd",liveRankRequest.getWd());
		params.put("liveRange",liveRankRequest.getLiveRange());
		params.put("userId",liveRankRequest.getUserId());
		
		 List<LiveRankResponse> list=new ArrayList<LiveRankResponse>();
			
			try {
				list=baseDao.selectNativeSqlList(sql, params, LiveRankResponse.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据用户id获取该用户获得的赞踩总数
	 * @param userId
	 * @return
	 */
	public LiveOneUserZanAndCaiTotalResponse getOneUserZanAndCaiTotalByBeUserId(String userId){
		String sql="SELECT\r\n" + 
				"	tuc.get_zan_total AS \"zanTotal\",\r\n" + 
				"	tuc.get_cai_total AS \"caiTotal\"\r\n" + 
				"FROM\r\n" + 
				"	t_userinfo_count tuc\r\n" + 
				"WHERE\r\n" + 
				"	tuc.user_id = :userId";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId",userId);
		
		 List<LiveOneUserZanAndCaiTotalResponse> list=new ArrayList<LiveOneUserZanAndCaiTotalResponse>();
			
			try {
				list=baseDao.selectNativeSqlList(sql, params, LiveOneUserZanAndCaiTotalResponse.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/**
	 * 根据用户id获取该用户今天获得的赞踩总数
	 * @param beUserId
	 * @return
	 */
	public LiveOneUserZanAndCaiTodayTotalResponse getOneUserZanAndCaiTodayTotalByBeUserId(String beUserId){
		String sql="SELECT\r\n" + 
				"	*\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"zanTodayTotal\"\r\n" +  
				"		FROM\r\n" + 
				"			t_live_zan\r\n" + 
				"		WHERE\r\n" + 
				"			be_user_id = :beUserId\r\n" + "AND CAST(zan_time AS DATE)=DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"' "+
				"	) zan,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			COUNT (*) AS \"caiTodayTotal\"\r\n" + 
				"		FROM\r\n" + 
				"			t_live_cai\r\n" + 
				"		WHERE\r\n" + 
				"			be_user_id = :beUserId\r\n" +"AND CAST(cai_time AS DATE)=DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"' "+  
				"	) cai\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("beUserId",beUserId);
		
		 List<LiveOneUserZanAndCaiTodayTotalResponse> list=new ArrayList<LiveOneUserZanAndCaiTodayTotalResponse>();
			
			try {
				list=baseDao.selectNativeSqlList(sql, params, LiveOneUserZanAndCaiTodayTotalResponse.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (list!=null && list.size()>0)?list.get(0):null;
	}
}
