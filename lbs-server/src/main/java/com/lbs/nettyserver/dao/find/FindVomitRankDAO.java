package com.lbs.nettyserver.dao.find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.request.find.FindVomitRankRequest;
import com.lbs.nettyserver.model.response.find.FindVomitRankResponse;

@Transactional
@Repository("findVomitRankDAO")
public class FindVomitRankDAO {
	
	@Autowired 
    private BaseDao baseDao;
	
	/**
	 * 得到发现-吐槽排名列表
	 * @param findVomitRankRequest
	 * @return
	 */
	public List<FindVomitRankResponse> getFindVomitRankInfo(FindVomitRankRequest findVomitRankRequest){
		/**
		 * sql:得到吐槽范围内在线人员的吐槽等级排名列表-按吐槽等级从大到小排序
		 */
		String sql="SELECT tu.user_id as \"userId\",tu.nickname as \"nickName\",tu.sex,tu.headimg as \"headImg\",tu.levl_val as \"lv\",round(earth_distance(ll_to_earth(:wd, :jd), ll_to_earth(tlu.wd, tlu.jd))) distance FROM t_login_user tlu,t_user tu WHERE tlu.user_id=tu.user_id AND earth_distance(ll_to_earth(:wd, :jd), ll_to_earth(tlu.wd, tlu.jd))<= :vomitRange AND tlu.is_online=TRUE ORDER BY tu.levl_val DESC";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", findVomitRankRequest.getWd());
		params.put("jd", findVomitRankRequest.getJd());
		params.put("vomitRange", findVomitRankRequest.getVomitRange());
		List<FindVomitRankResponse> list=new ArrayList<FindVomitRankResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, FindVomitRankResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
}
