package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.helpers.Preconditions;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Instructions;
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
		
		if(valueExp.getClass() == VariableValueExpression.class)
			variableValueExpressionAssignment(ctx, (VariableValueExpression)valueExp, initializationExpr);
		else if(valueExp.getClass() == ArrayValueExpression.class)
			arrayValueExpressionAssignment(ctx, (ArrayValueExpression)valueExp, initializationExpr);
		else if(valueExp.getClass() == ArrayVariableValueExpression.class)
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
		
		final int startLine = getLine(getStart()), startColumn = getLine(getStart()),
				endLine = getLine(getEnd()), endColumn = getColumn(getEnd());
		
		Preconditions.assureVariableIsDeclared(variable, startLine, startColumn, endLine, endColumn);
		Preconditions.assureArrayType(variable, startLine, startColumn, endLine, endColumn);
		Preconditions.assureIndexInRange(variable, index, startLine, startColumn, endLine, endColumn);
		
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
		final VariableInfo variable = valueExpression.getVariable(), 
				indexVar = valueExpression.getIndexVar();
		
		final int startLine = getLine(getStart()), startColumn = getLine(getStart()),
				endLine = getLine(getEnd()), endColumn = getColumn(getEnd());
		
		Preconditions.assureVariableIsDeclared(variable, startLine, startColumn, endLine, endColumn);
		Preconditions.assureVariableIsDeclared(indexVar, startLine, startColumn, endLine, endColumn);
		Preconditions.assureArrayType(variable, startLine, startColumn, endLine, endColumn);
		Preconditions.assureNoArrayType(indexVar, startLine, startColumn, endLine, endColumn);
		Preconditions.assureValueAssigned(indexVar, startLine, startColumn, endLine, endColumn);
		
		assignMemoryCellIfNotAssigned(ctx, variable);
		ctx.increaseLevel();
		initializationExpr.write(ctx, null);
		ctx.decreaseLevel();
		
		final int resultRegisterId = initializationExpr.getResultRegisterId();
		final Register resultRegister = ctx.getRegisterById(resultRegisterId);
		
		final MemoryAllocationInfo[] memory = new MemoryAllocationInfo[] {
				// niepotrzebny jest ciągły blok pamięci więc pojedyncze
				// komórki są rezerwowane osobno
				ctx.getMemoryAllocationStrategy().allocateTemporaryMemory()[0]
				, ctx.getMemoryAllocationStrategy().allocateTemporaryMemory()[0]
		};
		
		try(Deallocator _memoryDeallocator = Deallocator.of(memory)) {
			OperationsHelper.storeRegisterValue(ctx, resultRegister, memory[0], BigInteger.ZERO);
//			OperationsHelper.loadRegister(ctx, resultRegister, indexVar.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
//			OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), variable.getAssignedMemoryCells()[0].getStartCell());
			OperationsHelper.setRegisterValue(ctx, resultRegister, variable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), indexVar.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			final Writer writer = ctx.getWriter();
			writer.write(OperationsHelper.genInstruction(Instructions.ADD_i, resultRegister));
			OperationsHelper.storeRegisterValue(ctx, resultRegister, memory[1], BigInteger.ZERO);
			OperationsHelper.loadRegister(ctx, resultRegister, memory[0].getCellAddress(BigInteger.ZERO));
			OperationsHelper.loadRegister(ctx, ctx.getHelperRegister(), memory[1].getCellAddress(BigInteger.ZERO));
			writer.write(OperationsHelper.genInstruction(Instructions.STORE_i, resultRegister));
		}
		
		// TODO: uwzględnić poszczególne komórki
		variable.setValueAssigned(true);
		setResultRegisterId(-1);
		OperationsHelper.freeRegister(resultRegister);
	}
}
