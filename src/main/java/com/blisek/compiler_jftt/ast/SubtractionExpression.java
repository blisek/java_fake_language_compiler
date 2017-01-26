package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
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
	public int write(Writer writer_, Context ctx) {
		final Writer writer = ctx.getWriter();
		final int startLine = writer.getNextLineNumber();
		
		Expression secondExp = getSecondExpression();
		secondExp.write(writer, ctx);
		final int secondRegisterId = secondExp.getResultRegisterId();
		
		try(MemoryAllocationInfo temporaryAlloc = ctx.getMemoryAllocationStrategy().allocateTemporaryMemory()) {
			OperationsHelper.storeRegisterValue(ctx, ctx.getRegisterById(secondRegisterId), temporaryAlloc, 0);
			Expression firstExp = getFirstExpression();
			firstExp.write(writer, ctx);
			OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), temporaryAlloc.getCellAddress(0));
			final Register resultRegister = ctx.getRegisterById(firstExp.getResultRegisterId());
			writer.write(OperationsHelper.generateOneArgumentInstruction(Instructions.SUB_i, resultRegister));
			setResultRegisterId(resultRegister.getId());
		}
		
		return writer.getNextLineNumber() - startLine;
	}


}
