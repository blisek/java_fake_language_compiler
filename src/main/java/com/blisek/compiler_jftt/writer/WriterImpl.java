package com.blisek.compiler_jftt.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.blisek.compiler_jftt.context.Context;

public class WriterImpl implements Writer {
	private static final int WRITE_CANDIDATES_LENGTH = 100;
	private final Context ctx;
	private ArrayList<WriteCandidate> writeCandidates = new ArrayList<>(WRITE_CANDIDATES_LENGTH);
	private int lineCounter = 0;
	
	public WriterImpl(Context ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public void write(String phrase, int label, int jumpDestination) {
		Optional<Integer> line = ctx.getLineForLabel(jumpDestination);
		if(line.isPresent()) {
			writeCandidates.add(new WriteCandidate(String.format(phrase, line.get())));
		} 
		else {
			writeCandidates.add(new WriteCandidate(phrase, jumpDestination));
		}
		
		if(label >= 0)
			ctx.pairLabelWithLine(label, lineCounter);
		
		++lineCounter;
	}
	
	
	
	@Override
	public void write(String phrase) {
		write(phrase, -1, -1);
	}

	public void writeOutput(java.io.Writer writer, Context ctx) throws IOException {
		for(WriteCandidate wc : writeCandidates) {
			int jumpDestination = wc.getJumpDestination();
			if(jumpDestination >= 0) {
				writer.write(String.format("%s %d", wc.getInstruction(), ctx.getLineForLabel(jumpDestination).get()));
			}
			else {
				writer.write(wc.getInstruction());
			}
			
			writer.write(System.lineSeparator());
		}
	}
	
	
	
	@Override
	public int getNextLineNumber() {
		return lineCounter;
	}



	private static class WriteCandidate {
		private final int jumpDestination;
		private final String instruction;
		
		public WriteCandidate(String instruction, int jumpDestination) {
			super();
			this.jumpDestination = jumpDestination;
			this.instruction = instruction;
		}
		
		public WriteCandidate(String instruction) {
			this(instruction, -1);
		}
		

		public int getJumpDestination() {
			return jumpDestination;
		}

		public String getInstruction() {
			return instruction;
		}

	}

}
