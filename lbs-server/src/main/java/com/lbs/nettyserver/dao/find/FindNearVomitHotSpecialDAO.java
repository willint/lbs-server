package com.lbs.nettyserver.dao.find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.request.find.FindNearVomitHotSpecialRequest;
import com.lbs.nettyserver.model.response.find.FindNearVomitHotSpecialResponse;

@Transactional
@Repository("findNearVomitHotSpecialDAO")
public class FindNearVomitHotSpecialDAO {
	@Autowired 
    private BaseDao baseDao;
	
	/**
	 * 得到发现-现场排名列表
	 * @param FindLiveRankRequest
	 * @return
	 */
	public List<FindNearVomitHotSpecialResponse> getFindNearVomitHotSpecialList(FindNearVomitHotSpecialRequest findNearVomitHotSpecialRequest){
		/**
		 * sql:得到发现-附近热点专题(在有效时间内，不是自己创建的并且没有参与的)按距离和参与人数从近到远，从多到少排序后的前10条数据
		 */
		String sql="SELECT\r\n" + 
				"	userinfo.nickname as \\\"nickName\\\",userinfo.sex,userinfo.headimg as \\\"headImg\\\",userinfo.levl_val as \\\"lv\\\",ts.create_user_id as \\\"createUserId\\\" ,ts.them,ts.img_path as \\\"imgPath\\\",ts.condition,ts.person_count_limit as \\\"personCountLimit\\\",ts.jd,ts.wd,ts.special_id as \\\"specialId\\\",ts.creat_time as \\\"createTime\\\",ts.effective_time as \\\"effectiveTime\\\",ts.free0,ts.free1,suser.persontotal as \\\"personTotal\\\",round(earth_distance(ll_to_earth(:wd, :jd), ll_to_earth(ts.wd, ts.jd))) distance \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			tu.user_id,\r\n" + 
				"			tu.nickname,\r\n" + 
				"			tu.headimg,\r\n" + 
				"			tu.sex,\r\n" + 
				"			tu.levl_val\r\n" + 
				"		FROM\r\n" + 
				"			t_user tu,\r\n" + 
				"	) userinfo,\r\n" + 
				"	(SELECT * FROM t_special WHERE round(earth_distance(ll_to_earth(:wd, :jd), ll_to_earth(t_special.wd, t_special.jd)))<=:vomitRange AND (select date_part('day',current_timestamp - t_special.creat_time)*24< t_special.effective_time) AND  t_special.special_id NOT IN(SELECT t_special_users.special_id FROM t_special_users WHERE t_special_users.user_id='f4c7fb0fb4854d2dbf7c18e50858853d')) ts,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			tsu.special_id,\r\n" + 
				"			COUNT (*) AS persontotal\r\n" + 
				"		FROM\r\n" + 
				"			t_special_users tsu\r\n" + 
				"		GROUP BY\r\n" + 
				"			tsu.special_id\r\n" + 
				"	) suser\r\n" + 
				"WHERE\r\n" + 
				"	ts.create_user_id <> :userId\r\n" + 
				"AND ts.create_user_id = userinfo.user_id\r\n" + 
				"AND ts.special_id = suser.special_id ORDER BY distance ASC,personTotal ASC LIMIT 10";

		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", findNearVomitHotSpecialRequest.getWd());
		params.put("jd", findNearVomitHotSpecialRequest.getJd());
		params.put("vomitRange", findNearVomitHotSpecialRequest.getVomitRange());
		params.put("userId", findNearVomitHotSpecialRequest.getUserId());
		List<FindNearVomitHotSpecialResponse> list=new ArrayList<FindNearVomitHotSpecialResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, FindNearVomitHotSpecialResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
}
