package com.ostapula.maven.sort;

import java.util.*;

public class SortApp {

	public static void main( String[] args ) {
		List<Integer> xs = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
		List<Person> persons1 = Arrays.asList(
			new Person("John", "Doe"),
			new Person("Anna", "Brown"),
			new Person("Bob", "Clark"),
			new Person("Catherine", "Evans"),
			new Person("Daniel", "Fox"),
			new Person("Eva", "Green"),
			new Person("Frank", "Hill"),
			new Person("Grace", "Ivanova"),
			new Person("Harry", "Jones"),
			new Person("Iris", "Kelly"),
			new Person("joHn", "doE"),
			new Person("DANiel", "fOX"),
			new Person("eva", "Green"),
			new Person("Eva", "Green"),
			new Person("Jöhn", "Döe"),
			new Person("Ann-a", "Br+own"),
			new Person("Daniel-", "Fox"),
			new Person("Daniel", "Fox"),
			new Person(" Daniel", "Fox"),
			new Person("Daniel ", "Fox"),
			new Person("Anna", "B  Brown"),
			new Person("", "Jones"),
			new Person(null, "Kelly"),
			new Person("Kelly", null),
			new Person(null, "Kelly"),
			new Person("Kelly", null),
			new Person("Dan", "Fox"),
			new Person(null, null),
			new Person("Joh", "Doe"),
			new Person("John", "Doe"),
			new Person("Iris", "Kelly"),
			new Person("Iris", "Kelly"),
			new Person("Frank", "Hill"),
			new Person("Frank", "Jones"),
			null,
			new Person("I", "K"),
			new Person("IsabellaCharlotteSophia", "JohnsonClarkEvans"),
			new Person("Éva", "Gréen"),
			new Person("Иван", "Иванов"),
			new Person("Jo!@#hn", "Do!!@#e"),
			new Person("Bob", "Adams"),
			new Person("Bob", "Clark"));
		System.out.println("Unsorted list: " + xs + ". \n Sorted one " + sortObjects(xs));
		System.out.println("Unsorted list: " + persons1 + ". \n Sorted one " + sortObjects(persons1));
	}

	protected static <T extends Comparable<T>> List<T> sortObjects(List<T> xs) {
		if (xs == null) 
			throw new IllegalArgumentException("The list cannot be null!");

		List<T> copyXs = new ArrayList<T>(xs);
		copyXs.removeAll(Collections.singleton(null));
		final int listSize = copyXs.size();

		mergeSort(copyXs, listSize, 0);

		for (int i = 0; i < xs.size() - listSize; i++) {
			copyXs.add(i, null);
		}

		return copyXs;
	}

	private static <T extends Comparable<T>> int mergeSort(List<T> copyXs, final int listSize, int left) {
		return mergeSort(copyXs, listSize, left, 0, 0, 1);
	}

	private static <T extends Comparable<T>> int mergeSort(List<T> copyXs, final int listSize, int left, int mid, int right, int currSize) {
		if (currSize >= listSize) {
			return 1;
		}
		if (left >= listSize) {
			return mergeSort(copyXs, listSize, 0, 0, 0, currSize *= 2);
		}
		mid = left + currSize;
		if (mid >= listSize) {
			mid = listSize - 1;
		}

		right = left + currSize * 2;
		if (right >= listSize) {
			right = listSize;
		} 

		merge(copyXs, left, mid, right);

		return mergeSort(copyXs, listSize, left += currSize * 2, 0, 0, currSize);
	}

	private static <T extends Comparable<T>> void merge(List<T> copyXs, int left, int mid, int right) {

		List<T> leftList = new ArrayList<T>(copyXs.subList(left, mid));
		List<T> rightList = new ArrayList<T>(copyXs.subList(mid, right));

		int i = 0, j = 0, k = left;
		while (i < leftList.size() && j < rightList.size()) {
			if (leftList.get(i).compareTo(rightList.get(j)) <= 0) {
				copyXs.set(k++, leftList.get(i++));
			} else {
				copyXs.set(k++, rightList.get(j++));
			}
		}

		while (i < leftList.size()) {
			copyXs.set(k++, leftList.get(i++));
		}

		while (j < rightList.size()) {
			copyXs.set(k++, rightList.get(j++));
		}
	}
}
