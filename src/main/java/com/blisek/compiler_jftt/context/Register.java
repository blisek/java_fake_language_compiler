package com.blisek.compiler_jftt.context;

import java.math.BigInteger;

import com.blisek.compiler_jftt.structs.ValueType;
import com.blisek.compiler_jftt.structs.VariableInfo;

public class Register {
	private final int id;
	private boolean taken;
	private int usedByLevel;
	private boolean isHelpRegister;
	private Object value;
	private ValueType valueType;
	
	public Register(int id) {
		super();
		this.id = id;
		this.taken = false;
		this.usedByLevel = -1;
		this.isHelpRegister = false;
		this.value = null;
		this.valueType = ValueType.UNKNOWN;
	}
	
	public Register(int id, long value) {
		this(id);
		this.value = BigInteger.valueOf(value);
	}
	
	public Register(int id, BigInteger integer) {
		this(id);
		this.value = integer;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public int getUsedByLevel() {
		return usedByLevel;
	}

	public void setUsedByLevel(int usedByLevel) {
		this.usedByLevel = usedByLevel;
	}

	public int getId() {
		return id;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		Class<?> valueClass = value.getClass();
		if(valueClass == BigInteger.class) {
			this.valueType = ValueType.NUMERIC;
		}
		else if (valueClass == VariableInfo.class) {
			this.valueType = ValueType.VARIABLE;
		}
		else {
			this.valueType = ValueType.UNKNOWN;
		}
	}

	public boolean isHelpRegister() {
		return isHelpRegister;
	}

	public void setHelpRegister(boolean isHelpRegister) {
		this.isHelpRegister = isHelpRegister;
	}

	public ValueType getValueType() {
		return valueType;
	}

//	public void setValueType(ValueType valueType) {
//		this.valueType = valueType;
//	}
	
}
