package com.crucemelit.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GymTest {

	private static final String CITY = "City";
	private static final String ADDRESS = "Address";

	private Gym gym;

	@Before
	public void setup() {
		gym = new Gym();
	}

	@Test
	public void getFullNameNothingSetTest() {
		assertEquals("", gym.getFullAddress());
	}

	@Test
	public void getFullNameOnlyAddressTest() {
		gym.setAddress(ADDRESS);
		assertEquals(ADDRESS, gym.getFullAddress());
	}

	@Test
	public void getFullNameOnlyCityTest() {
		gym.setCity(CITY);
		assertEquals(CITY, gym.getFullAddress());
	}

	@Test
	public void getFullNameTest() {
		gym.setCity(CITY);
		gym.setAddress(ADDRESS);
		assertEquals(CITY + " " + ADDRESS, gym.getFullAddress());
	}

}
