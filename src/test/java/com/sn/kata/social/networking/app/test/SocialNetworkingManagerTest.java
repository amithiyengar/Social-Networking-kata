package com.sn.kata.social.networking.app.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runners.MethodSorters;

import com.sn.kata.data.UsersDataStore;
import com.sn.kata.input.impl.EventHandlerProvider;
import com.sn.kata.input.impl.EventHandlerProvider.InputEventType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialNetworkingManagerTest {

	@ClassRule
	public static ErrorCollector collector = new ErrorCollector();

	@BeforeClass
	public static void setup() {

		UsersDataStore datastore = new UsersDataStore("Alice");
		datastore = new UsersDataStore("Bob");
		datastore = new UsersDataStore("Charlie");

	}

	@Test
	public void aFeaturePublishing() {
		System.out.println("Feature: Publishing");
		System.out.println("\tScenario: Alice publishes messages to her personal timeline.");
		System.out.println("\t\tAlice is publishing -> I love the weather today.");

		UsersDataStore datastore = new UsersDataStore("Alice");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("message", "I love the weather today");
		try {
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}

		try {
			System.out.println("Sleeping for 4 secs");
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			collector.addError(e1);
		}

		System.out.println("\t\tAlice is viewing her timeline: ");

		pairs = new HashMap<String, String>();
		pairs.put("timeline_user", "Alice");
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.View).handle_event(pairs, datastore);
			assertTrue(post.contains("I love the weather today"));
		} catch (Exception e) {
			collector.addError(e);
		}
		System.out.println("=========================================================================");
	}

	@Test
	public void bFeatureTimeline() {
		System.out.println();
		System.out.println(System.lineSeparator() + "Feature: Publishing");
		System.out.println("\tScenario: Alice views Bob's timeline");
		System.out.println("\t\tBob is publishing -> Darn! We lost!");

		UsersDataStore datastore = new UsersDataStore("Bob");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("message", "Darn! We lost!");
		try {
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}
		
		try {
			System.out.println("Sleeping for 4 secs");
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			collector.addError(e1);
		}

		System.out.println("\t\tBob is publishing -> Good game though.");

		pairs = new HashMap<String, String>();
		pairs.put("message", "Good game though.");
		try {
			System.out.println(System.lineSeparator() + "Bob is Posting a message !" + System.lineSeparator());
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}
		
		try {
			System.out.println("Sleeping for 4 secs");
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			collector.addError(e1);
		}

		System.out.println("\t\tAlice is viewing Bob's timeline: ");

		datastore = new UsersDataStore("Bob");
		pairs = new HashMap<String, String>();
		pairs.put("timeline_user", "Bob");
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.View).handle_event(pairs, datastore);
			assertTrue(post.contains("Darn! We lost!") && post.contains("Good game though."));
		} catch (Exception e) {
			collector.addError(e);
		}

		System.out.println("=========================================================================");

	}

	@Test
	public void cFeatureFollowing() {
		System.out.println();
		System.out.println(System.lineSeparator() + "Feature: Following");
		System.out.println("\tCharlie can follow Alice and Bob, and he views an aggregated list of all timelines.");

		System.out.println("\t\tCharlie is publishing -> I'm in New York today! Anyone wants to have a coffee?");

		UsersDataStore datastore = new UsersDataStore("Charlie");
		HashMap<String, String> pairs = new HashMap<String, String>();
		pairs.put("message", "I'm in New York today! Anyone wants to have a coffee?");
		try {
			EventHandlerProvider.getInputHandler(InputEventType.Publish).handle_event(pairs, datastore);
		} catch (Exception e) {
			collector.addError(e);
		}
		
		try {
			System.out.println("Sleeping for 4 secs");
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			collector.addError(e1);
		}

		pairs = new HashMap<String, String>();
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

		System.out.println("\t\tCharlie is viewing his wall:");
		pairs = new HashMap<String, String>();
		try {
			String post = EventHandlerProvider.getInputHandler(InputEventType.Wall).handle_event(pairs, datastore);
			assertTrue((post.contains("Alice -> I love the weather today") && post.contains("Bob -> Good game though.")
					&& post.contains("Charlie -> I'm in New York today! Anyone wants to have a coffee?")
					&& post.contains("Bob -> Darn! We lost!")));
		} catch (Exception e) {
			collector.addError(e);
		}

		System.out.println("=========================================================================");

	}
}
