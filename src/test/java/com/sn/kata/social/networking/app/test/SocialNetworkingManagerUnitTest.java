package com.sn.kata.social.networking.app.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runners.MethodSorters;

import com.sn.kata.data.UsersDataStore;
import com.sn.kata.input.impl.EventHandlerProvider;
import com.sn.kata.input.impl.EventHandlerProvider.InputEventType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class SocialNetworkingManagerUnitTest {

	@ClassRule
	public static ErrorCollector collector = new ErrorCollector();

	@BeforeClass
	public static void setup() {

		UsersDataStore datastore = new UsersDataStore("Alice");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("message", "I love the weather today");
		try {
			System.out.println(System.lineSeparator() + "Alice Posting a message !" + System.lineSeparator());
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}

		datastore = new UsersDataStore("Bob");
		pairs = new HashMap<String, String>();
		pairs.put("message", "Darn! We lost!");
		try {
			System.out.println(System.lineSeparator() + "Bob's Posting a message !" + System.lineSeparator());
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}

		datastore = new UsersDataStore("Alice");
		pairs = new HashMap<String, String>();
		pairs.put("followed_user", "Bob");
		try {
			EventHandlerProvider.getInputHandler(InputEventType.Follow).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}

		pairs = new HashMap<String, String>();
		pairs.put("message", "Good game though.");
		try {
			System.out.println(System.lineSeparator() + "Alice Posting a message !" + System.lineSeparator());
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);

		} catch (Exception e) {
			collector.addError(e);
		}
		System.out.println("Done with Setiing up!!" + System.lineSeparator());
	}

	@Test
	public void aviewOwnTimeline() {

		System.out.println("Viewing Alice Timeline > ");
		UsersDataStore datastore = new UsersDataStore("Alice");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("timeline_user", "Alice");
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.View).handle_event(pairs, datastore);
			assertTrue((post.contains("I love the weather today") && post.contains("Good game though.")));
			System.out.println(post + System.lineSeparator());
		} catch (Exception e) {
			collector.addError(e);
		}
	}

	@Test
	public void bviewOthersTimeline() {

		System.out.println("Viewing Bob's Timeline > ");

		UsersDataStore datastore = new UsersDataStore("Alice");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("timeline_user", "Bob");
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.View).handle_event(pairs, datastore);
			assertTrue((post.contains("Darn! We lost!")));
			System.out.println(System.lineSeparator() + post + System.lineSeparator());
		} catch (Exception e) {
			collector.addError(e);
		}
	}

	@Test
	public void cpublish() {

		System.out.println(System.lineSeparator() + "Charlie Posting a message !" + System.lineSeparator());
		UsersDataStore datastore = new UsersDataStore("Charlie");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("message", "I'm in New York today! Anyone wants to have a coffee?");
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
			assertTrue((post.contains("Charlie's message posted successfully !!")));

		} catch (Exception e) {
			collector.addError(e);
		}
	}

	@Test
	public void dFollow() {

		UsersDataStore datastore = new UsersDataStore("Charlie");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("followed_user", "Alice");
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.Follow).handle_event(pairs, datastore);
			assertTrue((post.contains("Charlie follows Alice")));

		} catch (Exception e) {
			collector.addError(e);
		}
	}

	@Test
	public void eWall() {

		UsersDataStore datastore = new UsersDataStore("Charlie");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("followed_user", "Alice");
		try {
			EventHandlerProvider.getInputHandler(InputEventType.Follow).handle_event(pairs, datastore);

		} catch (Exception e) {
			collector.addError(e);
		}

		pairs = new HashMap<String, String>();
		pairs.put("followed_user", "Bob");
		try {
			EventHandlerProvider.getInputHandler(InputEventType.Follow).handle_event(pairs, datastore);

		} catch (Exception e) {
			collector.addError(e);
		}

		System.out.println(System.lineSeparator() + "Charlie's Wall > " + System.lineSeparator());
		pairs = new HashMap<String, String>();
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.Wall).handle_event(pairs, datastore);
			assertTrue(
					(post.contains("Alice -> I love the weather today") && post.contains("Alice -> Good game though.")
							&& post.contains("Charlie -> I'm in New York today! Anyone wants to have a coffee?")
							&& post.contains("Bob -> Darn! We lost!")));

			System.out.println(System.lineSeparator() + post + System.lineSeparator());

		} catch (Exception e) {
			collector.addError(e);
		}
	}

}
