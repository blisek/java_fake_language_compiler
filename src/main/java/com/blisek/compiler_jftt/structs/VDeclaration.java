package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;

public final class VDeclaration {
	private final String varName;
	private final BigInteger length;
	private final boolean array;
	
	public VDeclaration(String varName, BigInteger length, boolean array) {
		super();
		this.varName = varName;
		this.length = length;
		this.array = array;
	}
	
	public static VDeclaration of(String name) {
		return new VDeclaration(name, BigInteger.ONE, false);
	}
	
	public static VDeclaration arrayOf(String name, BigInteger length) {
		return new VDeclaration(name, length, true);
	}

	public String getVarName() {
		return varName;
	}

	public BigInteger getLength() {
		return length;
	}

	public boolean isArray() {
		return array;
	}
	
}
