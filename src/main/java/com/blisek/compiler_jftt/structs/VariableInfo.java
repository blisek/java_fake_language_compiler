package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class VariableInfo {
	private static final Map<String, VariableInfo> _variables = new TreeMap<>();
	private final String variableName;
	private MemoryAllocationInfo[] assignedMemoryCells;
	private BigInteger value;
	private int length;
	private boolean valueAssigned;
	
	public static VariableInfo of(String name) {
		VariableInfo varInfo = _variables.get(name);
		if(varInfo == null) {
			varInfo = new VariableInfo(name);
			_variables.put(name, varInfo);
		}
		return varInfo;
	}
	
	protected VariableInfo(String varName) {
		this.variableName = varName;
		this.length = 1;
	}
	
	public MemoryAllocationInfo[] getAssignedMemoryCells() {
		return assignedMemoryCells;
	}

	public void setAssignedMemoryCells(MemoryAllocationInfo[] assignedMemoryCells) {
		this.assignedMemoryCells = assignedMemoryCells;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isValueAssigned() {
		return valueAssigned;
	}

	public void setValueAssigned(boolean valueAssigned) {
		this.valueAssigned = valueAssigned;
	}

	public String getVariableName() {
		return variableName;
	}
	
}
