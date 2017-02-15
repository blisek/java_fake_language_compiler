package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Writer;

public class AssignmentExpression extends BiExpression {

	public AssignmentExpression(Expression identifier, Expression expr2) {
		super(identifier, expr2);
	}

	public AssignmentExpression(int label, Expression identifier, Expression expr2) {
		super(label, identifier, expr2);
	}

	@Override
	public int write(Writer writer_, Context ctx) {
		final Writer writer = ctx.getWriter();
		final int startLineNum = writer.getNextLineNumber();
		
		final Expression initializationExpr = getSecondExpression();
		final VariableValueExpression variableExpression = (VariableValueExpression)getFirstExpression();
		final VariableInfo variable = variableExpression.getVariable();
		assignMemoryCellIfNotAssigned(ctx, variable);
		
		initializationExpr.write(writer, ctx);
		final int resultRegister = initializationExpr.getResultRegisterId();
		OperationsHelper.storeRegisterValue(ctx, ctx.getRegisterById(resultRegister), 
				variable.getAssignedMemoryCells()[0], BigInteger.ZERO);
		variable.setValueAssigned(true);
		setResultRegisterId(resultRegister);
		
		return writer.getNextLineNumber() - startLineNum;
	}

	private void assignMemoryCellIfNotAssigned(Context ctx, VariableInfo varInfo) {
		if(varInfo.getAssignedMemoryCells() == null) {
			ctx.getMemoryAllocationStrategy().allocateMemoryForVariable(varInfo);
		}
	}
	
}
