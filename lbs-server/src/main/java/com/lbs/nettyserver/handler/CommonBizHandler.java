package com.lbs.nettyserver.handler;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbs.nettyserver.model.common.ReqHead;

import com.lbs.nettyserver.model.sys.MethodCodeConstants;
import com.lbs.nettyserver.service.functionsevice.common.AttentionPersonService;
import com.lbs.nettyserver.service.functionsevice.common.CancelAttentionPersonService;
import com.lbs.nettyserver.service.functionsevice.common.ChangePasswordService;
import com.lbs.nettyserver.service.functionsevice.common.CheckIsAttentionService;
import com.lbs.nettyserver.service.functionsevice.common.CheckUserPwsIsRightService;
import com.lbs.nettyserver.service.functionsevice.common.CheckVerificationCodeIsRightService;
import com.lbs.nettyserver.service.functionsevice.common.GetOneUserPowerValueService;
import com.lbs.nettyserver.service.functionsevice.common.OutLoginService;
import com.lbs.nettyserver.service.functionsevice.common.TLoginUserService;
import com.lbs.nettyserver.service.functionsevice.common.UserLoginService;
import com.lbs.nettyserver.service.functionsevice.common.UserRegistService;
import com.lbs.nettyserver.service.functionsevice.common.VerificationCodeService;
import com.lbs.nettyserver.service.functionsevice.find.FindAttentionLiveMessageService;
import com.lbs.nettyserver.service.functionsevice.find.FindLiveRankService;
import com.lbs.nettyserver.service.functionsevice.find.FindNearVomitHotSpecialService;
import com.lbs.nettyserver.service.functionsevice.find.FindVomitRankService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGetFixedPointMessageService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGetOneMessageCommentService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGetOneMessageCommentTotalAndMessageScoreService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGetPathMessageService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGetTodayMessageService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGetYesterdayMessageService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGiveCaiService;
import com.lbs.nettyserver.service.functionsevice.live.LiveGiveZanService;
import com.lbs.nettyserver.service.functionsevice.live.LiveOneUserZanAndCaiTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.live.LiveOneUserZanAndCaiTotalService;
import com.lbs.nettyserver.service.functionsevice.live.LivePublishMessageCommentService;
import com.lbs.nettyserver.service.functionsevice.live.LivePublishMessageService;
import com.lbs.nettyserver.service.functionsevice.live.LiveRankService;
import com.lbs.nettyserver.service.functionsevice.my.MyChangeBackImgService;
import com.lbs.nettyserver.service.functionsevice.my.MyChangeHeaderImgService;
import com.lbs.nettyserver.service.functionsevice.my.MyChangeNickNameService;
import com.lbs.nettyserver.service.functionsevice.my.MyChangePhoneService;
import com.lbs.nettyserver.service.functionsevice.my.MyDeleteOneLiveMessageService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetAttentionPersonsListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetAttentionTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetCreateSpecialTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetFansListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetFansTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetGetCaiListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetGetCaiTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetGetCommentListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetGetCommentTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetGetZanListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetGetZanTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetJoinSpecialTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetLiveMessageTodayListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetLiveMessageTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetLiveMessageYesterdayListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetPutCaiListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetPutCaiTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetPutCommentListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetPutCommentTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetPutZanListService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetPutZanTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetSmartMessageTodayTotalService;
import com.lbs.nettyserver.service.functionsevice.my.MyGetUserBaseInfoService;
import com.lbs.nettyserver.service.functionsevice.smart.SmartGetOneMessageCommentListService;
import com.lbs.nettyserver.service.functionsevice.smart.SmartGetOneMessageCommentTotalService;
import com.lbs.nettyserver.service.functionsevice.smart.SmartGetSmartMessageListService;
import com.lbs.nettyserver.service.functionsevice.smart.SmartGiveAgainstService;
import com.lbs.nettyserver.service.functionsevice.smart.SmartGiveAgreeService;
import com.lbs.nettyserver.service.functionsevice.smart.SmartPublishMessageCommentService;
import com.lbs.nettyserver.service.functionsevice.vomit.VomitSpecialCreateSpecialService;
import com.lbs.nettyserver.service.functionsevice.vomit.VomitSpecialGetCreatedSpecialService;
import com.lbs.nettyserver.service.functionsevice.vomit.VomitSpecialGetFindSpecialService;
import com.lbs.nettyserver.service.functionsevice.vomit.VomitSpecialGetJoinedSpecialService;
import com.lbs.nettyserver.service.functionsevice.vomit.VomitSpecialJoinSpecialService;
import com.lbs.nettyserver.service.sysservice.GetFuncSetListService;
import com.lbs.nettyserver.service.sysservice.SystemParamService;
import com.lbs.nettyserver.service.testservice.CitiesService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

/**
 * 公共业务类处理
 * @author will
 *
 */
@Component
public class CommonBizHandler {
	
	
	@Autowired
	private CitiesService citiesService;
	
	@Autowired
	private UserRegistService userRegistService;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private GetFuncSetListService getFuncSetListService;
	
	@Autowired
	private TLoginUserService tLoginUserService;
	
	@Autowired
	private SystemParamService systemParamService;
	
	@Autowired
	private OutLoginService outLoginService;
	
	@Autowired
	private CheckUserPwsIsRightService checkUserPwsIsRightService;
	
	@Autowired
	private AttentionPersonService attentionPersonService;
	
	@Autowired
	private CancelAttentionPersonService cancelAttentionPersonService;
	
	@Autowired
	private CheckIsAttentionService checkIsAttentionService;
	
	@Autowired
	private GetOneUserPowerValueService getOneUserPowerValueService;
	
	@Autowired
	private FindVomitRankService findVomitRankService;
	
	@Autowired
	private FindLiveRankService findLiveRankService;
	
	@Autowired
	private FindAttentionLiveMessageService findAttentionLiveMessageService;
	
	@Autowired
	private FindNearVomitHotSpecialService findNearVomitHotSpecialService;
	
	@Autowired
	private VomitSpecialCreateSpecialService vomitSpecialCreateSpecialService;
	
	@Autowired
	private VomitSpecialJoinSpecialService vomitSpecialJoinSpecialService;
	
	@Autowired
	private VomitSpecialGetFindSpecialService vomitSpecialGetFindSpecialService;
	
	@Autowired
	private VomitSpecialGetJoinedSpecialService vomitSpecialGetJoinedSpecialService;
	
	@Autowired
	private VomitSpecialGetCreatedSpecialService vomitSpecialGetCreatedSpecialService;
	
	@Autowired
	private LivePublishMessageService livePublishMessageService;
	
	@Autowired
	private LivePublishMessageCommentService livePublishMessageCommentService;
	
	@Autowired
	private LiveGetTodayMessageService liveGetTodayMessageService;
	
	@Autowired
	private LiveGetYesterdayMessageService liveGetYesterdayMessageService;
	
	@Autowired
	private LiveGiveCaiService liveGiveCaiService;
	
	@Autowired
	private LiveGiveZanService liveGiveZanService;
	
	@Autowired
	private LiveGetOneMessageCommentService liveGetOneMessageCommentService;
	
	@Autowired
	private LiveGetOneMessageCommentTotalAndMessageScoreService liveGetOneMessageCommentTotalAndMessageScoreService;
	
	
	@Autowired
	private LiveGetFixedPointMessageService liveGetFixedPointMessageService;
	
	@Autowired
	private LiveGetPathMessageService liveGetPathMessageService;
	
	
	@Autowired
	private LiveOneUserZanAndCaiTotalService liveOneUserZanAndCaiTotalService;
	
	@Autowired
	private LiveOneUserZanAndCaiTodayTotalService liveOneUserZanAndCaiTodayTotalService;

	@Autowired
	private LiveRankService liveRankService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@Autowired
	private CheckVerificationCodeIsRightService checkVerificationCodeIsRightService;
	
	@Autowired
	private MyChangeHeaderImgService changeHeaderImgService;
	
	@Autowired
	private MyChangeBackImgService changeBackImgService;
	
	@Autowired
	private MyGetUserBaseInfoService myGetUserBaseInfoService;
	
	@Autowired
	private MyGetCreateSpecialTodayTotalService myGetCreateSpecialTodayTotalService;
	
	@Autowired
	private MyGetJoinSpecialTodayTotalService myGetJoinSpecialTodayTotalService;
	
	@Autowired
	private MyGetLiveMessageTodayTotalService myGetLiveMessageTodayTotalService;
	
	@Autowired
	private MyGetGetZanTodayTotalService myGetGetZanTodayTotalService;
	
	@Autowired
	private MyGetPutZanTodayTotalService myGetPutZanTodayTotalService;
	
	@Autowired
	private MyGetGetCaiTodayTotalService myGetGetCaiTodayTotalService;
	
	@Autowired
	private MyGetPutCaiTodayTotalService myGetPutCaiTodayTotalService;
	
	@Autowired
	private MyGetPutCommentTodayTotalService myGetPutCommentTodayTotalService;
	
	@Autowired
	private MyGetGetCommentTodayTotalService myGetGetCommentTodayTotalService;
	
	@Autowired
	private MyGetFansTotalService myGetFansTotalService;
	
	@Autowired
	private MyGetAttentionTotalService myGetAttentionTotalService;
	
	@Autowired
	private MyChangePhoneService myChangePhoneService;
	
	@Autowired
	private MyChangeNickNameService myChangeNickNameService;
	
	@Autowired
	private MyGetSmartMessageTodayTotalService myGetSmartMessageTodayTotalService;
	
	@Autowired
	private MyGetLiveMessageTodayListService myGetLiveMessageTodayListService;
	
	@Autowired
	private MyGetLiveMessageYesterdayListService myGetLiveMessageYesterdayListService;
	
	@Autowired
	private MyGetGetZanListService myGetGetZanListService;
	
	@Autowired
	private MyGetPutZanListService myGetPutZanListService;
	
	@Autowired
	private MyGetGetCaiListService myGetGetCaiListService;
	
	@Autowired
	private MyGetPutCaiListService myGetPutCaiListService;
	
	@Autowired
	private MyGetGetCommentListService myGetGetCommentListService;
		
	@Autowired
	private MyGetPutCommentListService myGetPutCommentListService;
	
	@Autowired
	private MyGetFansListService myGetFansListService;
	
	@Autowired
	private MyGetAttentionPersonsListService myGetAttentionPersonsListService;
	
	@Autowired
	private MyDeleteOneLiveMessageService myDeleteOneLiveMessageService;
	
	@Autowired
	private SmartGetSmartMessageListService smartGetSmartMessageListService;
	
	@Autowired
	private SmartGetOneMessageCommentTotalService smartGetOneMessageCommentTotalService;
	
	@Autowired
	private SmartGetOneMessageCommentListService smartGetOneMessageCommentListService;
	
	@Autowired
	private SmartGiveAgreeService smartGiveAgreeService;
	
	@Autowired
	private SmartGiveAgainstService smartGiveAgainstService;
	
	@Autowired
	private SmartPublishMessageCommentService smartPublishMessageCommentService;
	
	private static final Log logger = LogFactory.getLog(ChannelCommunicateHandler.class);
	
	public JSONObject handleMethod(ReqHead head, JSONObject data){
		
		JSONObject body = null;
		try {
			switch (head.getMethod()) {
			case MethodCodeConstants.TESTINTERFACE:
				body = citiesService.handleBiz(data);
				break;
			case MethodCodeConstants.SYSTEMPARAM:
				body = systemParamService.handleBiz(data);
				break;
			case MethodCodeConstants.VOMITFUNCSET:
				body = getFuncSetListService.handleBiz(data);
				break;
			case MethodCodeConstants.CHANGEPASSWORD:
				body = changePasswordService.handleBiz(data);
				break;
			case MethodCodeConstants.getVerificationCode:
				body = verificationCodeService.handleBiz(data);
				break;
			case MethodCodeConstants.checkVerificationCodeIsRight:
				body = checkVerificationCodeIsRightService.handleBiz(data);
				break;
			case MethodCodeConstants.REGISTER:
				body = userRegistService.handleBiz(data);
				break;
			case MethodCodeConstants.LOGINER:
				body = userLoginService.handleBiz(data);
				break;
			case MethodCodeConstants.LOGININFO_UPDATE:
				body = tLoginUserService.handleBiz(data);
				break;
			case MethodCodeConstants.outLogin:
				body = outLoginService.handleBiz(data);
				break;
			case MethodCodeConstants.checkUserPassword:
				body = checkUserPwsIsRightService.handleBiz(data);
				break;
			case MethodCodeConstants.attentionUser:
				body = attentionPersonService.handleBiz(data);
				break;
			case MethodCodeConstants.cancelAttentionUser:
				body = cancelAttentionPersonService.handleBiz(data);
				break;
			case MethodCodeConstants.checkIsAttention:
				body = checkIsAttentionService.handleBiz(data);
				break;
			case MethodCodeConstants.getOneUserPower:
				body = getOneUserPowerValueService.handleBiz(data);
				break;
			case MethodCodeConstants.getFindVomitRank:
				body = findVomitRankService.handleBiz(data);
				break;
			case MethodCodeConstants.getFindLiveRank:
				body = findLiveRankService.handleBiz(data);
				break;
			case MethodCodeConstants.getFindAttentionLiveMessage:
				body = findAttentionLiveMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.getFindNearVomitHotSpecial:
				body = findNearVomitHotSpecialService.handleBiz(data);
				break;
			case MethodCodeConstants.createVomitSpecial:
				body = vomitSpecialCreateSpecialService.handleBiz(data);
				break;
			case MethodCodeConstants.joinVomitSpecial:
				body = vomitSpecialJoinSpecialService.handleBiz(data);
				break;
			case MethodCodeConstants.getFindVomitSpecial:
				body = vomitSpecialGetFindSpecialService.handleBiz(data);
				break;
			case MethodCodeConstants.getJoinedVomitSpecial:
				body = vomitSpecialGetJoinedSpecialService.handleBiz(data);
				break;
			case MethodCodeConstants.getCreatedVomitSpecial:
				body = vomitSpecialGetCreatedSpecialService.handleBiz(data);
				break;
			case MethodCodeConstants.publishLiveMessage:
				body = livePublishMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.publishLiveMessageComment:
				body = livePublishMessageCommentService.handleBiz(data);
				break;
			case MethodCodeConstants.getTodayLiveMessage:
				body = liveGetTodayMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.getYesterdayLiveMessage:
				body = liveGetYesterdayMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.giveZanLiveMessage:
				body = liveGiveZanService.handleBiz(data);
				break;
			case MethodCodeConstants.giveCaiLiveMessage:
				body = liveGiveCaiService.handleBiz(data);
				break;
			case MethodCodeConstants.getOneMessageComment:
				body = liveGetOneMessageCommentService.handleBiz(data);
				break;
			case MethodCodeConstants.getOneMessageCommentTotalAndScore:
				body = liveGetOneMessageCommentTotalAndMessageScoreService.handleBiz(data);
				break;
			case MethodCodeConstants.getFixedPointLiveMessage:
				body = liveGetFixedPointMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.getPathLiveMessage:
				body = liveGetPathMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.getOneUserLiveRank:
				body = liveRankService.handleBiz(data);
				break;
			case MethodCodeConstants.getOneUserLiveZanAndCaiTotal:
				body = liveOneUserZanAndCaiTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.getOneUserLiveZanAndCaiTodayTotal:
				body = liveOneUserZanAndCaiTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.changeHeaderImg:
				body = changeHeaderImgService.handleBiz(data);
				break;
			case MethodCodeConstants.changeBackImg:
				body = changeBackImgService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetUserBaseInfo:
				body = myGetUserBaseInfoService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetCreateSpecialTodayTotal:
				body = myGetCreateSpecialTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetJoinSpecialTodayTotal:
				body = myGetJoinSpecialTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLiveMessageTodayTotal:
				body = myGetLiveMessageTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetGetZanTodayTotal:
				body = myGetGetZanTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetPutZanTodayTotal:
				body = myGetPutZanTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetGetCaiTodayTotal:
				body = myGetGetCaiTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetPutCaiTodayTotal:
				body = myGetPutCaiTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetPutCommentTodayTotal:
				body = myGetPutCommentTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetGetCommentTodayTotal:
				body = myGetGetCommentTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetFansTotal:
				body = myGetFansTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetAttentionTotal:
				body = myGetAttentionTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myChangePhone:
				body = myChangePhoneService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetSmartMessageTodayTotal:
				body = myGetSmartMessageTodayTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.myChangeNickName:
				body = myChangeNickNameService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLiveMessageTodayList:
				body = myGetLiveMessageTodayListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLiveMessageYesterdayList:
				body = myGetLiveMessageYesterdayListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLiveGetZanList:
				body = myGetGetZanListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLivePutZanList:
				body = myGetPutZanListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLiveGetCaiList:
				body = myGetGetCaiListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLivePutCaiList:
				body = myGetPutCaiListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLiveGetCommentList:
				body = myGetGetCommentListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetLivePutCommentList:
				body = myGetPutCommentListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetFansList:
				body = myGetFansListService.handleBiz(data);
				break;
			case MethodCodeConstants.myGetAttentionPersonsList:
				body = myGetAttentionPersonsListService.handleBiz(data);
				break;
			case MethodCodeConstants.myDeleteOneLiveMessage:
				body = myDeleteOneLiveMessageService.handleBiz(data);
				break;
			case MethodCodeConstants.smartGetSmartMessageList:
				body = smartGetSmartMessageListService.handleBiz(data);
				break;
			case MethodCodeConstants.smartGetOneSmartMessageCommentTotal:
				body = smartGetOneMessageCommentTotalService.handleBiz(data);
				break;
			case MethodCodeConstants.smartGetOneMessageCommentList:
				body = smartGetOneMessageCommentListService.handleBiz(data);
				break;
			case MethodCodeConstants.smartGiveAgree:
				body = smartGiveAgreeService.handleBiz(data);
				break;
			case MethodCodeConstants.smartGiveAgainst:
				body = smartGiveAgainstService.handleBiz(data);
				break;
			case MethodCodeConstants.smartPublishComment:
				body = smartPublishMessageCommentService.handleBiz(data);
				break;
			default:
				break;
			}						
		} catch (Exception e) {
			logger.error("handleMethod error :"+e.getMessage());
			e.printStackTrace();
			ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
			body=responseBodyResultUtil.getSys_error_default_result();
		}
		
		return body;
	}
	
	

}
