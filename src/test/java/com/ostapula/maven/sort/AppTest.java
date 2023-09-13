package com.ostapula.maven.sort;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import java.lang.Math;


public class AppTest {
	@Test
	public void testEqualsMethodReflexivity() {
		Person person = new Person("John" , "Smith");
		assertEquals(true, person.equals(person));
	}

	@Test
	public void testEquaslMethodSymmetry() {
		Person person1 = new Person("John" , "Smith");
		Person person2 = new Person("John" , "Smith");
		assertEquals(person1.equals(person2), person2.equals(person1));
	}

	@Test
	public void testEquaslMethodTransitivity() {
		Person person1 = new Person("John" , "Smith");
		Person person2 = new Person("John" , "Smith");
		Person person3 = new Person("John" , "Smith");
		assertEquals(person1.equals(person2) && person2.equals(person3), person1.equals(person3));
	}

	@Test
	public void testEqualsMethodConsistency() {
		Person person1 = new Person("John" , "Smith");
		Person person2 = new Person("John" , "Smith");
		boolean result1 = person1.equals(person2);
		boolean result2 = person1.equals(person2);
		assertEquals(true, result1 && result2);
	}

	@Test
	public void testEqualsMethodNonNullity() {
		Person person1 = new Person("John" , "Smith");
		assertEquals(false, person1.equals(null));
	}

	@Test
	public void testCompareToMethodSymmetry() {
		Person person1 = new Person("John" , "Smith");
		Person person2 = new Person("John" , "Smith");
		boolean result = Math.signum(person1.compareTo(person2)) == -Math.signum(person2.compareTo(person1));
		assertEquals(true, result);
	}

	@Test
	public void testCompareToMethodTransitivityMajority() {
		Person person1 = new Person("A" , "A");
		Person person2 = new Person("B" , "B");
		Person person3 = new Person("D" , "D");
		boolean result = person1.compareTo(person2) > 0 && person2.compareTo(person3) > 0 ? true : false;
		assertEquals(result, person1.compareTo(person3) > 0);
	}

	@Test
	public void testCompareToMethodTransitivityEquality() {
		Person person1 = new Person("John" , "Smith");
		Person person2 = new Person("John" , "Smith");
		Person person3 = new Person("John" , "Smith");
		boolean result = person1.compareTo(person2) == 0;
		assertEquals(result, Math.signum(person1.compareTo(person3)) == Math.signum(person2.compareTo(person3)));
	}

	@Test
	public void testCompareToMehodInconsistentOrdering() {
		Person person1 = new Person("John" , "Smith");
		Person person2 = new Person("John" , "Smith");
		assertEquals(true , (person1.compareTo(person2) == 0) == person1.equals(person2));
	}

	@Test
	public void testUnorderedListOfIntegersBecomesOrdered() {
		List<Integer> inputList = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);
		expectedList.sort(null);
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testUnorderedListOfObjectsBecomesOrdered() {
		List<Person> inputList = Arrays.asList(
				new Person("John", "Smith"),
				new Person("Michael", "Johnson"),
				new Person("Johnathon", "Smythe"),
				new Person("Emma", "Thompson"),
				new Person("Mike", "Jonson"),
				new Person("Emily", "Smith"),
				new Person("Jess", "Turney"),
				new Person("Emilia", "Thomason"),
				new Person("William", "Williams"),
				new Person("Jessica", "Turner"));
		List<Person> expectedList = new ArrayList<>(inputList);
		expectedList.sort(null);
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testSortingOrderedListOfIntegersRemainsOrdered() {
		List<Integer> inputList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);
		expectedList.sort(null);
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testSortingOrderedListOfObjectsRemainsOrdered() {
		List<Person> inputList = Arrays.asList(
				new Person("Emilia", "Thomason"),
				new Person("Emily", "Smith"),
				new Person("Emma", "Thompson"),
				new Person("Jess", "Turney"),
				new Person("Jessica", "Turner"),
				new Person("John", "Smith"),
				new Person("Johnathon", "Smythe"),
				new Person("Michael", "Johnson"),
				new Person("Mike", "Jonson"),
				new Person("William", "Williams"));
		List<Person> expectedList = new ArrayList<>(inputList);
		expectedList.sort(null);
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testListContentRemainsUnchagedAfterSorting() {
		List<Integer> inputList = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);	
		List<Integer> copyOfInputList = new ArrayList<Integer>(inputList);
		SortApp.sortObjects(expectedList);
		assertEquals(copyOfInputList, inputList);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSortingNullListThrowsException() {
		List<Integer> integerInputList = null;
		SortApp.sortObjects(integerInputList);
		List<Person> personInputList = null;
		SortApp.sortObjects(personInputList);
	}

	@Test
	public void testSortingEmptyListIntegerTypeReturnsEmptyList() {
		List<Integer> inputList = new ArrayList<>();
		List<Integer> expectedList = new ArrayList<>();
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testSortingEmptyListPersonTypeReturnsEmptyList() {
		List<Person> inputList = new ArrayList<>();
		List<Person> expectedList = new ArrayList<>();
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testNullElementsAreSmallerThanAnyInteger() {
		List<Integer> inputList = Arrays.asList(1, 12, 5, null, 87, 56, 34, null, 20, 8);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);	
		int counter = 0;
		for (int i = 0; i < expectedList.size(); i++) {
			if(expectedList.get(i) == null) {
				expectedList.remove(i);
				counter++;
				i--;
			}
		}
		expectedList.sort(null);
		for (int i = 0; i < counter; i++) {
			expectedList.add(i, null);
		}
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testNullElementsAreSmallerThanAnyObject() {
		List<Person> inputList = Arrays.asList(
				new Person("Emilia", "Thomason"),
				null,
				new Person("Emily", "Smith"),
				new Person("Emma", "Thompson"),
				new Person("Jess", "Turney"),
				new Person("Jessica", "Turner"),
				null,
				new Person("John", "Smith"),
				new Person("Johnathon", "Smythe"),
				new Person("Michael", "Johnson"),
				new Person("Mike", "Jonson"),
				null,
				new Person("William", "Williams"));
		List<Person> expectedList = new ArrayList<Person>(inputList);	
		int counter = 0;
		for (int i = 0; i < expectedList.size(); i++) {
			if(expectedList.get(i) == null) {
				expectedList.remove(i);
				counter++;
				i--;
			}
		}
		expectedList.sort(null);
		for (int i = 0; i < counter; i++) {
			expectedList.add(i, null);
		}
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}

	@Test
	public void testNullNameOrSurnameAreSmallerThanAnyObject() {
		List<Person> inputList = Arrays.asList(
				new Person("William", "Williams"),
				new Person("Emily", "Smith"),
				new Person("Jessica", "Turner"),
				new Person("Emma", "Thompson"),
				new Person("Jess", "Turney"),
				new Person("Jessica", "Turner"),
				new Person("John", null),
				new Person("Johnathon", "Smythe"),
				new Person("Michael", "Johnson"),
				new Person("Mike", "Jonson"),
				new Person("Emma", "Thompson"),
				new Person(null, "Thomason"));
				
		List<Person> expectedList = new ArrayList<Person>(inputList);
		expectedList.sort(null);
		assertEquals(expectedList, SortApp.sortObjects(inputList));
	}
}
