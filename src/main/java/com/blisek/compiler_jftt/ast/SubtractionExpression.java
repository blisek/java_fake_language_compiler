package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class SubtractionExpression extends BiExpression {

	public SubtractionExpression(Expression expr1, Expression expr2) {
		super(expr1, expr2);
	}

	public SubtractionExpression(int label, Expression expr1, Expression expr2) {
		super(label, expr1, expr2);
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLine = writer.getNextLineNumber();
		final RegisterReservationInfo[] registers = OperationsHelper.getRegisters(ctx, 2);
		final Expression secondExp = getSecondExpression();

		ctx.increaseLevel();
		secondExp.write(ctx, registers[0]);
		ctx.decreaseLevel();

		final MemoryAllocationInfo[] allocatedMemory = ctx.getMemoryAllocationStrategy().allocateTemporaryMemory();
		try (Deallocator _registerDeallocator = Deallocator.of(registers[0])) {
			try (Deallocator _memoryDeallocator = Deallocator.of(allocatedMemory)) {
				final MemoryAllocationInfo temporaryAlloc = allocatedMemory[0];
				final Register resultRegister = registers[1].getRegister();
				final Expression firstExp = getFirstExpression();

				OperationsHelper.storeRegisterValue(ctx, registers[0].getRegister(), temporaryAlloc,
						BigInteger.ZERO);
				
				ctx.increaseLevel();
				firstExp.write(ctx, registers[1]);
				ctx.decreaseLevel();
				
				OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(),
						temporaryAlloc.getCellAddress(BigInteger.ZERO));
				writer.write(OperationsHelper.genInstruction(Instructions.SUB_i, resultRegister));
				setResultRegisterId(resultRegister.getId());
			}
		}

		return writer.getNextLineNumber() - startLine;
	}

}
