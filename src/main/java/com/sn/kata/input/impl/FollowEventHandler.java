package com.sn.kata.input.impl;

import java.util.HashMap;
import java.util.List;

import com.sn.kata.data.User;
import com.sn.kata.data.UsersDataStore;

public class FollowEventHandler extends EventHandlerProvider {

	public String handle_event(HashMap<String, String> pairs, UsersDataStore dataStore) {

		List<User> followed_users;

		(followed_users = dataStore.getUserDeatils().getFollowed_users())
				.add(dataStore.getUserDeatils(pairs.get("followed_user")));
		print(System.lineSeparator() + dataStore.getUserName() + " follows -> " + System.lineSeparator());

		followed_users.forEach((follwers) -> print(follwers.getUsername()));

		return dataStore.getUserName() + " follows " + pairs.get("followed_user");

	}
}