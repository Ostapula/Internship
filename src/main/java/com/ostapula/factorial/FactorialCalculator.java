package com.ostapula.factorial;

public class FactorialCalculator {

	private FactorialCalculator() {
		throw new AssertionError();
	}

	public  static int factorialNonTailRecursion(int n) {
		if (n == 1) {
			return n;
		}
		int result = factorialNonTailRecursion(n - 1);
		return n * result;
	}

	public static int factorialTailRecursion(int n) {
		return factorialTailRecursion(n, 1);
	}

	private static int factorialTailRecursion(int n, int result) {
		if (n == 0) {
			return result;
		}
		return factorialTailRecursion(n - 1, result * n);
	}
}
