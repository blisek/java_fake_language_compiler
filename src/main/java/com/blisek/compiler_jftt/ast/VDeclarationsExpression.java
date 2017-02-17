package com.blisek.compiler_jftt.ast;

import java.util.ArrayList;
import java.util.List;

import com.blisek.compiler_jftt.structs.VDeclaration;

import beaver.Symbol;

public class VDeclarationsExpression extends Symbol {
	private final List<VDeclaration> vdeclarations;

	public VDeclarationsExpression() {
		this(new ArrayList<>(8));
	}
	
	public VDeclarationsExpression(List<VDeclaration> vdec) {
		this.vdeclarations = vdec;
	}

	public List<VDeclaration> getVdeclarations() {
		return vdeclarations;
	}
	
}
