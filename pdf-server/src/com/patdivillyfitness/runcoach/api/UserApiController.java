package com.patdivillyfitness.runcoach.api;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.patdivillyfitness.runcoach.model.STGroup;
import com.patdivillyfitness.runcoach.model.STUser;
import com.patdivillyfitness.runcoach.model.WrapperSTWeighIn;
import com.patdivillyfitness.runcoach.service.STGroupService;
import com.patdivillyfitness.runcoach.service.STUserService;


/**
 * Controller for SocialTransformation app api services.
 */
@Controller
@RequestMapping("/users")
public class UserApiController {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UserApiController.class.getName());

	@Autowired
	STUserService stUserService;

	@Autowired
	STGroupService stGroupService;
	/**
	 * @return Fetch all registered users in the database.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getAllUsers() {
		List<STUser> allUsers = stUserService.getAllUsers();
		for (STUser usr:allUsers)
			log.info("User: " + usr.getName());
		return new ModelAndView("userView", BindingResult.MODEL_KEY_PREFIX
				+ "users", allUsers);
	}
	

	/**
	 * @return Fetch a user by searching with the supplied email address.
	 * eg request url - http://socialtransform.appspot.com/api/users/get?&email=d.lyons88@gmail.com
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ModelAndView getUsers(@RequestParam("email") String email) {
		STUser user = stUserService.getUserByEmail(email);
		return new ModelAndView("userView", BindingResult.MODEL_KEY_PREFIX
				+ "user", user);
	}

	/**
	 * Add a new user to the database .
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser() {
		STUser user = new STUser("Darren Lyons", "d.lyons88@gmail.com", 107.7, 100.0, 107.7, 38, 34, 38, 24, "male");
		boolean success = stUserService.addUser(user);
		user = stUserService.getUserByEmail("d.lyons88@gmail.com");
		if (success)
			stGroupService.addUserToGlobalGroup(user);
		return new ModelAndView("userView",
				BindingResult.MODEL_KEY_PREFIX + "success",success
				);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public @ResponseBody
	ModelAndView postUserJson(@RequestBody STUser user) {
		boolean success = stUserService.addUser(user);
		log.info("User added: " + user.getName()); 	
		if (success)
			stGroupService.addUserToGlobalGroup(user);
		return new ModelAndView("userView",
				BindingResult.MODEL_KEY_PREFIX + "success",
				success);
	}
	
	@RequestMapping(value = "/weighin", method = RequestMethod.GET)
	public ModelAndView addWeighIn() {
		List<STUser> allGroups = stUserService.getAllUsers();
		stUserService.addNewWeighIn( allGroups.get(0).getEmail(), 100.0, 1);
		stUserService.addNewWeighIn( allGroups.get(0).getEmail(), 95.0, 2);
		stUserService.addNewWeighIn( allGroups.get(0).getEmail(), 90.0, 3);
		log.info("Weighin added"); 		
		return new ModelAndView("userView",
				BindingResult.MODEL_KEY_PREFIX + "success",
				true);
	}
	
	@RequestMapping(value = "/weighin", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public @ResponseBody
	ModelAndView postWeighInJson(@RequestBody WrapperSTWeighIn wrapper) {
		log.info("in postWeighInJson"); 		
		return new ModelAndView("userView",
				BindingResult.MODEL_KEY_PREFIX + "success",
				stUserService.addNewWeighIn(wrapper.getUser(), wrapper.getWeight(), wrapper.getWaistCircumference()));
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public @ResponseBody
	ModelAndView postEditUserJson(@RequestBody STUser user) {
		boolean success = stUserService.editUser(user);
		log.info("User edited: " + user.getName()); 	
		return new ModelAndView("userView",
				BindingResult.MODEL_KEY_PREFIX + "success",
				success);
	}
}
