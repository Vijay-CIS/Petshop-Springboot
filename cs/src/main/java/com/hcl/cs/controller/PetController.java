package com.hcl.cs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.cs.model.Pet;
import com.hcl.cs.model.User;
import com.hcl.cs.service.PetService;
import com.hcl.cs.service.UserService;

@Controller
public class PetController {
	
	@Autowired
	private PetService petService;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		List<Pet> list = petService.fetchAllPet();
		model.addAttribute("plist", list);
		return "home";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createProduct(Model model) {
		model.addAttribute("petForm", new Pet());
		return "addPet";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("petForm") Pet pet, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		User owner = userService.findByUserId(user.getUserId());
		pet.setUser(owner);
		petService.savePet(pet);
		return "redirect:/home";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied() {
		return "403";
	}

	@RequestMapping(value = "/myPet", method = RequestMethod.GET)
	public String myPet(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<Pet> list = petService.fetchAllPet(user.getUserId());
		model.addAttribute("plist", list);
		return "myPet";
	}

	@RequestMapping(value = "/addPet", method = RequestMethod.GET)
	public String addPet(Model model) {
		model.addAttribute("petForm", new Pet());
		return "addPet";
	}

	@RequestMapping(value = "/buyproduct/{petId}", method = RequestMethod.GET)
	public String buyProduct(@PathVariable ("petId") Long petId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		User owner = userService.findByUserId(user.getUserId());
	  	Pet pet = petService.findByPetId(petId);
		pet.setUser(owner);
	
		petService.buyPet(pet);
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/updatePetAvailableStatus/{petId}/{status}", method = RequestMethod.GET)
	public String buyProduct(@PathVariable ("petId") Long petId,@PathVariable("status")boolean status, HttpSession session) {
		
	  	Pet pet = petService.findByPetId(petId);
		pet.setAvailable(status);
	
		petService.updatePet(pet);
		return "redirect:/myPet";
	}
	
	
	
}
