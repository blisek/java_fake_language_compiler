package com.blisek.compiler_jftt.context;

public class MemoryAllocationInfo {
	private static int idCounter = 0;
	private final int id;
	private int size;
	private int useCounter;
	
	public MemoryAllocationInfo(int size) {
		super();
		this.id = idCounter++;
		this.size = size;
		this.useCounter = 0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getUseCounter() {
		return useCounter;
	}

	public void setUseCounter(int useCounter) {
		this.useCounter = useCounter;
	}
	
	
}
