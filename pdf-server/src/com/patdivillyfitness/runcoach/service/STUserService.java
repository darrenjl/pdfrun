package com.patdivillyfitness.runcoach.service;

import java.util.List;

import com.patdivillyfitness.runcoach.model.STUser;

public interface STUserService {
	public List<STUser> getAllUsers();
	public STUser getUserByEmail(String email);
	public boolean addUser(STUser user);
	public boolean editUser(STUser tempUser);
	public boolean addNewWeighIn(String user, double weight, double waistCircumference);
}
