package com.blisek.compiler_jftt.ast;

import java.io.StringWriter;
import java.util.Arrays;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Writer;

public abstract class VelocityExpression extends Expression {
	public static final String NEW_LINE_VAR_NAME = "nl";
	public static final String START_LINE_VAR_NAME = "startLine";
	private final static VelocityEngine velocityEngine;
	
	static {
		velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
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
	
	public abstract void setUpContext(Context ctx, VelocityContext vCtx, 
			RegisterReservationInfo[] registers, MemoryAllocationInfo[] allocations);
	
	public abstract void finishUp(Context ctx);
	
	public abstract Template getTemplate();
	
	public abstract int getUsedMemoryBlockSize();
	
	public abstract int getUsedRegistersCount();
	
	public void postProcessLines(String[] lines) {}

	@Override
	public int write(Context ctx, Object additionalData) {
		final String lineSeparator = System.lineSeparator();
		final Writer writer = ctx.getWriter();
		
		int memorySize = getUsedMemoryBlockSize();
		int registersCount = getUsedRegistersCount();
		int lineCounterStartValue = writer.getNextLineNumber();
		
		final MemoryAllocationInfo[] allocatedMemory = (memorySize > 0) ? 
				ctx.getMemoryAllocationStrategy().allocateTemporaryMemory(memorySize) 
				: null;
		final RegisterReservationInfo[] registers = (registersCount > 0) ? 
				OperationsHelper.getRegisters(ctx, registersCount)
				: null;
				
		VelocityContext vContext = new VelocityContext();
		setUpContext(ctx, vContext, registers, allocatedMemory);
		vContext.put(NEW_LINE_VAR_NAME, lineSeparator);
		vContext.put(START_LINE_VAR_NAME, writer.getNextLineNumber());
		
		final int resultRegId = getResultRegisterId();
		final RegisterReservationInfo[] registersWithoutResultRegister = (registers != null) ?
					Arrays.stream(registers)
					.filter(r -> r.getRegister().getId() != resultRegId)
					.toArray(size -> new RegisterReservationInfo[size])
				: null;
		
		try(Deallocator registersAllocator = Deallocator.of(registersWithoutResultRegister)) {
			try(Deallocator memoryDeallocator = Deallocator.of(allocatedMemory)) {
			
				
				Template algTemplate = getTemplate();
				StringWriter strWriter = new StringWriter();
				algTemplate.merge(vContext, strWriter);
				String[] lines = strWriter.toString().split("[\\r]??\\n");
				postProcessLines(lines);
				
				finishUp(ctx);
				
				for(String line : lines)
					writer.write(line);
			}
		}
		
		return writer.getNextLineNumber() - lineCounterStartValue;
	}
	
}
