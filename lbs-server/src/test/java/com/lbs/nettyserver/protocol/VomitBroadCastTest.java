package com.lbs.nettyserver.protocol;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lbs.nettyserver.dao.LoginUserDAO;
import com.lbs.nettyserver.model.pojo.LoginUser;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/application-context.xml"})
public class VomitBroadCastTest {
	@Autowired
    private LoginUserDAO loginUserDAO;
	
	
	@Test
	public void getBroadcastUser(){
		
		
	}

}
