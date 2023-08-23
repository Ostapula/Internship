package com.ostapula.factorial;

public class FactorialCalculator {

	public int factorialNonTailRecursion(int n) {
		if (n == 1) {
			return n;
		}
		int result = factorialNonTailRecursion(n - 1);
		return n * result;
	}

	public int factorialTailRecursion(int n) {
		return factorialTailRecursion(n, 1);
	}

	private int factorialTailRecursion(int n, int result) {
		if (n == 0) {
			return result;
		}
		return factorialTailRecursion(n - 1, result * n);
	}
}
