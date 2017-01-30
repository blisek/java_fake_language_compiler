package com.blisek.compiler_jftt.strategies;

import java.util.ArrayList;

import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;

public class SimpleMemoryAllocationStrategy implements MemoryAllocationStrategy {
	private final MemoryAllocationInfo[] temporaryMemory;
	private final ArrayList<MemoryAllocationInfo> normalMemory;
	private int temporaryMemoryIndex;
	private int nextCell;

	public SimpleMemoryAllocationStrategy(int tempMemSize) {
		this.temporaryMemory = new MemoryAllocationInfo[tempMemSize];
		this.normalMemory = new ArrayList<>(64);
		temporaryMemoryIndex = 0;
		initTemporaryMemory();
	}

	private void initTemporaryMemory() {
		for(int i = 0; i < temporaryMemory.length; ++i)
			temporaryMemory[i] = new MemoryAllocationInfo(nextCell++, 1);
	}

	@Override
	public MemoryAllocationInfo[] allocateNormalMemory(int size) {
		MemoryAllocationInfo mai = new MemoryAllocationInfo(nextCell, size);
		if(size > 1) {
			normalMemory.ensureCapacity(normalMemory.size() + size + 1);
			for(int i = 0; i < size; ++i)
				normalMemory.add(mai);
		}
		else {
			normalMemory.add(mai);
		}
		nextCell += size;
		return new MemoryAllocationInfo[] { mai };
	}

	@Override
	public MemoryAllocationInfo[] allocateNearMemory(int size) {
		return allocateNormalMemory(size);
	}

	@Override
	public MemoryAllocationInfo[] allocateTemporaryMemory() {
		temporaryMemoryIndex = nextIndex(temporaryMemory, temporaryMemoryIndex);
		if(temporaryMemoryIndex < 0) {
			return allocateNearMemory(1);
		}
		else {
			MemoryAllocationInfo mai = temporaryMemory[temporaryMemoryIndex];
			mai.setAllocated(true);
			return new MemoryAllocationInfo[] { mai };
		}
	}
	
	@Override
	public MemoryAllocationInfo[] allocateTemporaryMemory(int size) {
		int blockStart = -1;
		for(int i = 0; i <= temporaryMemory.length - size; ++i) {
			if((blockStart = findContinuousMemoryBlock(temporaryMemory, i, i, size)) >= 0)
				break;
		}
		
		if(blockStart < 0)
			return allocateNearMemory(size);
		
		temporaryMemoryIndex = blockStart + size - 1;
		MemoryAllocationInfo[] allocatedBlock = new MemoryAllocationInfo[size];
		for(int i = 0; i < size; ++i) {
			allocatedBlock[i] = temporaryMemory[blockStart + i];
			allocatedBlock[i].setAllocated(true);
		}
			
		return allocatedBlock;
	}
	
	private int findContinuousMemoryBlock(MemoryAllocationInfo[] arr, int startIndex, int index, int countdown) {
		if(countdown <= 0)
			return startIndex;
		
		if(nextIndex(arr, index) == index)
			return findContinuousMemoryBlock(arr, startIndex, index+1, countdown-1);
		else
			return -1;
	}
	
	private int nextIndex(MemoryAllocationInfo[] arr, int startIndex) {
		final int arrSize = arr.length;
		final int loopStopValue = Math.max(0, startIndex - 1);
		for(int index = Math.max(0, startIndex); index != loopStopValue; index = (index+1) % arrSize) {
			MemoryAllocationInfo mai = arr[index];
			
			if(!mai.isAllocated()) {
				return index;
			}
		}
		
		return -1;
	}

}
