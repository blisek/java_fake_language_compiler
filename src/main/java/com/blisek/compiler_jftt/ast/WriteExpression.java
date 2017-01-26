package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Writer;

public class WriteExpression extends SingleExpression {

	public WriteExpression(Expression expression) {
		super(expression);
	}

	public WriteExpression(int label, Expression expression) {
		super(label, expression);
	}

	@Override
	public int write(Writer writer_, Context ctx) {
		// TODO: implement
		return 0;
	}

}
