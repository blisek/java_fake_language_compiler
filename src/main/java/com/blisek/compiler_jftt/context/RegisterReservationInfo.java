package com.blisek.compiler_jftt.context;

import java.math.BigInteger;

public final class RegisterReservationInfo implements AutoCloseable {
	private final int registerId;
	private final MemoryAllocationInfo storedPlace;
	private final Object oldValue;
	private final ValueType valueType;
	
	public RegisterReservationInfo(int registerId,
			MemoryAllocationInfo storedPlace, Object oldValue,
			ValueType valueType) {
		super();
		this.registerId = registerId;
		this.storedPlace = storedPlace;
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
	}

	@Override
	public void close() { free(); }
	
	public void free() {
		
	}
}
