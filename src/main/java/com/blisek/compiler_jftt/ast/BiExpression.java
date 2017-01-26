package com.blisek.compiler_jftt.ast;

public class BiExpression extends Expression {

	public BiExpression(Expression expr1, Expression expr2) {
		super(expr1, expr2);
	}

	public BiExpression(int label, Expression expr1, Expression expr2) {
		super(label, expr1, expr2);
	}

	public Expression getFirstExpression() {
		return expressions[0];
	}
	
	public Expression getSecondExpression() {
		return expressions[1];
	}
}
