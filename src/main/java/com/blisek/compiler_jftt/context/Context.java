package com.blisek.compiler_jftt.context;

import java.util.Optional;

import com.blisek.compiler_jftt.writer.Writer;

public interface Context {
	
	void pairLabelWithLine(int label, int line);
	
	Optional<Integer> getLineForLabel(int label);
	
	Register reserveRegister(Writer writer, boolean restorePreviousValueOnLevelChange);
	
//	void addChangeLevelListener(ChangeLevelListener changeLevelListener);
	
//	Register getCounterRegister();
	
	Register getHelperRegister();
	
	void addRestoreValueCallback(ChangeLevelListener valueCallback);

	void increaseLevel();
	
	void decreaseLevel();
	
	int getLevel();
	
	int allocMemory(int size);
	
	Writer getWriter();
}
