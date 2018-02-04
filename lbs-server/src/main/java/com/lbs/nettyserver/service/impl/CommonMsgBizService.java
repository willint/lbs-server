package com.lbs.nettyserver.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.lbs.nettyserver.model.common.FromInfo;
import com.lbs.nettyserver.model.pojo.LoginUser;

/**
 * 通用消息有误处理接口
 * Created by will on 18/1/5.
 */
public interface CommonMsgBizService {

    //消息入库
    public boolean storeMessage(JSONObject message , FromInfo fromInfo);

    //算出推送范围用户
    public List<LoginUser> calculateRangeComsumer( FromInfo fromInfo);


    //消息入队
    public boolean pushMessage(List<String> consumerList);


}
