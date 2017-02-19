package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class NotEqualsConditionExpression extends EqualsConditionExpression {

	public NotEqualsConditionExpression(ValueExpression expr1, ValueExpression expr2) {
		super(expr1, expr2);
	}

	public NotEqualsConditionExpression(int label, ValueExpression expr1, ValueExpression expr2) {
		super(label, expr1, expr2);
	}

	@Override
	protected void writeComparison(Context ctx, int[] jumpLabels, RegisterReservationInfo[] reservedRegisters,
			MemoryAllocationInfo[] allocatedCells) {
		final Writer writer = ctx.getWriter();
		final ValueExpression leftExp = getFirstExpression(), rightExp = getSecondExpression();
		final int jumpTrue = jumpLabels[ConditionExpression.JUMP_TRUE_INDEX],
				jumpFalse = jumpLabels[ConditionExpression.JUMP_FALSE_INDEX];
		
		leftExp.write(ctx, reservedRegisters[0]);
		rightExp.write(ctx, reservedRegisters[1]);
		
		final Register firstReg = reservedRegisters[0].getRegister(),
				secondReg = reservedRegisters[1].getRegister();
		
		OperationsHelper.storeRegisterValue(ctx, firstReg, allocatedCells[0], BigInteger.ZERO);
		writer.write(OperationsHelper.genInstruction(Instructions.INC_i, secondReg));
		writer.write(OperationsHelper.genInstruction(Instructions.SUB_i, secondReg));
		writer.write(OperationsHelper.genInstruction(Instructions.JZERO_i_j, secondReg), -1, jumpTrue);
		writer.write(OperationsHelper.genInstruction(Instructions.DEC_i, secondReg));
		writer.write(OperationsHelper.genInstruction(Instructions.JZERO_i_j, secondReg), -1, jumpFalse);
		writer.write(Instructions.JUMP_i, -1, jumpTrue);
	}

	
}
