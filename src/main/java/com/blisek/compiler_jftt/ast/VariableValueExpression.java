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
		MemoryAllocationInfo[] mai = variable.getAssignedMemoryCells();
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

	public VariableInfo getVariable() {
		return variable;
	}

	
}
