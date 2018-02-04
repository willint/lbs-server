package com.lbs.nettyserver.utils.sysutils;

import java.util.UUID;

/**
 * UUID工具类
 * @author visual
 *
 */
public class UUIDUtil {
	
	//获取32位UUID
	public static String getUUID(){
		String uuid  = UUID.randomUUID().toString().replace("-", "");
		return uuid;
		
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			System.out.println(getUUID());
		}
		
	}
}
