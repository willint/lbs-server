package com.lbs.nettyserver.model.common;

/**
 * 直线方程Ax+By+C=0
 * @author visual
 *
 */
public class Line {
	
	private double A;
	
	private double B;
	
	private double C;
	
	/**
	 * Ax+By+C=0系数构造函数
	 * @param a
	 * @param b
	 * @param c
	 */
	public Line(double a,double b,double c){
		this.A=a;
		this.B=b;
		this.C=c;
	}
	
	public Line(){
		
	}

	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

	public double getB() {
		return B;
	}

	public void setB(double b) {
		B = b;
	}

	public double getC() {
		return C;
	}

	public void setC(double c) {
		C = c;
	}	
}
