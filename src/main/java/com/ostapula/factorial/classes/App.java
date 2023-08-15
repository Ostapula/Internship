package com.ostapula.factorial.classes;

public class App 
{
    public static void main( String[] args )
    {
        int n = 5;
        System.out.println("The Non-tail recursion: " + factorialNonTailRecursion(n) + " The Tail-recursion: " + factorialTailRecursion(n));
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
