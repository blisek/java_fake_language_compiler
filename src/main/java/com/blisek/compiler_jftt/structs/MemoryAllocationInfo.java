package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;

public class MemoryAllocationInfo implements AutoCloseable {
	public static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);
	private static int idCounter = 0;
	private final int id;
	private BigInteger startCell;
	private BigInteger size;
	private int useCounter;
	private boolean allocated;
	private BigInteger cellStep;
	
	public MemoryAllocationInfo(int startCell, int size) {
		this(BigInteger.valueOf(startCell), BigInteger.valueOf(size));
	}
	
	public MemoryAllocationInfo(BigInteger startCell, BigInteger size) {
		super();
		this.id = idCounter++;
		this.startCell = startCell;
		this.size = size;
		this.useCounter = 0;
		this.cellStep = BigInteger.ONE;
		this.allocated = false;
	}
	
	public int getId() {
		return id;
	}
	
	public BigInteger getStartCell() {
		return startCell;
	}

	public void setStartCell(BigInteger startCell) {
		this.startCell = startCell;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
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
	
	public boolean isAllocated() {
		return allocated;
	}

	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}
	
	public BigInteger getCellAddress(int index) {
		return getCellAddress(BigInteger.valueOf(index));
	}
	
	public BigInteger getCellAddress(BigInteger index) {
		if(BigInteger.ZERO.equals(index))
			return startCell;
			
		if(size.compareTo(index) <= 0)
			return null;
		
		return startCell.add(cellStep.equals(BigInteger.ONE) ? index : index.multiply(cellStep));
	}
	
	public BigInteger getCellStep() {
		return cellStep;
	}

	public void setCellStep(BigInteger cellStep) {
		this.cellStep = cellStep;
	}

	public void free() {
		setAllocated(false);
	}
	
	@Override
	public void close() {
		free();
	}
}
