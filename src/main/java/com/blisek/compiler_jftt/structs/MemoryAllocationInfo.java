package com.blisek.compiler_jftt.structs;

public class MemoryAllocationInfo implements AutoCloseable {
	private static int idCounter = 0;
	private final int id;
	private int startCell;
	private int size;
	private int useCounter;
	
	public MemoryAllocationInfo(int startCell, int size) {
		super();
		this.id = idCounter++;
		this.startCell = startCell;
		this.size = size;
		this.useCounter = 0;
	}
	
	public int getId() {
		return id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int getStartCell() {
		return startCell;
	}

	public void setStartCell(int startCell) {
		this.startCell = startCell;
	}

	public int getUseCounter() {
		return useCounter;
	}

	public void setUseCounter(int useCounter) {
		this.useCounter = useCounter;
	}
	
	public void increaseUseCounter() {
		++useCounter;
	}
	
	public void decreaseUseCounter() {
		if(useCounter == 0)
			return;
		--useCounter;
	}
	
	public void free() {
		// TODO: implement
	}

	@Override
	public void close() {
		free();		
	}
	
	
}
