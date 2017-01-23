package com.blisek.compiler_jftt.ast;

import org.apache.velocity.app.VelocityEngine;

public abstract class VelocityExpression extends Expression {
	private final static VelocityEngine velocityEngine;
	
	static {
		velocityEngine = new VelocityEngine();
		velocityEngine.init();
	}
	
	public static VelocityEngine getEngine() {
		return velocityEngine;
	}

	public VelocityExpression(Expression... expressions) {
		super(expressions);
	}

	public VelocityExpression(int label, Expression... expressions) {
		super(label, expressions);
	}
	
	
}
