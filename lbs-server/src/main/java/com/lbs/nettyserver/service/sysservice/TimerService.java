package com.lbs.nettyserver.service.sysservice;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.nettyserver.dao.PowerValueDAO;
import com.lbs.nettyserver.dao.sys.HistoryDataMoveDAO;
import com.lbs.nettyserver.dao.sys.LiveMessageScoreCalculateDAO;
import com.lbs.nettyserver.dao.sys.SmartMessageScoreCalculateDAO;
import com.lbs.nettyserver.dao.sys.SystemCaseMethodDAO;
import com.lbs.nettyserver.dao.sys.UserLiveScoreCalculateDAO;
import com.lbs.nettyserver.factory.ManagerFactoryImpl;
import com.lbs.nettyserver.filter.ValidationFilter;
import com.lbs.nettyserver.session.Manager;
import com.lbs.nettyserver.session.Session;
import com.lbs.nettyserver.utils.sysutils.SpringContextUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;

public class TimerService {
	
	@Autowired
	private SystemCaseMethodDAO systemCaseMethodDAO;
	
	@Autowired
	private LiveMessageScoreCalculateDAO liveMessageScoreCalculateDAO;
	
	@Autowired
	private UserLiveScoreCalculateDAO userLiveScoreCalculateDAO;
	
	@Autowired
	private SmartMessageScoreCalculateDAO smartMessageScoreCalculateDAO;
	
	@Autowired
	private HistoryDataMoveDAO historyDataMoveDAO;
	
	@Autowired
	private PowerValueDAO powerValueDAO;
	
	
	private static final Log logger = LogFactory.getLog(TimerService.class);
	
	public void refreshSystemCaseMethodList(){
		try {
			logger.info("timer service start");
			ValidationFilter.methodList = systemCaseMethodDAO.getSystemCaseMethodList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 刷新sessionCode的状态
	 */
	public  void refreshSessionCode(){
		try {
			ManagerFactoryImpl factory =(ManagerFactoryImpl) SpringContextUtil.getBean("managerFactory");
			List<Manager> managers = factory.getContainer();
			for(Manager m : managers){
				for(Session s : m.getSessions()){
					s.isValid();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 现场消息质量得分计算
	 */
	public void liveMessageScoreCalculate(){
		try{
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":现场消息质量得分计算开始");
			
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":现场消息质量得分计算开始");
			boolean isSuccess=false;
			isSuccess=(boolean)liveMessageScoreCalculateDAO.calculateLiveMessageScoreByProc();
			if(isSuccess){
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":现场消息质量得分计算完成|成功");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":现场消息质量得分计算完成|成功");
			}else{
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":现场消息质量得分计算完成|失败");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":现场消息质量得分计算完成|失败");
			}
		}catch(Exception e){
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+"现场消息质量得分计算完成|错误："+e.getMessage());
			e.printStackTrace();
			logger.error(TimeUtil.getChinaLocalDateTimeNowStr()+"现场消息质量得分计算完成|错误："+e.getMessage());
		}
	}
	
	/**
	 * 用户现场综合得分计算
	 */
	public void userLiveScoreCalculate(){
		try{
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":用户现场综合得分计算开始");
			
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":用户现场综合得分计算开始");
			boolean isSuccess=false;
			isSuccess=(boolean)userLiveScoreCalculateDAO.calculateUserLiveScoreByProc();
			if(isSuccess){
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":用户现场综合得分计算完成|成功");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":用户现场综合得分计算完成|成功");
			}else{
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":用户现场综合得分计算完成|失败");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":用户现场综合得分计算完成|失败");
			}
		}catch(Exception e){
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+"用户现场综合得分计算完成|错误："+e.getMessage());
			e.printStackTrace();
			logger.error(TimeUtil.getChinaLocalDateTimeNowStr()+"用户现场综合得分计算完成|错误："+e.getMessage());
		}
	}
	
	
	/**
	 * 智囊消息得分计算
	 */
	public void smartMessageScoreCalculate(){
		try{
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":智囊消息得分计算开始");
			
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":智囊消息得分计算开始");
			boolean isSuccess=false;
			isSuccess=(boolean)smartMessageScoreCalculateDAO.calculateLiveSmartMessageScoreByProc();
			if(isSuccess){
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":智囊消息得分计算完成|成功");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":智囊消息得分计算完成|成功");
			}else{
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":智囊消息得分计算完成|失败");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":智囊消息得分计算完成|失败");
			}
		}catch(Exception e){
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+"智囊消息得分计算完成|错误："+e.getMessage());
			e.printStackTrace();
			logger.error(TimeUtil.getChinaLocalDateTimeNowStr()+"智囊消息得分计算完成|错误："+e.getMessage());
		}
	}
	
	/**
	 * 重置所有用户能量和体力值
	 */
	public void userPowerValueResetAllUser(){
		try{
			
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":重置所有用户能量和体力值开始");
			
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":重置所有用户能量和体力值开始");
			boolean isSuccess=false;
			isSuccess=(boolean)powerValueDAO.userPowerValueResetByProc();
			if(isSuccess){
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":重置所有用户能量和体力值完成|成功");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":重置所有用户能量和体力值完成|成功");
			}else{
				
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":重置所有用户能量和体力值完成|失败");
				
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":重置所有用户能量和体力值完成|失败");
			}
		}catch(Exception e){
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+"重置所有用户能量和体力值完成|错误："+e.getMessage());
			e.printStackTrace();
			logger.error(TimeUtil.getChinaLocalDateTimeNowStr()+"重置所有用户能量和体力值完成|错误："+e.getMessage());
		}
	}
	
	
	/**
	 * 历史数据搬移
	 */
	public void moveHistoryData(){
		try{
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":历史数据搬移开始");
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":历史数据搬移开始");
			
			//现场-历史数据搬移
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":现场-历史数据搬移开始");
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":现场-历史数据搬移开始");
			if((boolean)historyDataMoveDAO.moveLiveHistoryDataByProc()){
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":现场-历史数据搬移完成|成功");
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":现场-历史数据搬移完成|成功");
			}else{
				System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":现场-历史数据搬移完成|失败");
				logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":现场-历史数据搬移完成|失败");
			}
			
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+":历史数据搬移结束");
			logger.info(TimeUtil.getChinaLocalDateTimeNowStr()+":历史数据搬移结束");

		}catch(Exception e){
			System.out.println(TimeUtil.getChinaLocalDateTimeNowStr()+"历史数据搬移|错误："+e.getMessage());
			e.printStackTrace();
			logger.error(TimeUtil.getChinaLocalDateTimeNowStr()+"历史数据搬移|错误："+e.getMessage());
		}
	}
	
	public void initTimer(){
		refreshSystemCaseMethodList();
	}
}
