package com.sn.kata.data;

import java.util.ArrayList;
import java.util.List;

public class Wall {

	private List<Post> posts;

	public List<Post> getPosts() {
		if (posts == null)
			posts = new ArrayList<Post>();
		return posts;
	}

	public void setPosts(List<Post> posts) {

		this.posts = posts;
	}
}
