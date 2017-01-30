package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class AdditionExpression extends BiExpression {

	public AdditionExpression(Expression expr1, Expression expr2) {
		super(expr1, expr2);
	}

	public AdditionExpression(int label, Expression expr1, Expression expr2) {
		super(label, expr1, expr2);
	}

	@Override
	public int write(Writer writer_, Context ctx) {
		final Writer writer = ctx.getWriter();
		final int startLine = writer.getNextLineNumber();
		
		Expression secondExp = getSecondExpression();
		secondExp.write(writer, ctx);
		final int secondRegisterId = secondExp.getResultRegisterId();
		
		MemoryAllocationInfo[] allocatedMem = ctx.getMemoryAllocationStrategy().allocateTemporaryMemory();
		try(Deallocator deallocator = Deallocator.of(allocatedMem)) {
			MemoryAllocationInfo temporaryAlloc = allocatedMem[0];
			OperationsHelper.storeRegisterValue(ctx, ctx.getRegisterById(secondRegisterId), allocatedMem[0], BigInteger.ZERO);
			Expression firstExp = getFirstExpression();
			firstExp.write(writer, ctx);
			OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), temporaryAlloc.getCellAddress(0));
			final Register resultRegister = ctx.getRegisterById(firstExp.getResultRegisterId());
			writer.write(OperationsHelper.genInstruction(Instructions.ADD_i, resultRegister));
			setResultRegisterId(resultRegister.getId());
		}
		
		return writer.getNextLineNumber() - startLine;
	}

	
}
