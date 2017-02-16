package com.blisek.compiler_jftt.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import com.blisek.compiler_jftt.strategies.EagerMemoryAllocationStrategy;
import com.blisek.compiler_jftt.strategies.MemoryAllocationStrategy;
import com.blisek.compiler_jftt.strategies.RegistryManagementStrategy;
import com.blisek.compiler_jftt.strategies.UnusedAndWithHighestLevelRegistriesFirstStrategy;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.writer.Writer;
import com.blisek.compiler_jftt.writer.WriterImpl;

public class ContextImpl implements Context {
	public static final int TEMPORARY_MEMORY_CELL_COUNT = 5;
	private final List<ChangeLevelListener> valueRestoreListener;
	private final Map<Integer, Integer> labelsAssociations;
	private final Register[] registers;
	private final Writer writer;
	private int level;
	private int nextFreeMemoryCell;
	private final RegistryManagementStrategy registryManagementStrategy;
	private final MemoryAllocationStrategy memoryAllocationStrategy;
	
	public ContextImpl(int registersCount) {
		labelsAssociations = new HashMap<>();
		registers = IntStream.range(0, registersCount)
				.mapToObj(Register::new)
				.toArray(size -> new Register[size]);
		registers[0].setHelpRegister(true);
		level = 0;
		valueRestoreListener = new ArrayList<>(12);
		writer = new WriterImpl(this);
		registryManagementStrategy = new UnusedAndWithHighestLevelRegistriesFirstStrategy();
		memoryAllocationStrategy = new EagerMemoryAllocationStrategy(TEMPORARY_MEMORY_CELL_COUNT);
	}

	@Override
	public void pairLabelWithLine(int label, int line) {
		if(labelsAssociations.putIfAbsent(label, line) != null)
			throw new IllegalStateException("[INTERNAL] Second association with same label");
	}

	@Override
	public Optional<Integer> getLineForLabel(int label) {
		return Optional.ofNullable(labelsAssociations.get(label));
	}

	@Override
	public void addRestoreValueCallback(ChangeLevelListener valueCallback) {
		
	}

	@Override
	public void increaseLevel() {
		int oldLevel = level++;
		notifyValueRestoreListener(oldLevel, level);
	}

	@Override
	public void decreaseLevel() {
		if(level == 0) return;
		int oldLevel = level--;
		notifyValueRestoreListener(oldLevel, level);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public MemoryAllocationInfo allocMemory(int size) {
		int tmp = nextFreeMemoryCell;
		nextFreeMemoryCell += size;
		return new MemoryAllocationInfo(tmp, size);
	}
	
	@Override
	public Register getHelperRegister() {
		return Arrays.stream(registers)
				.filter(Register::isHelpRegister)
				.findFirst()
				.get();
	}

	@Override
	public Register getRegisterById(final int id) {
		Optional<Register> reg =  Arrays.stream(registers)
				.filter(r -> r.getId() == id)
				.findFirst();
		
		if(reg.isPresent())
			return reg.get();
		else
			return null;
	}

	@Override
	public Writer getWriter() {
		return writer;
	}
	
	@Override
	public Register[] getRegisters() {
		return registers;
	}
	
	@Override
	public RegistryManagementStrategy getRegistryManagementStrategy() {
		return registryManagementStrategy;
	}

	@Override
	public MemoryAllocationStrategy getMemoryAllocationStrategy() {
		return memoryAllocationStrategy;
	}

	private void notifyValueRestoreListener(int oldLevel, int newLevel) {
		ListIterator<ChangeLevelListener> it = valueRestoreListener.listIterator();
		while(it.hasNext()) {
			ChangeLevelListener listener = it.next();
			if(listener.onLevelChange(oldLevel, newLevel, this))
				it.remove();
		}
	}
	
}
