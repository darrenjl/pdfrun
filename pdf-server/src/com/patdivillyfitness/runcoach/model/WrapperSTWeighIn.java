package com.patdivillyfitness.runcoach.model;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class WrapperSTWeighIn {

	private String user;

	private Long group;

	private double weight;

	private double waistCircumference;

	public WrapperSTWeighIn(String user, Long group, double weight,
			double waistCircumference) {
		super();
		this.user = user;
		this.group = group;
		this.weight = weight;
		this.waistCircumference = waistCircumference;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
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

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

}
