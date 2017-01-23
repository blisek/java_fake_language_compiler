package com.blisek.compiler_jftt.structs;

import java.math.BigInteger;

import com.blisek.compiler_jftt.ast.OperationsHelper;
import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;

public final class RegisterReservationInfo implements AutoCloseable {
	private final Context context;
	private final Register register;
	private final MemoryAllocationInfo storedPlace;
	private final Object oldValue;
	private final ValueType valueType;
	private final boolean restoreValueWhenReleasing;
	private boolean stored;
	
	public RegisterReservationInfo(Context context, Register register,
			MemoryAllocationInfo storedPlace, Object oldValue,
			boolean restoreValueWhenReleasing) {
		super();
		this.context = context;
		this.register = register;
		this.oldValue = oldValue;
		
		ValueType valType = ValueType.UNKNOWN;
		if(oldValue != null) {
			Class<?> oldValueType = oldValue.getClass();
			if(oldValueType == BigInteger.class) {
				valType = ValueType.NUMERIC;
			}
			else if(oldValueType == VariableInfo.class) {
				valType = ValueType.VARIABLE;
			}
		} 

		this.valueType = valType;
		this.restoreValueWhenReleasing = restoreValueWhenReleasing;
		
		if(storedPlace == null && valType == ValueType.VARIABLE)
			this.storedPlace = ((VariableInfo)oldValue).getAssignedMemoryCell();
		else
			this.storedPlace = storedPlace;
	}
	
	public Register getRegister() {
		return register;
	}

	public boolean isRestoreValueWhenReleasing() {
		return restoreValueWhenReleasing;
	}

	public MemoryAllocationInfo getStoredPlace() {
		return storedPlace;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public ValueType getValueType() {
		return valueType;
	}

	@Override
	public void close() { free(); }
	
	public void free() {
		if(stored && restoreValueWhenReleasing) {
			OperationsHelper.loadRegister(context, register, storedPlace.getStartCell());
		}
		else {
			register.setTaken(false);
		}
	}
	
	/**
	 * Store in memory current value
	 */
	public void reserve() {
		if(storedPlace == null) return;
		OperationsHelper.storeRegisterValue(context, getRegister(), storedPlace, 0);
		stored = true;
	}
}
