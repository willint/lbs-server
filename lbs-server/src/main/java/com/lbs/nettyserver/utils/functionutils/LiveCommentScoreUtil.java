package com.lbs.nettyserver.utils.functionutils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 评论综合得分计算类
 * @author visual
 *
 */
public class LiveCommentScoreUtil {

	/**
	 * 得到评论综合得分
	 * @param realScore
	 * @param clearScore
	 * @param useScore
	 * @return
	 */
	public static BigDecimal getCommentScore(BigDecimal realScore,BigDecimal clearScore,BigDecimal useScore){
		
		Double rs=realScore.doubleValue();
		Double cs=clearScore.doubleValue();
		Double ts=useScore.doubleValue();
		
		Double score=rs*0.8+cs*0.05+ts*0.15;
		
		DecimalFormat df = new DecimalFormat("#.0");  
		
		return BigDecimal.valueOf(Double.parseDouble(df.format(score)));
	}
	/**
	 * 得到评论综合得分对应的等级描述，好，中，差等
	 * @param commentScore
	 * @return
	 */
	public static String getCommentScoreView(BigDecimal commentScore){
		Double cs=commentScore.doubleValue();
		String view="";
		if(cs<=2){
			view="很差";
		}else if(cs<=4){
			view="差";
		}else if(cs<=6){
			view="一般";
		}else if(cs<=8){
			view="好";
		}else{
			view="很好";
		}
		return view;
	}
}
