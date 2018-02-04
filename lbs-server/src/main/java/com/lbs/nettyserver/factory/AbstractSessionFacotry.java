package com.lbs.nettyserver.factory;

import com.lbs.nettyserver.session.Session;

public abstract class AbstractSessionFacotry implements NormalFactory {
	
	public abstract <T extends Session> T createSession(Class<T> c); 

}
