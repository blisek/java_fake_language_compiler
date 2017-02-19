package com.blisek.compiler_jftt.helpers;

import java.math.BigInteger;

import com.blisek.compiler_jftt.exceptions.GlobalVariableConcealedByLocalVariableCompilationException;
import com.blisek.compiler_jftt.exceptions.IndexOutOfRangeCompilationException;
import com.blisek.compiler_jftt.exceptions.InvalidUseOfVariableCompilationException;
import com.blisek.compiler_jftt.exceptions.ReadonlyVariableOverwriteAttemptCompilationException;
import com.blisek.compiler_jftt.exceptions.UsedUndeclaredVariableCompilationException;
import com.blisek.compiler_jftt.exceptions.UsedUninitialisedVariableCompilationException;
import com.blisek.compiler_jftt.structs.VariableInfo;

public final class Preconditions {

	private Preconditions() {
		// TODO Auto-generated constructor stub
	}

	public static void assureVariableIsDeclared(VariableInfo variable, int startLine, int startColumn, int endLine, int endColumn) {
		if(!variable.isVariableDeclared()) {
			String msg = "[%s-%s] Undeclared variable \"%s\" used.";
			String startPos = String.format("%d,%d", startLine, startColumn);
			String endPos = String.format("%d,%d", endLine, endColumn);
			throw new UsedUndeclaredVariableCompilationException(String.format(msg, startPos, endPos, variable.getVariableName()));
		}
	}
	
	public static void assureIndexInRange(VariableInfo variable, BigInteger index, int startLine, int startColumn, int endLine, int endColumn) {
		if(index.compareTo(variable.getLength()) >= 0) {
			String msg = "[%s-%s] Index %s out of variable's \"%s\" range (must be less than %s).";
			String startPos = String.format("%d,%d", startLine, startColumn);
			String endPos = String.format("%d,%d", endLine, endColumn);
			throw new IndexOutOfRangeCompilationException(String.format(msg, startPos, endPos, 
					index.toString(), variable.getVariableName(), variable.getLength().toString()));
		}
	}
	
	public static void assureNoArrayType(VariableInfo variable, int startLine, int startColumn, int endLine, int endColumn) {
		assureNoArrayType(variable, startLine, startColumn, endLine, endColumn, false);
	}
	
	public static void assureArrayType(VariableInfo variable, int startLine, int startColumn, int endLine, int endColumn) {
		assureNoArrayType(variable, startLine, startColumn, endLine, endColumn, true);
	}
	
	public static void assureNoArrayType(VariableInfo variable, int startLine, int startColumn, int endLine, int endColumn, boolean negate) {
		boolean condition = !BigInteger.ONE.equals(variable.getLength());
		if(negate ? !condition : condition) {
			String msg = "[%s-%s] Array variable \"%s\" used like single variable in assignment statement.";
			String startPos = String.format("%d,%d", startLine, startColumn);
			String endPos = String.format("%d,%d", endLine, endColumn);
			throw new InvalidUseOfVariableCompilationException(String.format(msg, startPos, endPos, variable.getVariableName()));
		}
	}
	
	public static void assureVariableIsNotReadonly(VariableInfo variable, int startLine, int startColumn, int endLine, int endColumn) {
		if(variable.isReadonly()) {
			String msg = "[%s-%s] Variable \"%s\" is readonly.";
			String startPos = String.format("%d,%d", startLine, startColumn);
			String endPos = String.format("%d,%d", endLine, endColumn);
			throw new ReadonlyVariableOverwriteAttemptCompilationException(String.format(msg, startPos, endPos, variable.getVariableName()));
		}
	}
	
	public static void assureValueAssigned(VariableInfo variable, int startLine, int startColumn, int endLine, int endColumn) {
		if(variable.getAssignedMemoryCells() == null || !variable.isValueAssigned()) {
			String msg = "[%s-%s] Use of uninitialised variable \"%s\"!";
			String startPos = String.format("%d,%d", startLine, startColumn);
			String endPos = String.format("%d,%d", endLine, endColumn);
			throw new UsedUninitialisedVariableCompilationException(String.format(msg, startPos, endPos, variable.getVariableName()));
		}
	}
	
	public static void assureVariableIsNotDeclared(String varName, int startLine, int startColumn, int endLine, int endColumn) {
		if(VariableInfo.isVariableDeclared(varName)) {
			String msg = "[%s-%s] Variable %s used as counter is already declared in global scope.";
			String startPos = String.format("%d,%d", startLine, startColumn);
			String endPos = String.format("%d,%d", endLine, endColumn);
			throw new GlobalVariableConcealedByLocalVariableCompilationException(String.format(msg, startPos, endPos, varName));
		}
	}
}
