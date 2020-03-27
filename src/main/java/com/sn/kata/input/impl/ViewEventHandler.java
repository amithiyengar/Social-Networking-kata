package com.sn.kata.input.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.sn.kata.data.Post;
import com.sn.kata.data.UsersDataStore;

public class ViewEventHandler extends EventHandlerProvider {

	public String handle_event(HashMap<String, String> pairs, UsersDataStore dataStore) {
		List<Post> posts = dataStore.getUserDeatils(pairs.get("timeline_user")).getUser_wall().getPosts();
		posts.sort(Comparator.comparing(Post::getTime));
		print(System.lineSeparator() + "Here is " + pairs.get("timeline_user") + "'s timeline >");
		posts.forEach((user_posts) -> print(user_posts.getMessage()));

		StringBuffer sb = new StringBuffer();

		for (Post post : posts) {
			sb.append(post.getMessage());
			sb.append(System.lineSeparator());
		}
		String str = sb.toString();

		return str;
	}
}