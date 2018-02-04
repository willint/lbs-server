package com.lbs.nettyserver.utils.functionutils;

import java.util.Random;

/**
 * 昵称生成工具类
 * @author visual
 *
 */
public class NickNameUtil {
	
    private final static int delta = 0x9fa5 - 0x4e00 + 1;
	/**
	 * 随机生成昵称
	 * @return
	 * @throws Exception
	 */
	public static String generateNickName(){
		/*String numbers = "1234567890";
		String smallChars="abcdefghijklmnopqrstuvwxyz";
		String bigChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ";*/
				
		String hanzi = "";
		Random ran = new Random();
        for (int i = 0; i < ran.nextInt(4)+2; i++) {
            char c = (char)(0x4e00 + ran.nextInt(delta));
            hanzi += String.valueOf(c);
        }
        return hanzi;//+smallChars.charAt((int)(Math.random() * 26))+numbers.charAt((int)(Math.random() * 10))+bigChars.charAt((int)(Math.random() * 26));
	}
	
}
