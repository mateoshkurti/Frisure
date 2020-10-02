package com.frisure.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

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

@Repository
public class DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void addUser(String username, String password, String userType) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO users " + "(username, password, userType)"
				+ " values (:username, :encryptedPassword, :userType)";
		parameters.addValue("username", username);
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		parameters.addValue("userType", userType);
		jdbc.update(query, parameters);
	}

	public User findUserAccount(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM users where username=:username";
		parameters.addValue("username", username);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}

	public List<String> getRoleById(String username) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select userType" + " FROM users " + "WHERE username=:username";
		parameters.addValue("username", username);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("userType"));
		}
		for (String r : roles) {
			System.out.println("DA. ROLES ARE" + r);
		}
		return roles;

	}

	public void addAddress(String streetAddress, String city, String province, String country, String postalCode) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Insert INTO addresses (streetAddress, city, province, country, postalCode)"
				+ "Values (:streetAddress, :city, :province, :country, :postalCode)";
		parameters.addValue("streetAddress", streetAddress);
		parameters.addValue("city", city);
		parameters.addValue("province", province);
		parameters.addValue("country", country);
		parameters.addValue("postalCode", postalCode);
		jdbc.update(query, parameters);

	}

	public void addUserInfo(String username, String firstName, String lastName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Insert INTO userinfo (username, firstName, lastName) "
				+ "VALUES (:username, :firstName, :lastName)";
		parameters.addValue("username", username);
		parameters.addValue("firstName", firstName);
		parameters.addValue("lastName", lastName);
		jdbc.update(query, parameters);
	}

	public void addContactInfo(String username, Long phoneNumber, String email, String postalCode) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Insert INTO contactinfo (username, phoneNumber, email, postalCode) "
				+ "VALUES (:username, :phoneNumber, :email, :postalCode)";
		parameters.addValue("username", username);
		parameters.addValue("phoneNumber", phoneNumber);
		parameters.addValue("email", email);
		parameters.addValue("postalCode", postalCode);
		jdbc.update(query, parameters);
	}

	public void addBusiness(String name, String licenseID, String owner) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Insert INTO business (name, licenseID, owner) " + "VALUES (:name, :licenseID, :owner)";
		parameters.addValue("name", name);
		parameters.addValue("licenseID", licenseID);
		parameters.addValue("owner", owner);
		jdbc.update(query, parameters);
	}

	public Business getBusinessByOwner(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Business where owner = :username;";
		parameters.addValue("username", username);
		List<Business> b = jdbc.query(query, parameters, new BeanPropertyRowMapper<Business>(Business.class));
		if(b.size()>0)
		return b.get(0);
		else
			return null;
	}

	public Business getBusinessByID(Long businessID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Business where businessID = :businesID;";
		parameters.addValue("businessID", businessID);
		List<Business> b = jdbc.query(query, parameters, new BeanPropertyRowMapper<Business>(Business.class));
		if(b.size()>0)
			return b.get(0);
			else
				return null;
	}

	public Addresses getAddressesByBusinessID(Long businessID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Addresses where businessID = :businessID;";
		parameters.addValue("businessID", businessID);
		List<Addresses> b = jdbc.query(query, parameters, new BeanPropertyRowMapper<Addresses>(Addresses.class));
		if(b.size()>0)
			return b.get(0);
			else
				return null;
	}

	public List<Booking> getBusinessBookingList(Long businessID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Booking where businessID = :businesID;";
		parameters.addValue("businessID", businessID);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Booking>(Booking.class));
	}

	public Booking getBookingByBookingID(Long bookingID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Booking where bookingID = :bookingID;";
		parameters.addValue("bookingID", bookingID);
		List <Booking> b= jdbc.query(query, parameters, new BeanPropertyRowMapper<Booking>(Booking.class));
		if(b.size()>0)
			return b.get(0);
			else
				return null;
	}

	public List<Ratings> getReviewsByBusiness(Long businessID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Ratings where businessID = :businesID;";
		parameters.addValue("businessID", businessID);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Ratings>(Ratings.class));
	}

	public List <Services> getServicesByBusinessID(Long businessID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Services where businessID = :businesID;";
		parameters.addValue("businessID", businessID);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Services>(Services.class));
	}

	public ContactInfo getContactInfoByUsername(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from ContactInfo where username = username;";
		parameters.addValue("username",username);
		List <ContactInfo> c= jdbc.query(query, parameters, new BeanPropertyRowMapper<ContactInfo>(ContactInfo.class));
		if(c.size()>0)
			return c.get(0);
			else
				return null;
	}

	public List<Booking> getAppointmentsByUsername(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Booking where username = :username;";
		parameters.addValue("username", username);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Booking>(Booking.class));
	}

	public List<Service_Booking> getBookedServices(Long bookingID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Service_Booking where bookingID = :bookingID;";
		parameters.addValue("bookingID", bookingID);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Service_Booking>(Service_Booking.class));
	}

	public List<Employees> getBarbers() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Employees;";
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Employees>(Employees.class));

	}

	public List<Haircuts> getHaircuts() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Haircuts;";
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Haircuts>(Haircuts.class));

	}

	public List<Business> getBusinessesByHairstyle(String haircutName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Businesses where businessID in (Select businessID from Haircuts "
				+ "where name= :haircutName);";
		parameters.addValue("haircutName", haircutName);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Business>(Business.class));
	}

	public List<Business> getBusinesses() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from Businesses;";
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Business>(Business.class));
	}

	public void updateUser(String username, String password) {

	}

	public void updateUserInfo(String username, String firstName, String lastName) {

	}

	public void updateContactInfo(String username, Long phoneNumber, String email, String postalCode) {

	}

	public void updateAddress(String streetAddress, String city, String province, String country, String postalCode) {

	}
}
