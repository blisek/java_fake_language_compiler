package com.blisek.compiler_jftt.ast;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class ModuloExpression extends VelocityExpression {
	private final static Template moduloTemplate;
	
	static {
		moduloTemplate = VelocityExpression
				.getEngine().getTemplate(MultiplyExpression.class.getResource("modulo_c").getFile());
	}

	public ModuloExpression(Expression left, Expression right) {
		super(left, right);
	}

	public ModuloExpression(int label, Expression left, Expression right) {
		super(label, left, right);
	}

	@Override
	public void setUpVelocityContext(VelocityContext vCtx) {
		// TODO resrve registers
	}

	@Override
	public Template getTemplate() {
		return moduloTemplate;
	}

}
