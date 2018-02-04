package com.lbs.nettyserver.model.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpoint;
@Entity
@Table(name="cities")
public class Cities {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "population")
	private Float population;
	
	@Column(name = "altitude")
	private Integer altitude;
	
//	@Column(name = "location",columnDefinition="point")
//	private PGpoint location;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPopulation() {
		return population;
	}

	public void setPopulation(Float population) {
		this.population = population;
	}

	public Integer getAltitude() {
		return altitude;
	}

	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}

//	public PGpoint getLocation() {
//		return location;
//	}
//
//	public void setLocation(PGpoint location) {
//		this.location = location;
//	}


	
}
