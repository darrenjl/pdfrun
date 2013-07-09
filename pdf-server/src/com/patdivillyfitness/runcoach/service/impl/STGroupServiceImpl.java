package com.patdivillyfitness.runcoach.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patdivillyfitness.runcoach.jdo.PMF;
import com.patdivillyfitness.runcoach.model.STGroup;
import com.patdivillyfitness.runcoach.model.STUser;
import com.patdivillyfitness.runcoach.model.STWeighIn;
import com.patdivillyfitness.runcoach.model.WeighInComparator;
import com.patdivillyfitness.runcoach.service.EmailService;
import com.patdivillyfitness.runcoach.service.STGroupService;
import com.patdivillyfitness.runcoach.service.STUserService;

@Service
public class STGroupServiceImpl implements STGroupService {

	private static final Logger log = Logger.getLogger(STGroupServiceImpl.class
			.getName());

	@Autowired
	EmailService emailService;

	@Autowired
	STUserService stUserService;

	@Override
	public List<STGroup> getAllGroups() {
		List<STGroup> list = null;
		List<STUser> userList;
		List<STWeighIn> weighInList;
		List<List<STUser>> listUserLists = new ArrayList<List<STUser>>();
		List<STGroup> list_copy = new ArrayList<STGroup>();
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(STGroup.class);
		try {
			list = (List<STGroup>) q.execute();
			log.info("List size: " + list.size());
			for (STGroup group : list)
				list_copy.add(group);
			for (STGroup grp : list_copy) {
				userList = new ArrayList<STUser>();
				weighInList = new ArrayList<STWeighIn>();
				for (Long key : grp.getUsers()) {
					STUser user = pm.getObjectById(STUser.class, key);
					weighInList = new ArrayList<STWeighIn>();
					if (user.getWeighIns() != null
							&& user.getWeighIns().size() > 0) {
						for (Long id : user.getWeighIns()) {
							weighInList.add(pm.getObjectById(STWeighIn.class,
									id));
						}
						user.setWeighInList(weighInList);
					}
					userList.add(user);
				}
				listUserLists.add(userList);
			}
		} finally {
			q.closeAll();
			pm.close();
		}

		for (int i = 0; i < list.size(); i++) {
			list_copy.get(i).setUserList(listUserLists.get(i));
		}
		log.info("List 2 size: " + list_copy.size());
		return list_copy;
	}

	@Override
	public List<STGroup> getUserGroups(String email) {
		List<STGroup> list = null;
		List<STUser> userList;
		List<STWeighIn> weighInList;
		List<List<STUser>> listUserLists = new ArrayList<List<STUser>>();
		List<STGroup> list_copy = new ArrayList<STGroup>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		STUser currentUser = stUserService.getUserByEmail(email);
		Query q = pm.newQuery(STGroup.class);
		try {
			list = (List<STGroup>) q.execute();
			log.info("List size: " + list.size());
			for (STGroup group : list) {
				if (!group.getUniqueCode().equalsIgnoreCase("global")
						&& group.getUsers().contains(currentUser.getId()))
					list_copy.add(group);
			}
			for (STGroup grp : list_copy) {
				userList = new ArrayList<STUser>();
				weighInList = new ArrayList<STWeighIn>();
				for (Long key : grp.getUsers()) {
					STUser user = pm.getObjectById(STUser.class, key);
					weighInList = new ArrayList<STWeighIn>();
					if (user.getWeighIns() != null
							&& user.getWeighIns().size() > 0) {
						for (Long id : user.getWeighIns()) {
							weighInList.add(pm.getObjectById(STWeighIn.class,
									id));
						}
						Collections.sort(weighInList, new WeighInComparator());
						user.setWeighInList(weighInList);
					}
					userList.add(user);
				}
				listUserLists.add(userList);
			}
		} finally {
			q.closeAll();
			pm.close();
		}

		for (int i = 0; i < list_copy.size(); i++) {
			list_copy.get(i).setUserList(listUserLists.get(i));
		}
		log.info("List 2 size: " + list_copy.size());
		return list_copy;
	}

	@Override
	public STGroup getGroup(String code) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query groupQuery = pm.newQuery(STGroup.class);
		groupQuery.setFilter("uniqueCode == uniqueCodeParam");
		groupQuery.declareParameters("String uniqueCodeParam");
		List<STGroup> groupList = null;
		try {
			groupList = (List<STGroup>) groupQuery.execute(code);
			if (groupList.size() > 0) {
				return groupList.get(0);
			}
		} finally {
			groupQuery.closeAll();
			pm.close();
		}
		log.info("No group found with that code - " + code);
		return null;
	}

	@Override
	public String addGlobalGroup(STGroup group) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<STUser> list = null;
		List<STGroup> results = null;
		String uniqueCode;
		Set<Long> users = new HashSet<Long>();
		group.setStartDate(new Date());
		Query q = pm.newQuery(STUser.class);
		try {
			pm.makePersistent(group);
			list = (List<STUser>) q.execute();
			log.info("List size: " + list.size());
			for (STUser user : list)
				users.add(user.getId());
			group.setUsers(users);
		} finally {
			q.closeAll();
			pm.close();
		}
		return group.getUniqueCode();
	}

	public String createGroup(String email, STGroup group) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<STUser> list = null;
		List<STGroup> results = null;
		String uniqueCode;
		Query q = pm.newQuery(STGroup.class);
		q.setFilter("uniqueCode == uniqueCodeParam");
		q.declareParameters("String uniqueCodeParam");
		try {
			do {
				log.info("Generating unique group code");
				uniqueCode = RandomStringUtils.randomAlphanumeric(6);
				results = (List<STGroup>) q.execute(uniqueCode);
			} while (results.size() > 0);
			log.info("Unique code generated: " + uniqueCode);
			group.setUniqueCode(uniqueCode);
			Set<Long> users = new HashSet<Long>();
			group.setStartDate(new Date());
			group.setOwnerEmail(email);
			q = pm.newQuery(STUser.class);
			q.setFilter("email == emailParam");
			q.declareParameters("String emailParam");
			pm.makePersistent(group);
			list = (List<STUser>) q.execute(email);
			log.info("List size: " + list.size());
			STUser user = null;
			if (list.size() > 0) {
				user = list.get(0);
				users.add(user.getId());
			}
			group.setUsers(users);
			InternetAddress toAdd = null;
			if (user != null) {
				try {
					toAdd = new InternetAddress(user.getEmail(), user.getName());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String body = "Hi "
						+ user.getName()
						+ ",\n\nYou have successfully created a Social Transformation group with the information: \n\n"
						+ "Name: "+group.getName()+"\n\n"
						+ "Unique Code: "+uniqueCode+"\n\n"
						+ " \n\nPlease share this with any users you would like to join the group.\n\nYours Sincerely,\nThe Social Transformation Team";
				emailService.sendPlainMail(body, toAdd,
						"Social Transformation Group Created");
			}
		} finally {
			q.closeAll();
			pm.close();
		}
		return uniqueCode;
	}

	@Override
	public boolean addUserToGroup(STGroup group, STUser user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<STUser> list;
		Set<Long> users;
		return false;
	}

	public boolean joinGroup(String userEmail, String uniqueCode) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Set<Long> userSet;
		List<STGroup> groupList;
		List<STUser> userList;
		log.info("user " + userEmail + " joining group with code " + uniqueCode);
		Query groupQuery = pm.newQuery(STGroup.class);
		groupQuery.setFilter("uniqueCode == uniqueCodeParam");
		groupQuery.declareParameters("String uniqueCodeParam");
		Query userQuery = pm.newQuery(STUser.class);
		userQuery.setFilter("email == emailParam");
		userQuery.declareParameters("String emailParam");
		try {
			groupList = (List<STGroup>) groupQuery.execute(uniqueCode);
			if (groupList.size() > 0) {
				log.info("Group found with code: " + uniqueCode);
				userSet = groupList.get(0).getUsers();
				userList = (List<STUser>) userQuery.execute(userEmail);
				if (userList.size() > 0){
					userSet.add(userList.get(0).getId());
					groupList.get(0).setUsers(userSet);
					log.info("success");
					return true;
				}
			}
			log.info("could not add user to group");
			return false;
		} finally {
			userQuery.closeAll();
			groupQuery.closeAll();
			pm.close();
		}
	}
	
	public boolean deleteGroup(String userEmail, String uniqueCode) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<STGroup> groupList;
		log.info("user " + userEmail + " joining group with code " + uniqueCode);
		Query groupQuery = pm.newQuery(STGroup.class);
		groupQuery.setFilter("uniqueCode == uniqueCodeParam");
		groupQuery.declareParameters("String uniqueCodeParam");
		try {
			groupList = (List<STGroup>) groupQuery.execute(uniqueCode);
			if (groupList.size() > 0) {
				log.info("Group found with code: " + uniqueCode);
				if (groupList.get(0).getOwnerEmail().equalsIgnoreCase(userEmail)){
					pm.deletePersistent(groupList.get(0));
					log.info("group deleted");
					return true;
				}
				
			}
			log.info("could not add user to group");
			return false;
		} finally {
			groupQuery.closeAll();
			pm.close();
		}
	}

	@Override
	public boolean addUserToGlobalGroup(STUser user) {
		log.info("in addUserToGlobalGroup: User email - " + user.getEmail());
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<STUser> list;
		Set<Long> users;
		STGroup group = getGroup("global");
		if (null==group)
			return false;
		Query userQuery = pm.newQuery(STUser.class);
		userQuery.setFilter("email == emailParam");
		userQuery.declareParameters("String emailParam");
		if (group != null) {
			try {
				group = pm.getObjectById(STGroup.class, group.getId());
				list = (List<STUser>) userQuery.execute(user.getEmail());
				users = group.getUsers();
				if (list.size() == 1) {
					users.add(list.get(0).getId());
					log.info("adding user to global group");
				} else {
					log.info("User not found");
				}
				group.setUsers(users);
			} finally {
				pm.close();
			}
			return true;
		}
		return false;
	}

}