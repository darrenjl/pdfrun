package com.patdivillyfitness.runcoach.model;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class STWeighIn {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long user;

	@Persistent
	private double weight;

	@Persistent
	private double waistCircumference;

	@Persistent
	private Date date;

	public STWeighIn(Long user, double weight, double waistCircumference) {
		super();
		this.user = user;
		this.weight = weight;
		this.waistCircumference = waistCircumference;
		this.date = new Date();
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWaistCircumference() {
		return waistCircumference;
	}

	public void setWaistCircumference(double waistCircumference) {
		this.waistCircumference = waistCircumference;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

}
