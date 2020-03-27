package com.sn.kata.data;

import java.util.ArrayList;
import java.util.List;

public class UsersDataStore {

	private String user_name;

	private static List<User> users_repo = new ArrayList<User>();

	public UsersDataStore(String user_name) {
		this.user_name = user_name;
		User user_details = getUserDeatils();
		if (user_details == null) {
			User user = new User(user_name);
			users_repo.add(user);
		}
	}

	public User getUserDeatils() {
		User user_details = users_repo.stream().filter(users -> users.getUsername().equalsIgnoreCase(user_name))
				.findAny().orElse(null);

		return user_details;
	}

	public User getUserDeatils(String username) {
		User user_details = users_repo.stream().filter(users -> users.getUsername().equalsIgnoreCase(username))
				.findAny().orElse(new User(username));

		return user_details;
	}

	public String getUserName() {
		return user_name;
	}
}