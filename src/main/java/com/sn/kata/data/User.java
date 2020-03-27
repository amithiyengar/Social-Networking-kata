package com.sn.kata.data;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String username;

	private List<User> followed_users;

	private Wall user_wall;

	public User(String username) {
		this.username = username;
		this.user_wall = new Wall();
		this.followed_users = new ArrayList<User>();
	}

	public String getUsername() {
		return username;
	}

	public List<User> getFollowed_users() {
		return followed_users;
	}

	public void setFollowed_users(List<User> followed_users) {
		if (followed_users == null)
			followed_users = new ArrayList<User>();
		this.followed_users = followed_users;
	}

	public Wall getUser_wall() {
		return user_wall;
	}

	public void setUser_wall(Wall user_wall) {
		if (user_wall == null)
			user_wall = new Wall();
		this.user_wall = user_wall;
	}

}