package com.blisek.compiler_jftt.ast;

import java.math.BigInteger;
import java.util.Objects;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.ValueType;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class OperationsHelper {
	
	public static void changeValue(Context ctx, Register reg, long value) {
//		BigInteger bi = reg.getValue();
		// TODO: implement this
//		if(bi == null)
//			changeValueUnknown(ctx, reg, value);
//		else
//			changeValueKnown(ctx, reg, value);
		changeValueUnknown(ctx, reg, value);
	}

//	private static void changeValueKnown(Context ctx, Register reg, long value) {
//		if(reg.getValue().longValue() == value)
//			return;
//		
//		// TODO: implement correct way
//		changeValueUnknown(ctx, reg, value);
//	}

	private static void changeValueUnknown(Context ctx, Register reg, long value) {
		setRegisterValue(ctx, reg, value);
	}

	public static MemoryAllocationInfo storeRegister(Context ctx, Writer _writer, Register register) {
		MemoryAllocationInfo cell = ctx.allocMemory(1);
		
		Writer writer = ctx.getWriter();
		Register helperRegister = ctx.getHelperRegister();
		changeValue(ctx, helperRegister, cell.getStartCell());
		writer.write(generateOneArgumentInstruction(Instructions.STORE_i, register));
		
		return cell;
	}
	
	public static void storeRegisterValue(Context ctx, Register register, MemoryAllocationInfo destinationCell, int offset) {
		Objects.requireNonNull(ctx, "context can't be null");
		Objects.requireNonNull(register, "register can't be null");
		Objects.requireNonNull(destinationCell, "destinationCell can't be null");
		
		
		Writer writer = ctx.getWriter();
		int cell = destinationCell.getStartCell() + offset;
		Register helpRegister = ctx.getHelperRegister();
		if(register.getId() == helpRegister.getId() && helpRegister.getValueType() == ValueType.NUMERIC) {
			if(((BigInteger)helpRegister.getValue()).longValue() != cell) {				
				if(register.isHelpRegister())
					throw new IllegalStateException("Used for store register is help register. Operation is unallowed");
			}
		}
		
		setRegisterValue(ctx, helpRegister, cell);
		writer.write(generateOneArgumentInstruction(Instructions.STORE_i, register));
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
