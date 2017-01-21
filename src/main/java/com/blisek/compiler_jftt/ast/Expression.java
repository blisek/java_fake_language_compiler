package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Writer;

public abstract class Expression {
	protected final int label;
	protected Expression[] expressions;
	
	public Expression(Expression... expressions) {
		this(-1, expressions);
	}
	
	public Expression(int label, Expression... expressions) {
		this.label = label;
		this.expressions = expressions;
	}

	public int getLabel() {
		return label;
	}

	public abstract int write(Writer writer, Context ctx);
	
}
