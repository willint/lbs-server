package com.lbs.nettyserver.factory;

public abstract class AbstractBeanFacotry {
	
	public abstract <T extends Class<T>> T createBean(Class<T> c); 

}
