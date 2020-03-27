package com.sn.kata.input.impl;

import java.util.HashMap;

import com.sn.kata.data.Post;
import com.sn.kata.data.User;
import com.sn.kata.data.UsersDataStore;

public class PublishEventHandler extends EventHandlerProvider {

	public String handle_event(HashMap<String, String> pairs, UsersDataStore dataStore) {
		User user = dataStore.getUserDeatils();
		Post post = new Post(user, pairs.get("message"));
		user.getUser_wall().getPosts().add(post);
		print(System.lineSeparator() + dataStore.getUserName() + "'s message posted successfully !!"
				+ System.lineSeparator());

		return dataStore.getUserName() + "'s message posted successfully !!";
	}

}
