package com.blisek.compiler_jftt.ast;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public class DivisionExpression extends VelocityExpression {
	private static final String REG1_VAR = "reg1", REG2_VAR = "reg2", REG3_VAR = "reg3", REG4_VAR = "reg4";
	private final static Template divisionTemplate;
	private int[] nestedExpressionsRegisters = new int[0];
	private int resRegId;
	
	static {
		divisionTemplate = VelocityExpression
				.getEngine().getTemplate("templates/division.template");
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

		final Register ownRegister1 = registers[0].getRegister();
		final int ownRegister1OldLevel = ownRegister1.getUsedByLevel();
		ownRegister1.setUsedByLevel(Integer.MAX_VALUE);
		final Register ownRegister2 = registers[1].getRegister();
		final int ownRegister2OldLevel = ownRegister2.getUsedByLevel();
		ownRegister2.setUsedByLevel(Integer.MAX_VALUE);
		
		Expression expr1 = expressions[0];
		Expression expr2 = expressions[1];
		
		expr1.write(ctx.getWriter(), ctx);
		expr2.write(ctx.getWriter(), ctx);
		
		ownRegister1.setUsedByLevel(ownRegister1OldLevel);
		ownRegister2.setUsedByLevel(ownRegister2OldLevel);
		
		int reg1 = expr1.getResultRegisterId();
		int reg2 = expr2.getResultRegisterId();
		
		nestedExpressionsRegisters = new int[] { reg1, reg2 };
		
		vCtx.put(REG1_VAR, reg1);
		vCtx.put(REG2_VAR, reg2);
		vCtx.put(REG3_VAR, ownRegister1.getId());
		vCtx.put(REG4_VAR, ownRegister2.getId());
		
		resRegId = ownRegister2.getId();
		OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), allocations[0].getStartCell());
	}

	@Override
	public void finishUp(Context ctx) {
		setResultRegisterId(resRegId);
		
//		for(int reg : nestedExpressionsRegisters) {
//			Register register = ctx.getRegisterById(reg);
//			register.setTaken(false);
//			register.setUsedByLevel(-1);
//		}
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
		return divisionTemplate;
	}

}
