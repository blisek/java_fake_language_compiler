package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;

public class NumberValueExpression extends ValueExpression {
	private BigInteger number;

	public NumberValueExpression(BigInteger bi) {
		super(null);
		this.number = bi;
	}

	public NumberValueExpression(int label, BigInteger bi) {
		super(label, null);
		this.number = bi;
	}

	@Override
	protected void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister) {
		OperationsHelper.setRegisterValue(ctx, destinationRegister, number);
	}

	public BigInteger getNumber() {
		return number;
	}
	
}
