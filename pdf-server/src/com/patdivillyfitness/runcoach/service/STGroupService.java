package com.patdivillyfitness.runcoach.service;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.patdivillyfitness.runcoach.model.STGroup;
import com.patdivillyfitness.runcoach.model.STUser;

public interface STGroupService {
	public List<STGroup> getAllGroups();
	public List<STGroup> getUserGroups(String email);
	public STGroup getGroup(String name);
	public String addGlobalGroup(STGroup group);
	public String createGroup(String email, STGroup group);
	public boolean joinGroup(String userEmail, String uniqueCode);
	public boolean deleteGroup(String userEmail, String uniqueCode);
	public boolean addUserToGroup(STGroup group, STUser user);
	public boolean addUserToGlobalGroup(STUser user);
}
