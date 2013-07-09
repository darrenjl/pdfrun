package com.patdivillyfitness.runcoach.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patdivillyfitness.runcoach.api.UserApiController;
import com.patdivillyfitness.runcoach.jdo.PMF;
import com.patdivillyfitness.runcoach.model.STGroup;
import com.patdivillyfitness.runcoach.model.STUser;
import com.patdivillyfitness.runcoach.model.STWeighIn;
import com.patdivillyfitness.runcoach.model.WeighInComparator;
import com.patdivillyfitness.runcoach.service.EmailService;
import com.patdivillyfitness.runcoach.service.STUserService;

@Service
public class STUserServiceImpl implements STUserService {

	private static final Logger log = Logger.getLogger(STUserServiceImpl.class
			.getName());

	@Autowired
	EmailService emailService;

	@Override
	public List<STUser> getAllUsers() {
		List<STUser> list = null;
		List<STUser> list_copy = new ArrayList<STUser>();
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(STUser.class);
		try {
			list = (List<STUser>) q.execute();
			log.info("List size: " + list.size());
			for (STUser usr : list)
				list_copy.add(usr);
		} finally {
			q.closeAll();
			pm.close();
		}
		log.info("List 2 size: " + list_copy.size());
		return list_copy;
	}

	public STUser getUserByEmail(String email) {
		log.info("in getUserByEmail: " + email);
		List<STUser> list;
		List<STWeighIn> weighInList;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(STUser.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		try {
			list = (List<STUser>) q.execute(email);
			// change to >=for debugging but whould be just ==1 or else there is
			// duplicate users
			if (list.size() >= 1) {
				STUser user = list.get(0);
				log.info("user found: " + list.get(0).getName());
				weighInList = new ArrayList<STWeighIn>();
				if (user.getWeighIns() != null) {
					if (user.getWeighIns().size() > 0) {
						for (Long key : user.getWeighIns()) {
							weighInList.add(pm.getObjectById(STWeighIn.class,
									key));
						}
						Collections.sort(weighInList, new WeighInComparator());
						user.setWeighInList(weighInList);
					}
				}
				return list.get(0);
			} else if (list.size() < 1) {
				log.info("No users found with this email");
				return null;
			} else {
				log.info("More than one user found for this email");
				return null;
			}
		} finally {
			q.closeAll();
			pm.close();
		}
	}

	@Override
	public boolean addUser(STUser user) {
		if (getUserByEmail(user.getEmail()) != null) {
			log.info("cannot add the user as one already exists with that email address");
			return false;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		user.setCurrentWeightInKilos(user.getStartWeightInKilos());
		user.setCurrentWaistCircumferenceInCm(user
				.getStartWaistCircumferenceInCm());
		try {
			pm.makePersistent(user);
			pm.flush();
		} finally {
			pm.close();
		}
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
					+ ",\n\nYou have successfully registered for Social Transformation. "
					+ " \n\nWe hope our service provides the motivation you need to improve your health and fitness metrics.\n\nYours Sincerely,\nThe Social Transformation Team";
			emailService.sendPlainMail(body, toAdd,
					"Social Transformation Registration Success");
		}
		addNewWeighIn(user.getEmail(), user.getStartWeightInKilos(), user.getStartWaistCircumferenceInCm());
		return true;
	}

	public boolean editUser(STUser tempUser) {
		List<STUser> list;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(STUser.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		try {
			list = (List<STUser>) q.execute(tempUser.getEmail());
			// change to >=for debugging but whould be just ==1 or else there is
			// duplicate users
			if (list.size() == 1) {
				STUser user = list.get(0);
				log.info("user found: " + list.get(0).getName());
				if (tempUser.getName() != null)
					user.setName(tempUser.getName());
				if (tempUser.getAge() != 0)
					user.setAge(tempUser.getAge());
				if (tempUser.getSex() != null)
					user.setSex(tempUser.getSex());
				if (tempUser.getStartWaistCircumferenceInCm() != 0)
					user.setStartWaistCircumferenceInCm(tempUser
							.getStartWaistCircumferenceInCm());
				if (tempUser.getStartWeightInKilos() != 0)
					user.setStartWeightInKilos(tempUser.getStartWeightInKilos());
				if (tempUser.getTargetWaistCircumferenceInCm() != 0)
					user.setTargetWaistCircumferenceInCm(tempUser
							.getTargetWaistCircumferenceInCm());
				if (tempUser.getTargetWeightInKilos() != 0)
					user.setTargetWeightInKilos(tempUser
							.getTargetWeightInKilos());
			} else if (list.size() < 1) {
				log.info("No users found with this email");
				return false;
			} else {
				log.info("More than one user found for this email");
				return false;
			}
		} finally {
			q.closeAll();
			pm.close();
		}
		return true;
	}

	@Override
	public boolean addNewWeighIn(String userEmail, double weight,
			double waistCircumference) {
		log.info("inAddNewWeighIn");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(STUser.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		try {
			List<STUser> list = (List<STUser>) q.execute(userEmail);
			if (list.size() != 1)
				return false;
			STWeighIn weighIn = new STWeighIn(list.get(0).getId(), weight,
					waistCircumference);
			weighIn = pm.makePersistent(weighIn);
			Set<Long> keys = list.get(0).getWeighIns();
			if (keys == null) {
				keys = new HashSet<Long>();
			}
			keys.add(weighIn.getId());
			list.get(0).setWeighIns(keys);
			list.get(0).setCurrentWeightInKilos(weight);
			list.get(0).setCurrentWaistCircumferenceInCm(waistCircumference);
			checkWeightAgainstTarget(list.get(0), weight);
			checkWaistAgainstTarget(list.get(0), waistCircumference);
			log.info("user for key: " + list.get(0).getName());
		} finally {
			pm.close();
		}
		return true;
	}

	private void checkWeightAgainstTarget(STUser user, double weight) {
		if (weight <= user.getTargetWeightInKilos()) {
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
						+ ",\n\nYou have successfully met your target weight, congratulations. "
						+ " \n\nYou can now focus on meeting your other targets but remember you can set a new, more ambitious target through our application\n\nYours Sincerely,\nThe Social Transformation Team";
				emailService.sendPlainMail(body, toAdd,
						"Congratulations from Social Transformation");
			}
		}
	}

	private void checkWaistAgainstTarget(STUser user, double waist) {
		if (waist <= user.getTargetWaistCircumferenceInCm()) {
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
						+ ",\n\nYou have successfully met your target waist size, congratulations. "
						+ " \n\nYou can now focus on meeting your other targets but remember you can set a new, more ambitious target through our application\n\nYours Sincerely,\nThe Social Transformation Team";
				emailService.sendPlainMail(body, toAdd,
						"Congratulations from Social Transformation");
			}
		}
	}
}