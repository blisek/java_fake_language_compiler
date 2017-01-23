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
		super(new Expression[] { left, right });
	}

	public MultiplyExpression(int label, Expression left, Expression right) {
		super(label, new Expression[] { left, right });
	}

	@Override
	public int write(Writer writer, Context ctx) {
		// TODO Auto-generated method stub
		final String lineSeparator = System.lineSeparator();
		Collection<Register> reservedRegisters = new ArrayList<Register>(3);
		VelocityContext velCtx = new VelocityContext();
		velCtx.put("nl", lineSeparator);
		velCtx.put("reg", reservedRegisters.stream().map(Register::getId)
				.toArray(itSize -> new Integer[itSize]));
		StringWriter stringWriter = new StringWriter();
		multiplyTemplate.merge(velCtx, stringWriter);
		String[] generatedLines = stringWriter.toString().split(lineSeparator);
		
		return 0;
	}
	
	

}
