package com.lbs.nettyserver.utils.sysutils;

import java.math.BigDecimal;

import com.lbs.nettyserver.model.common.Line;

/**
 * 点到直线的距离计算工具类
 * d=|Ax0+By0+C/√A>2+B>2|
 * @author visual
 *
 */
public class PotinToLineDistanceUtil {
	
	/**
	 * 点到直线的距离计算,保留两位小数
	 * @param line
	 * @param x0经度
	 * @param y0纬度
	 * @return距离
	 */
	public static double getDistance(Line line,double x0,double y0){
		double powABsum=Math.pow(line.getA(), 2)+Math.pow(line.getB(), 2);
		double distance=Math.abs((line.getA()*x0+line.getB()*y0+line.getC())/Math.sqrt(powABsum));
		BigDecimal bd = new BigDecimal(distance);
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
