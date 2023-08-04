package com.ostapula.maven.classes;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import org.junit.Before;

public class AppTest 
{
	private List<Integer> sx;

	@Before
	public void setUp() {
		sx = Arrays.asList(23, 12, 1, 5, 87, 56, 34, 9, 20, 8);
	}
	
    @Test
    public void twoListsAreEqual()
    {
    	List<Integer> copySx = new ArrayList<Integer>(sx);
    	copySx.sort(null);    	
    	
    	assertEquals(App.sortIntegers(sx), copySx);
    }
}
