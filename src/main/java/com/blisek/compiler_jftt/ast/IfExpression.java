package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.UniqueNumbersGenerator;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class IfExpression extends BiExpression {
	private ConditionExpression condition;

	public IfExpression(ConditionExpression condition, Expression expr1, Expression expr2) {
		super(expr1, expr2);
		this.condition = condition;
	}

	public IfExpression(int label, ConditionExpression condition, Expression expr1, Expression expr2) {
		super(label, expr1, expr2);
		this.condition = condition;
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNumber = writer.getNextLineNumber();
		final int[] labels = new int[] {
				UniqueNumbersGenerator.nextNumber(),
				UniqueNumbersGenerator.nextNumber()
		};
		final int jumpOutLabel = UniqueNumbersGenerator.nextNumber();
		
		condition.write(ctx, labels);
		final Expression trueExpr = getFirstExpression(), falseExpr = getSecondExpression();
		
		final int trueJumpDest = writer.getNextLineNumber();
		ctx.pairLabelWithLine(labels[ConditionExpression.JUMP_TRUE_INDEX], trueJumpDest);
		trueExpr.write(ctx, null);
		writer.write(Instructions.JUMP_i, -1, jumpOutLabel);
		
		final int falseJumpDest = writer.getNextLineNumber();
		ctx.pairLabelWithLine(labels[ConditionExpression.JUMP_FALSE_INDEX], falseJumpDest);
		falseExpr.write(ctx, null);
		writer.write(Instructions.JUMP_i, -1, jumpOutLabel);
		
		ctx.pairLabelWithLine(jumpOutLabel, writer.getNextLineNumber());
		
		return writer.getNextLineNumber() - startLineNumber;
	}

	
}
