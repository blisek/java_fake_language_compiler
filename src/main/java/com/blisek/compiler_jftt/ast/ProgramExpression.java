package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.structs.VDeclaration;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class ProgramExpression extends SingleExpression {
	private final VDeclarationsExpression declarations;

	public ProgramExpression(Expression expression, VDeclarationsExpression declarations) {
		super(expression);
		this.declarations = declarations; 
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		markDeclaredVariables();
		
		final Writer writer = ctx.getWriter();
		Expression commands = getExpression();
		int linesWritten = commands.write(ctx, null);
		writer.write(Instructions.HALT);
		return linesWritten + 1;
	}

	private void markDeclaredVariables() {
		for(VDeclaration vd : declarations.getVdeclarations()) {
			System.out.println("Delaration: " + vd.getVarName());
			VariableInfo var = VariableInfo.of(vd.getVarName());
			var.setLength(vd.getLength());
			var.setVariableDeclared(true);
		}
	}
	
	

}
