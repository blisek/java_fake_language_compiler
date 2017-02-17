package com.blisek.compiler_jftt.structs;

import java.util.ArrayList;
import java.util.List;

import com.blisek.compiler_jftt.ast.VDeclarationsExpression;

public final class VDeclarationHelper {

	public static VDeclarationsExpression extendOrMakeListOfVDec(VDeclaration newElement, VDeclarationsExpression vdecs) {
		if(vdecs == null)
			vdecs = new VDeclarationsExpression();
		vdecs.getVdeclarations().add(newElement);
		return vdecs;
	}
}
