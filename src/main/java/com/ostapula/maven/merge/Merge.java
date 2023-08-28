package com.ostapula.maven.merge;

import java.util.*;

public class Merge {

	private Merge() {
		throw new AssertionError();
	}

	public static void main( String[] args ) {
		List<Integer> xs = new ArrayList<Integer>();
		generateInteger(xs);
		System.out.println("Not sorted list " + xs + ". Sorted list " + sortIntegers(xs));
	}

	private static void generateInteger(List<Integer> xs) {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			xs.add(random.nextInt(1, 100)); 
		}
	}

	protected static List<Integer> sortIntegers(List<Integer> xs) {
		if (xs == null) {
			throw new IllegalArgumentException("The list cannot be null!");
		}else if (xs.isEmpty()) {
			return xs;
		}

		List<Integer> copyXs = new ArrayList<Integer>(xs);
		copyXs.removeAll(Collections.singleton(null));
		int listSize = copyXs.size();

		merge(copyXs, listSize, 1, 0);

		for (int i = 0; i < xs.size() - listSize; i++) {
			copyXs.add(i, null);
		}

		return copyXs;
	}

	private static void merge(List<Integer> copyXs, int listSize, int currSize, int left) {
		int mid = left + currSize;
		if (mid >= listSize) {
			mid = listSize - 1;
		}

		int right = left + currSize * 2;
		if (right >= listSize) {
			right = listSize;
		}

		List<Integer> leftList = new ArrayList<Integer>(copyXs.subList(left, mid));
		List<Integer> rightList = new ArrayList<Integer>(copyXs.subList(mid, right));

		int i = 0, j = 0, k = left;
		while (i < leftList.size() && j < rightList.size()) {
			if (leftList.get(i) <= rightList.get(j)) {
				copyXs.set(k, leftList.get(i));
				i++;
			} else {
				copyXs.set(k, rightList.get(j));
				j++;
			}
			k++;
		}

		while (i < leftList.size()) {
			copyXs.set(k, leftList.get(i));
			i++;
			k++;
		}

		while (j < rightList.size()) {
			copyXs.set(k, rightList.get(j));
			j++;
			k++;
		}

		left += currSize * 2;
		if (left < listSize) {
			merge(copyXs, listSize, currSize, left);
		} else if (left >= listSize && currSize <= listSize) {
			merge(copyXs, listSize, currSize *= 2, 0);
		}
	}
}
