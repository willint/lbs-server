package com.lbs.nettyserver;

import org.junit.Test;
import org.postgresql.geometric.PGpoint;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lbs.nettyserver.model.test.Cities;
import com.lbs.nettyserver.service.testservice.CitiesService;

public class CitiesServiceTest {
	
	@Test
	public void testAdd(){
		
		try {
//			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("config/application-context.xml");
//			CitiesService service = (CitiesService)ctx.getBean("citiesService");
//	        System.out.println(service.getClass());
//	        PGpoint location = new PGpoint(123,24);
//	        Cities cities = new Cities();
//	        cities.setName("LosAngle");
//	        cities.setAltitude(158);
//	        cities.setPopulation(new Float(1843.8));
////	        cities.setLocation(location);
//	        service.storeCities(cities);
//	        ctx.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
