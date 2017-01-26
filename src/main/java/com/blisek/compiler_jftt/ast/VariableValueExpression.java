package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;

public class VariableValueExpression extends ValueExpression {
	private VariableInfo variable;

	public VariableValueExpression(VariableInfo var) {
		super(null);
		this.variable = var;
	}

	public VariableValueExpression(int label, VariableInfo var) {
		super(label, null);
		this.variable = var;
	}

	@Override
	protected void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister) {
		BigInteger val = variable.getValue();
		MemoryAllocationInfo mai = variable.getAssignedMemoryCell();
		int cellAddress = mai.getCellAddress(0);
		if(val == null) {
			OperationsHelper.loadRegister(ctx, destinationRegister, cellAddress);
		}
		else {
			String binaryString = val.toString(2);
			final int loadCost = 10 + mai.getCellAddress(0);
			if(OperationsHelper.calculateInitializationCost(binaryString) < loadCost) {
				OperationsHelper.setRegisterValue(ctx, destinationRegister, binaryString, val);
			}
			else {
				OperationsHelper.loadRegister(ctx, destinationRegister, cellAddress);
			}
		}
	}

	public VariableInfo getVariable() {
		return variable;
	}

	
}
