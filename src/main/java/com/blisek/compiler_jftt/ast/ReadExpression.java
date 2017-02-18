package com.blisek.compiler_jftt.ast;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class ReadExpression extends SingleExpression {

	public ReadExpression(Expression expression) {
		super(expression);
	}

	public ReadExpression(int label, Expression expression) {
		super(label, expression);
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNumber = writer.getNextLineNumber();
		final ValueExpression valueExpr = (ValueExpression)getExpression();
		final RegisterReservationInfo reservedReg = 
				ctx.getRegistryManagementStrategy().reserveRegister(ctx, null);
		
		try(Deallocator _deallocator = Deallocator.of(reservedReg)) {
			OperationsHelper.setHelperRegisterCellAddress(ctx, valueExpr, reservedReg.getRegister());
			writer.write(OperationsHelper.genInstruction(
					Instructions.GET_i, reservedReg.getRegister()));
			writer.write(OperationsHelper.genInstruction(
					Instructions.STORE_i, reservedReg.getRegister()));
		}
		
		OperationsHelper.setValueAssignedForValueExpression(valueExpr, true);
		return writer.getNextLineNumber() - startLineNumber;
	}

}
