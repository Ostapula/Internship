package com.ostapula.maven.classes;

import java.util.*;

public class App 
{
	
    public static void main( String[] args )
    {
    	List<Integer> xs = new ArrayList<Integer>();
    	generateInteger(xs);
    	System.out.println("Not sorted list " + xs + ". Sorted list " + sortIntegers(xs));
    }
    
    public static void generateInteger(List<Integer> xs) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            xs.add(random.nextInt(1, 100)); 
        }
    }
	
	public static List<Integer> sortIntegers(List<Integer> xs) {
		List<Integer> copyXs = new ArrayList<Integer>(xs);
    	int listSize = copyXs.size();

        for (int currSize = 1; currSize < listSize; currSize *= 2) {
            for (int left = 0; left < listSize; left += currSize * 2) { 
            	
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
            }
        }
        return copyXs;
	}
}
