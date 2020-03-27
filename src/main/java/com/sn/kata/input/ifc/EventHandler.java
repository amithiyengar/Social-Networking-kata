package com.sn.kata.input.ifc;

import java.util.HashMap;

import com.sn.kata.data.UsersDataStore;

public interface EventHandler {

	public String handle_event(HashMap<String, String> pairs, UsersDataStore datastore);
}