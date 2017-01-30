package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;

import com.blisek.compiler_jftt.ast.OperationsHelper;
import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;

public final class RegisterReservationInfo implements AutoCloseable {
	private final Context context;
	private final Register register;
	private MemoryAllocationInfo memoryAllocation;
	private int lastLevel;
	
	public RegisterReservationInfo(Context context, Register register) {
		this.context = context;
		this.register = register;
	}
	
	public Register getRegister() {
		return register;
	}
	
	@Override
	public void close() { free(); }
	
	public void free() {
		if(memoryAllocation != null) {
			OperationsHelper.loadRegister(context, register, memoryAllocation.getCellAddress(BigInteger.ZERO));
			getRegister().setUsedByLevel(lastLevel);
			memoryAllocation.free();
		}
		getRegister().setTaken(false);
	}
	
	public void store(MemoryAllocationInfo memAllocInfo) {
		this.memoryAllocation = memAllocInfo;
		this.lastLevel = getRegister().getUsedByLevel();
		getRegister().setUsedByLevel(context.getLevel());
		OperationsHelper.storeRegisterValue(context, getRegister(), memAllocInfo, BigInteger.ZERO);
	}
}
