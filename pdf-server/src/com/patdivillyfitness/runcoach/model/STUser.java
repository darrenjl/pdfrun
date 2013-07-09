package com.patdivillyfitness.runcoach.model;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class STUser {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String email;

	@Persistent
	private double startWeightInKilos;

	@Persistent
	private double targetWeightInKilos;

	@Persistent
	private double currentWeightInKilos;

	@Persistent
	private double startWaistCircumferenceInCm;

	@Persistent
	private double targetWaistCircumferenceInCm;

	@Persistent
	private double currentWaistCircumferenceInCm;

	@Persistent
	private int age;

	@Persistent
	private String sex;

	@Persistent
	private Set<Long> weighIns;

	@NotPersistent
	private List<STWeighIn> weighInList;

	public STUser(String name, String email, double startWeightInKilos,
			double targetWeightInKilos, double currentWeightInKilos, double startWaistCircumferenceInCm,
			double targetWaistCircumferenceInCm, double currentWaistCircumferenceInCm, int age,
			String sex) {
		super();
		this.name = name;
		this.email = email;
		this.startWeightInKilos = startWeightInKilos;
		this.targetWeightInKilos = targetWeightInKilos;
		this.currentWeightInKilos = currentWeightInKilos;
		this.startWaistCircumferenceInCm = startWaistCircumferenceInCm;
		this.targetWaistCircumferenceInCm = targetWaistCircumferenceInCm;
		this.currentWaistCircumferenceInCm = currentWaistCircumferenceInCm;
		this.age = age;
		this.sex = sex;
	}

	public STUser() {

	}

	public Long getId() {
		return id;
	}

	public STUser(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getStartWeightInKilos() {
		return startWeightInKilos;
	}

	public void setStartWeightInKilos(double startWeightInKilos) {
		this.startWeightInKilos = startWeightInKilos;
	}

	public double getTargetWeightInKilos() {
		return targetWeightInKilos;
	}

	public void setTargetWeightInKilos(double targetWeightInKilos) {
		this.targetWeightInKilos = targetWeightInKilos;
	}

	public double getCurrentWeightInKilos() {
		return currentWeightInKilos;
	}

	public void setCurrentWeightInKilos(double currentWeightInKilos) {
		this.currentWeightInKilos = currentWeightInKilos;
	}

	public double getStartWaistCircumferenceInCm() {
		return startWaistCircumferenceInCm;
	}

	public void setStartWaistCircumferenceInCm(
			double startWaistCircumferenceInCm) {
		this.startWaistCircumferenceInCm = startWaistCircumferenceInCm;
	}

	public double getTargetWaistCircumferenceInCm() {
		return targetWaistCircumferenceInCm;
	}

	public void setTargetWaistCircumferenceInCm(
			double targetWaistCircumferenceInCm) {
		this.targetWaistCircumferenceInCm = targetWaistCircumferenceInCm;
	}

	public double getCurrentWaistCircumferenceInCm() {
		return currentWaistCircumferenceInCm;
	}

	public void setCurrentWaistCircumferenceInCm(
			double currentWaistCircumferenceInCm) {
		this.currentWaistCircumferenceInCm = currentWaistCircumferenceInCm;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Long> getWeighIns() {
		return weighIns;
	}

	public void setWeighIns(Set<Long> weighIns) {
		this.weighIns = weighIns;
	}

	public List<STWeighIn> getWeighInList() {
		return weighInList;
	}

	public void setWeighInList(List<STWeighIn> weighInList) {
		this.weighInList = weighInList;
	}

}
