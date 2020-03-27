package com.sn.kata.input.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.sn.kata.data.Post;
import com.sn.kata.data.UsersDataStore;

public class WallEventHandler extends EventHandlerProvider {

	public String handle_event(HashMap<String, String> pairs, UsersDataStore dataStore) {
		List<Post> wall_posts = new ArrayList<Post>();
		List<Post> user_posts = dataStore.getUserDeatils().getUser_wall().getPosts();
		wall_posts.addAll(user_posts);
		dataStore.getUserDeatils().getFollowed_users()
				.forEach(followedUser -> wall_posts.addAll(followedUser.getUser_wall().getPosts()));
		wall_posts.sort(Comparator.comparing(Post::getTime));
		Collections.reverse(wall_posts);
		wall_posts.forEach((user_wall) -> print(user_wall.getUser().getUsername() + " -> " + user_wall.getMessage()
				+ " (" + getTimeDifference(user_wall.getTime()) + ")"));

		StringBuffer sb = new StringBuffer();

		for (Post post : wall_posts) {
			sb.append(post.getUser().getUsername() + " -> " + post.getMessage());
			sb.append(System.lineSeparator());
		}
		String str = sb.toString();

		return str;
	}

}