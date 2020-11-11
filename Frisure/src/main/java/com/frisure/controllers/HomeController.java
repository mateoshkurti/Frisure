package com.frisure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frisure.beans.Addresses;
import com.frisure.beans.Booking;
import com.frisure.beans.Business;
import com.frisure.beans.ContactInfo;
import com.frisure.beans.Employees;
import com.frisure.beans.Haircuts;
import com.frisure.beans.Ratings;
import com.frisure.beans.Service_Booking;
import com.frisure.beans.Services;
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
		User user = da.findUserAccount(auth.getName());
		System.out.println(user.getUserType());

		if (user.getUserType().equals("ROLE_SP"))
			return "/secure/serviceProvider/index";

		else if (user.getUserType().equals("ROLE_user"))
			return "/secure/user/index";

		else
			return "/error/permission-denied";
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
		try {
			da.addUser(username, password, "ROLE_user");
		} catch (Exception e) {
			return "/register?loginError";
		}
		try {
		da.addAddress(streetAddress, city, province, country, postalCode);
		da.addUserInfo(username, firstName, lastName);
		da.addContactInfo(username, phoneNumber, email, postalCode);}
		catch(Exception e) {
			return "/register?credErr";
		}

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

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	@GetMapping("/secure/serviceProvider")
	public String getSpIndex(Model model, Authentication auth) {
		String username = auth.getName();
		Long businessID = da.getBusinessByOwner(username).getBusinessID();
		Business salon = da.getBusinessByID(businessID);
		Addresses salonAddress = da.getAddressesByBusinessID(businessID);
		model.addAttribute("salon", salon);
		model.addAttribute("salonAddress", salonAddress);
		return "/secure/serviceProvider/index";
	}

	@GetMapping("/secure/serviceProvider/appointment")
	public String getSpAppointment(Model model, Authentication auth) {
		String username = auth.getName();
		Long businessID = da.getBusinessByOwner(username).getBusinessID();
		List<Booking> bookingList = da.getBusinessBookingList(businessID);
		model.addAttribute("bookingList", bookingList);
		return "/secure/serviceProvider/appointment";
	}

	@GetMapping("/secure/serviceProvider/appointmentInfo/{bookingID}")
	public String getSpAppointmentInfo(Model model, Authentication auth, @PathVariable Long bookingID) {

		Booking booking = da.getBookingByBookingID(bookingID);
		model.addAttribute("booking", booking);

		return "/secure/serviceProvider/apointmentInfo";
	}

	@GetMapping("/secure/serviceProvider/insightsActivity")
	public String getSpInsightsActivity(Model model, Authentication auth) {
		// to do
		return "/secure/serviceProvider/insightsActivity";
	}

	@GetMapping("/secure/serviceProvider/insightsAudience")
	public String getSpInsightsAudience(Model model, Authentication auth) {
		// to do
		return "/secure/serviceProvider/insightsAudience";
	}

	@GetMapping("/secure/serviceProvider/insightsReviews")
	public String getSpInsightsReviews(Model model, Authentication auth) {
		String username = auth.getName();
		Long businessID = da.getBusinessByOwner(username).getBusinessID();
		List<Ratings> reviewList = da.getReviewsByBusiness(businessID);
		model.addAttribute("reviewList", reviewList);
		return "/secure/serviceProvider/insightsReviews";
	}

	@GetMapping("/secure/serviceProvider/services")
	public String getSpServices(Model model, Authentication auth) {
		String username = auth.getName();
		Long businessID = da.getBusinessByOwner(username).getBusinessID();
		Business salon = da.getBusinessByID(businessID);
		List<Services> salonServices = da.getServicesByBusinessID(businessID);
		model.addAttribute("salon", salon);
		model.addAttribute("salonServices", salonServices);
		return "/secure/serviceProvider/services";
	}

	@GetMapping("/secure/serviceProvider/settings")
	public String getSpInsightsSettings(Model model, Authentication auth) {
		// to do
		return "/secure/serviceProvider/settings/index";
	}

	@GetMapping("/secure/serviceProvider/settings/authentication")
	public String getSpInsightsSettingsAuth(Model model, Authentication auth) {
		String username = auth.getName();
		User user = da.findUserAccount(username);
		model.addAttribute("user", user);
		return "/secure/serviceProvider/settings/authentication";
	}

	@GetMapping("/secure/serviceProvider/settings/contactInfo")
	public String getSpInsightsSettingsContactInfo(Model model, Authentication auth) {
		String username = auth.getName();
		ContactInfo userContactInfo = da.getContactInfoByUsername(username);
		model.addAttribute("userContactInfo", userContactInfo);
		return "/secure/serviceProvider/settings/contactInfo";
	}

	@GetMapping("/secure/serviceProvider/preferences")
	public String getSpInsightsSettingsPreferences(Model model, Authentication auth) {
		// to do
		return "/secure/serviceProvider/settings/preferences";
	}

	@GetMapping("/secure/serviceProvider/settings/editAuthentication")
	public String getSpInsightsSettingsEditAuth(Model model, Authentication auth) {
		String username = auth.getName();
		User user = da.findUserAccount(username);
		model.addAttribute("user", user);
		return "/secure/serviceProvider/settings/editAuthentication";
	}

	@GetMapping("/secure/serviceProvider/settings/editContactInfo")
	public String getSpInsightsSettingsEditContactInfo(Model model, Authentication auth) {
		String username = auth.getName();
		ContactInfo userContactInfo = da.getContactInfoByUsername(username);
		model.addAttribute("userContactInfo", userContactInfo);
		return "/secure/serviceProvider/settings/editContactInfo";
	}

	@GetMapping("/secure/serviceProvider/editPreferences")
	public String getSpInsightsSettingsEditPreferences(Model model, Authentication auth) {
		// to do
		return "/secure/serviceProvider/settings/editPreferences";
	}

	@GetMapping("/secure/user")
	public String getUserIndex(Model model, Authentication auth) {
		// to do
		return "/secure/user/index";
	}

	@GetMapping("/secure/user/appointment")
	public String getUserAppointment(Model model, Authentication auth) {
		String username = auth.getName();
		List<Booking> appointmentsList = da.getAppointmentsByUsername(username);
		model.addAttribute("appointmentsList", appointmentsList);
		return "/secure/user/appointment";
	}

	@GetMapping("/secure/user/appointmentInfo/{bookingID}")
	public String getUserAppointmentInfo(Model model, Authentication auth, @PathVariable Long bookingID) {
		Booking appointment = da.getBookingByBookingID(bookingID);
		List<Service_Booking> bookedServices = da.getBookedServices(bookingID);
		model.addAttribute("appointment", appointment);
		model.addAttribute("bookedServices", bookedServices);
		return "/secure/user/appointmentInfo";
	}

	@GetMapping("/secure/user/booking")
	public String getUserBooking(Model model, Authentication auth) {

		// to do

		return "/secure/user/booking";
	}

	@GetMapping("/secure/user/business/{businessID}")
	public String getUserBusiness(Model model, Authentication auth, @PathVariable Long businessID) {
		Business salon = da.getBusinessByID(businessID);
		Addresses salonAddress = da.getAddressesByBusinessID(businessID);
		model.addAttribute("salon", salon);
		model.addAttribute("salonAddress", salonAddress);
		return "/secure/user/business";
	}

	@GetMapping("/secure/user/businessServices/{businessID}")
	public String getUserBusinessServices(Model model, Authentication auth, @PathVariable Long businessID) {
		Business salon = da.getBusinessByID(businessID);
		List<Services> salonServices = da.getServicesByBusinessID(businessID);
		model.addAttribute("salon", salon);
		model.addAttribute("salonServices", salonServices);
		return "/secure/user/businessServices";
	}

	@GetMapping("/secure/user/review")
	public String getUserReview(Model model, Authentication auth) {
		// to do
		return "/secure/user/review";
	}

	@GetMapping("/secure/user/searchBarber")
	public String getUserSearchBarber(Model model, Authentication auth) {
		List<Employees> barberList = da.getBarbers();
		model.addAttribute("barberList", barberList);
		return "/secure/user/searchBarber";
	}

	@GetMapping("/secure/user/searchHairstyle")
	public String getUserSearchHairstyle(Model model, Authentication auth) {
		List<Haircuts> hairstyleList = da.getHaircuts();
		model.addAttribute("hairstyleList", hairstyleList);
		return "/secure/user/searchHairstyle";
	}

	@GetMapping("/secure/user/searchHairstyle/{haircutName}")
	public String getUserSearchSalonByHairstyle(Model model, Authentication auth, @PathVariable String haircutName) {
		List<Business> salonList = da.getBusinessesByHairstyle(haircutName);
		model.addAttribute("salonList", salonList);
		return "/secure/user/searchSalon";
	}

	@GetMapping("/secure/user/searchSalon")
	public String getUserSearchSalon(Model model, Authentication auth) {
		List<Business> salonList = da.getBusinesses();
		model.addAttribute("salonList", salonList);
		return "/secure/user/searchSalon";
	}

	@GetMapping("/secure/user/user")
	public String getUserUserProfile(Model model, Authentication auth) {
		// to do
		return "/secure/user/user";
	}

	@GetMapping("/secure/user/userHairstyles")
	public String getUserUserHairstyles(Model model, Authentication auth) {
		// to do
		return "/secure/user/userHairstyles";
	}

	@GetMapping("/secure/user/settings")
	public String getUserSettings(Model model, Authentication auth) {
		// to do
		return "/secure/user/settings/index";
	}

	@GetMapping("/secure/user/settings/authentication")
	public String getUserSettingsAuthentication(Model model, Authentication auth) {
		String username = auth.getName();
		User user = da.findUserAccount(username);
		model.addAttribute("user", user);
		return "/secure/user/settings/authentication";
	}

	@GetMapping("/secure/user/settings/contactInfo")
	public String getUserSettingsContactInfo(Model model, Authentication auth) {
		String username = auth.getName();
		ContactInfo userContactInfo = da.getContactInfoByUsername(username);
		model.addAttribute("userContactInfo", userContactInfo);
		return "/secure/user/settings/contactInfo";
	}

	@GetMapping("/secure/user/settings/preferences")
	public String getUserSettingsPreferences(Model model, Authentication auth) {
		// to do
		return "/secure/user/settings/preferences";
	}

	@GetMapping("/secure/user/settings/editAuthentication")
	public String editUserSettingsAuthentication(Model model, Authentication auth) {
		String username = auth.getName();
		User user = da.findUserAccount(username);
		model.addAttribute("user", user);
		return "/secure/user/settings/editAuthentication";
	}

	@GetMapping("/secure/user/settings/editEontactInfo")
	public String editUserSettingsContactInfo(Model model, Authentication auth) {
		String username = auth.getName();
		ContactInfo userContactInfo = da.getContactInfoByUsername(username);
		model.addAttribute("userContactInfo", userContactInfo);
		return "/secure/user/settings/editContactInfo";
	}

	@GetMapping("/secure/user/settings/editPreferences")
	public String editUserSettingsPreferences(Model model, Authentication auth) {
		// to do
		return "/secure/user/settings/editPreferences";
	}

	@PostMapping("/secure/user/settings/editAuthentication")
	public String UpdateAuthentication(Model model, Authentication auth, @RequestParam String username,
			@RequestParam String password) {

		da.updateUser(username, password);

		return "/secure/user/settings/index";
	}

	@PostMapping("/secure/user/settings/editEontactInfo")
	public String UpdateContactInfo(Model model, Authentication auth, @RequestParam String email,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam Long phoneNumber,
			@RequestParam String streetAddress, @RequestParam String city, @RequestParam String province,
			@RequestParam String country, @RequestParam String postalCode) {
		String username = auth.getName();
		da.updateAddress(streetAddress, city, province, country, postalCode);
		da.updateUserInfo(username, firstName, lastName);
		da.updateContactInfo(username, phoneNumber, email, postalCode);
		return "/secure/user/settings/index";
	}

	@PostMapping("/secure/user/settings/editPreferences")
	public String UpdatePreferences(Model model, Authentication auth) {
		// to do
		return "/secure/user/settings/index";
	}
}
