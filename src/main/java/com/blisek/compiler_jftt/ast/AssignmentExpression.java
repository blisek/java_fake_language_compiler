package com.blisek.compiler_jftt.ast;

public class AssignmentExpression extends BiExpression {

	public AssignmentExpression(Expression identifier, Expression expr2) {
		super(identifier, expr2);
	}

	public AssignmentExpression(int label, Expression identifier, Expression expr2) {
		super(label, identifier, expr2);
	}

	
}
