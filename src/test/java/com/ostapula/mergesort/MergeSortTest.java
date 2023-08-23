package com.ostapula.mergesort;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;


public class MergeSortTest 
{
	@Test
	public void testUnorderedListBecomesOrdered()
	{
		List<Integer> inputList = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);
		expectedList.sort(null);
		assertEquals(expectedList, MergeSort.sortIntegers(inputList));
	}

	@Test
	public void testSortingOrderedListRemainsOrdered()
	{
		List<Integer> inputList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);
		expectedList.sort(null);    		
		assertEquals(expectedList, MergeSort.sortIntegers(inputList));
	}

	@Test
	public void testListContentRemainsUnchagedAfterSorting()
	{
		List<Integer> inputList = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
		List<Integer> expectedList = new ArrayList<Integer>(inputList);	
		List<Integer> copyOfInputList = new ArrayList<Integer>(inputList);
		expectedList.sort(null);    		
		assertEquals(copyOfInputList, inputList);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSortingEmptyListThrowsException() {
		List<Integer> inputList = new ArrayList<Integer>();
		MergeSort.sortIntegers(inputList);        
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
		assertEquals(expectedList, MergeSort.sortIntegers(inputList));
	}
}
