package com.ostapula.maven.classes;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class AppTest 
{   
    @Test
    public void testTwoListsOfIntegersAreEqual()
    {
    	List<Integer> inputList = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
    	List<Integer> expectedList = new ArrayList<Integer>(inputList);
    	expectedList.sort(null);    		
    	assertEquals(expectedList, App.sortIntegers(inputList));
    }
    
    @Test
    public void testSortSortedListOfIntegers()
    {
    	List<Integer> inputList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    	List<Integer> expectedList = new ArrayList<Integer>(inputList);
    	expectedList.sort(null);    		
    	assertEquals(expectedList, App.sortIntegers(inputList));
    }
    
    @Test
    public void testSortTwoIntegerNumbers()
    {
    	List<Integer> inputList = Arrays.asList(2, 1);
    	List<Integer> expectedList = new ArrayList<Integer>(inputList);
    	expectedList.sort(null);    	
    	assertEquals(expectedList, App.sortIntegers(inputList));
    }
    
    @Test
    public void testSortTwoEqualIntegerNumbers()
    {
    	List<Integer> inputList = Arrays.asList(2, 2);
    	List<Integer> expectedList = new ArrayList<Integer>(inputList);
    	expectedList.sort(null);    	
    	assertEquals(expectedList, App.sortIntegers(inputList));
    }
    
    @Test
    public void testSortOneIntegerNumber()
    {
    	List<Integer> inputList = Arrays.asList(0);
    	List<Integer> expectedList = new ArrayList<Integer>(inputList);
    	expectedList.sort(null);    		
    	assertEquals(expectedList, App.sortIntegers(inputList));
    }
    
    @Test
    public void testSortEmptyList()
    {
    	List<Integer> inputList = new ArrayList<Integer>();
    	List<Integer> expectedList = new ArrayList<Integer>(inputList);
    	expectedList.sort(null);    	
    	assertEquals(expectedList, App.sortIntegers(inputList));
    }
    
    @Test
    public void  testGenerateIntegerListSize () {
    	List<Integer> generateList = new ArrayList<Integer>();
    	
    	App.generateInteger(generateList);
    	
    	assertEquals(10, generateList.size());
    }
    
    @Test
    public void testGenerateIntegerValues() {
    	List<Integer> generateList = new ArrayList<Integer>();
    	
    	App.generateInteger(generateList);
    	
    	for (int a : generateList) {
    		assertEquals(true, 0 < a && a < 100);
    	}
    }
}
