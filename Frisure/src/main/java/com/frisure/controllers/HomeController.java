package com.frisure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frisure.beans.User;
import com.frisure.database.DatabaseAccess;

@Controller
public class HomeController {

	@Lazy
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String index() {

		return "index";
	}
	
	@GetMapping("/home")
	public String home(Authentication auth) {
		User user= da.findUserAccount(auth.getName());
		System.out.println(user.getUserType());
		
		if(user.getUserType().equals("ROLE_SP"))
		return"/secure/serviceProvider/index";
		
		else if(user.getUserType().equals("ROLE_user"))
			return"/secure/user/index";
		
		else
			return"/error/permission-denied";
	}

	@GetMapping("/userType")
	public String getuserType() {
		return "userType";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password, @RequestParam String email,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam Long phoneNumber,
			@RequestParam String streetAddress, @RequestParam String city, @RequestParam String province,
			@RequestParam String country, @RequestParam String postalCode) {

		da.addUser(username, password, "ROLE_user");
		da.addAddress(streetAddress, city, province, country, postalCode);
		da.addUserInfo(username, firstName, lastName);
		da.addContactInfo(username, phoneNumber, email, postalCode);

		return "redirect:/";
	}

	@GetMapping("/registerSP")
	public String getRegisterSP() {
		return "register";
	}

	@PostMapping("/registerSP")
	public String postRegisterSP(@RequestParam String username, @RequestParam String password,
			@RequestParam String email, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam Long phoneNumber, @RequestParam String streetAddress, @RequestParam String city,
			@RequestParam String province, @RequestParam String country, @RequestParam String postalCode,
			@RequestParam String name, @RequestParam String licenseID) {

		da.addUser(username, password, "ROLE_SP");
		da.addAddress(streetAddress, city, province, country, postalCode);
		da.addUserInfo(username, firstName, lastName);
		da.addContactInfo(username, phoneNumber, email, postalCode);
		da.addBusiness(name, licenseID, username);

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	@GetMapping("/secure/serviceProvider")
	public String getSpIndex() {
		// to do
		return "/secure/serviceProvider/index";
	}

	@GetMapping("/secure/serviceProvider/appointment")
	public String getSpAppointment() {
		// to do
		return "/secure/serviceProvider/appointment";
	}

	@GetMapping("/secure/serviceProvider/appointmentInfo")
	public String getSpAppointmentInfo() {
		// to do
		return "/secure/serviceProvider/apointmentInfo";
	}

	@GetMapping("/secure/serviceProvider/insightsActivity")
	public String getSpInsightsActivity() {
		// to do
		return "/secure/serviceProvider/insightsActivity";
	}

	@GetMapping("/secure/serviceProvider/insightsAudience")
	public String getSpInsightsAudience() {
		// to do
		return "/secure/serviceProvider/insightsAudience";
	}

	@GetMapping("/secure/serviceProvider/insightsReviews")
	public String getSpInsightsReviews() {
		// to do
		return "/secure/serviceProvider/insightsReviews";
	}

	@GetMapping("/secure/serviceProvider/services")
	public String getSpServices() {
		// to do
		return "/secure/serviceProvider/services";
	}

	@GetMapping("/secure/serviceProvider/settings")
	public String getSpInsightsSettings() {
		// to do
		return "/secure/serviceProvider/settings/index";
	}

	@GetMapping("/secure/serviceProvider/settings/authentication")
	public String getSpInsightsSettingsAuth() {
		// to do
		return "/secure/serviceProvider/settings/authentication";
	}

	@GetMapping("/secure/serviceProvider/settings/contactInfo")
	public String getSpInsightsSettingsContactInfo() {
		// to do
		return "/secure/serviceProvider/settings/contactInfo";
	}

	@GetMapping("/secure/serviceProvider/preferences")
	public String getSpInsightsSettingsPreferences() {
		// to do
		return "/secure/serviceProvider/settings/preferences";
	}

	@GetMapping("/secure/user")
	public String getUserIndex() {
		// to do
		return "/secure/user/index";
	}

	@GetMapping("/secure/user/appointment")
	public String getUserAppointment() {
		// to do
		return "/secure/user/appointment";
	}

	@GetMapping("/secure/user/appointmentInfo")
	public String getUserAppointmentInfo() {
		// to do
		return "/secure/user/appointmentInfo";
	}

	@GetMapping("/secure/user/booking")
	public String getUserBooking() {
		// to do
		return "/secure/user/booking";
	}

	@GetMapping("/secure/user/business")
	public String getUserBusiness() {
		// to do
		return "/secure/user/business";
	}

	@GetMapping("/secure/user/businessServices")
	public String getUserBusinessServices() {
		// to do
		return "/secure/user/businessServices";
	}

	@GetMapping("/secure/user/review")
	public String getUserReview() {
		// to do
		return "/secure/user/review";
	}

	@GetMapping("/secure/user/searchBarber")
	public String getUserSearchBarber() {
		// to do
		return "/secure/user/searchBarber";
	}

	@GetMapping("/secure/user/searchHairstyle")
	public String getUserSearchHairstyle() {
		// to do
		return "/secure/user/searchHairstyle";
	}

	@GetMapping("/secure/user/searchSalon")
	public String getUserSearchSalon() {
		// to do
		return "/secure/user/searchSalon";
	}

	@GetMapping("/secure/user/user")
	public String getUserUserProfile() {
		// to do
		return "/secure/user/user";
	}

	@GetMapping("/secure/user/userHairstyles")
	public String getUserUserHairstyles() {
		// to do
		return "/secure/user/userHairstyles";
	}

	@GetMapping("/secure/user/settings")
	public String getUserSettings() {
		// to do
		return "/secure/user/settings/index";
	}

	@GetMapping("/secure/user/settings/authentication")
	public String getUserSettingsAuthentication() {
		// to do
		return "/secure/user/settings/authentication";
	}

	@GetMapping("/secure/user/settings/contactInfo")
	public String getUserSettingsContactInfo() {
		// to do
		return "/secure/user/settings/contactInfo";
	}

	@GetMapping("/secure/user/settings/preferences")
	public String getUserSettingsPreferences() {
		// to do
		return "/secure/user/settings/preferences";
	}
}
