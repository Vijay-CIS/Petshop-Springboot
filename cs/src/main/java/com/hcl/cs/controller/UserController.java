package com.hcl.cs.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.cs.dto.UserDTO;
import com.hcl.cs.model.Pet;
import com.hcl.cs.model.User;
import com.hcl.cs.service.PetService;
import com.hcl.cs.service.UserService;
import com.hcl.cs.validator.UserValidator;

@Controller
public class UserController {
	

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new UserDTO());

		return "register";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") UserDTO userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		User user = new User();
		user.setUserName(userForm.getUserName());
		user.setUserPassword(userForm.getUserPassword());

		userService.save(user);
		return "redirect:/home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("userForm", new User());
		return "login";
	}

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(Model model, String error, String logout) {
//    	
//        if (error != null) 
//            model.addAttribute("error", "Your username and password is invalid.");
//   
//        if (logout != null) 
//            model.addAttribute("message", "You have been logged out successfully.");
//
//        return "login";
//       
//}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("userForm") User userForm, HttpSession session, Model model) {
		try {
			User user = userService.findByUsernameAndPassword(userForm.getUserName(), userForm.getUserPassword());
			// System.out.println("user" + user);
			session.setAttribute("user", user);
			return "redirect:/home";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "login";
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("logout");
		session.invalidate();
		return "redirect:/login";
	}

}
