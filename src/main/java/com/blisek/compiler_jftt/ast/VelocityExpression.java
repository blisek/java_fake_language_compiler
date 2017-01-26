package com.blisek.compiler_jftt.ast;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.writer.Writer;

public abstract class VelocityExpression extends Expression {
	public static final String NEW_LINE_VAR_NAME = "nl";
	public static final String CURRENT_LINE_VAR_NAME = "startLineNum";
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
	
	public abstract void setUpVelocityContext(Context ctx, VelocityContext vCtx);
	
	public abstract Template getTemplate();
	
	public void postProcessLines(String[] lines) {}

	@Override
	public int write(Writer writer_, Context ctx) {
		final String lineSeparator = System.lineSeparator();
		final Writer writer = ctx.getWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put(NEW_LINE_VAR_NAME, lineSeparator);
		vContext.put(CURRENT_LINE_VAR_NAME, writer.getNextLineNumber());
		setUpVelocityContext(ctx, vContext);
		
		Template algTemplate = getTemplate();
		StringWriter strWriter = new StringWriter();
		algTemplate.merge(vContext, strWriter);
		String[] lines = strWriter.toString().split("[\\r]??\\n");
		postProcessLines(lines);
		
		for(String line : lines) {
			writer.write(line);
		}
		return 0;
	}
	
	
}
