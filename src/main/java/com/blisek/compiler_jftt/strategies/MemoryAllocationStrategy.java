package com.blisek.compiler_jftt.strategies;

import java.math.BigInteger;

import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.VariableInfo;

public interface MemoryAllocationStrategy extends Strategy {
	default MemoryAllocationInfo[] allocateNormalMemory(int size) {
		return allocateNormalMemory(BigInteger.valueOf(size));
	}
	
	default MemoryAllocationInfo[] allocateNearMemory(int size) {
		return allocateNearMemory(BigInteger.valueOf(size));
	}
	
	MemoryAllocationInfo[] allocateTemporaryMemory(int size);
	MemoryAllocationInfo[] allocateTemporaryMemory();
	MemoryAllocationInfo[] allocateMemoryForVariable(VariableInfo vi);
	MemoryAllocationInfo[] allocateNormalMemory(BigInteger size);
	MemoryAllocationInfo[] allocateNearMemory(BigInteger size);
}
