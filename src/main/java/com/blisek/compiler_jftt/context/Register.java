package com.blisek.compiler_jftt.context;

import java.math.BigInteger;

public class Register {
	private final int id;
	private boolean taken;
	private int usedByLevel;
	private boolean isCounter, isHelpRegister;
	private BigInteger value;
	
	public Register(int id) {
		super();
		this.id = id;
		this.taken = false;
		this.usedByLevel = -1;
		this.isCounter = false;
		this.isHelpRegister = false;
	}
	
	public Register(int id, long value) {
		this(id);
		this.value = BigInteger.valueOf(value);
	}
	
	public Register(int id, BigInteger integer) {
		this(id);
		this.value = integer;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public int getUsedByLevel() {
		return usedByLevel;
	}

	public void setUsedByLevel(int usedByLevel) {
		this.usedByLevel = usedByLevel;
	}

	public int getId() {
		return id;
	}

	public boolean isCounter() {
		return isCounter;
	}

	public void setCounter(boolean isCounter) {
		this.isCounter = isCounter;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public boolean isHelpRegister() {
		return isHelpRegister;
	}

	public void setHelpRegister(boolean isHelpRegister) {
		this.isHelpRegister = isHelpRegister;
	}
	
	
	
}
