package com.blisek.compiler_jftt.ast;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.writer.Writer;

public class MultiplyExpression extends VelocityExpression {
	private final static Template multiplyTemplate;
	
	static {
		multiplyTemplate = VelocityExpression
				.getEngine().getTemplate(MultiplyExpression.class.getResource("mnozenie_c").getFile());
	}

	public MultiplyExpression(Expression left, Expression right) {
		super(left, right);
	}

	public MultiplyExpression(int label, Expression left, Expression right) {
		super(label, left, right);
	}

	@Override
	public void setUpVelocityContext(Context ctx, VelocityContext vCtx) {
		
	}

	@Override
	public Template getTemplate() {
		return multiplyTemplate;
	}

	
	
	

}
