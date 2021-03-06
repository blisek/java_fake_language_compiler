package com.blisek.compiler_jftt;


import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.blisek.compiler_jftt.ast.MultiplyExpression;
import com.blisek.compiler_jftt.exceptions.CompilationException;
import com.blisek.compiler_jftt.scanner.Parser;

public class Compiler {
	private static final Options cliOptions;
	private static File inputFile, outputFile;
	public static boolean debugMode;
	

	public static void main(String[] args) {

        if(!parseArguments(args)) {
            printHelp();
            return;
        }

        if(debugMode) {
            for (String arg : args) {
                System.out.println("Argument: " + arg);
            }
        }

        try {
			Parser.parse(inputFile, outputFile);
		}
		catch(beaver.Parser.Exception | CompilationException e) {
			System.err.print("Compile error: ");
			System.err.println(e.getMessage());
			if(debugMode)
				e.printStackTrace();
		}
		catch(IOException ioe) {
			System.err.println("IO error while reading/writing file: ");
			ioe.printStackTrace();
			if(debugMode)
				ioe.printStackTrace();
		}
		catch(Exception ex) {
			System.err.println("Unknown error occurred: ");
			ex.printStackTrace();
			if(debugMode)
				ex.printStackTrace();
		}
	}
	
	private static boolean parseArguments(String[] args) {
		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse(cliOptions, args);
			if(!parseInputFileName(cmd))
				return false;
			if(!parseOutputFileName(cmd))
				return false;
			parseDebugModeArg(cmd);
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}

	private static boolean parseOutputFileName(CommandLine cmd) {
		String outputFileName = cmd.getOptionValue('o');
		if(outputFileName == null) {
			return false;
		}

		outputFile = new File(outputFileName);
		return true;
	}

	private static boolean parseDebugModeArg(CommandLine cmd) {
		return (debugMode = cmd.hasOption("d"));
	}

	private static boolean parseInputFileName(CommandLine cmd) {
		String inputFileName = cmd.getOptionValue('i');
		if(inputFileName == null) {
			return false;
		}
		
		File inputF = new File(inputFileName);
		if(!inputF.exists())
			return false;
		
		inputFile = inputF;
		return true;
	}

	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		String programName = String.format("java %s", Compiler.class.getCanonicalName());
		formatter.printHelp(programName, cliOptions);
	}
	
	static {
		cliOptions = new Options();
		cliOptions.addOption("i", true, "input file");
		cliOptions.addOption("o", true, "output file name");
		cliOptions.addOption("d", false, "turn on debug mode");

	}
}
