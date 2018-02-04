package com.lbs.nettyserver.service.functionsevice.vomit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.LoginUserDAO;
import com.lbs.nettyserver.dao.vomit.VomitFreeChatDAO;
import com.lbs.nettyserver.model.common.FromInfo;
import com.lbs.nettyserver.model.pojo.FreeChat;
import com.lbs.nettyserver.model.pojo.LoginUser;
import com.lbs.nettyserver.service.impl.CommonMsgBizService;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

/**
 *
 *
 * Created by will on 18/1/5.
 */
@Service("vomitMessagePushService")
public class VomitMessagePushService implements CommonMsgBizService {


    @Autowired
    private VomitFreeChatDAO vomitFreeChatDAO;
    
    @Autowired
    private LoginUserDAO loginUserDAO;

    @Override
    public boolean storeMessage(JSONObject message , FromInfo fromInfo) {

        boolean rstFlag = false;
        //判断消息类型存储
//        FromInfo fromInfo = JsonPluginsUtil.jsonToBean(message.get("fromInfo").toString(),FromInfo.class);

        //封装存储实体
        FreeChat freeChat = new FreeChat();
        freeChat.setFcId(UUIDUtil.getUUID());
        freeChat.setLoginId(fromInfo.getLoginId());
        freeChat.setContent(message.toString());
        freeChat.setSend(true);//默认发送
        freeChat.setJd(fromInfo.getJd());
        freeChat.setWd(fromInfo.getWd());
        freeChat.setSendTime(new Date());

        rstFlag = vomitFreeChatDAO.saveFreeChat(freeChat);
        return rstFlag;
    }

    @Override
    public  List<LoginUser>  calculateRangeComsumer( FromInfo fromInfo) {
    	BigDecimal jd = fromInfo.getJd();
    	BigDecimal wd = fromInfo.getWd();
    	
    	List<LoginUser> loginList = loginUserDAO.getBroadcastUser(jd, wd);
    	
        return loginList;
    }

    @Override
    public boolean pushMessage(List<String> consumerList) {
    	
    	
        return true;
    }
}
