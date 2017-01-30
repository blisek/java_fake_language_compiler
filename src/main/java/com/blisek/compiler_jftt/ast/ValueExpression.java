package com.blisek.compiler_jftt.ast;

import java.util.Collection;
import java.util.Collections;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.strategies.RegistryManagementStrategy;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Writer;

public abstract class ValueExpression extends SingleExpression {
	private static final Collection<Integer> emptyCollection = Collections.emptyList();

	public ValueExpression(Expression expression) {
		super(expression);
	}

	public ValueExpression(int label, Expression expression) {
		super(label, expression);
	}
	
	protected abstract void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister);

	@Override
	public int write(Writer writer_, Context ctx) {
		final Writer writer = ctx.getWriter();
		final int startLine = writer.getNextLineNumber();
		
		final Register addressRegister = ctx.getHelperRegister();
		final RegistryManagementStrategy rms = ctx.getRegistryManagementStrategy();
		final RegisterReservationInfo resultRegister = rms.reserveRegister(ctx, emptyCollection); 
		loadValueIntoRegister(ctx, addressRegister, resultRegister.getRegister());
		
		final int lastLine = writer.getNextLineNumber();
		setResultRegisterId(resultRegister.getRegister().getId());
		return lastLine - startLine;
	}
	
	

}
