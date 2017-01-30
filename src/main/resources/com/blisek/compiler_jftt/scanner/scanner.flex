package com.blisek.compiler_jftt.scanner;

import beaver.Symbol;
import beaver.Scanner;
import java.math.BigInteger;

import com.blisek.compiler_jftt.parser.ExpressionParser.Terminals;

%%

%class ExpressionScanner
%extends Scanner
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode
%line
%column
%{
	private Symbol newToken(short id)
	{
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength());
	}

	private Symbol newToken(short id, Object value)
	{
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
	}
	
	private BigInteger string2BigInteger(String val) {
		return BigInteger.valueOf(Long.parseLong(val));
	}
%}
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

Number = [1-9][:digit:]*
OnlyZero = [0]{WhiteSpace}+
OnlyOne = [1]{WhiteSpace}+

Identifier = [_a-z]+

%%

{WhiteSpace}+   { /* ignore */ }

<YYINITIAL> {
	{OnlyZero} 	{ return newToken(Terminals.ZERO, BigInteger.ZERO); }
	{OnlyOne} 	{ return newToken(Terminals.ONE, BigInteger.ONE); }
	{Number}    { return newToken(Terminals.NUMBER, new BigInteger(yytext())); }
	{Identifier} { return newToken(Terminals.PIDENTIFIER, yytext()); }

	":="		{ return newToken(Terminals.ASSIGN); }
	";"			{ return newToken(Terminals.SEMIC); }
	
	"*"         { return newToken(Terminals.MULT); }
	"/"         { return newToken(Terminals.DIV); }
	"+"         { return newToken(Terminals.PLUS); }
	"-"         { return newToken(Terminals.MINUS); }
	"%"			{ return newToken(Terminals.MOD); }
	
	"BEGIN"		{ return newToken(Terminals.BEGIN); }
	"END"		{ return newToken(Terminals.END); }
	"READ"		{ return newToken(Terminals.READ); }
	"WRITE"		{ return newToken(Terminals.WRITE); }
	"SKIP"		{ return newToken(Terminals.SKIP); }
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }
