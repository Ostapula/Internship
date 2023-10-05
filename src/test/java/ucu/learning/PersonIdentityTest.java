package ucu.learning;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ucu.learning.App.Person;

public class PersonIdentityTest {

	@Test
	public void equals_is_reflexive() {
		final Person a = new Person(0L, "S1", "N1", new Date());
		assertTrue(a.equals(a));
		assertTrue(a.equals(new Person(0L, a.surname, a.name, new Date())));
		assertTrue((new Person(0L, a.surname, a.name, new Date())).equals(a));
	}

	@Test
	public void equals_is_symetric() {
		final Person a = new Person(0L, "S1", "N1", new Date());
		final Person b = new Person(0L, "S1", "N1", new Date());
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
	}

	@Test
	public void equals_is_transitive() {
		final Person a = new Person(0L, "S1", "N1", new Date());
		final Person b = new Person(0L, "S1", "N1", new Date());
		final Person c = new Person(0L, "S1", "N1", new Date());
		assertTrue(a.equals(b));
		assertTrue(b.equals(c));
		assertTrue(a.equals(c));
	}
}
