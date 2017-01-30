package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class ProgramExpression extends SingleExpression {

	public ProgramExpression(Expression expression) {
		super(expression);
	}

	@Override
	public int write(Writer writer_, Context ctx) {
		Writer writer = ctx.getWriter();
		Expression commands = getExpression();
		int linesWritten = commands.write(writer, ctx);
		writer.write(Instructions.HALT);
		return linesWritten + 1;
	}
	
	

}
