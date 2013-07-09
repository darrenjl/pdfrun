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

import com.google.appengine.api.datastore.Key;
import com.patdivillyfitness.runcoach.model.STGroup;
import com.patdivillyfitness.runcoach.model.STUser;
import com.patdivillyfitness.runcoach.model.WrapperSTWeighIn;
import com.patdivillyfitness.runcoach.service.STGroupService;
import com.patdivillyfitness.runcoach.service.STUserService;


/**
 * Controller for SocialTransformation app api services.
 */
@Controller
@RequestMapping("/groups")
public class GroupApiController {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GroupApiController.class.getName());

	@Autowired
	STGroupService stGroupService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getAllGroups() {
		List<STGroup> allGroups = stGroupService.getAllGroups();
		for (STGroup grp:allGroups)
			log.info("User: " + grp.getName());
		return new ModelAndView("groupView", BindingResult.MODEL_KEY_PREFIX
				+ "groups", allGroups);
	}
	
	@RequestMapping(value = "/getUserGroups", method = RequestMethod.GET)
	public ModelAndView getUserGroups(@RequestParam("email") String email) {
		List<STGroup> allGroups = stGroupService.getUserGroups(email);
		for (STGroup grp:allGroups)
			log.info("User: " + grp.getName());
		return new ModelAndView("groupView", BindingResult.MODEL_KEY_PREFIX
				+ "groups", allGroups);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addGroup() {
		STGroup group = new STGroup();
		group.setName("Global Group");
		group.setUniqueCode("global");
		return new ModelAndView("groupView",
				BindingResult.MODEL_KEY_PREFIX + "code",
				stGroupService.addGlobalGroup(group));
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public @ResponseBody
	ModelAndView createGroupJson(@RequestBody STGroup group, @RequestParam("email") String email) {
		log.info("Group created: " + group.getName() + " by user: " + email); 
		String result = stGroupService.createGroup(email, group);
		return new ModelAndView("groupView",
				BindingResult.MODEL_KEY_PREFIX + "code", result
				);
	}
	
	@RequestMapping(value = "/joinGroup", method = RequestMethod.GET)
	public ModelAndView joinGroup(@RequestParam("email") String email, @RequestParam("uniqueCode") String uniqueCode) {
		return new ModelAndView("groupView", BindingResult.MODEL_KEY_PREFIX
				+ "success", stGroupService.joinGroup(email, uniqueCode));
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteGroup(@RequestParam("email") String email, @RequestParam("uniqueCode") String uniqueCode) {
		return new ModelAndView("groupView", BindingResult.MODEL_KEY_PREFIX
				+ "success", stGroupService.deleteGroup(email, uniqueCode));
	}
}
