package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Writer;

public abstract class SingleLineExpression extends Expression {

	public SingleLineExpression(Expression... expressions) {
		super(expressions);
	}

	public SingleLineExpression(int label, Expression... expressions) {
		super(label, expressions);
	}

	@Override
	public int write(Writer writer, Context ctx) {
		writer.write(ctx, getLabel(), getCommand(), -1);
		return 0;
	}
	
	public abstract String getCommand();

}
