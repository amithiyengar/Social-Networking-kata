package com.sn.kata.data;

public class Post {

	private User user;

	private String message;

	private long time;

	public Post(User user, String message) {
		this.user = user;
		this.message = message;
		this.time = System.currentTimeMillis();
	}

	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	public long getTime() {
		return time;
	}

}