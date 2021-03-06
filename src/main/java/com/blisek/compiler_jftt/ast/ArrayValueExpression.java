package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.helpers.Preconditions;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Instructions;

public class ArrayValueExpression extends ValueExpression {
	private VariableInfo variable;
	private BigInteger index;

	public ArrayValueExpression(VariableInfo variable, BigInteger index) {
		super(null);
		this.variable = variable;
		this.index = index;
	}

	public ArrayValueExpression(int label, VariableInfo variable, BigInteger index) {
		super(label, null);
		this.variable = variable;
		this.index = index;
	}

	@Override
	public ValueExpression createWorkingCopy(Context ctx) {
		checkPreconditions();
		VariableInfo vi = OperationsHelper.cloneVariableInfoCell(ctx, getVariable(), getIndex());
        vi.setVariableDeclared(true);
        vi.setValueAssigned(true);
//		return new ArrayValueExpression(getLabel(), vi, BigInteger.ZERO);
        return new VariableValueExpression(vi, BigInteger.ZERO);
	}

	@Override
	protected void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister) {
		checkPreconditions();

		OperationsHelper.setRegisterValue(ctx, addressRegister,
				variable.getAssignedMemoryCells()[0].getCellAddress(index));
		ctx.getWriter().write(OperationsHelper.genInstruction(Instructions.LOAD_i, destinationRegister));

	}

	public VariableInfo getVariable() {
		return variable;
	}

	public void setVariable(VariableInfo variable) {
		this.variable = variable;
	}

	public BigInteger getIndex() {
		return index;
	}

	public void setIndex(BigInteger index) {
		this.index = index;
	}

	private void checkPreconditions() {
		Preconditions.assureNoArrayType(variable, getLine(start), getColumn(start), getLine(end), getColumn(end), true);
		Preconditions.assureVariableIsDeclared(variable, getLine(start), getColumn(start), getLine(end),
				getColumn(end));
		Preconditions.assureIndexInRange(variable, index, getLine(start), getColumn(start), getLine(end),
				getColumn(end));
	}
}
