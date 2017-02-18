package com.blisek.compiler_jftt.ast;


import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.helpers.OperationsHelper;
import com.blisek.compiler_jftt.structs.Deallocator;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Writer;

public abstract class ConditionExpression extends BiExpression {
	public static final int JUMP_TRUE_INDEX = 0;
	public static final int JUMP_FALSE_INDEX = 1;

	public ConditionExpression(Expression expr1, Expression expr2) {
		super(expr1, expr2);
	}

	public ConditionExpression(int label, Expression expr1, Expression expr2) {
		super(label, expr1, expr2);
	}

	@Override
	public int write(Context ctx, Object additionalData) {
		final Writer writer = ctx.getWriter();
		final int startLineNumber = writer.getNextLineNumber();
		final int[] labels = (int[])additionalData;
		
		RegisterReservationInfo[] reservedRegisters = new RegisterReservationInfo[0];
		if(getRequiredRegistersAmount() > 0) {
			reservedRegisters = OperationsHelper.getRegisters(
					ctx, getRequiredRegistersAmount());
		}
		
		MemoryAllocationInfo[] allocatedCells = new MemoryAllocationInfo[0];
		if(getRequiredMemoryCellsAmount() > 0) {
			allocatedCells = ctx.getMemoryAllocationStrategy()
					.allocateTemporaryMemory(getRequiredMemoryCellsAmount());
		}
		
		try(Deallocator _registersDeallocator = Deallocator.of(reservedRegisters)) {
			try(Deallocator _memoryDeallocator = Deallocator.of(allocatedCells)) {
				writeComparison(ctx, labels, reservedRegisters, allocatedCells);
			}
		}
		
		setResultRegisterId(-1);
		return writer.getNextLineNumber() - startLineNumber;
	}

	protected int getRequiredRegistersAmount() { return 0; }
	protected int getRequiredMemoryCellsAmount() { return 0; }
	protected abstract void writeComparison(Context ctx, int[] jumpLabels, 
			RegisterReservationInfo[] reservedRegisters, MemoryAllocationInfo[] allocatedCells);
}
