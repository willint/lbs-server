package com.lbs.nettyserver.model.sys;

/**
 * 接口方法编码常量
 * @author visual
 *
 */
public class MethodCodeConstants {
	
	/**基础系统接口：00001~09999  ------------------------------
	 * 
	
	 */
	/**
	 * 测试方法
	 */
    public static final String TESTINTERFACE = "00001"; // 测试方法
	/**
	 * 获取系统参数
	 */
	public static final String SYSTEMPARAM = "00002"; // 获取系统参数
	/**
	 * 获取吐槽技能信息
	 */
	public static final String VOMITFUNCSET = "00003"; // 获取吐槽技能信息
	
	
	
	/**
	 * 全局业务接口：10001~19999   -------------------------------
	 */
	/**
	 * 用户注册
	 */
	public static final String REGISTER = "10001";	// 注册接口
	/**
	 * 用户登录
	 */
	public static final String LOGINER = "10002";	// 登录接口
	
	/**
	 * 提交完整登录状态信息
	 */
	public static final String LOGININFO_UPDATE = "10003";	// 登录信息更新接口
	
	/**
	 * 获取验证码接口
	 */
	public static final String getVerificationCode="10004";//获取验证码接口
	
	/**
	 * 密码修改接口
	 */
	public static final String CHANGEPASSWORD = "10005"; // 密码修改接口
	
	/**
	 *检测验证码是否正确接口
	 */
	public static final String checkVerificationCodeIsRight = "10006"; // 检测验证码是否正确接口
	
	/**
	 *用户退出登录接口
	 */
	public static final String outLogin = "10007"; // 用户退出登录接口
	
	/**
	 *验证用户密码是否正确接口
	 */
	public static final String checkUserPassword = "10008"; //验证用户密码是否正确接口
	
	/**
	 * 关注
	 */
	public static final String attentionUser= "10009";//关注
	
	
	/**
	 * 取消关注
	 */
	public static final String cancelAttentionUser= "10010";//取消关注
	
	/**
	 * 检查是否已经关注
	
	 */
	public static final String checkIsAttention= "10011";//检查是否已经关注
	
	
	/**
	 * 获取用户的能量和体力值
	
	 */
	public static final String getOneUserPower= "10012";//获取用户的能量和体力值
	
	/**
	 * 发现模块接口：20001~29999  --------------------------------
	 */
	
	/**
	 * 获取发现-吐槽排名接口
	 */
	public static final String getFindVomitRank = "20001";//获取发现-吐槽排名
	/**
	 * 获取发现-现场排名
	 */
	public static final String getFindLiveRank = "20002";//获取发现-现场排名
	/**
	 * 获取发现-附近吐槽热点专题
	 */
	public static final String getFindNearVomitHotSpecial= "20003";//获取发现-附近吐槽热点专题
	
	/**
	 * 获取发现-我关注的人的现场消息
	 */
	public static final String getFindAttentionLiveMessage= "20004";//获取发现-我关注的人的现场消息
	
	/**
	 * 吐槽模块接口：30001~39999  -------------------------------------
	 */
	
	/**
	 * 创建吐槽专题
	 */
	public static final String createVomitSpecial= "30001";//创建吐槽专题
	/**
	 * 加入吐槽专题
	 */
	public static final String joinVomitSpecial= "30002";//加入吐槽专题
	
	/**
	 * 获取未参与的吐槽专题
	 */
	public static final String getFindVomitSpecial= "30003";//获取未参与的吐槽专题
	/**
	 *获取已加入的吐槽专题
	 */
	public static final String getJoinedVomitSpecial= "30004";//获取已加入的吐槽专题
	
	/**
	 *获取我创建的吐槽专题
	 */
	public static final String getCreatedVomitSpecial= "30005";//获取我创建的吐槽专题
	
	
	/**
	 * 现场模块接口：40001~49999  ----------------------------------
	 */
	/**
	 * 发布现场消息
	 */
	public static final String publishLiveMessage= "40001";//发布现场消息
	/**
	 * 评论现场消息
	 */
	public static final String publishLiveMessageComment= "40002";//评论现场消息
	/**
	 * 获取现场今天消息
	 */
	public static final String getTodayLiveMessage= "40003";//获取现场今天消息
	/**
	 * 获取现场昨天消息
	 */
	public static final String getYesterdayLiveMessage= "40004";//获取现场昨天消息
	/**
	 * 现场点赞
	 */
	public static final String giveZanLiveMessage= "40005";//现场点赞
	/**
	 * 现场点踩
	 */
	public static final String giveCaiLiveMessage= "40006";//现场点踩
	
	/**
	 * 现场-获取某一套消息的评论
	 */
	public static final String getOneMessageComment= "40007";//获取某一条消息的评论
	
	/**
	 * 获取某一条消息的评论总数和消息质量得分
	 */
	public static final String getOneMessageCommentTotalAndScore= "40008";//获取某一条消息的评论总数和消息质量得分
	
	/*40009-10未用*/
	
	/**
	 * 现场-获取定点消息
	 */
	public static final String getFixedPointLiveMessage= "40011";//现场-获取定点消息
	
	/**
	 * 现场-获取当前用户排名
	 */
	public static final String getOneUserLiveRank= "40012";//现场-获取当前用户排名
	
	/**
	 * 现场-获取当前用户获得的赞踩总数
	 */
	public static final String getOneUserLiveZanAndCaiTotal= "40013";//现场-获取当前用户获得的赞踩总数
	
	/**
	 * 现场-获取当前用户今天获得的赞踩总数
	 */
	public static final String getOneUserLiveZanAndCaiTodayTotal= "40014";//现场-获取当前用户今天获得的赞踩总数
	
	/**
	 * 现场-获取轨迹消息
	 */
	public static final String getPathLiveMessage= "40015";//现场-获取轨迹消息

	/**
	 *我的模块接口：50001~59999  ----------------------------------
	 */
	
	/**
	 * 我的-用户修改头像接口
	 */
	public static final String changeHeaderImg= "50001";//我的-用户修改头像接口
	
	/**
	 * 我的-用户修改背景图片接口
	 */
	public static final String changeBackImg= "50002";//我的-用户修改背景图片接口
	
	/**
	 * 我的-获取用户基本信息
	 */
	public static final String myGetUserBaseInfo= "50003";//我的-获取用户基本信息
	
	/**
	 * 我的-获取用户今天创建的专题总数接口
	 */
	public static final String myGetCreateSpecialTodayTotal= "50004";//我的-获取用户今天创建的专题总数接口
	
	/**
	 * 我的-获取用户今天加入的专题总数接口
	 */
	public static final String myGetJoinSpecialTodayTotal= "50005";//我的-获取用户今天加入的专题总数接口
	
	/**
	 * 我的-获取用户今天发布的现场消息接口
	 */
	public static final String myGetLiveMessageTodayTotal= "50006";//我的-获取用户今天发布的现场消息接口
	
	/**
	 * 我的-获取用户今天得到的赞的接口
	 */
	public static final String myGetGetZanTodayTotal= "50007";//我的-获取用户今天得到的赞的接口
	
	/**
	 * 我的-获取用户今天点出的赞的接口
	 */
	public static final String myGetPutZanTodayTotal= "50008";//我的-获取用户今天点出的赞的接口
	
	/**
	 * 我的-获取用户今天得到的踩的接口
	 */
	public static final String myGetGetCaiTodayTotal= "50009";//我的-获取用户今天得到的踩的接口
	
	/**
	 * 我的-获取用户今天点出的踩的接口
	 */
	public static final String myGetPutCaiTodayTotal= "50010";//我的-获取用户今天点出的踩的接口
	
	/**
	 * 我的-获取用户今天发表评论的总数接口
	 */
	public static final String myGetPutCommentTodayTotal= "50011";//我的-获取用户今天发表评论的总数接口
	
	/**
	 * 我的-获取用户今天得到的评论的总数接口
	 */
	public static final String myGetGetCommentTodayTotal= "50012";//我的-获取用户今天得到的评论的总数接口
	
	/**
	 * 我的-获取用户粉丝总数
	 */
	public static final String myGetFansTotal= "50013";//我的-获取用户粉丝总数
	
	/**
	 * 我的-获取用户关注的人数（我关注的人数）的接口
	 */
	public static final String myGetAttentionTotal= "50014";//我的-获取用户关注的人数（我关注的人数）的接口
	
	/**
	 * 我的-用户修改手机号接口
	 */
	public static final String myChangePhone= "50015";//我的-用户修改手机号接口
	
	/**
	 * 我的-用户修改昵称接口
	 */
	public static final String myChangeNickName= "50016";//我的-用户修改昵称接口
	
	/**
	 * 我的-获取用户今天发布的智囊消息总数
	 */
	public static final String myGetSmartMessageTodayTotal= "50017";//我的-获取用户今天发布的智囊消息总数
	
	/**
	 * 我的-获取用户今天发布的现场消息列表
	 */
	public static final String myGetLiveMessageTodayList= "50018";//我的-获取用户今天发布的现场消息列表
	

	/**
	 * 我的-获取用户昨天发布的现场消息列表
	 */
	public static final String myGetLiveMessageYesterdayList= "50019";//我的-获取用户昨天发布的现场消息列表
	
	/**
	 * 我的-获取用户现场得到的赞的列表
	 */
	public static final String myGetLiveGetZanList= "50020";//我的-获取用户现场得到的赞的列表
	
	/**
	 * 我的-获取用户现场点出的赞的列表
	 */
	public static final String myGetLivePutZanList= "50021";//我的-获取用户现场点出的赞的列表
	
	/**
	 * 我的-获取用户现场得到的踩的列表
	 */
	public static final String myGetLiveGetCaiList= "50022";//我的-获取用户现场得到的踩的列表
	
	/**
	 * 我的-获取用户现场点出的踩的列表
	 */
	public static final String myGetLivePutCaiList= "50023";//我的-获取用户现场点出的踩的列表
	
	/**
	 * 我的-获取用户现场得到的评论的列表
	 */
	public static final String myGetLiveGetCommentList= "50024";//我的-获取用户现场得到的评论的列表
	
	/**
	 * 我的-获取用户现场发表的评论的列表
	 */
	public static final String myGetLivePutCommentList= "50025";//我的-获取用户现场发表的评论的列表
	
	/**
	 * 我的-获取我的粉丝列表
	 */
	public static final String myGetFansList= "50026";//我的-获取我的粉丝列表
	
	/**
	 * 我的-获取我关注的人员列表
	 */
	public static final String myGetAttentionPersonsList= "50027";//我的-获取我关注的人员列表
	
	/**
	 * 我的-删除现场某一条消息
	 */
	public static final String myDeleteOneLiveMessage= "50028";//我的-删除现场某一条消息
	
	/**
	 * 智囊业务接口：60001~69999   -------------------------------
	 */
	/**
	 * 智囊-获取智囊消息列表
	 */
	public static final String smartGetSmartMessageList= "60001";//智囊-获取智囊消息列表
	/**
	 * 智囊-获取某一条消息得到的评论总数
	 */
	public static final String smartGetOneSmartMessageCommentTotal= "60002";//智囊-获取某一条消息得到的评论总数
	
	/**
	 * 智囊-获取某一条智囊消息评论列表
	 */
	public static final String smartGetOneMessageCommentList= "60003";//智囊-获取某一条智囊消息评论列表
	
	/**
	 * 智囊-点同意
	 */
	public static final String smartGiveAgree= "60004";//智囊-点同意
	
	/**
	 * 智囊-点反对
	 */
	public static final String smartGiveAgainst= "60005";//智囊-点反对
	
	/**
	 * 智囊-发表评论
	 */
	public static final String smartPublishComment= "60006";//智囊-发表评论
	
}
