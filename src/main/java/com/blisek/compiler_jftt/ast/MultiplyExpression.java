package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public class MultiplyExpression extends VelocityExpression {
	private static final String REGISTER1_VAR = "reg1", REGISTER2_VAR = "reg2"; 
	private final static Template multiplyTemplate;
	
	static {
		multiplyTemplate = VelocityExpression
				.getEngine().getTemplate("templates/multiplication.template");
	}

	public MultiplyExpression(Expression left, Expression right) {
		super(left, right);
	}

	public MultiplyExpression(int label, Expression left, Expression right) {
		super(label, left, right);
	}

	@Override
	public void finishUp(Context ctx) {
		setOperationResultAvailable(true);
	}

	@Override
	public void setUpContext(Context ctx, VelocityContext vCtx, RegisterReservationInfo[] registers,
			MemoryAllocationInfo[] allocations) {
		
		final Expression expr1 = expressions[0];
		final Expression expr2 = expressions[1];
		final RegisterReservationInfo reg1 = registers[0];
		final RegisterReservationInfo reg2 = registers[1];
		
		ctx.increaseLevel();
		expr1.write(ctx, reg1);
		expr2.write(ctx, reg2);
		ctx.decreaseLevel();
		
		vCtx.put(REGISTER1_VAR, reg1.getRegister().getId());
		vCtx.put(REGISTER2_VAR, reg2.getRegister().getId());
		setResultRegisterId(reg1.getRegister().getId());
		setOperationResultAvailable(false);
		
		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), 
				allocations[0].getCellAddress(BigInteger.ZERO));
	}
	
	

	@Override
	public int getUsedMemoryBlockSize() {
		return 2;
	}

	@Override
	public int getUsedRegistersCount() {
		return 2;
	}

	@Override
	public Template getTemplate() {
		return multiplyTemplate;
	}

	
	
	

}
