package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class ReadExpression extends SingleExpression {

	public ReadExpression(Expression expression) {
		super(expression);
	}

	public ReadExpression(int label, Expression expression) {
		super(label, expression);
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNumber = writer.getNextLineNumber();
		
		final VariableValueExpression variableExpr = (VariableValueExpression)getExpression();
		final VariableInfo variable = variableExpr.getVariable();
		
		if(variable.getAssignedMemoryCells() == null)
			ctx.getMemoryAllocationStrategy().allocateMemoryForVariable(variable);
		
		final BigInteger variableIndex = variableExpr.getIndex();
		final BigInteger variableCell = 
				variable.getAssignedMemoryCells()[0].getCellAddress(variableIndex);
		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), variableCell);
		
		final RegisterReservationInfo reservedReg = 
				ctx.getRegistryManagementStrategy().reserveRegister(ctx, null);
		
		try(Deallocator _deallocator = Deallocator.of(reservedReg)) {
			writer.write(OperationsHelper.genInstruction(
					Instructions.GET_i, reservedReg.getRegister()));
			writer.write(OperationsHelper.genInstruction(
					Instructions.STORE_i, reservedReg.getRegister()));
		}
		
		variable.setValueAssigned(true);
		return writer.getNextLineNumber() - startLineNumber;
	}

}
