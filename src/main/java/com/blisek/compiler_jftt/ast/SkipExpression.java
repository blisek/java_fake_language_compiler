package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Writer;

public class SkipExpression extends Expression {

	public SkipExpression() {
		super();
	}

	public SkipExpression(int label) {
		super(label);
	}

	@Override
	public int write(Writer writer_, Context ctx) {
		return 0;
	}

}
