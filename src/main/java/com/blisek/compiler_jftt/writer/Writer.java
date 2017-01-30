package com.blisek.compiler_jftt.writer;

import java.io.IOException;

import com.blisek.compiler_jftt.context.Context;

public interface Writer {
	
	int getNextLineNumber();
	
	void write(String phrase, int label, int jumpDestination);
	
	void write(String phrase);
	
	void writeOutput(java.io.Writer writer, Context ctx) throws IOException;
}
