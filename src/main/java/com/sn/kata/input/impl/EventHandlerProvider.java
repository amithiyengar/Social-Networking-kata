package com.sn.kata.input.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.sn.kata.input.ifc.EventHandler;

public abstract class EventHandlerProvider implements EventHandler {

	public enum InputEventType {
		Follow("follow"), Wall("wall"), Publish("publish"), View("view");

		private final String type;

		private InputEventType(String type) {
			this.type = type;
		}

		public String value() {
			return type;
		}

		public static InputEventType fromValue(String v) {
			for (InputEventType c : InputEventType.values()) {
				if (c.type.equals(v)) {
					return c;
				}
			}
			throw new IllegalArgumentException("invalid argument " + v);
		}
	}

	private static FollowEventHandler follow = new FollowEventHandler();
	private static PublishEventHandler publish = new PublishEventHandler();
	private static ViewEventHandler view = new ViewEventHandler();
	private static WallEventHandler wall = new WallEventHandler();

	public static EventHandlerProvider getInputHandler(InputEventType type) throws Exception {

		EventHandlerProvider mgr = null;
		switch (type) {
		case Follow:
			mgr = follow;
			break;

		case Publish:
			mgr = publish;
			break;

		case Wall:
			mgr = wall;
			break;

		case View:
			mgr = view;
			break;

		default:
			throw new Exception(" " + type.value() + " not supported");

		}
		return mgr;
	}

	public void print(String output) {
		System.out.println(output);
	}

	public String getTimeDifference(long time) {

		String difference = null;

		long timeNow = System.currentTimeMillis();

		LocalDateTime dateNow = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeNow), ZoneId.systemDefault());

		LocalDateTime post_time = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());

		long diffInMinutes = java.time.Duration.between(post_time, dateNow).toMinutes();
		difference = Long.toString(diffInMinutes) + " mins";

		if (diffInMinutes == 0) {
			long diffInSecs = java.time.Duration.between(post_time, dateNow).getSeconds();
			difference = Long.toString(diffInSecs) + " secs";
		}

		return difference;
	}

}
