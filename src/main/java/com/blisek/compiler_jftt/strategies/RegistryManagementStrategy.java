package com.blisek.compiler_jftt.strategies;

import java.util.Collection;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;

public interface RegistryManagementStrategy extends Strategy {

	RegisterReservationInfo reserveRegister(Context ctx, MemoryAllocationInfo memoryCell, boolean restoreValueWhenReleasing, Collection<Integer> excludeRegisters);
	
}
