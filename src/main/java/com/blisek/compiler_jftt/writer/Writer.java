package com.blisek.compiler_jftt.writer;

public interface Writer {
	
	void write(String phrase, int label, int jumpDestination);
	
	void write(String phrase);
}
