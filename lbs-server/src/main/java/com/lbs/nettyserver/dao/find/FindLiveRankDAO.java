package com.lbs.nettyserver.dao.find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.request.find.FindLiveRankRequest;
import com.lbs.nettyserver.model.response.find.FindLiveRankResponse;

/**
 * 发现-现场排名
 * @author visual
 *
 */
@Transactional
@Repository("findLiveRankDAO")
public class FindLiveRankDAO {
	
	@Autowired 
    private BaseDao baseDao;
	
	/**
	 * 得到发现-现场排名列表
	 * @param FindLiveRankRequest
	 * @return
	 */
	public List<FindLiveRankResponse> getFindLiveRankInfo(FindLiveRankRequest findliveRankRequest){
		/**
		 * sql:得到吐槽范围内在线人员的吐槽等级排名列表-按吐槽等级从大到小排序
		 */
		String sql="SELECT tu.user_id as \"userId\",tu.nickname as \"nickName\",tu.sex,tu.headimg as \"headImg\",tu.live_score as \"liveScore\",round(earth_distance(ll_to_earth(:wd, :jd), ll_to_earth(tlu.wd, tlu.jd))) distance FROM t_login_user tlu,t_user tu WHERE tlu.user_id=tu.user_id AND earth_distance(ll_to_earth(:wd, :jd), ll_to_earth(tlu.wd, tlu.jd))<= :liveRange AND tlu.is_online=TRUE ORDER BY tu.live_score DESC";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", findliveRankRequest.getWd());
		params.put("jd", findliveRankRequest.getJd());
		params.put("liveRange", findliveRankRequest.getLiveRange());
		List<FindLiveRankResponse> list=new ArrayList<FindLiveRankResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, FindLiveRankResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}

}
