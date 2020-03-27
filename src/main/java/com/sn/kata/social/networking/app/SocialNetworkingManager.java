package com.sn.kata.social.networking.app;

import java.util.HashMap;
import java.util.Scanner;

import com.sn.kata.data.UsersDataStore;
import com.sn.kata.input.impl.EventHandlerProvider;
import com.sn.kata.input.impl.EventHandlerProvider.InputEventType;

public class SocialNetworkingManager {

	public enum CommandType {
		Init("init"), Guide("guide"), Wall("1"), Publish("2"), View("3"), Follow("4"), SwitchUser("5"), Exit("6");

		private final String type;

		private CommandType(String type) {
			this.type = type;
		}

		public String value() {
			return type;
		}

		public static CommandType fromValue(String v) {
			for (CommandType c : CommandType.values()) {
				if (c.type.equals(v)) {
					return c;
				}
			}
			throw new IllegalArgumentException("invalid argument " + v);
		}
	}

	private String user_name;
	UsersDataStore datastore = null;
	String newLine = System.getProperty("line.separator");

	public void initalize() throws Exception {
		guideUser(CommandType.Init);
	}

	private void guideUser(CommandType type) throws Exception {

		switch (type) {
		case Init:
			print(newLine + "Which user you want to be");
			this.user_name = readCommand();
			this.datastore = new UsersDataStore(user_name);
			guideUser(CommandType.Wall);
			break;

		case Guide:
			print(newLine + "What features you want to perform -> ");
			print(newLine + "1. Wall" + newLine + "2. Publish" + newLine + "3. View Timeline" + newLine + "4. Follow"
					+ newLine + "5. Switch User" + newLine + "6. Exit ");
			CommandType event_type = CommandType.fromValue(readCommand());
			guideUser(event_type);
			break;

		case Publish:
			print(newLine + "Enter your message to publish : " + user_name + " >");
			String message = readCommand();
			HashMap<String, String> pairs = new HashMap<String, String>();
			pairs.put("message", message);
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
			guideUser(CommandType.Wall);
			break;

		case Wall:
			print(newLine + user_name + "'s wall >");
			pairs = new HashMap<String, String>();
			EventHandlerProvider.getInputHandler(InputEventType.Wall).handle_event(pairs, datastore);
			guideUser(CommandType.Guide);
			break;

		case View:
			print(newLine + "Which user timeline would you like to see?" + newLine);
			String timeline_user = readCommand();
			pairs = new HashMap<String, String>();
			pairs.put("timeline_user", timeline_user);
			EventHandlerProvider.getInputHandler(InputEventType.View).handle_event(pairs, datastore);
			guideUser(CommandType.Guide);
			break;

		case Follow:
			print(newLine + "Which user you want to follow?");
			String followed_user = readCommand();
			pairs = new HashMap<String, String>();
			pairs.put("followed_user", followed_user);
			EventHandlerProvider.getInputHandler(InputEventType.Follow).handle_event(pairs, datastore);
			guideUser(CommandType.Wall);

		case SwitchUser:
			print(newLine + "Switching user from " + user_name);
			guideUser(CommandType.Init);
			break;

		case Exit:
			print("Exiting application !!");
			System.exit(0);
		default:
			throw new Exception(" " + type.value() + " not supported");
		}
	}

	private String readCommand() {
		String input = new Scanner(System.in).nextLine();
		return input;
	}

	private void print(String output) {
		System.out.println(output);
	}

}
