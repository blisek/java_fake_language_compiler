package com.blisek.compiler_jftt.structs;

import java.util.Arrays;

public class Deallocator implements AutoCloseable {
	private final AutoCloseable[] autoCloseables;
	
	public Deallocator(AutoCloseable... autoCloseables) {
		this.autoCloseables = autoCloseables;
	}

	@Override
	public void close() {
		for(AutoCloseable ac : autoCloseables) {
			try {
				ac.close();
			} catch(Exception ex) {}
		}
	}
	
	public static <T extends AutoCloseable> Deallocator of(T[] arr) {
		if(arr == null) {
			return new Deallocator();
		}
		return new Deallocator(Arrays.stream(arr).toArray(size -> new AutoCloseable[size]));
	}
	
	public static <T extends AutoCloseable> Deallocator of(T obj) {
		if(obj == null)
			return new Deallocator();
		return new Deallocator(obj);
	}
}
