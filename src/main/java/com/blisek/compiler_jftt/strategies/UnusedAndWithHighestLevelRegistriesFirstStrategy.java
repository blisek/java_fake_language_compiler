package com.blisek.compiler_jftt.strategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.structs.ValueType;

public class UnusedAndWithHighestLevelRegistriesFirstStrategy implements RegistryManagementStrategy {

	@Override
	public RegisterReservationInfo reserveRegister(Context ctx, Collection<Integer> excludeRegisters) {
		Register reservedRegister = chooseRegister(ctx, excludeRegisters);
		reservedRegister.setTaken(true);
		reservedRegister.setUsedByLevel(ctx.getLevel());
		return new RegisterReservationInfo(ctx, reservedRegister);
	}
	
	private Register chooseRegister(Context ctx, final Collection<Integer> excludeReg) {
		final int currentLevel = ctx.getLevel();
		final Predicate<Register> excludeRegisters = (r -> excludeReg == null || !excludeReg.contains(r.getId()));
		// lower level value = higher level
		final Predicate<Register> onlyHigherLevels = (r -> r.getUsedByLevel() < currentLevel);
		final Predicate<Register> onlyLowerOrEqualLevels = (r -> r.getUsedByLevel() >= currentLevel);
		final Comparator<Register> registerLevelComparator = (r1, r2) -> Integer.compare(r1.getUsedByLevel(), r2.getUsedByLevel());
		
		Optional<Register> upperLevelNoHelperRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.filter(pNotTaken)
				.filter(onlyHigherLevels)
				.min(registerLevelComparator);
		
		if(upperLevelNoHelperRegister.isPresent())
			return upperLevelNoHelperRegister.get();
		
		Optional<Register> sameLevelNoHelperRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.filter(pNotTaken)
				.filter(onlyLowerOrEqualLevels)
				.min(registerLevelComparator);
		
		if(sameLevelNoHelperRegister.isPresent())
			return sameLevelNoHelperRegister.get();
		
		Optional<Register> anyNotHelperHigherLevelRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.filter(onlyHigherLevels)
				.min(registerLevelComparator);
		
		if(anyNotHelperHigherLevelRegister.isPresent())
			return anyNotHelperHigherLevelRegister.get();
		
		Optional<Register> anyNotHelperRegister = Arrays.stream(ctx.getRegisters())
				.filter(excludeRegisters)
				.filter(pNotHelpRegister)
				.min(registerLevelComparator);
		
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
