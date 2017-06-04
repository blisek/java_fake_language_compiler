package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.exceptions.UsedUndeclaredVariableCompilationException;
import com.blisek.compiler_jftt.exceptions.UsedUninitialisedVariableCompilationException;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;

public class VariableValueExpression extends ValueExpression {
	private VariableInfo variable;
	private BigInteger index = BigInteger.ZERO;

	public VariableValueExpression(VariableInfo var) {
		super(null);
		this.variable = var;
	}

	public VariableValueExpression(int label, VariableInfo var) {
		super(label, null);
		this.variable = var;
	}
	
	public VariableValueExpression(VariableInfo var, BigInteger index) {
		this(var);
		this.index = index;
	}
	
	public VariableValueExpression(int label, VariableInfo var, BigInteger index) {
		this(label, var);
		this.index = index;
	}

	@Override
	public VariableValueExpression createWorkingCopy(Context ctx) {
        VariableInfo viCopy = OperationsHelper.cloneVariableInfoCell(ctx, variable, index);
        viCopy.setVariableDeclared(true);
        viCopy.setValueAssigned(true);
		return new VariableValueExpression(getLabel(), viCopy, index);
	}

	@Override
	protected void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister) {
		BigInteger val = variable.getValue();
		MemoryAllocationInfo[] mai = variable.getAssignedMemoryCells();
		
		// TODO: sprawdzanie czy zmienna została zainicjalizowana
		assureVariableDeclared();
		assureValueAssigned();
		
		BigInteger cellAddress = mai[0].getCellAddress(BigInteger.ZERO);
		if(val == null) {
			OperationsHelper.loadRegister(ctx, destinationRegister, cellAddress);
		}
		else {
			String binaryString = val.toString(2);
			final BigInteger loadCost = BigInteger.valueOf(10).add(mai[0].getCellAddress(BigInteger.ZERO));
			if(BigInteger.valueOf(OperationsHelper.calculateInitializationCost(binaryString)).compareTo(loadCost) < 0) {
				OperationsHelper.setRegisterValue(ctx, destinationRegister, binaryString, val);
			}
			else {
				OperationsHelper.loadRegister(ctx, destinationRegister, cellAddress);
			}
		}
	}
	
	private void assureVariableDeclared() {
		if(!variable.isVariableDeclared()) {
			String msg = "[%s-%s] Undeclared variable \"%s\" used.";
			String startPos = String.format("%d,%d", getLine(start), getColumn(start));
			String endPos = String.format("%d,%d", getLine(end), getColumn(end));
			throw new UsedUndeclaredVariableCompilationException(String.format(msg, startPos, endPos, variable.getVariableName()));
		}
	}
	
	private void assureValueAssigned() {
		if(variable.getAssignedMemoryCells() == null || !variable.isValueAssigned()) {
			String msg = "[%s-%s] Use of uninitialised variable \"%s\"!";
			String startPos = String.format("%d,%d", getLine(start), getColumn(start));
			String endPos = String.format("%d,%d", getLine(end), getColumn(end));
			throw new UsedUninitialisedVariableCompilationException(String.format(msg, startPos, endPos, variable.getVariableName()));
		}
	}

	public VariableInfo getVariable() {
		return variable;
	}

	public BigInteger getIndex() {
		return index;
	}

	public void setIndex(BigInteger index) {
		this.index = index;
	}

}
