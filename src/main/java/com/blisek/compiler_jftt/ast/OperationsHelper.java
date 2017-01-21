package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class OperationsHelper {
	
	public static void changeValue(Context ctx, Register reg, long value) {
		BigInteger bi = reg.getValue();
		// TODO: implement this
		if(bi == null)
			changeValueUnknown(ctx, reg, value);
		else
			changeValueKnown(ctx, reg, value);
		changeValueUnknown(ctx, reg, value);
	}

	private static void changeValueKnown(Context ctx, Register reg, long value) {
		if(reg.getValue().longValue() == value)
			return;
		
		// TODO: implement correct way
		changeValueUnknown(ctx, reg, value);
	}

	private static void changeValueUnknown(Context ctx, Register reg, long value) {
		setRegisterValue(ctx, reg, value);
	}

	public static int storeRegister(Context ctx, Writer _writer, Register register) {
		int cellId = ctx.allocMemory(1);
		
		Writer writer = ctx.getWriter();
		Register helperRegister = ctx.getHelperRegister();
		changeValue(ctx, helperRegister, cellId);
		writer.write(generateOneArgumentInstruction(Instructions.STORE_i, register));
		
		return cellId;
	}
	
	public static void loadRegister(Context ctx, Register register, int cell) {
		Writer writer = ctx.getWriter();
		Register helpRegister = ctx.getHelperRegister();
		changeValue(ctx, helpRegister, cell);
		writer.write(generateOneArgumentInstruction(Instructions.LOAD_i, register));
	}
	
	public static void setRegisterValue(Context ctx, Register reg, long value) {
		setRegisterValue(ctx, reg, BigInteger.valueOf(value));
	}
	
	public static void setRegisterValue(Context ctx, Register reg, BigInteger bi) {
		Writer writer = ctx.getWriter();
		String binaryString = bi.toString(2);
		
		writer.write(generateOneArgumentInstruction(Instructions.ZERO_i, reg));
		for(int i = 0; i < binaryString.length(); ++i) {
			char c = binaryString.charAt(i);
			if(c == '0')
				writer.write(generateOneArgumentInstruction(Instructions.SHL_i, reg));
			else if(c == '1')
				writer.write(generateOneArgumentInstruction(Instructions.INC_i, reg));
			else
				throw new IllegalArgumentException("Binary string has invalid sign: " + c);
		}
		
		reg.setValue(bi);
	}
	
	public static String generateOneArgumentInstruction(String phrase, Register arg) {
		return String.format(phrase, arg.getId());
	}
}
