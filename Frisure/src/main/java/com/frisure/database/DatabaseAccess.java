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
		for(String r:roles)
		{
			System.out.println("DA. ROLES ARE"+r);
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

}
