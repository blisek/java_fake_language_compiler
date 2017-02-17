package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public class ModuloExpression extends VelocityExpression {
	private static final String REG1_VAR = "reg1", REG2_VAR = "reg2", REG3_VAR = "reg3";
	private final static Template moduloTemplate;
	
	static {
		moduloTemplate = VelocityExpression
				.getEngine().getTemplate("templates/modulo.template");
	}

	public ModuloExpression(Expression left, Expression right) {
		super(left, right);
	}

	public ModuloExpression(int label, Expression left, Expression right) {
		super(label, left, right);
	}

	@Override
	public void setUpContext(Context ctx, VelocityContext vCtx, RegisterReservationInfo[] registers,
			MemoryAllocationInfo[] allocations) {
		
		final RegisterReservationInfo reg1 = registers[0],
				reg2 = registers[1],
				reg3 = registers[2];
		final Expression expr1 = expressions[0];
		final Expression expr2 = expressions[1];
		
		ctx.increaseLevel();
		expr1.write(ctx, reg1);
		expr2.write(ctx, reg2);
		ctx.decreaseLevel();
		
		vCtx.put(REG1_VAR, reg1.getRegister().getId());
		vCtx.put(REG2_VAR, reg2.getRegister().getId());
		vCtx.put(REG3_VAR, reg3.getRegister().getId());

		setResultRegisterId(reg1.getRegister().getId());
		setOperationResultAvailable(false);
		
		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), allocations[0].getCellAddress(BigInteger.ZERO));
	}

	@Override
	public void finishUp(Context ctx) {
		setOperationResultAvailable(true);
	}

	@Override
	public int getUsedMemoryBlockSize() {
		return 2;
	}

	@Override
	public int getUsedRegistersCount() {
		//return 1; // +2 z załadowanych w rejestrach wartości
		return 3;
	}

	@Override
	public Template getTemplate() {
		return moduloTemplate;
	}

}
