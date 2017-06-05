package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.helpers.Preconditions;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
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
		checkPreconditions();
		final BigInteger index = indexVar.getValue();
		if(index != null) {
			Preconditions.assureIndexInRange(variable, index, getLine(start), getColumn(start), getLine(end),
				getColumn(end));
		}
		
		
		MemoryAllocationInfo[] mai = ctx.getMemoryAllocationStrategy().allocateTemporaryMemory();
		try(Deallocator _memoryDeallocator = Deallocator.of(mai)) {
            OperationsHelper.setRegisterValue(ctx, destinationRegister, variable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
            OperationsHelper.setRegisterValue(ctx, addressRegister, indexVar.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
			
			final Writer writer = ctx.getWriter();
			writer.write(OperationsHelper.genInstruction(Instructions.ADD_i, destinationRegister));
			writer.write(OperationsHelper.genInstruction(Instructions.COPY_i, destinationRegister));
			writer.write(OperationsHelper.genInstruction(Instructions.LOAD_i, destinationRegister));
			
		}
		
		
	}

	@Override
	public ValueExpression createWorkingCopy(Context ctx) {
		checkPreconditions();
		final VariableInfo variable = OperationsHelper.cloneVariableInfoCell(ctx, getVariable(), null);

        RegisterReservationInfo[] rri;
        try(Deallocator _dealloc = Deallocator.of(rri = OperationsHelper.getRegisters(ctx, 1))) {
            final Register tempRegister = rri[0].getRegister();
//            OperationsHelper.setRegisterValue(ctx, tempRegister, variable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
//            OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), indexVar.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
//            final Writer writer = ctx.getWriter();
//            writer.write(OperationsHelper.genInstruction(Instructions.ADD_i, tempRegister));
//            writer.write(OperationsHelper.genInstruction(Instructions.COPY_i, tempRegister));
//            writer.write(OperationsHelper.genInstruction(Instructions.LOAD_i, tempRegister));
            loadValueIntoRegister(ctx, ctx.getHelperRegister(), tempRegister);
            OperationsHelper.setRegisterValue(ctx, ctx.getHelperRegister(), variable.getAssignedMemoryCells()[0].getCellAddress(BigInteger.ZERO));
            ctx.getWriter().write(OperationsHelper.genInstruction(Instructions.STORE_i, tempRegister));
        }
//		return new ArrayVariableValueExpression(getLabel(), variable, indexVar.getVariableName());
        variable.setVariableDeclared(true);
        variable.setValueAssigned(true);
        return new VariableValueExpression(variable);
	}

	public VariableInfo getVariable() {
		return variable;
	}

	public VariableInfo getIndexVar() {
		return indexVar;
	}

	private void checkPreconditions() {
		Preconditions.assureArrayType(variable, getLine(start), getColumn(start), getLine(end), getColumn(end));
		Preconditions.assureVariableIsDeclared(variable, getLine(start), getColumn(start), getLine(end),
				getColumn(end));
	}
}
