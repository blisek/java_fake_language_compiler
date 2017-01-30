package com.blisek.compiler_jftt.ast;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public class MultiplyExpression extends VelocityExpression {
	private static final String REGISTER1_VAR = "reg1", REGISTER2_VAR = "reg2"; 
	private final static Template multiplyTemplate;
	private int resRegId;
	
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
		setResultRegisterId(resRegId);
	}

	@Override
	public void setUpContext(Context ctx, VelocityContext vCtx, RegisterReservationInfo[] registers,
			MemoryAllocationInfo[] allocations) {
		
		Expression expr1 = expressions[0];
		Expression expr2 = expressions[1];
		
		ctx.increaseLevel();
		expr1.write(ctx.getWriter(), ctx);
		expr2.write(ctx.getWriter(), ctx);
		ctx.decreaseLevel();
		
		int reg1 = expr1.getResultRegisterId();
		int reg2 = expr2.getResultRegisterId();
		
		vCtx.put(REGISTER1_VAR, reg1);
		vCtx.put(REGISTER2_VAR, reg2);
		resRegId = reg1;
		
		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), allocations[0].getStartCell());
	}

	@Override
	public int getUsedMemoryBlockSize() {
		return 2;
	}

	@Override
	public int getUsedRegistersCount() {
		return 0;
	}

	@Override
	public Template getTemplate() {
		return multiplyTemplate;
	}

	
	
	

}
