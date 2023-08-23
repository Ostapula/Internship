package com.ostapula.recursioncomparison;

import com.ostapula.factorial.FactorialCalculator;

public class RecursionComparison {
 
	private RecursionComparison() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		FactorialCalculator factorialCalculator = new FactorialCalculator();
		
		final int nonTailMax = maxIntNonTailRecursion(1, factorialCalculator);
		final int tailMax = maxIntTailRecursion(1, factorialCalculator);

		if (nonTailMax >= tailMax) {
			System.out.println("The function IS NOT benefiting from tail recursion: non-tail recursion: " + nonTailMax + " >= tail recursion: " + tailMax);
		} else {
			System.out.println("The function IS benefiting from tail recursion: non-tail recursion: " + nonTailMax + " < tail recursion: " + tailMax);
		}
	}

	private static int maxIntNonTailRecursion(int n, FactorialCalculator factorialCalculator) {
		while(true) {
			try {
				factorialCalculator.factorialNonTailRecursion(n++);
			} catch (StackOverflowError e) {
				return n - 1;
			}
		}
	}

	private static int maxIntTailRecursion(int n, FactorialCalculator factorialCalculator) {
		while(true) {
			try {
				factorialCalculator.factorialTailRecursion(n++);
			} catch (StackOverflowError e) {
				return n - 1;
			}
		}
	}
}
