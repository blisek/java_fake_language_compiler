package com.blisek.compiler_jftt.context;

public final class UniqueNumbersGenerator {
	private static int counter = 0;

	private UniqueNumbersGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static int nextNumber() {
		return ++counter;
	}
}
