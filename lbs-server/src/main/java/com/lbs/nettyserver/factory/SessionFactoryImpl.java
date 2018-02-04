package com.lbs.nettyserver.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.lbs.nettyserver.session.Session;

@Component("mysessionFactory")
public class SessionFactoryImpl extends AbstractSessionFacotry{
	
	private static final Log logger = LogFactory.getLog(SessionFactoryImpl.class);

	@Override
	public <T extends Session> T createSession(Class<T> c) {
		
		Session session = null;
		try {
			session =(Session)Class.forName(c.getName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return (T)session;
	}

}
