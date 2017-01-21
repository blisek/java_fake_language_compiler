package com.blisek.compiler_jftt.context;

@FunctionalInterface
public interface ChangeLevelListener {

	/**
	 * 
	 * @param from
	 * @param to
	 * @param ctx
	 * @return true if listener done with job
	 */
	boolean onLevelChange(int from, int to, Context ctx);
	
}
