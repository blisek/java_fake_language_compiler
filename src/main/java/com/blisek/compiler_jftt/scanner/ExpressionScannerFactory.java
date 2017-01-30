package com.blisek.compiler_jftt.scanner;

import java.io.Reader;

public class ExpressionScannerFactory {

	public static ExpressionScanner newInstance(Reader reader) {
		return new ExpressionScanner(reader);
	}

}
