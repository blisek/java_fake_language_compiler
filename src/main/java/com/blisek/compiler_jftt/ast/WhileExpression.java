package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.UniqueNumbersGenerator;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class WhileExpression extends SingleExpression {
	private ConditionExpression condition;

	public WhileExpression(ConditionExpression condition, Expression expression) {
		super(expression);
		this.condition = condition;
	}

	public WhileExpression(int label, ConditionExpression condition, Expression expression) {
		super(label, expression);
		this.condition = condition;
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNum = writer.getNextLineNumber();
		final int[] labels = new int[] {
				UniqueNumbersGenerator.nextNumber(),
				UniqueNumbersGenerator.nextNumber()
		};
		
		int jumpToStartLabel = UniqueNumbersGenerator.nextNumber();
		
		final int loopStartJump = writer.getNextLineNumber();
		ctx.pairLabelWithLine(jumpToStartLabel, loopStartJump);
		
		condition.write(ctx, labels);
		final Expression expression = getExpression();
		
		final int trueJumpDest = writer.getNextLineNumber();
		ctx.pairLabelWithLine(labels[ConditionExpression.JUMP_TRUE_INDEX], trueJumpDest);
		expression.write(ctx, null);
		writer.write(Instructions.JUMP_i, -1, jumpToStartLabel);
		
		ctx.pairLabelWithLine(labels[ConditionExpression.JUMP_FALSE_INDEX], writer.getNextLineNumber());
		
		return writer.getNextLineNumber() - startLineNum;
	}

}
