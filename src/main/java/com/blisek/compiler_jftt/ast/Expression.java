package com.blisek.compiler_jftt.ast;

import java.util.Arrays;

import com.blisek.compiler_jftt.context.Context;

import beaver.Symbol;

public class Expression extends Symbol {
	private static int expressionIdCounter = 0;
	protected final int label;
	protected Expression[] expressions;
	private final int expressionId;
	private boolean operationResultAvailable;
	private int resultRegisterId;
	
	public Expression(Expression... expressions) {
		this(-1, expressions);
	}
	
	public Expression(int label, Expression... expressions) {
		this.label = label;
		this.expressions = expressions;
		this.expressionId = expressionIdCounter++;
	}

	public int getLabel() {
		return label;
	}

	public int write(Context ctx, Object additionalData) {
		return Arrays.stream(expressions)
				.mapToInt(exp -> exp.write(ctx, additionalData))
				.sum();
	}

	public int getExpressionId() {
		return expressionId;
	}

	public boolean isOperationResultAvailable() {
		return operationResultAvailable;
	}

	protected void setOperationResultAvailable(boolean operationResultAvailable) {
		this.operationResultAvailable = operationResultAvailable;
	}

	public int getResultRegisterId() {
		return resultRegisterId;
	}

	public void setResultRegisterId(int resultRegisterId) {
		this.resultRegisterId = resultRegisterId;
		setOperationResultAvailable(true);
	}

	
}
