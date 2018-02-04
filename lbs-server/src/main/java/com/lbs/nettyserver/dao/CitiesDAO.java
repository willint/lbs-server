package com.lbs.nettyserver.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.model.test.Cities;

@Transactional
@Repository("citiesDAO")
public class CitiesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void storeCities(Cities e){
		this.sessionFactory.getCurrentSession().saveOrUpdate(e);
		this.sessionFactory.getCurrentSession().flush();
	}

}
