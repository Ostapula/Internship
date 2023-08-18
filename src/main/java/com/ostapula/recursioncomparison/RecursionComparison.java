package com.ostapula.recursioncomparison;

public class RecursionComparison 
{
	private RecursionComparison() {
		throw new AssertionError();
	}
	
    public static void main( String[] args )
    {
    	int nonTailMax = maxIntNonTailRecursion();
    	int tailMax = maxIntTailRecursion();
    	
    	if (nonTailMax >= tailMax) {
    		System.out.println("The function IS NOT benefiting from tail recursion: non-tail recursion: " + nonTailMax + " >= tail recursion: " + tailMax);
    	} else {
    		System.out.println("The function IS benefiting from tail recursion: non-tail recursion: " + nonTailMax + " < tail recursion: " + tailMax);
    	}   	
    }
    
    public static int maxIntNonTailRecursion() {
    	int n = 1;
    	while(true) {
    		try {
    			factorialNonTailRecursion(n);
    			n++;
    		} catch (StackOverflowError e) {
    			return n - 1;
    		}
    	}
    }
    
    public static int maxIntTailRecursion() {
    	int n = 1;
    	while(true) {
    		try {
    			factorialTailRecursion(n);
    			n++;
    		} catch (StackOverflowError e) {
    			return n - 1;
    		}
    	}
    }
    
    public static int factorialNonTailRecursion(int n) {
    	if (n == 1) {
    		return n;
    	}
    	int sum = factorialNonTailRecursion(n - 1);
    	return n *= sum;
    }
    
    public static int factorialTailRecursion(int n) {
    	return factorialTailRecursion(n, 1);
    }
    
    public static int factorialTailRecursion(int n, int sum) {
    	if (n == 0) {
    		return sum;
    	}
    	return factorialTailRecursion(n - 1, sum *= n);
    }
}
