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
		static public final short FOR = 6;
		static public final short IF = 7;
		static public final short WHILE = 8;
		static public final short READ = 9;
		static public final short WRITE = 10;
		static public final short SKIP = 11;
		static public final short RBRACKET = 12;
		static public final short DO = 13;
		static public final short PLUS = 14;
		static public final short MINUS = 15;
		static public final short MULT = 16;
		static public final short MOD = 17;
		static public final short ENDFOR = 18;
		static public final short LBRACKET = 19;
		static public final short DIV = 20;
		static public final short FROM = 21;
		static public final short TO = 22;
		static public final short DOWNTO = 23;
		static public final short THEN = 24;
		static public final short ELSE = 25;
		static public final short ENDIF = 26;
		static public final short VAR = 27;
		static public final short BEGIN = 28;
		static public final short END = 29;
		static public final short ENDWHILE = 30;
		static public final short ASSIGN = 31;
		static public final short EQ = 32;
		static public final short NEQ = 33;
		static public final short LT = 34;
		static public final short GT = 35;
		static public final short LE = 36;
		static public final short GE = 37;
	}

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9pDbabq55KKXczr9sOWCQQR98I4C1YQs26468mO44LD54L5S4Pm148O0uGGG8bBzHnMhbr" +
		"utBXX0IxOi72UeWmg3g2Y4cV50GUShbzLRfVLrGNdn1NLvwl$l$zx$LvrrNlTBUBibAHSBb" +
		"V8LT8fDyirSgCiaNlaVab8YHJg2x9CygLOccIQDCjqQQ5tKihbGhbCpidgdvH2YT7xkfnqF" +
		"kdreJHEVeCKX6PS9LjecwSgTycYhBOYc$TSEK#wSsR8OuQ5EUaeqiNXL8T8LuRIX9J1#50w" +
		"MdyahG#cEaZI8AKoJYRA1TuLJzRFP86qoiLoYLnACbLcoMfPA#jbWsoIpL8ZbL8XLLBDY6F" +
		"u17Ko0NSjRfASZxi9bv8fk5jmQMd4tOgxIDfmT$ekwRkblkloNR$ljlfEpXgNa1am4zfXDi" +
		"o1kN0bTC3LFAz2raC1J#XJpWcvJMwNvUWTv6Ezv5vOGLNhLMjW7VJ0HkY5FZsc7#bHFQBlw" +
		"xjwMD$Mj$IG7jGtzGSzeT$hT$gj7jUFzGDzHm$e9$gXlgVxvMwvJnwGg6EREAouCjPSEQkT" +
		"QsCT5hEktEULOWyqi0Qc1lQ6molEQindVGJxE$IkyNg7q#g8j95yB7buHdVqD5ULO0q5syO" +
		"7vatap50REMzi1EaKZ#mqITzmEfnaxfVhHvEbGya4TewxykjbCkEKs7K7tHZVbVhEyLt56L" +
		"jJ4Qr9tyKpZc#OiyLbxk#PIV5a6km9kKabtoZ3BIMMH5NPnFxlIoCEPXBPFZ2yJufDEs2Hr" +
		"oeopx9Mg8Hkgn#3XT0BJuWudMvkRVsm2vwo#kbXTJfCvrk#1y#ETPR1Skgzw0heWfNK#z1k" +
		"M0MhgPz3ry1QM0VheGSsm4ROH9$7uMLyR#1w1gatemVHFlGmkWKzWlRpCN$67yDlHOVGRUZ" +
		"Nw7Rq1FeWUXAUXALG1UxrJRBnJy5kSETliknvU0R#uVrj4CS$2pjWflKvHFwGtPD2s65#uB" +
		"zhTqev7O17eTFUzmXvmjghJ6jD9vgcJ1ljFVlXEFN3cR7vt4UHClDV81MPDik6WZN7uc0zK" +
		"ZYMXEgkKDqJgWU2zUd7TKQ5wbBJCjDoqsIWJuLfvlvLczROt7MkSW$SvpSEMuJMMLkVQNlc" +
		"VWV6hJTji3xi9ITjVdUWNzDfFakpzLs0RmdaCqpRJ6qDEUsciqtdH8yhxbeM$Kliq3zDJub" +
		"$w1lwchxgkLU0NQGlmOiomaC7zVMSB2V9QltTTx$bjFtgdKzwPtQg$gH$#6s$UETlWFsgNy" +
		"6Nm5hMpzqdX$xDQHQ$yVdy7iV0LU7NCGOEtx9vK03vK6J4ZHZ$2fn8qhH7qHJ1j0YQ8vWUe" +
		"YK2zzz3a9H7NXQj$mAs2P3p");

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
			new Action() {	// [9] command = FOR PIDENTIFIER.p FROM value.v1 TO value.v2 DO commands.c ENDFOR
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 2];
					final String p = (String) _symbol_p.value;
					final Symbol _symbol_v1 = _symbols[offset + 4];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 6];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					final Symbol _symbol_c = _symbols[offset + 8];
					final Expression c = (Expression) _symbol_c.value;
					 return new ForFromToExpression(p, v1, v2, c);
				}
			},
			new Action() {	// [10] command = FOR PIDENTIFIER.p FROM value.v1 DOWNTO value.v2 DO commands.c ENDFOR
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 2];
					final String p = (String) _symbol_p.value;
					final Symbol _symbol_v1 = _symbols[offset + 4];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 6];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					final Symbol _symbol_c = _symbols[offset + 8];
					final Expression c = (Expression) _symbol_c.value;
					 return new ForFromDownToExpression(p, v1, v2, c);
				}
			},
			new Action() {	// [11] command = READ identifier.i SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 2];
					final ValueExpression i = (ValueExpression) _symbol_i.value;
					 return new ReadExpression(i);
				}
			},
			new Action() {	// [12] command = WRITE value.v SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 2];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new WriteExpression(v);
				}
			},
			new Action() {	// [13] command = SKIP SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new SkipExpression();
				}
			},
			Action.RETURN,	// [14] expression = value
			Action.RETURN,	// [15] expression = addition
			Action.RETURN,	// [16] expression = subtraction
			Action.RETURN,	// [17] expression = multiplication
			Action.RETURN,	// [18] expression = division
			Action.RETURN,	// [19] expression = modulo
			new Action() {	// [20] condition = value.v1 EQ value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new EqualsConditionExpression(v1, v2);
				}
			},
			new Action() {	// [21] condition = value.v1 NEQ value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new NotEqualsConditionExpression(v1, v2);
				}
			},
			new Action() {	// [22] condition = value.v1 LT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new LessThanConditionExpression(v1, v2);
				}
			},
			new Action() {	// [23] condition = value.v1 GT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new GreaterThanConditionExpression(v1, v2);
				}
			},
			new Action() {	// [24] condition = value.v1 LE value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new LessEqualsConditionExpression(v1, v2);
				}
			},
			new Action() {	// [25] condition = value.v1 GE value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new GreaterEqualsConditionExpression(v1, v2);
				}
			},
			new Action() {	// [26] addition = value.v PLUS ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [27] addition = ZERO PLUS value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [28] addition = value.v1 PLUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new AdditionExpression(v1, v2);
				}
			},
			new Action() {	// [29] addition = value.v1 PLUS ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					 return new AdditionExpression(new NumberValueExpression(BigInteger.ONE), v1);
				}
			},
			new Action() {	// [30] addition = ONE PLUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new AdditionExpression(new NumberValueExpression(BigInteger.ONE), v2);
				}
			},
			new Action() {	// [31] subtraction = value.v MINUS ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [32] subtraction = ZERO MINUS value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [33] subtraction = value.v1 MINUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new SubtractionExpression(v1, v2);
				}
			},
			new Action() {	// [34] subtraction = ONE MINUS value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new SubtractionExpression(new NumberValueExpression(BigInteger.ONE), v);
				}
			},
			new Action() {	// [35] subtraction = value.v MINUS ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new SubtractionExpression(v, new NumberValueExpression(BigInteger.ONE));
				}
			},
			new Action() {	// [36] multiplication = value MULT ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [37] multiplication = ZERO MULT value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [38] multiplication = value.v MULT ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [39] multiplication = ONE MULT value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [40] multiplication = value.v1 MULT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new MultiplyExpression(v1, v2);
				}
			},
			new Action() {	// [41] division = value DIV ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [42] division = ZERO DIV value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [43] division = value.v DIV ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [44] division = value.v1 DIV value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new DivisionExpression(v1, v2);
				}
			},
			new Action() {	// [45] modulo = value MOD ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [46] modulo = value MOD ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [47] modulo = ZERO MOD value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [48] modulo = ONE MOD value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new ModuloExpression(new NumberValueExpression(BigInteger.ONE), v);
				}
			},
			new Action() {	// [49] modulo = value.v1 MOD value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new ModuloExpression(v1, v2);
				}
			},
			new Action() {	// [50] value = NUMBER.n
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 1];
					final BigInteger n = (BigInteger) _symbol_n.value;
					 return new NumberValueExpression(n);
				}
			},
			new Action() {	// [51] value = ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [52] value = ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ONE);
				}
			},
			Action.RETURN,	// [53] value = identifier
			new Action() {	// [54] identifier = PIDENTIFIER.p
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new VariableValueExpression(VariableInfo.of(p));
				}
			},
			new Action() {	// [55] identifier = PIDENTIFIER.p LBRACKET NUMBER.n RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					final Symbol _symbol_n = _symbols[offset + 3];
					final BigInteger n = (BigInteger) _symbol_n.value;
					 return new ArrayValueExpression(VariableInfo.of(p), n);
				}
			},
			new Action() {	// [56] identifier = PIDENTIFIER.p LBRACKET ZERO RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new ArrayValueExpression(VariableInfo.of(p), BigInteger.ZERO);
				}
			},
			new Action() {	// [57] identifier = PIDENTIFIER.p LBRACKET ONE RBRACKET
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new ArrayValueExpression(VariableInfo.of(p), BigInteger.ONE);
				}
			},
			new Action() {	// [58] identifier = PIDENTIFIER.p1 LBRACKET PIDENTIFIER.p2 RBRACKET
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
