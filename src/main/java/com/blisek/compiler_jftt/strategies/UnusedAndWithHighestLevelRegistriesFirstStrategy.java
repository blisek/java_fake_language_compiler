package com.blisek.compiler_jftt.strategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.structs.ValueType;

public class UnusedAndWithHighestLevelRegistriesFirstStrategy implements RegistryManagementStrategy {

	@Override
	public RegisterReservationInfo reserveRegister(Context ctx, MemoryAllocationInfo memoryCell, boolean restoreValueWhenReleasing, Collection<Integer> excludeRegisters) {
		Register reservedRegister = chooseRegister(ctx, excludeRegisters);
		ValueType valueType = reservedRegister.getValueType();
		MemoryAllocationInfo mai = null;
		if(memoryCell == null && (valueType == ValueType.UNKNOWN || valueType == ValueType.NUMERIC)) {
			mai = ctx.allocMemory(1);
		}
		else if(memoryCell != null) {
			mai = memoryCell;
		}
		reservedRegister.setTaken(true);
		reservedRegister.setUsedByLevel(ctx.getLevel());
		return new RegisterReservationInfo(ctx, reservedRegister, mai, reservedRegister.getValue(), restoreValueWhenReleasing);
	}
	
	private Register chooseRegister(Context ctx, final Collection<Integer> excludeReg) {
		final int currentLevel = ctx.getLevel();
		final Predicate<Register> excludeRegisters = (r -> excludeReg == null || !excludeReg.contains(r.getId()));
		// lower level value = higher level
		final Predicate<Register> onlyHigherLevels = (r -> r.getUsedByLevel() < currentLevel);
		final Predicate<Register> onlyLowerOrEqualLevels = (r -> r.getUsedByLevel() >= currentLevel);
		
		Optional<Register> upperLevelNoHelperRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.filter(pNotTaken)
				.filter(onlyHigherLevels)
				.findFirst();
		
		if(upperLevelNoHelperRegister.isPresent())
			return upperLevelNoHelperRegister.get();
		
		Optional<Register> sameLevelNoHelperRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.filter(pNotTaken)
				.filter(onlyLowerOrEqualLevels)
				.findFirst();
		
		if(sameLevelNoHelperRegister.isPresent())
			return sameLevelNoHelperRegister.get();
		
		Optional<Register> anyNotHelperHigherLevelRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.filter(onlyHigherLevels)
				.findAny();
		
		if(anyNotHelperHigherLevelRegister.isPresent())
			return anyNotHelperHigherLevelRegister.get();
		
		Optional<Register> anyNotHelperRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.findAny();
		
		if(anyNotHelperRegister.isPresent())
			return anyNotHelperRegister.get();
		
		Register helpRegister = ctx.getHelperRegister();
		if(excludeRegisters.test(helpRegister))
			return helpRegister;
		
		throw new IllegalStateException("No register is available.");
	}
	
	
	private final Predicate<Register> pNotHelpRegister = (r -> !r.isHelpRegister());
	private final Predicate<Register> pNotTaken = (r -> !r.isTaken());

}
