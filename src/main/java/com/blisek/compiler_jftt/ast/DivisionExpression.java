package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public class DivisionExpression extends VelocityExpression {
	private static final String REG1_VAR = "reg1", REG2_VAR = "reg2", REG3_VAR = "reg3", REG4_VAR = "reg4";
	private final static Template divisionTemplate;
	
	static {
		divisionTemplate = VelocityExpression
				.getEngine().getTemplate("templates/division2.template");
	}

	public DivisionExpression(Expression left, Expression right) {
		super(left, right);
	}

	public DivisionExpression(int label, Expression left, Expression right) {
		super(label, left, right);
	}

	
	
	@Override
	public void setUpContext(Context ctx, VelocityContext vCtx, RegisterReservationInfo[] registers,
			MemoryAllocationInfo[] allocations) {
		final RegisterReservationInfo reg1 = registers[0],
				reg2 = registers[1], 
				reg3 = registers[2],
				reg4 = registers[3];
		final Expression expr1 = expressions[0];
		final Expression expr2 = expressions[1];
		
		ctx.increaseLevel();
		expr1.write(ctx, reg1);
		expr2.write(ctx, reg2);
		ctx.decreaseLevel();
		
		vCtx.put(REG1_VAR, reg1.getRegister().getId());
		vCtx.put(REG2_VAR, reg2.getRegister().getId());
		vCtx.put(REG3_VAR, reg3.getRegister().getId());
		vCtx.put(REG4_VAR, reg4.getRegister().getId());
		
		setResultRegisterId(reg4.getRegister().getId());
		setOperationResultAvailable(false);

		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), 
				allocations[0].getCellAddress(BigInteger.ZERO));
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
		return 4;
	}

	@Override
	public Template getTemplate() {
		return divisionTemplate;
	}

}
