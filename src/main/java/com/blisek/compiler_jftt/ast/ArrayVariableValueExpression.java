package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.helpers.Preconditions;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class ArrayVariableValueExpression extends ValueExpression {
	private VariableInfo variable, indexVar;

	public ArrayVariableValueExpression(VariableInfo variable, String indexVar) {
		super(null);
		this.variable = variable;
		this.indexVar = VariableInfo.of(indexVar);
	}

	public ArrayVariableValueExpression(int label, VariableInfo variable, String indexVar) {
		super(label, null);
		this.variable = variable;
		this.indexVar = VariableInfo.of(indexVar);
	}

	@Override
	protected void loadValueIntoRegister(Context ctx, Register addressRegister, Register destinationRegister) {
		Preconditions.assureNoArrayType(variable, getLine(start), getColumn(start), getLine(end), getColumn(end), true);
		Preconditions.assureVariableIsDeclared(variable, getLine(start), getColumn(start), getLine(end),
				getColumn(end));
		final BigInteger index = indexVar.getValue();
		if(index != null) {
			Preconditions.assureIndexInRange(variable, index, getLine(start), getColumn(start), getLine(end),
				getColumn(end));
		}
		
		
		MemoryAllocationInfo[] mai = ctx.getMemoryAllocationStrategy().allocateTemporaryMemory();
		try(Deallocator _memoryDeallocator = Deallocator.of(mai)) {
			
//			OperationsHelper.loadRegister(ctx, destinationRegister, 
//					indexVar.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
//			OperationsHelper.setRegisterValue(ctx, addressRegister, 
//					variable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			OperationsHelper.setRegisterValue(ctx, destinationRegister, variable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			OperationsHelper.setRegisterValue(ctx, addressRegister, indexVar.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			
			final Writer writer = ctx.getWriter();
			writer.write(OperationsHelper.genInstruction(Instructions.ADD_i, destinationRegister));
			writer.write(OperationsHelper.genInstruction(Instructions.COPY_i, destinationRegister));
			writer.write(OperationsHelper.genInstruction(Instructions.LOAD_i, destinationRegister));
			
		}
		
		
	}

	public VariableInfo getVariable() {
		return variable;
	}

	public VariableInfo getIndexVar() {
		return indexVar;
	}
}
