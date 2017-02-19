package com.blisek.compiler_jftt.parser;

import com.blisek.compiler_jftt.ast.*;
import java.util.List;
import com.blisek.compiler_jftt.structs.*;
import java.math.BigInteger;
import beaver.*;
import java.util.ArrayList;

/**
 * This class is a LALR parser generated by
 * <a href="http://beaver.sourceforge.net">Beaver</a> v0.9.6.1
 * from the grammar specification "parser.grammar".
 */
public class ExpressionParser extends Parser {
	static public class Terminals {
		static public final short EOF = 0;
		static public final short PIDENTIFIER = 1;
		static public final short SEMIC = 2;
		static public final short NUMBER = 3;
		static public final short ZERO = 4;
		static public final short ONE = 5;
		static public final short IF = 6;
		static public final short WHILE = 7;
		static public final short READ = 8;
		static public final short WRITE = 9;
		static public final short SKIP = 10;
		static public final short RBRACKET = 11;
		static public final short PLUS = 12;
		static public final short MINUS = 13;
		static public final short MULT = 14;
		static public final short MOD = 15;
		static public final short LBRACKET = 16;
		static public final short DIV = 17;
		static public final short THEN = 18;
		static public final short ELSE = 19;
		static public final short ENDIF = 20;
		static public final short VAR = 21;
		static public final short BEGIN = 22;
		static public final short END = 23;
		static public final short DO = 24;
		static public final short ENDWHILE = 25;
		static public final short ASSIGN = 26;
		static public final short EQ = 27;
		static public final short NEQ = 28;
		static public final short LT = 29;
		static public final short GT = 30;
		static public final short LE = 31;
		static public final short GE = 32;
	}

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9p5bKbq55KKXl#g90OP97O88X12EkdC0uH9DCwYA0eYAeg24yx3peqB1tJXneqRNUY2ZPw" +
		"Z6pTktBZnE1l5A28oYSWWA282OjJhLvLRHNMdoH4tl3xV#$ztNzNjL#$TxfA2yvNJU5sYot" +
		"I5hjHOTQj7lPgbsQhL68MwNLLQh6MwKIkrAehP#yIMP68FgK9dC5EhizMdYtLjzdepecUf9" +
		"ivMgMejBSwcCybstOcO7MOzgpLEAtIr3U0dsXvDKBqQrMu$gg0vMg25kf1xBs2smNQLv9j4" +
		"lXkAOZarGricjaPtwcwjrNrwGDCqMNKwH#TovHJMEaCpSLDnUJNXfkDQr8gx1jUXJjnot3p" +
		"DzsSUTZUbxk7KFPAwHrEdq#AYFSsfIzVJpuNp83hvVhW8BeNBOH5SnIwEuQIgEUkTz3dT0d" +
		"V0NN0Ft0ltmuEsmxRPLllElhLDjj6#jgziqBwmt#oG$Me7xHVRRT$R5ll6lhGVRBjjjWswM" +
		"RVgDaupJzLDe38cihTjQeztjai1CtNiUpCLLHrNnzWurZqYriAP1NmggAegdZGx9uz6Tqxc" +
		"paabsQQMP0lZzOJKG27roUf2TZ3n0NvujZXVJPaLvsDEnAPHIVLKLmFr5L3VoGf7TxdKJKb" +
		"Ta7cwnDMahc9a5dw3fyCbUr18tUWqiejDz67Sd$pAy7z#fCUUfZEpuv7I6g0Vna6lnmgGXy" +
		"K#NWJTi1HMmEeexdChuKbutCTFk3xb#fphir8m7gP3FUFdqHd0zmSp6R#0DWBV6pGnVX5j1" +
		"jOIj40hj44xT40droo1b$1TcVMymZXQwtgq1tqDxKNVH6Vnt4VnR#5des#ZVUWxw1pqNNGk" +
		"#XwiWrLG1skXoVD$1Cz4#mXz7dkL##Qv7y3F7$R1Wiowri2W#qskMrrtkkvnFU3wChm1hoT" +
		"vMFz#679$0AbDvZosFJkcTMV7fmgjlsIyh6Iyl6Gy#1$pRYmP9tkosNMBwxRCDJjSa$tQvR" +
		"hRjJLIF#Ez4DNPFf$Ru1hLShA$wpAv3uworaEkXrsFk1vpJNvBGoUvFweLsL$oPaEknvKs#" +
		"zm#jKzYzp5GPVOXVA1JR3PWdus8ZOWKpVwHkcDnVpJkZy3lzcViVmPgpVR3Jx0Fzje$yTpV" +
		"T0lvTwZYRHH2fE9D44B0lrWbL4CLL3WXxxkWB0Ncoz5MXlOoT9IXiuIkCkJvxYoDCPL5zFm" +
		"BlojMgG==");

	private final Action[] actions;

	public ExpressionParser() {
		super(PARSING_TABLES);
		actions = new Action[] {
			new Action() {	// [0] program = VAR vdeclarations.v BEGIN commands.c END
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 2];
					final VDeclarationsExpression v = (VDeclarationsExpression) _symbol_v.value;
					final Symbol _symbol_c = _symbols[offset + 4];
					final Expression c = (Expression) _symbol_c.value;
					 return new ProgramExpression(c, v);
				}
			},
			new Action() {	// [1] vdeclarations = vdeclarations.v PIDENTIFIER.p
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final VDeclarationsExpression v = (VDeclarationsExpression) _symbol_v.value;
					final Symbol _symbol_p = _symbols[offset + 2];
					final String p = (String) _symbol_p.value;
					 VDeclaration vd = VDeclaration.of(p); 
						return VDeclarationHelper.extendOrMakeListOfVDec(vd, v);
				}
			},
			new Action() {	// [2] vdeclarations = vdeclarations.v PIDENTIFIER.p LBRACKET NUMBER.n RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final VDeclarationsExpression v = (VDeclarationsExpression) _symbol_v.value;
					final Symbol _symbol_p = _symbols[offset + 2];
					final String p = (String) _symbol_p.value;
					final Symbol _symbol_n = _symbols[offset + 4];
					final BigInteger n = (BigInteger) _symbol_n.value;
					 
						VDeclaration vd = VDeclaration.arrayOf(p, n); 
						return VDeclarationHelper.extendOrMakeListOfVDec(vd, v);
				}
			},
			new Action() {	// [3] vdeclarations = 
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new VDeclarationsExpression();
				}
			},
			new Action() {	// [4] commands = commands.s command.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_s = _symbols[offset + 1];
					final Expression s = (Expression) _symbol_s.value;
					final Symbol _symbol_c = _symbols[offset + 2];
					final Expression c = (Expression) _symbol_c.value;
					 return new Expression(s, c);
				}
			},
			new Action() {	// [5] commands = command.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final Expression c = (Expression) _symbol_c.value;
					 return c;
				}
			},
			new Action() {	// [6] command = identifier.i ASSIGN expression.e SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 1];
					final ValueExpression i = (ValueExpression) _symbol_i.value;
					final Symbol _symbol_e = _symbols[offset + 3];
					final Expression e = (Expression) _symbol_e.value;
					 return new AssignmentExpression(i, e);
				}
			},
			new Action() {	// [7] command = IF condition.c THEN commands.cs1 ELSE commands.cs2 ENDIF
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 2];
					final ConditionExpression c = (ConditionExpression) _symbol_c.value;
					final Symbol _symbol_cs1 = _symbols[offset + 4];
					final Expression cs1 = (Expression) _symbol_cs1.value;
					final Symbol _symbol_cs2 = _symbols[offset + 6];
					final Expression cs2 = (Expression) _symbol_cs2.value;
					 return new IfExpression(c, cs1, cs2);
				}
			},
			new Action() {	// [8] command = WHILE condition.c DO commands.cs ENDWHILE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 2];
					final ConditionExpression c = (ConditionExpression) _symbol_c.value;
					final Symbol _symbol_cs = _symbols[offset + 4];
					final Expression cs = (Expression) _symbol_cs.value;
					 return new WhileExpression(c, cs);
				}
			},
			new Action() {	// [9] command = READ identifier.i SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 2];
					final ValueExpression i = (ValueExpression) _symbol_i.value;
					 return new ReadExpression(i);
				}
			},
			new Action() {	// [10] command = WRITE value.v SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 2];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new WriteExpression(v);
				}
			},
			new Action() {	// [11] command = SKIP SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new SkipExpression();
				}
			},
			Action.RETURN,	// [12] expression = value
			Action.RETURN,	// [13] expression = addition
			Action.RETURN,	// [14] expression = subtraction
			Action.RETURN,	// [15] expression = multiplication
			Action.RETURN,	// [16] expression = division
			Action.RETURN,	// [17] expression = modulo
			new Action() {	// [18] condition = value.v1 EQ value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new EqualsConditionExpression(v1, v2);
				}
			},
			new Action() {	// [19] condition = value.v1 NEQ value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return null;
				}
			},
			new Action() {	// [20] condition = value.v1 LT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return null;
				}
			},
			new Action() {	// [21] condition = value.v1 GT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return null;
				}
			},
			new Action() {	// [22] condition = value.v1 LE value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return null;
				}
			},
			new Action() {	// [23] condition = value.v1 GE value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return null;
				}
			},
			new Action() {	// [24] addition = value.v PLUS ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [25] addition = ZERO PLUS value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [26] addition = value.v1 PLUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new AdditionExpression(v1, v2);
				}
			},
			new Action() {	// [27] addition = value.v1 PLUS ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					 return new AdditionExpression(new NumberValueExpression(BigInteger.ONE), v1);
				}
			},
			new Action() {	// [28] addition = ONE PLUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new AdditionExpression(new NumberValueExpression(BigInteger.ONE), v2);
				}
			},
			new Action() {	// [29] subtraction = value.v MINUS ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [30] subtraction = ZERO MINUS value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [31] subtraction = value.v1 MINUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new SubtractionExpression(v1, v2);
				}
			},
			new Action() {	// [32] subtraction = ONE MINUS value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new SubtractionExpression(new NumberValueExpression(BigInteger.ONE), v);
				}
			},
			new Action() {	// [33] subtraction = value.v MINUS ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new SubtractionExpression(v, new NumberValueExpression(BigInteger.ONE));
				}
			},
			new Action() {	// [34] multiplication = value MULT ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [35] multiplication = ZERO MULT value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [36] multiplication = value.v MULT ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [37] multiplication = ONE MULT value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [38] multiplication = value.v1 MULT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new MultiplyExpression(v1, v2);
				}
			},
			new Action() {	// [39] division = value DIV ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [40] division = ZERO DIV value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [41] division = value.v DIV ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [42] division = value.v1 DIV value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new DivisionExpression(v1, v2);
				}
			},
			new Action() {	// [43] modulo = value MOD ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [44] modulo = value MOD ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [45] modulo = ZERO MOD value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [46] modulo = ONE MOD value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ONE);
				}
			},
			new Action() {	// [47] modulo = value.v1 MOD value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new ModuloExpression(v1, v2);
				}
			},
			new Action() {	// [48] value = NUMBER.n
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 1];
					final BigInteger n = (BigInteger) _symbol_n.value;
					 return new NumberValueExpression(n);
				}
			},
			new Action() {	// [49] value = ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [50] value = ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ONE);
				}
			},
			Action.RETURN,	// [51] value = identifier
			new Action() {	// [52] identifier = PIDENTIFIER.p
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new VariableValueExpression(VariableInfo.of(p));
				}
			},
			new Action() {	// [53] identifier = PIDENTIFIER.p LBRACKET NUMBER.n RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					final Symbol _symbol_n = _symbols[offset + 3];
					final BigInteger n = (BigInteger) _symbol_n.value;
					 return new ArrayValueExpression(VariableInfo.of(p), n);
				}
			},
			new Action() {	// [54] identifier = PIDENTIFIER.p LBRACKET ZERO RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new ArrayValueExpression(VariableInfo.of(p), BigInteger.ZERO);
				}
			},
			new Action() {	// [55] identifier = PIDENTIFIER.p LBRACKET ONE RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new ArrayValueExpression(VariableInfo.of(p), BigInteger.ONE);
				}
			},
			new Action() {	// [56] identifier = PIDENTIFIER.p1 LBRACKET PIDENTIFIER.p2 RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p1 = _symbols[offset + 1];
					final String p1 = (String) _symbol_p1.value;
					final Symbol _symbol_p2 = _symbols[offset + 3];
					final String p2 = (String) _symbol_p2.value;
					 return new ArrayVariableValueExpression(VariableInfo.of(p1), p2);
				}
			}
		};
	}

	protected Symbol invokeReduceAction(int rule_num, int offset) {
		return actions[rule_num].reduce(_symbols, offset);
	}
}
