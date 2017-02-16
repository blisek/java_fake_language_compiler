package com.blisek.compiler_jftt.scanner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.blisek.compiler_jftt.ast.Expression;
import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.ContextImpl;
import com.blisek.compiler_jftt.parser.ExpressionParser;

public class Parser {

	public static void parse(File inputFile, File outputFile) throws Exception {
		ExpressionParser parser = new ExpressionParser();
		FileReader fileReader = new FileReader(inputFile);
		ExpressionScanner scanner = ExpressionScannerFactory.newInstance(fileReader);
		
		Expression program = (Expression)parser.parse(scanner);
		Context context = new ContextImpl(5);
		program.write(context, null);
		
		try(FileWriter writer = new FileWriter(outputFile)) {
			context.getWriter().writeOutput(writer, context);
		}
	}
}
