package com.patdivillyfitness.runcoach.api;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;

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
import com.patdivillyfitness.runcoach.service.EmailService;
import com.patdivillyfitness.runcoach.service.STGroupService;
import com.patdivillyfitness.runcoach.service.STUserService;


/**
 * Controller for SocialTransformation app api services.
 */
@Controller
@RequestMapping("/email")
public class EmailApiController {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(EmailApiController.class.getName());

	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView testEmailService() {
		InternetAddress toAdd=null;
		try {
			toAdd = new InternetAddress("d.lyons88@gmail.com", "Darren Lyons");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("emailView", BindingResult.MODEL_KEY_PREFIX
				+ "success", emailService.sendPlainMail("Testing", toAdd, "Testing"));
	}
	
}
