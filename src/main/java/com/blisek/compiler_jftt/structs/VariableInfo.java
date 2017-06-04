package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class VariableInfo {
    private static final String WORKING_COPY_TEMPLATE = "%s_copy_%d";
	private static final Map<String, VariableInfo> _variables = new TreeMap<>();
	private static final Random randomSuffixGenerator = new Random();
	private final String variableName;
	private MemoryAllocationInfo[] assignedMemoryCells;
	private BigInteger value;
	private BigInteger length;
	private boolean variableDeclared;
	private boolean valueAssigned;
	private boolean readonly;
    private VariableInfo origin;
	
	public static VariableInfo of(final String name) {
		VariableInfo varInfo = _variables.get(name);
		if(varInfo == null) {
			varInfo = new VariableInfo(name);
			_variables.put(name, varInfo);
		}
		return varInfo;
	}
	
	public static boolean isVariableDeclared(final String name) {
		return _variables.containsKey(name);
	}
	
	public static boolean registerVariable(final VariableInfo variable) {
		VariableInfo varInfo = _variables.get(variable.getVariableName());
		if(varInfo == null) {
			varInfo = variable;
			_variables.put(variable.getVariableName(), varInfo);
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean unregisterVariable(VariableInfo variable) {
		return _variables.remove(variable.getVariableName()) != null;
	}
	
	public VariableInfo(String varName) {
		this.variableName = varName;
		this.length = BigInteger.ONE;
	}

	public VariableInfo createWorkingCopy() {
        String newName;
        while (isVariableDeclared(newName = generateWorkingCopyName(variableName)))
            ;
        VariableInfo vi = of(newName);
        vi.setOrigin(this);
        return vi;
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

	public BigInteger getLength() {
		return length;
	}

	public void setLength(BigInteger length) {
		this.length = length;
	}

	public boolean isValueAssigned() {
		return valueAssigned;
	}

	public void setValueAssigned(boolean valueAssigned) {
		this.valueAssigned = valueAssigned;
	}
	
	public boolean isVariableDeclared() {
		return variableDeclared;
	}

	public void setVariableDeclared(boolean variableDeclared) {
		this.variableDeclared = variableDeclared;
	}

	public String getVariableName() {
		return variableName;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

    public VariableInfo getOrigin() {
        return origin;
    }

    public void setOrigin(VariableInfo origin) {
        this.origin = origin;
    }

    private static String generateWorkingCopyName(final String varName) {
        return String.format(WORKING_COPY_TEMPLATE, varName, randomSuffixGenerator.nextInt());
    }
}
