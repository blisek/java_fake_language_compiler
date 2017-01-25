package com.blisek.compiler_jftt.ast;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class DivisionExpression extends VelocityExpression {
	private final static Template divisionTemplate;
	
	static {
		divisionTemplate = VelocityExpression
				.getEngine().getTemplate(DivisionExpression.class.getResource("dzielenie_c").getFile());
	}

	
	
	public DivisionExpression(Expression left, Expression right) {
		super(left, right);
	}

	public DivisionExpression(int label, Expression left, Expression right) {
		super(label, left, right);
	}

	@Override
	public void setUpVelocityContext(VelocityContext vCtx) {
		// TODO reserve registers

	}

	@Override
	public Template getTemplate() {
		return divisionTemplate;
	}

}
