package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Writer;

public class ReadExpression extends SingleExpression {

	public ReadExpression(Expression expression) {
		super(expression);
	}

	public ReadExpression(int label, Expression expression) {
		super(label, expression);
	}

	@Override
	public int write(Writer writer_, Context ctx) {
		// TODO: implement this
		System.out.println("READ expression");
		return 0;
	}

}
