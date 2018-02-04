package com.lbs.nettyserver.handler;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbs.nettyserver.dispatcher.HandlerDispatcher;
import com.lbs.nettyserver.filter.ValidationFilter;
import com.lbs.nettyserver.model.common.ReqHead;
import com.lbs.nettyserver.model.common.RespHead;
import com.lbs.nettyserver.utils.sysutils.Md5Util;
import com.lbs.nettyserver.utils.sysutils.RSAUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

@Sharable
@Component
public class ChannelCommunicateHandler  extends SimpleChannelInboundHandler<Object>{
	
	@Autowired
	private HandlerDispatcher handlerDispatcher;
	
	@Autowired
	private CommonBizHandler commonBizHandler;
	
	
	private static final Log logger = LogFactory.getLog(ChannelCommunicateHandler.class);

	
	
//	public ChannelCommunicateHandler(HandlerDispatcher handler){
//		this.handlerDispatcher = handler;
//		commonStoreService = new CommonStoreService();
//	}
	
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object data)
			throws Exception {
		
		if ((data instanceof ByteBuf)){
//			System.out.println("socket data");
			logger.info("socket data");
//			socketRequest(ctx, data);
		}
		 if ((data instanceof FullHttpRequest)){
			 httpFullRequest(ctx, data);
		 }else{
			 
		 }
	}
	
	/**
	 * 校驗用戶連接有效性
	 * 1.連接必須帶userID
	 * 2.同一個userID可以多次登錄，即可以連接多個channel
	 * 
	 * 
	 */
	private String checkChannelValidation(QueryStringDecoder querydecoder){
		String result ="";
		int userID = 0;
		Map<String, List<String>> uriAttributes = querydecoder.parameters(); 
		for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) { 
			for (String attrVal : attr.getValue()) { 
				if("userID".equals(attr.getKey())&&(!"0".equals(attrVal))){
					userID = Integer.parseInt(attrVal);
					break;
				}
			}
			if(userID!=0){
				break;
			}
		} 
		if(userID == 0){
			result ="parameter userID is null or its value is 0 ";
		}
		return result;
	}
	private void httpFullRequest(ChannelHandlerContext ctx, Object msg) throws Exception {
		FullHttpRequest req = (FullHttpRequest) msg;
		DecoderResult decoderResult =  req.decoderResult();
		// 选用方法处理
		RespHead resphead = new RespHead();
		JSONObject returnJson = new JSONObject();
		JSONObject body = new JSONObject();
		try {
			if (decoderResult.isSuccess()) {
				//存入链接信息
				if(this.handlerDispatcher !=null ){
					if(req.headers().get("Content-Type").contains("application/json")&& req.method().equals(HttpMethod.POST)){
						String content =  req.content().toString(CharsetUtil.UTF_8);
						JSONObject requestJson = JSONObject.fromObject(content);
						ReqHead head  = (ReqHead)JSONObject.toBean(requestJson.getJSONObject("head"), ReqHead.class);
						if(null != head){
							boolean validateFlag = false;
							if(ValidationFilter.checkReqireParams(head)){
								//封装头
								resphead.setAttach(head.getAttach());
								resphead.setResTime(TimeUtil.getChinaLocalDateTimeNowStr());
								resphead.setTransactionId(head.getTransactionId());
								resphead.setSign(RSAUtil.serverGenerateSign(head.getTransactionId()));
//								resphead.setSign(Md5Util.getEncryptedPwd(head.getTransactionId()));
								
								//校验sign
								validateFlag = ValidationFilter.checkSign(head);
								if(validateFlag){
									//校验sessionCode
									validateFlag = ValidationFilter.checkSessionCode(head);
									if(validateFlag){
										//接口业务处理
										body = commonBizHandler.handleMethod(head, requestJson.getJSONObject("body"));
										//业务处理的后续工作
										ValidationFilter.tailHandle(head, body, resphead);
									}else{
										body.put("flag", "11");
										body.put("code", "0002");
										body.put("msg", "session失效");
									}
								}else{
									
									body.put("flag", "11");
									body.put("code", "0001");
									body.put("msg", "签名错误");
								}
								
							}else{
								// 请求头验证失败
								body.put("flag", "11");
								body.put("code", "0003");
								body.put("msg", "请求头验证失败");
								resphead.setAttach("request head error");
							}
							
						}
					}else{
						// 请求格式不正确
						body.put("flag", "11");
						body.put("code", "0004");
						body.put("msg", "请求格式不正确");
					}
				}
			}else{
				//请求解析失败
				body.put("flag", "11");
				body.put("code", "0005");
				body.put("msg", "请求解析失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			body.put("flag", "99");
			body.put("code", "9999");
			body.put("msg", "服务器内部错误");
		}finally{
			try {
				returnJson.put("head", JSONObject.fromObject(resphead));
				returnJson.put("body", body);
				// 响应给客户端
				ByteBuf content = Unpooled.copiedBuffer(returnJson.toString(), CharsetUtil.UTF_8);
				FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
				resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
				HttpUtil.setContentLength(resp, content.readableBytes());
				ctx.channel().writeAndFlush(resp);
			} catch (Exception e2) {
				logger.equals(e2.getMessage());
				e2.printStackTrace();
			}
			
		}
	}


	public HandlerDispatcher getHandlerDispatcher() {
		return handlerDispatcher;
	}

	public void setHandlerDispatcher(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	public CommonBizHandler getCommonBizHandler() {
		return commonBizHandler;
	}

	public void setCommonBizHandler(CommonBizHandler commonBizHandler) {
		this.commonBizHandler = commonBizHandler;
	}
	
	
}
