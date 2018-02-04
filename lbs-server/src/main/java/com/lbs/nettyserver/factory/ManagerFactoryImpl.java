package com.lbs.nettyserver.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.lbs.nettyserver.session.Manager;

@Component("managerFactory")
public class ManagerFactoryImpl extends AbstractManagerFacotry{
	
	
	
	private static final Log logger = LogFactory.getLog(ManagerFactoryImpl.class);
	
	@Override
	public <T extends Manager> T createManager(Class<T> c) {
		Manager manager = null;
		try {
			manager =(Manager)Class.forName(c.getName()).newInstance();
			this.container.add(manager);
		} catch (Exception e) {
			logger.error(e);
		}
		return (T)manager;
	}

}
