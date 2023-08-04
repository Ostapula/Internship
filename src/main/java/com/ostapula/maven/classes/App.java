package com.ostapula.maven.classes;

import java.util.*;

public class App 
{
	public static Scanner scanner = new Scanner(System.in);
	
    public static void main( String[] args )
    {
    	List<Integer> xs = new ArrayList<Integer>();
    	inputIntegers(xs);
    	System.out.println("Not sorted list " + xs + ". Sorted list " + sortIntegers(xs));
    }
    
    public static void inputIntegers(List<Integer> xs) {
    	System.out.println("To stop typing, enter 0: ");
		int input = -1;
		while (input != 0) {
			if (scanner.hasNextInt()) {
				input = scanner.nextInt();
				xs.add(input);
			} else {
				System.out.println("Invalid input!");
				scanner.next();
			}
		}
	}
	
	public static List<Integer> sortIntegers(List<Integer> xs) {
		List<Integer> copyXs = new ArrayList<Integer>(xs);
		mergeSort(copyXs);
		return copyXs;
	}

	public static void mergeSort(List<Integer> copyXs) {
		int lengthList = copyXs.size();
		
		if (lengthList > 1) {
			int mid = lengthList / 2;
			
			List<Integer> leftHalf = new ArrayList<Integer>(copyXs.subList(0, mid));
			List<Integer> rightHalf = new ArrayList<Integer>(copyXs.subList(mid, lengthList));
			
			mergeSort(leftHalf);
			mergeSort(rightHalf);
			merge(copyXs, leftHalf, rightHalf);	
		}
	}

	
	public static void merge(List<Integer> copyXs, List<Integer> leftHalf, List<Integer> rightHalf) {
		int leftSize = leftHalf.size();
		int rightSize = rightHalf.size();
		
		int i = 0, j = 0, k = 0;
		
		while(i < leftSize && j < rightSize) {
			if(leftHalf.get(i) <= rightHalf.get(j)) {
				copyXs.set(k, leftHalf.get(i));
				i++;
			} else {
				copyXs.set(k, rightHalf.get(j));
				j++;
			}
			k++;
		}
		
		while (i < leftSize) {
			copyXs.set(k, leftHalf.get(i));
			i++;
			k++;
		}
		while (j < rightSize) {
			copyXs.set(k, rightHalf.get(j));
			j++;
			k++;
		}
	}
	
}
