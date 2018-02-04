package com.lbs.nettyserver.utils.sysutils;

/**
 * 计算两个地球坐标点的距离
 * @author visual
 *
 */
public class DistanceCalculationUtil {
	
	private static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m)

	 private static double rad(double d)  
	    {  
	       return d * Math.PI / 180.0;  
	    }  
	      
	    /** 
	     * 基于余弦定理求两经纬度距离 
	     * @param jd1 第一点的经度
	     * @param wd2 第一点的纬度 
	     * @param jd2 第二点的经度
	     * @param wd2 第二点的纬度 
	     * @return 返回的距离，单位m 
	     * */  
	    public static double LantitudeLongitudeDist(double jd1, double wd1,double jd2, double wd2) {  
	        double radLat1 = rad(wd1);  
	        double radLat2 = rad(wd2);  
	  
	        double radLon1 = rad(jd1);  
	        double radLon2 = rad(jd2);  
	  
	        if (radLat1 < 0)  
	            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south  
	        if (radLat1 > 0)  
	            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north  
	        if (radLon1 < 0)  
	            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west  
	        if (radLat2 < 0)  
	            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south  
	        if (radLat2 > 0)  
	            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north  
	        if (radLon2 < 0)  
	            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west  
	        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);  
	        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);  
	        double z1 = EARTH_RADIUS * Math.cos(radLat1);  
	  
	        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);  
	        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);  
	        double z2 = EARTH_RADIUS * Math.cos(radLat2);  
	  
	        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));  
	        //余弦定理求夹角  
	        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));  
	        double dist = theta * EARTH_RADIUS;  
	        return Math.floor(dist);  
	    }
	    
	    public static void main(String[] args)
	    	    throws Exception
	    	  {
	    	int a=10;
	    	int b=8;
	    	int c=10;
	    	System.out.println(a+b);
    		      System.out.println(LantitudeLongitudeDist(116.481903,39.98373,116.481659,39.983494));
    		      System.out.println((0.8*a+0.05*b+0.15*c));
	    	  
	    	  }
}
