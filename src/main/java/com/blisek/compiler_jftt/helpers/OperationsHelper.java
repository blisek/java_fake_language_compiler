package com.blisek.compiler_jftt.helpers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.blisek.compiler_jftt.context.Context;
import com.blisek.compiler_jftt.context.Register;
import com.blisek.compiler_jftt.strategies.MemoryAllocationStrategy;
import com.blisek.compiler_jftt.strategies.RegistryManagementStrategy;
import com.blisek.compiler_jftt.structs.MemoryAllocationInfo;
import com.blisek.compiler_jftt.structs.RegisterReservationInfo;
import com.blisek.compiler_jftt.writer.Instructions;
import com.blisek.compiler_jftt.writer.Writer;

public class OperationsHelper {
	
//	public static void changeValue(Context ctx, Register reg, long value) {
////		BigInteger bi = reg.getValue();
//		// TODO: implement this
////		if(bi == null)
////			changeValueUnknown(ctx, reg, value);
////		else
////			changeValueKnown(ctx, reg, value);
//		changeValueUnknown(ctx, reg, value);
//	}
	
	public static void changeValue(Context ctx, Register reg, BigInteger value) {
		setRegisterValue(ctx, reg, value);
	}

//	private static void changeValueKnown(Context ctx, Register reg, long value) {
//		if(reg.getValue().longValue() == value)
//			return;
//		
//		// TODO: implement correct way
//		changeValueUnknown(ctx, reg, value);
//	}

//	private static void changeValueUnknown(Context ctx, Register reg, long value) {
//		setRegisterValue(ctx, reg, value);
//	}
	
	public static RegisterReservationInfo[] getRegisters(Context ctx, int count) {
		final RegisterReservationInfo[] regResInfo = new RegisterReservationInfo[count];
		final RegistryManagementStrategy rms = ctx.getRegistryManagementStrategy();
		final MemoryAllocationStrategy mas = ctx.getMemoryAllocationStrategy();
		final List<Integer> excludedRegisters = new ArrayList<>(5);
		
		for(int i = 0; i < count; ++i) {
			RegisterReservationInfo rri = rms.reserveRegister(ctx, excludedRegisters, false);
			final Register tmpRegister = rri.getRegister();
			excludedRegisters.add(tmpRegister.getId());
			if(tmpRegister.isTaken())
				rri.store(mas.allocateTemporaryMemory()[0]);
			regResInfo[i] = rri;
		}
		
		return regResInfo;
	}
	
	public static void freeRegister(Register reg) {
		reg.setTaken(false);
		reg.setUsedByLevel(-1);
	}
	
	public static void freeRegister(Context ctx, int regId) {
		final Register reg = ctx.getRegisterById(regId);
		freeRegister(reg);
	}

	public static MemoryAllocationInfo storeRegister(Context ctx, Writer _writer, Register register) {
		MemoryAllocationInfo cell = ctx.allocMemory(1);
		
		Writer writer = ctx.getWriter();
		Register helperRegister = ctx.getHelperRegister();
		changeValue(ctx, helperRegister, cell.getStartCell());
		writer.write(genInstruction(Instructions.STORE_i, register));
		
		return cell;
	}
	
	public static void storeRegisterValue(Context ctx, Register register, MemoryAllocationInfo destinationCell, BigInteger offset) {
		Objects.requireNonNull(ctx, "context can't be null");
		Objects.requireNonNull(register, "register can't be null");
		Objects.requireNonNull(destinationCell, "destinationCell can't be null");
		
		final Writer writer = ctx.getWriter();
		final BigInteger cell = destinationCell.getCellAddress(offset);
		final Register helpRegister = ctx.getHelperRegister();
		
		setRegisterValue(ctx, helpRegister, cell);
		writer.write(genInstruction(Instructions.STORE_i, register));
	}
	
//	public static void loadRegister(Context ctx, Register register, int cell) {
//		Writer writer = ctx.getWriter();
//		Register helpRegister = ctx.getHelperRegister();
//		changeValue(ctx, helpRegister, cell);
//		writer.write(genInstruction(Instructions.LOAD_i, register));
//	}
	
	public static void loadRegister(Context ctx, Register register, BigInteger cell) {
		Writer writer = ctx.getWriter();
		Register helpRegister = ctx.getHelperRegister();
		changeValue(ctx, helpRegister, cell);
		writer.write(genInstruction(Instructions.LOAD_i, register));
	}
	
//	public static void setRegisterValue(Context ctx, Register reg, long value) {
//		setRegisterValue(ctx, reg, BigInteger.valueOf(value));
//	}
	
	public static void setRegisterValue(Context ctx, Register reg, BigInteger bi) {
		Writer writer = ctx.getWriter();
		String binaryString = bi.toString(2);
		
		boolean firstLoop = true;
		writer.write(genInstruction(Instructions.ZERO_i, reg));
		for(int i = 0; i < binaryString.length(); ++i) {
			if(!firstLoop)
				writer.write(genInstruction(Instructions.SHL_i, reg));
			else
				firstLoop = false;
			
			if(binaryString.charAt(i) == '1')
				writer.write(genInstruction(Instructions.INC_i, reg));
		}
		
		reg.setValue(bi);
	}
	
	public static void setRegisterValue(Context ctx, Register reg, String binaryString, BigInteger bi) {
		Writer writer = ctx.getWriter();
		
		writer.write(genInstruction(Instructions.ZERO_i, reg));
		for(int i = 0; i < binaryString.length(); ++i) {
			char c = binaryString.charAt(i);
			if(c == '0')
				writer.write(genInstruction(Instructions.SHL_i, reg));
			else if(c == '1')
				writer.write(genInstruction(Instructions.INC_i, reg));
			else
				throw new IllegalArgumentException("Binary string has invalid sign: " + c);
		}
		
		reg.setValue(bi != null ? bi : binaryString);
	}
	
	public static String genInstruction(String phrase, Register arg) {
		return genInstruction(phrase, arg.getId());
	}
	
	public static String genInstruction(String phrase, int regId) {
		return String.format(phrase, Integer.toString(regId));
	}
	
	public static String genJumpInstruction(String phrase, long destination) {
		return String.format("%s %s", phrase, Long.toString(destination));
	}
	
	public static int calculateInitializationCost(String binaryString) {
		final int binaryStringLength = binaryString.length();
		int cost = binaryStringLength - 1;
		for(int i = 0; i < binaryStringLength; ++i)
			if(binaryString.charAt(i) == '1')
				++cost;
		
		return cost;
	}
//	
//	public static int calculateInitializationCostBi(String binaryString) {
//		final BigInteger number = BigInteger.valueOf(binaryString.length() - 1);
//		
//	}
	
	public static int calculateInitializationCost(BigInteger bi) {
		return calculateInitializationCost(bi.toString(2));
	}
}
