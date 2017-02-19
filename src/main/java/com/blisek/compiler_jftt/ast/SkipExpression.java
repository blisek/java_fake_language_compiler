package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.writer.Instructions;

public class SkipExpression extends Expression {

	public SkipExpression() {
		super();
	}

	public SkipExpression(int label) {
		super(label);
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		ctx.getWriter().write(OperationsHelper.genInstruction(Instructions.ZERO_i, ctx.getHelperRegister()));
		return 1;
//		return 0;
	}

}
