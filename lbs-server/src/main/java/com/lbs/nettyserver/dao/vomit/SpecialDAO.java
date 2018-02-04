package com.lbs.nettyserver.dao.vomit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.pojo.Special;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialGetCreatedSpecialRequest;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialGetFindSpecialRequest;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialGetJoinedSpecialRequest;
import com.lbs.nettyserver.model.response.vomit.VomitSpecialGetCreatedSpecialResponse;
import com.lbs.nettyserver.model.response.vomit.VomitSpecialGetFindSpecialResponse;
import com.lbs.nettyserver.model.response.vomit.VomitSpecialGetJoinedSpecialResponse;

/**
 * 吐槽专题
 * @author visual
 *
 */
@Transactional
@Repository("specialDAO")
public class SpecialDAO {
	
	@Autowired 
    private BaseDao baseDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存专题
	 * @param special
	 */
	public void saveSpecial(Special special){
		this.sessionFactory.getCurrentSession().save(special);
	}
	/**
	 * 判断某一专题是否已经超期
	 * @param specialId
	 * @return false-超期，true-未超期
	 * @throws Exception 
	 */
	public boolean getSpecialIsOverTime(String specialId) throws Exception{
		String sql="SELECT\r\n" + 
				"	date_part(\r\n" + 
				"		'day',\r\n" + 
				"		CURRENT_TIMESTAMP - t_special.creat_time\r\n" + 
				"	) * 24 < t_special.effective_time AS \"isOverTime\"\r\n" + 
				"FROM\r\n" + 
				"	t_special\r\n" + 
				"WHERE\r\n" + 
				"	t_special.special_id = :specialId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("specialId", specialId);
		return (boolean)baseDao.selectNativeSqlUniqueResult(sql, params, "isOverTime");
	}
	
	/**
	 * 
	 * 吐槽专题-发现专题（在有效时间内，不是自己创建的并且没有参与的，按距离从近到远，参与人数从多到少）
	 * @param vomitFindSpecialRequest
	 * @return
	 */
	public List<VomitSpecialGetFindSpecialResponse> getVomitFindSpecial(VomitSpecialGetFindSpecialRequest vomitSpecialGetFindSpecialRequest){
		String sql="SELECT\r\n" + 
				"	userinfo.nickname AS \"nickName\",\r\n" + 
				"	userinfo.sex,\r\n" + 
				"	userinfo.headimg AS \"headImg\",\r\n" + 
				"	userinfo.lv,\r\n" + 
				"	ts.create_user_id AS \"userId\",\r\n" + 
				"	ts.them,\r\n" + 
				"	ts.img_path AS \"imgPath\",\r\n" + 
				"	ts. CONDITION,\r\n" + 
				"	ts.person_count_limit AS \"personCountLimit\",\r\n" + 
				"	ts.jd,\r\n" + 
				"	ts.wd,\r\n" + 
				"	ts.special_id AS \"specialId\",\r\n" + 
				"	ts.creat_time AS \"createTime\",\r\n" + 
				"	ts.effective_time AS \"effectiveTime\",\r\n" + 
				"	ts.free0,\r\n" + 
				"	ts.free1,\r\n" + 
				"	suser.persontotal AS \"personTotal\",\r\n" + 
				"	round(\r\n" + 
				"		earth_distance (\r\n" + 
				"			ll_to_earth (:wd, :jd),\r\n" + 
				"			ll_to_earth (ts.wd, ts.jd)\r\n" + 
				"		)\r\n" + 
				"	) distance\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			tu.user_id,\r\n" + 
				"			tu.nickname,\r\n" + 
				"			tu.headimg,\r\n" + 
				"			tu.sex,\r\n" + 
				"			tu.levl_val as \"lv\"\r\n" + 
				"		FROM\r\n" + 
				"			t_user tu,\r\n" + 
				"	) userinfo,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			*\r\n" + 
				"		FROM\r\n" + 
				"			t_special\r\n" + 
				"		WHERE\r\n" + 
				"			round(\r\n" + 
				"				earth_distance (\r\n" + 
				"					ll_to_earth (:wd, :jd),\r\n" + 
				"					ll_to_earth (t_special.wd, t_special.jd)\r\n" + 
				"				)\r\n" + 
				"			) <= :vomitRange\r\n" + 
				"		AND (\r\n" + 
				"			SELECT\r\n" + 
				"				date_part(\r\n" + 
				"					'day',\r\n" + 
				"					CURRENT_TIMESTAMP - t_special.creat_time\r\n" + 
				"				) * 24 < t_special.effective_time\r\n" + 
				"		)\r\n" + 
				"		AND t_special.special_id NOT IN (\r\n" + 
				"			SELECT\r\n" + 
				"				t_special_users.special_id\r\n" + 
				"			FROM\r\n" + 
				"				t_special_users\r\n" + 
				"			WHERE\r\n" + 
				"				t_special_users.user_id = :userId\r\n" + 
				"		)\r\n" + 
				"	) ts,\r\n" + 
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
				"AND ts.special_id = suser.special_id\r\n" + 
				"ORDER BY\r\n" + 
				"	distance ASC,\r\n" + 
				"	\"personTotal\"  DESC \r\n" + 
				"LIMIT :limit OFFSET :offset\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", vomitSpecialGetFindSpecialRequest.getWd());
		params.put("jd", vomitSpecialGetFindSpecialRequest.getJd());
		params.put("vomitRange", vomitSpecialGetFindSpecialRequest.getVomitRange());
		params.put("userId", vomitSpecialGetFindSpecialRequest.getUserId());
		params.put("limit", vomitSpecialGetFindSpecialRequest.getLimit());
		params.put("offset", vomitSpecialGetFindSpecialRequest.getOffset());
		
		List<VomitSpecialGetFindSpecialResponse> list=new ArrayList<VomitSpecialGetFindSpecialResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, VomitSpecialGetFindSpecialResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 吐槽专题-参与的专题（在有效时间内，不是自己创建的并且参与的，按距离从近到远，参与人数从多到少）
	 * @param vomitJoinedSpecialRequest
	 * @return
	 */
	public List<VomitSpecialGetJoinedSpecialResponse> getVomitJoinedSpecial(VomitSpecialGetJoinedSpecialRequest vomitSpecialGetJoinedSpecialRequest){
		String sql="SELECT\r\n" + 
				"	userinfo.nickname AS \"nickName\",\r\n" + 
				"	userinfo.sex,\r\n" + 
				"	userinfo.headimg AS \"headImg\",\r\n" + 
				"	userinfo.lv,\r\n" + 
				"	ts.create_user_id AS \"userId\",\r\n" + 
				"	ts.them,\r\n" + 
				"	ts.img_path AS \"imgPath\",\r\n" + 
				"	ts. CONDITION,\r\n" + 
				"	ts.person_count_limit AS \"personCountLimit\",\r\n" + 
				"	ts.jd,\r\n" + 
				"	ts.wd,\r\n" + 
				"	ts.special_id AS \"specialId\",\r\n" + 
				"	ts.creat_time AS \"createTime\",\r\n" + 
				"	ts.effective_time AS \"effectiveTime\",\r\n" + 
				"	ts.free0,\r\n" + 
				"	ts.free1,\r\n" + 
				"	suser.persontotal AS \"personTotal\",\r\n" + 
				"	round(\r\n" + 
				"		earth_distance (\r\n" + 
				"			ll_to_earth (:wd, :jd),\r\n" + 
				"			ll_to_earth (ts.wd, ts.jd)\r\n" + 
				"		)\r\n" + 
				"	) distance\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			tu.user_id,\r\n" + 
				"			tu.nickname,\r\n" + 
				"			tu.headimg,\r\n" + 
				"			tu.sex,\r\n" + 
				"			tu.levl_val as \"lv\"\r\n" + 
				"		FROM\r\n" + 
				"			t_user tu,\r\n" + 
				"	) userinfo,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			*\r\n" + 
				"		FROM\r\n" + 
				"			t_special\r\n" + 
				"		WHERE\r\n" + 
				"			round(\r\n" + 
				"				earth_distance (\r\n" + 
				"					ll_to_earth (:wd, :jd),\r\n" + 
				"					ll_to_earth (t_special.wd, t_special.jd)\r\n" + 
				"				)\r\n" + 
				"			) <= :vomitRange\r\n" + 
				"		AND (\r\n" + 
				"			SELECT\r\n" + 
				"				date_part(\r\n" + 
				"					'day',\r\n" + 
				"					CURRENT_TIMESTAMP - t_special.creat_time\r\n" + 
				"				) * 24 < t_special.effective_time\r\n" + 
				"		)\r\n" + 
				"		AND t_special.special_id IN (\r\n" + 
				"			SELECT\r\n" + 
				"				t_special_users.special_id\r\n" + 
				"			FROM\r\n" + 
				"				t_special_users\r\n" + 
				"			WHERE\r\n" + 
				"				t_special_users.user_id = :userId\r\n" + 
				"		)\r\n" + 
				"	) ts,\r\n" + 
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
				"AND ts.special_id = suser.special_id\r\n" + 
				"ORDER BY\r\n" + 
				"	distance ASC,\r\n" + 
				"	\"personTotal\"  DESC \r\n" + 
				"LIMIT :limit OFFSET :offset\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wd", vomitSpecialGetJoinedSpecialRequest.getWd());
		params.put("jd", vomitSpecialGetJoinedSpecialRequest.getJd());
		params.put("vomitRange", vomitSpecialGetJoinedSpecialRequest.getVomitRange());
		params.put("userId", vomitSpecialGetJoinedSpecialRequest.getUserId());
		params.put("limit", vomitSpecialGetJoinedSpecialRequest.getLimit());
		params.put("offset", vomitSpecialGetJoinedSpecialRequest.getOffset());
		
		List<VomitSpecialGetJoinedSpecialResponse> list=new ArrayList<VomitSpecialGetJoinedSpecialResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, VomitSpecialGetJoinedSpecialResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 吐槽专题-我创建的专题（在有效时间内，自己创建的，按参与人数从多到少，创建时间从早到晚）
	 * @param vomitCreatedSpecialRequest
	 * @return
	 */
	public List<VomitSpecialGetCreatedSpecialResponse> getVomitCreatedSpecial(VomitSpecialGetCreatedSpecialRequest vomitSpecialGetCreatedSpecialRequest){
		String sql="SELECT\r\n" + 
				"	userinfo.nickname AS \"nickName\",\r\n" + 
				"	userinfo.sex,\r\n" + 
				"	userinfo.headimg AS \"headImg\",\r\n" + 
				"	userinfo.lv,\r\n" + 
				"	ts.create_user_id AS \"userId\",\r\n" + 
				"	ts.them,\r\n" + 
				"	ts.img_path AS \"imgPath\",\r\n" + 
				"	ts. CONDITION,\r\n" + 
				"	ts.person_count_limit AS \"personCountLimit\",\r\n" + 
				"	ts.jd,\r\n" + 
				"	ts.wd,\r\n" + 
				"	ts.special_id AS \"specialId\",\r\n" + 
				"	ts.creat_time AS \"createTime\",\r\n" + 
				"	ts.effective_time AS \"effectiveTime\",\r\n" + 
				"	ts.free0,\r\n" + 
				"	ts.free1,\r\n" + 
				"	suser.persontotal AS \"personTotal\"\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			tu.user_id,\r\n" + 
				"			tu.nickname,\r\n" + 
				"			tu.headimg,\r\n" + 
				"			tu.sex,\r\n" + 
				"			tu.levl_val as \"lv\"\r\n" + 
				"		FROM\r\n" + 
				"			t_user tu,\r\n" + 
				"	) userinfo,\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			*\r\n" + 
				"		FROM\r\n" + 
				"			t_special\r\n" + 
				"		WHERE\r\n" + 
				"(\r\n" + 
				"			SELECT\r\n" + 
				"				date_part(\r\n" + 
				"					'day',\r\n" + 
				"					CURRENT_TIMESTAMP - t_special.creat_time\r\n" + 
				"				) * 24 < t_special.effective_time\r\n" + 
				"		)\r\n" + 
				"	) ts,\r\n" + 
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
				"	ts.create_user_id = :userId\r\n" + 
				"AND ts.create_user_id = userinfo.user_id\r\n" + 
				"AND ts.special_id = suser.special_id\r\n" + 
				"ORDER BY\r\n" + 
				"	\"personTotal\" DESC ,\r\n" + 
				"\"createTime\"  DESC\r\n" + 
				"LIMIT :limit OFFSET :offset\r\n" + 
				"";
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", vomitSpecialGetCreatedSpecialRequest.getUserId());
		params.put("limit", vomitSpecialGetCreatedSpecialRequest.getLimit());
		params.put("offset", vomitSpecialGetCreatedSpecialRequest.getOffset());
		
		List<VomitSpecialGetCreatedSpecialResponse> list=new ArrayList<VomitSpecialGetCreatedSpecialResponse>();
		try {
			list=baseDao.selectNativeSqlList(sql, params, VomitSpecialGetCreatedSpecialResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (list!=null && list.size()>0)?list:null;
	}
	
	
}
