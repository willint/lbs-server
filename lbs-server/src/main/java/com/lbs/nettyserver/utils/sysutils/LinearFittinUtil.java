package com.lbs.nettyserver.utils.sysutils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.lbs.nettyserver.model.common.Line;

/**
 * 最小二乘法拟合y = a x + b 直线方程
 * b = sum( y ) / n - a * sum( x ) / n 
 * a = ( n * sum( xy ) - sum( x ) * sum( y ) ) / ( n * sum( x^2 ) - sum(x) ^ 2 ) 
 * @author visual
 *
 */
public class LinearFittinUtil {

	 
//public static void main(String[] args) {
//
//		/**
//		 * 纬度
//		 */
//		double[] x = { 116.434959};//,116.434952, 116.434891, 116.434891, 116.434814, 116.43483, 116.435005 };
//
//		/**
//		 * 经度
//		 */
//		double[] y = { 39.90905};//, 39.908985, 39.908867, 39.908867, 39.908653, 39.908428, 39.908081 };
//
//		estimate(x, y);
//	}
//
//	/**
//	 * 拟合-入口主方法
//	 * 
//	 * @param x,y
//	 */
//	public static void estimate(double[] x, double[] y) {
//		double a = getA(x, y);
//		double b = getB(x, y, a);
//		// 设置doubl字符串输出格式，不以科学计数法输出
//		DecimalFormat df = new DecimalFormat("#,##0.000000");// 格式化设置
//	System.out.println("y=" + a + "*x" + "+" + b);
//	}


	/**
	 * 计算y = a x + b，x的系数a
	 * @param x经度
	 * @param y纬度
	 * @return a保留6位小数
	 */
	public static double getA(double[] x, double[] y) {
		int n = x.length;
		if(n<=1){
			return 0;
		}
		double a = (n * pSum(x, y) - sum(x) * sum(y)) / (n * sqSum(x) - Math.pow(sum(x), 2));
		BigDecimal bd = new BigDecimal(a);
		return bd.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 *  计算y = a x + b，常量系数b
	 * @param x经度
	 * @param y纬度
	 * @param a
	 * @return b保留6位小数
	 */
	public static double getB(double[] x, double[] y, double a) {
		int n = x.length;
		double b = sum(y) / n - a * sum(x) / n;
		BigDecimal bd = new BigDecimal(b);
		return bd.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 将y = a x + b直线方程系数转为为Ax+By+C=0直线方程的系数
	 * @param a
	 * @param b
	 * @return
	 */
	public static Line lineParamTransform(double a,double b){
		Line line=new Line(a*-1,1,b*-1);
		return line;
	}

	// 计算和值
	private static double sum(double[] ds) {
		double s = 0;
		for (double d : ds)
			s = s + d;
		return s;
	}

	// 计算开平方和值
	private static double sqSum(double[] ds) {
		double s = 0;
		for (double d : ds)
			s = s + Math.pow(d, 2);
		return s;
	}

	// 计算x和y积的和值
	private static double pSum(double[] x, double[] y) {
		double s = 0;
		for (int i = 0; i < x.length; i++)
			s = s + x[i] * y[i];
		return s;
	}
}
