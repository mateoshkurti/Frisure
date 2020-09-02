package com.frisure.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.frisure.database.DatabaseAccess;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Lazy
	@Autowired
	private DatabaseAccess da;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Find the user based on the username (read email)
		com.frisure.beans.User user = da.findUserAccount(username);
		// If the user doesn't exist throw an exception
		if (user == null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		// Get a list of roles for that user
		List<String> roleNames = da.getRoleById(user.getUsername());
		// Change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		// Create a user based on the information above.
		UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), grantList);
		return userDetails;

	}
}
