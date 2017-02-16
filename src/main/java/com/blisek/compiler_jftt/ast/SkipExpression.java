package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;

public class SkipExpression extends Expression {

	public SkipExpression() {
		super();
	}

	public SkipExpression(int label) {
		super(label);
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		return 0;
	}

}
