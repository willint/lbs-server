package com.lbs.nettyserver.factory;

import java.util.ArrayList;
import java.util.List;

import com.lbs.nettyserver.session.Manager;

public abstract class AbstractManagerFacotry implements NormalFactory{
	

	
	protected final List<Manager> container = new ArrayList<Manager>();
	
	public abstract <T extends Manager> T createManager(Class<T> c);

	public List<Manager> getContainer() {
		return container;
	} 

	
}
