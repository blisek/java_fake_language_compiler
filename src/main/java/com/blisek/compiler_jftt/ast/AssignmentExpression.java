package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.Preconditions;
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
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNum = writer.getNextLineNumber();
		
		final Expression initializationExpr = getSecondExpression();
		final Expression valueExp = getFirstExpression();
		
		if(valueExp instanceof VariableValueExpression)
			variableValueExpressionAssignment(ctx, (VariableValueExpression)valueExp, initializationExpr);
		else if(valueExp instanceof ArrayValueExpression)
			arrayValueExpressionAssignment(ctx, (ArrayValueExpression)valueExp, initializationExpr);
		else if(valueExp instanceof ArrayVariableValueExpression)
			arrayVariableValueExpressionAssignment(ctx, (ArrayVariableValueExpression)valueExp, initializationExpr);
		else 
			throw new RuntimeException("Invalid variable reference!");
				
		return writer.getNextLineNumber() - startLineNum;
	}


	private void assignMemoryCellIfNotAssigned(Context ctx, VariableInfo varInfo) {
		if(varInfo.getAssignedMemoryCells() == null) {
			ctx.getMemoryAllocationStrategy().allocateMemoryForVariable(varInfo);
		}
	}
	
	private void variableValueExpressionAssignment(Context ctx, VariableValueExpression variableExpression, Expression initializationExpr) {
		final VariableInfo variable = variableExpression.getVariable();
		
		Preconditions.assureVariableIsDeclared(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		Preconditions.assureNoArrayType(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		Preconditions.assureVariableIsNotReadonly(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		
		assignMemoryCellIfNotAssigned(ctx, variable);
		ctx.increaseLevel();
		initializationExpr.write(ctx, null);
		ctx.decreaseLevel();
		
		final Register resultRegister = ctx.getRegisterById(initializationExpr.getResultRegisterId());
		OperationsHelper.storeRegisterValue(ctx, resultRegister, variable.getAssignedMemoryCells()[0], 
				BigInteger.ZERO);
		variable.setValueAssigned(true);
		setResultRegisterId(-1);
		OperationsHelper.freeRegister(resultRegister);

	}
	
	private void arrayValueExpressionAssignment(Context ctx, ArrayValueExpression valueExpression, Expression initializationExpr) {
		final VariableInfo variable = valueExpression.getVariable();
		final BigInteger index = valueExpression.getIndex();
		
		Preconditions.assureVariableIsDeclared(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		Preconditions.assureNoArrayType(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		Preconditions.assureVariableIsNotReadonly(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		
		assignMemoryCellIfNotAssigned(ctx, variable);
		ctx.increaseLevel();
		initializationExpr.write(ctx, null);
		ctx.decreaseLevel();
		
		final int resultRegisterId = initializationExpr.getResultRegisterId();
		final Register resultRegister = ctx.getRegisterById(resultRegisterId);
		OperationsHelper.storeRegisterValue(ctx, resultRegister, 
				variable.getAssignedMemoryCells()[0], index);
		
		variable.setValueAssigned(true);
		setResultRegisterId(-1);
		OperationsHelper.freeRegister(resultRegister);
	}
	
	private void arrayVariableValueExpressionAssignment(Context ctx, ArrayVariableValueExpression valueExpression,
			Expression initializationExpr) {
		// TODO: obsługa adresowania przy uzyciu zmiennej
	}
}
