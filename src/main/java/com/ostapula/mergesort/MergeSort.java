package com.ostapula.mergesort;

import java.util.*;

class MergeSort {
	
	private MergeSort() {
		throw new AssertionError();
	}
	
	private static int counter = 0;

	public static void main( String[] args ) {
		List<Integer> xs = new ArrayList<Integer>();
		generateInteger(xs); 
		System.out.println("Not sorted list " + xs + ". Sorted list " + sortIntegers(xs));
	}

	private static void generateInteger(List<Integer> xs) {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			xs.add(random.nextInt(1 ,100));
		}
	}

	protected static List<Integer> sortIntegers(List<Integer> xs) {
		if (xs == null) {
			throw new IllegalArgumentException("The list cannot be null!");
		} else if(xs.isEmpty()) {
			return xs;
		}

		List<Integer> copyXs = new ArrayList<Integer>(xs);
		if (copyXs.contains(null)) {
			for (int i = 0; i < copyXs.size(); i++) {
				if(copyXs.get(i) == null) {
					copyXs.remove(i);
					counter++;
					i--;
				}
			}
		}

		int listSize = copyXs.size();
		int left = 0;
		mergeSort(copyXs, listSize, left);

		for (int i = 0; i < counter; i++) {
			copyXs.add(i, null);
		}
		return copyXs;
	}
	
	private static int mergeSort(List<Integer> copyXs, int listSize, int left) {
		return mergeSort(copyXs, listSize, left, 0, 0, 1);
	}

	private static int mergeSort(List<Integer> copyXs, int listSize, int left, int mid, int right, int currSize) {
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

	private static void merge(List<Integer> copyXs, int left, int mid, int right) {

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
	}
}
