package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;

public class VariableInfo {
	private final String variableName;
	private MemoryAllocationInfo assignedMemoryCell;
	private BigInteger value;
	
	public VariableInfo(String varName) {
		this.variableName = varName;
	}
	
	public MemoryAllocationInfo getAssignedMemoryCell() {
		return assignedMemoryCell;
	}

	public void setAssignedMemoryCell(MemoryAllocationInfo assignedMemoryCell) {
		this.assignedMemoryCell = assignedMemoryCell;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	
}
