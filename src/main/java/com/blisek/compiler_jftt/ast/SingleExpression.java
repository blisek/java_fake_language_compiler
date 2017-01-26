package com.blisek.compiler_jftt.ast;

public class SingleExpression extends Expression {

	public SingleExpression(Expression expression) {
		super(expression);
	}

	public SingleExpression(int label, Expression expression) {
		super(label, expression);
	}

}
