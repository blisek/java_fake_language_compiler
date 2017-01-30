package com.blisek.compiler_jftt.strategies;

import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;

public interface MemoryAllocationStrategy extends Strategy {
	MemoryAllocationInfo[] allocateNormalMemory(int size);
	MemoryAllocationInfo[] allocateNearMemory(int size);
	MemoryAllocationInfo[] allocateTemporaryMemory();
	MemoryAllocationInfo[] allocateTemporaryMemory(int size);
}
