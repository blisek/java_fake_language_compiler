package com.blisek.compiler_jftt.ast;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public class ModuloExpression extends VelocityExpression {
	private static final String REG1_VAR = "reg1", REG2_VAR = "reg2", REG3_VAR = "reg3";
	private final static Template moduloTemplate;
	private int resultRegId;
	
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
		
		final Register ownRegister = registers[0].getRegister();
		final int ownRegisterOldLevel = ownRegister.getUsedByLevel();
		ownRegister.setUsedByLevel(Integer.MAX_VALUE);
		
		Expression expr1 = expressions[0];
		Expression expr2 = expressions[1];
		
		expr1.write(ctx.getWriter(), ctx);
		expr2.write(ctx.getWriter(), ctx);
		
		ownRegister.setUsedByLevel(ownRegisterOldLevel);
		
		int reg1 = expr1.getResultRegisterId();
		int reg2 = expr2.getResultRegisterId();
		
		vCtx.put(REG1_VAR, reg1);
		vCtx.put(REG2_VAR, reg2);
		vCtx.put(REG3_VAR, ownRegister.getId());
		resultRegId = reg1;
		
		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), allocations[0].getStartCell());
	}

	@Override
	public void finishUp(Context ctx) {
		setResultRegisterId(resultRegId);
	}

	@Override
	public int getUsedMemoryBlockSize() {
		return 2;
	}

	@Override
	public int getUsedRegistersCount() {
		return 1; // +2 z załadowanych w rejestrach wartości
	}

	@Override
	public Template getTemplate() {
		return moduloTemplate;
	}

}
