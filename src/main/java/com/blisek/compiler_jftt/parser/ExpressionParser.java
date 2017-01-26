package com.blisek.compiler_jftt.parser;

import com.blisek.compiler_jftt.ast.*;
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
		static public final short SEMIC = 1;
		static public final short PIDENTIFIER = 2;
		static public final short NUMBER = 3;
		static public final short ZERO = 4;
		static public final short ONE = 5;
		static public final short MULT = 6;
		static public final short MOD = 7;
		static public final short READ = 8;
		static public final short WRITE = 9;
		static public final short SKIP = 10;
		static public final short PLUS = 11;
		static public final short MINUS = 12;
		static public final short DIV = 13;
		static public final short BEGIN = 14;
		static public final short END = 15;
		static public final short ASSIGN = 16;
	}

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9ojr5bF544K1U3JpGYEABCuCZ2Yeg0eY2CYA0eaccY8CSQeBpxuu8lxWlk#H8r$qdtVHTp" +
		"tuwcQEwJJQOqnzkJhqtLhkgQgI07WEZAO8jCm6QsOYtbeGpi$enOXXb230bgGLVzqr2E79l" +
		"HX9LQZ4qkGvGsai00BiTYgMJHWFfRwd856dyqe#Po5sJwd#diUjIGcQToYp9Gvlfv1X#KYo" +
		"oxBRiiowj23NcijirnkEO0qt$8Dn$YAB$cSJ$c4Z$YG3tYVT$Y97$c1x$cEutpD5tpCUtp6" +
		"k#ZNsokm2eDQOTvpiozgjMxsWIeDMdz1UrAdNN7zzLg7sx6qzgvITMk8LaFjNKeh$7DMxZb" +
		"0urT$st7rlyci$NfoR$0FdvpUhZm1mvTr8$bJKPAC3BgQhX5fartMJaklx9OzhbQbgr$soZ" +
		"xfaFroG0vAbnoImp8gPJaYH#MO79SJSb9EoMafo5ev8#lbh6oGSx99piiMkI1RPRjSb1roo" +
		"QPrnUQsKwxQyxNOl4Ej$QkoCrfNRIpM7e#s8#y7aUVkM5yvrkv96YDXp3xB0SiXopMMwoBV" +
		"tMYvsN8qqhVjD#CFO#BoVtUNtoSgjtcBD$4VBtxpT#qol$277jjrajn$dL02dOgKr9XGvp9" +
		"8rAZ#gc94KuBcc582TtRo$ZG4QFa5LzwRGG==");

	static final Action RETURN3 = new Action() {
		public Symbol reduce(Symbol[] _symbols, int offset) {
			return _symbols[offset + 3];
		}
	};

	private final Action[] actions;

	public ExpressionParser() {
		super(PARSING_TABLES);
		actions = new Action[] {
			RETURN3,	// [0] program = BEGIN commands END; returns 'END' although none is marked
			new Action() {	// [1] commands = commands.s command.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_s = _symbols[offset + 1];
					final Expression s = (Expression) _symbol_s.value;
					final Symbol _symbol_c = _symbols[offset + 2];
					final Expression c = (Expression) _symbol_c.value;
					 return new Expression(s, c);
				}
			},
			new Action() {	// [2] commands = command.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final Expression c = (Expression) _symbol_c.value;
					 return c;
				}
			},
			new Action() {	// [3] command = identifier.i ASSIGN expression.e SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 1];
					final ValueExpression i = (ValueExpression) _symbol_i.value;
					final Symbol _symbol_e = _symbols[offset + 3];
					final Expression e = (Expression) _symbol_e.value;
					 return new AssignmentExpression(i, e);
				}
			},
			new Action() {	// [4] command = READ identifier.i SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 2];
					final ValueExpression i = (ValueExpression) _symbol_i.value;
					 return new ReadExpression(i);
				}
			},
			new Action() {	// [5] command = WRITE value.v SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 2];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return new WriteExpression(v);
				}
			},
			new Action() {	// [6] command = SKIP SEMIC
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new SkipExpression();
				}
			},
			Action.RETURN,	// [7] expression = value
			Action.RETURN,	// [8] expression = addition
			Action.RETURN,	// [9] expression = subtraction
			Action.RETURN,	// [10] expression = multiplication
			Action.RETURN,	// [11] expression = division
			Action.RETURN,	// [12] expression = modulo
			new Action() {	// [13] addition = value.v PLUS ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [14] addition = ZERO PLUS value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [15] addition = value.v1 PLUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new AdditionExpression(v1, v2);
				}
			},
			new Action() {	// [16] subtraction = value.v MINUS ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [17] subtraction = ZERO MINUS value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [18] subtraction = value.v1 MINUS value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new SubtractionExpression(v1, v2);
				}
			},
			new Action() {	// [19] multiplication = value MULT ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [20] multiplication = ZERO MULT value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [21] multiplication = value.v MULT ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [22] multiplication = ONE MULT value.v
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 3];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [23] multiplication = value.v1 MULT value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new MultiplyExpression(v1, v2);
				}
			},
			new Action() {	// [24] division = value DIV ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [25] division = ZERO DIV value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [26] division = value.v DIV ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [27] division = value.v1 DIV value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new DivisionExpression(v1, v2);
				}
			},
			new Action() {	// [28] modulo = value MOD ZERO
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [29] modulo = value.v MOD ONE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v = _symbols[offset + 1];
					final ValueExpression v = (ValueExpression) _symbol_v.value;
					 return v;
				}
			},
			new Action() {	// [30] modulo = ZERO MOD value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ZERO);
				}
			},
			new Action() {	// [31] modulo = ONE MOD value
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new NumberValueExpression(BigInteger.ONE);
				}
			},
			new Action() {	// [32] modulo = value.v1 MOD value.v2
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_v1 = _symbols[offset + 1];
					final ValueExpression v1 = (ValueExpression) _symbol_v1.value;
					final Symbol _symbol_v2 = _symbols[offset + 3];
					final ValueExpression v2 = (ValueExpression) _symbol_v2.value;
					 return new ModuloExpression(v1, v2);
				}
			},
			new Action() {	// [33] value = NUMBER.n
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 1];
					final BigInteger n = (BigInteger) _symbol_n.value;
					 return new NumberValueExpression(n);
				}
			},
			Action.RETURN,	// [34] value = identifier
			new Action() {	// [35] identifier = PIDENTIFIER.p
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_p = _symbols[offset + 1];
					final String p = (String) _symbol_p.value;
					 return new VariableValueExpression(new VariableInfo(p));
				}
			}
		};
	}

	protected Symbol invokeReduceAction(int rule_num, int offset) {
		return actions[rule_num].reduce(_symbols, offset);
	}
}
