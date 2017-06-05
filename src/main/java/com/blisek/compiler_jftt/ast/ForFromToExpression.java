package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.helpers.Preconditions;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class ForFromToExpression extends SingleExpression {
	private String variableName;
	private ValueExpression fromVe, toVe;

	public ForFromToExpression(String variableName, ValueExpression fromValue, ValueExpression toValue,
			Expression expression) {
		super(expression);
		this.variableName = variableName;
        VariableInfo.unregisterVariable(variableName);
		this.fromVe = fromValue;
		this.toVe = toValue;
	}

	public ForFromToExpression(int label, String variableName, ValueExpression fromValue, ValueExpression toValue,
			Expression expression) {
		super(label, expression);
		this.variableName = variableName;
        VariableInfo.unregisterVariable(variableName);
		this.fromVe = fromValue;
		this.toVe = toValue;
	}

	@SuppressWarnings("unused")
	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNum = writer.getNextLineNumber();
		final int startLine = getLine(getStart()), startColumn = getColumn(getStart()), endLine = getLine(getEnd()),
				endColumn = getColumn(getEnd());
		Preconditions.assureVariableIsNotDeclared(variableName, startLine, startColumn, endLine, endColumn);
		VariableInfo localVariable = setUpLocalVariable(ctx);
		RegisterReservationInfo[] reservedRegisters = OperationsHelper.getRegisters(ctx, 1);
		ValueExpression toValueCopy = toVe.createWorkingCopy(ctx);

		try (Deallocator _registersDeallocator = Deallocator.of(reservedRegisters)) {
			ctx.increaseLevel();
			fromVe.write(ctx, reservedRegisters[0]);
			ctx.decreaseLevel();

			final Register reservedRegister = reservedRegisters[0].getRegister();
			OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(),
					localVariable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			writer.write(OperationsHelper.genInstruction(Instructions.STORE_i, reservedRegister));
			localVariable.setValueAssigned(true);

            VariableValueExpression localVariableExpression = new VariableValueExpression(localVariable);
            localVariableExpression.setIgnoreSafetyChecks(true);
			ctx.increaseLevel();
			new WhileExpression(new LessEqualsConditionExpression(localVariableExpression, toValueCopy),
					new Expression(getExpression(), new CounterIncreaserExpression(localVariable, reservedRegister)))
							.write(ctx, null);
			ctx.decreaseLevel();
		}

		VariableInfo.unregisterVariable(localVariable);
		localVariable.getAssignedMemoryCells()[0].free();
		return writer.getNextLineNumber() - startLineNum;
	}

	private VariableInfo setUpLocalVariable(Context ctx) {
		VariableInfo localVariable = VariableInfo.of(variableName);
		localVariable.setVariableDeclared(true);
		localVariable.setReadonly(true);
		ctx.getMemoryAllocationStrategy().allocateMemoryForVariable(localVariable);
		return localVariable;
	}

	private class CounterIncreaserExpression extends Expression {
		private Register workingRegister;
		private VariableInfo localVariable;

		public CounterIncreaserExpression(VariableInfo localVariable, Register workingRegister) {
			this.workingRegister = workingRegister;
			this.localVariable = localVariable;
		}

		@Override
		public int write(Context ctx, Object additionalData) {
			final Writer writer = ctx.getWriter();
			final int startLineNum = writer.getNextLineNumber();

			OperationsHelper.loadRegister(ctx, workingRegister,
					localVariable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			writer.write(OperationsHelper.genInstruction(Instructions.INC_i, workingRegister));
			writer.write(OperationsHelper.genInstruction(Instructions.STORE_i, workingRegister));

			return writer.getNextLineNumber() - startLineNum;
		}

	}

}
