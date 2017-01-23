package com.blisek.compiler_jftt.context;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import com.blisek.compiler_jftt.ast.OperationsHelper;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.writer.Writer;
import com.blisek.compiler_jftt.writer.WriterImpl;

public class ContextImpl implements Context {
	private final List<ChangeLevelListener> valueRestoreListener;
	private final Map<Integer, Integer> labelsAssociations;
	private final Register[] registers;
	private final Writer writer;
	private int level;
	private int nextFreeMemoryCell;
	
	public ContextImpl(int registersCount) {
		labelsAssociations = new HashMap<>();
		registers = IntStream.range(0, registersCount)
				.mapToObj(Register::new)
				.toArray(size -> new Register[size]);
		registers[0].setHelpRegister(true);
		level = 0;
		valueRestoreListener = new ArrayList<>(12);
		writer = new WriterImpl(this);
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

//	@Override
//	public Register reserveRegister(Writer writer, boolean restoreOnLevelChange) {
//		final Register register = chooseRegisterForTake();
//		final int currentLevel = getLevel();
//		final int storedCellId = OperationsHelper.storeRegister(this, null, register);
//		if(restoreOnLevelChange) {
//			valueRestoreListener.add((from, to, ctx) -> {
//				if(from == currentLevel && to < from) {
//					OperationsHelper.loadRegister(this, register, storedCellId);
//					return true;
//				}
//				return false;
//			});
//		}
//		register.setUsedByLevel(currentLevel);
//		return register;
//	}

	private Register chooseRegisterForTake() {
		Optional<Register> freeRegister = Arrays.stream(registers).filter(r -> !r.isTaken()).findFirst();
		if(freeRegister.isPresent())
			return freeRegister.get();
		
		// select most non-local register
		Optional<Register> takenRegisterWithLowestLevel = Arrays.stream(registers)
				.filter(r -> !r.isHelpRegister())
				.min((r1, r2) -> Integer.compare(r1.getUsedByLevel(), r2.getUsedByLevel()));
		
		if(takenRegisterWithLowestLevel.isPresent())
			return takenRegisterWithLowestLevel.get();
		
		// if still not found
		return registers[registers.length-1];
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

//	@Override
//	public Register getCounterRegister() {
//		Optional<Register> reg = Arrays.stream(registers).filter(Register::isCounter).findFirst();
//		if(reg.isPresent())
//			return reg.get();
//		
//		
//	}

	@Override
	public Register getHelperRegister() {
		return Arrays.stream(registers)
				.filter(Register::isHelpRegister)
				.findFirst()
				.get();
	}

	@Override
	public Writer getWriter() {
		return writer;
	}
	
	@Override
	public Register[] getRegisters() {
		return registers;
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
