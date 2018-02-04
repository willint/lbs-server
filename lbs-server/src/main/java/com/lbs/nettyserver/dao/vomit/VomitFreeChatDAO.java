package com.lbs.nettyserver.dao.vomit;

import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.model.common.LiveMessageZanCaiTotal;
import com.lbs.nettyserver.model.pojo.FreeChat;
import com.lbs.nettyserver.model.pojo.LiveMessage;
import com.lbs.nettyserver.model.request.live.*;
import com.lbs.nettyserver.model.response.live.*;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 现场消息表
 * @author visual
 *
 */
@Transactional
@Repository("vomitFreeChatDAO")
public class VomitFreeChatDAO {

	@Autowired 
    private BaseDao baseDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 保存现场发布的消息
	 * @param freeChat
	 */
	public boolean saveFreeChat(FreeChat freeChat){
		boolean success = true;
		Serializable a = this.sessionFactory.getCurrentSession().save(freeChat);
		this.sessionFactory.getCurrentSession().flush();

		return success;
	}
}
