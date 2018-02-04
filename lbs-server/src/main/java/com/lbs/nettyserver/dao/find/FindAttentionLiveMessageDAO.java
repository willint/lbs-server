package com.lbs.nettyserver.dao.find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.request.find.FindAttentionLiveMessageRequest;
import com.lbs.nettyserver.model.response.find.FindAttentionLiveMessageResponse;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;

@Transactional
@Repository("findAttentionLiveMessageDAO")
public class FindAttentionLiveMessageDAO {

	@Autowired 
    private BaseDao baseDao;
	
	/**
	 * 获取我关注的人的现场动态（今天），按照时间、距离从近到远排序
	 * @param findAttentionLiveMessageRequest
	 * @return
	 */
	public List<FindAttentionLiveMessageResponse> getAttentionLiveMessageList(FindAttentionLiveMessageRequest findAttentionLiveMessageRequest){
		
		String sql="SELECT\r\n" + 
				"	tlm.lm_id AS \"lmId\",\r\n" + 
				"	tlm.jd,\r\n" + 
				"	tlm.wd,\r\n" + 
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
				"  t_live_attention_person tlap,\r\n" + 
				"	t_live_message tlm,\r\n" + 
				"	t_user tu\r\n" + 
				"WHERE\r\n" + 
				"  tlap.user_id=:userId\r\n" + 
				"AND\r\n" + 
				"	tlm.user_id <> :userId\r\n" + 
				"AND CAST (tlm.send_time AS DATE) = DATE '"+TimeUtil.dateTimeFormatToString(TimeUtil.getChinaLocalDateTimeNow(), "yyyy-MM-dd")+"' " + 
				"AND tlap.be_user_id=tlm.user_id AND tlap.be_user_id= tu.user_id\r\n" + 
				"AND round(\r\n" + 
				"	earth_distance (\r\n" + 
				"		ll_to_earth (:wd, :jd),\r\n" + 
				"		ll_to_earth (tlm.wd, tlm.jd)\r\n" + 
				"	)\r\n" + 
				") <= :liveRange ORDER BY \"sendTime\" DESC,\r\n" + 
				" distance ASC\r\n" + 
				"LIMIT 10 OFFSET 0";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", findAttentionLiveMessageRequest.getWd());
		params.put("jd", findAttentionLiveMessageRequest.getJd());
		params.put("liveRange", findAttentionLiveMessageRequest.getLiveRange());
		params.put("userId", findAttentionLiveMessageRequest.getUserId());
		List<FindAttentionLiveMessageResponse> list=new ArrayList<FindAttentionLiveMessageResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, FindAttentionLiveMessageResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
}
