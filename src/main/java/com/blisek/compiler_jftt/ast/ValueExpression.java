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

	public abstract ValueExpression createWorkingCopy(Context ctx);

	public ValueExpression(int label, Expression expression) {
		super(label, expression);
	}

	protected abstract void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister);

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLine = writer.getNextLineNumber();

		final Register addressRegister = ctx.getHelperRegister();
		@SuppressWarnings("resource")
		final RegisterReservationInfo resultRegister =
				(additionalData instanceof RegisterReservationInfo)
				? (RegisterReservationInfo)additionalData
				: reserveWorkingRegister(ctx);
		loadValueIntoRegister(ctx, addressRegister, resultRegister.getRegister());

		setResultRegisterId(resultRegister.getRegister().getId());
		return writer.getNextLineNumber() - startLine;
	}

	private RegisterReservationInfo reserveWorkingRegister(Context ctx) {
		final RegistryManagementStrategy rms = ctx.getRegistryManagementStrategy();
		final RegisterReservationInfo resultRegister = rms.reserveRegister(ctx, emptyCollection);
		return resultRegister;
	}

}
