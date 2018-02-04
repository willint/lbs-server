package com.lbs.nettyserver.dispatcher;


import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lbs.nettyserver.domain.MessageQueue;
import com.lbs.nettyserver.protocol.LbsMessage;
import com.lbs.nettyserver.protocol.LbsMessageConst;

/**
 * 消息处理分发器
 * @author will
 *
 */
public class HandlerDispatcher implements Runnable {
	
	private static final Log logger = LogFactory.getLog(HandlerDispatcher.class);
	private MessageQueue messagequeue;
	private Executor handlerExecutor;
	private  ConcurrentMap<String,Object> userLinkedMap ;//用户通过私有协议链接信息

	private boolean running;
	private long sleepTime;
	
	/**
	 * 构造方法初始化
	 */
	public HandlerDispatcher(){
		this.running = true;
		this.sleepTime = 200L;
		this.messagequeue = new MessageQueue(new ConcurrentLinkedQueue<Map>());
		this.userLinkedMap = new ConcurrentHashMap<String ,Object>();
	}
	
	public void addMessage(Map m){
		try {
			if (this.messagequeue == null) {
				this.messagequeue = new MessageQueue(new ConcurrentLinkedQueue<Map>());
				this.messagequeue.add(m);
			} else {
				this.messagequeue.add(m);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	
	/**
	 * 启动轮询
	 */
	public void run() {
		while (this.running) {
			try {
				//
				if(messagequeue!=null && messagequeue.size()>0){
					Map map = messagequeue.getAcceptQueue().poll();
					PublishWorkers publisher = new PublishWorkers(this.userLinkedMap,map);
					this.handlerExecutor.execute(publisher);
				}
				
			} catch (Exception e) {
				logger.error("error:"+e.getMessage());
			}
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void stop() {
		this.running = false;
	}
	
	private final class PublishWorkers implements Runnable{
			
		private Map map;
		private final ConcurrentMap<String,Object>  userContextMap;
		
		private  PublishWorkers(ConcurrentMap<String,Object>  ctxMap,Map map){
			this.map = map;
			this.userContextMap =ctxMap;
		}
		
		public void run() {
			try {
				logger.info("PublishWorkers run ,start publish lbsmessage to users ");
				// 包含协议数据和待推送用户列表
				if(null != map && map.containsKey(LbsMessageConst.MSG_DISPATCH_KEY.DISPATCH_LIST)
						&& map.containsKey(LbsMessageConst.MSG_DISPATCH_KEY.DISPATCH_MSG)){
					LbsMessage lbsmsg = (LbsMessage)map.get(LbsMessageConst.MSG_DISPATCH_KEY.DISPATCH_MSG);
					//封装消息协议
					lbsmsg.setType(LbsMessageConst.API_TYPE.BIZ_RESPONSE);//设置为业务响应标识
					lbsmsg.setVerCode(LbsMessageConst.VER_CODE);
					lbsmsg.setPriority(LbsMessageConst.DEFAULT_PRIORITY);
					
					List<String> userList = (List)map.get(LbsMessageConst.MSG_DISPATCH_KEY.DISPATCH_LIST);
					for(String user : userList){
						ChannelHandlerContext ctx = (ChannelHandlerContext)userContextMap.get(user);
						if(null != ctx ){
							ctx.writeAndFlush(lbsmsg);
						}
					}
				}
				logger.info("PublishWorkers run end ... ");
			} catch (Exception e) {
				logger.error("PublishWorkers error:"+e.getMessage());
			} finally {
				
			}
		}
	}

	
	public Executor getHandlerExecutor() {
		return handlerExecutor;
	}
	
	public void setHandlerExecutor(Executor handlerExecutor) {
		this.handlerExecutor = handlerExecutor;
	}
	
	
	public boolean isRunning() {
		return running;
	}
	
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	public long getSleepTime() {
		return sleepTime;
	}
	
	
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public ConcurrentMap<String, Object> getUserLinkedMap() {
		return userLinkedMap;
	}

	public void setUserLinkedMap(ConcurrentMap<String, Object> userLinkedMap) {
		this.userLinkedMap = userLinkedMap;
	}
}
