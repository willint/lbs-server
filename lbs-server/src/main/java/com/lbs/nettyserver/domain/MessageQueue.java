package com.lbs.nettyserver.domain;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 消息处理队列
 * 队列以map形式接收参数，待发送消息lbsMessage和推送的范围用户
 * @author will
 *
 */
public class MessageQueue {
	private Queue<Map> acceptQueue;
	private boolean running = false;


	public MessageQueue(ConcurrentLinkedQueue<Map> acceptQueue) {
		this.acceptQueue = acceptQueue;
	}

	public void clear() {
		this.acceptQueue.clear();
	}

	public int size() {
		return this.acceptQueue != null ? this.acceptQueue.size() : 0;
	}

	public boolean add(Map<String, Object> request) {
		return this.acceptQueue.add(request);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return this.running;
	}

	public Queue<Map> getAcceptQueue() {
		return acceptQueue;
	}

	public void setAcceptQueue(Queue<Map> acceptQueue) {
		this.acceptQueue = acceptQueue;
	}
	
}