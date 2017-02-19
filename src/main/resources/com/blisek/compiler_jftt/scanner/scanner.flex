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
OnlyZero = [0]
Comment = [\{].*[\}]

Identifier = [_a-z]+

%%

{WhiteSpace}+   { /* ignore */ }

<YYINITIAL> {
	{Comment}	{}
	{Number}    {
					final String yyt = yytext();
					if("1".equals(yyt)) return newToken(Terminals.ONE, BigInteger.ONE);
					return newToken(Terminals.NUMBER, new BigInteger(yyt)); 
				}
	{OnlyZero} 	{ return newToken(Terminals.ZERO, BigInteger.ZERO); }
	{Identifier} { return newToken(Terminals.PIDENTIFIER, yytext()); }

	":="		{ return newToken(Terminals.ASSIGN); }
	";"			{ return newToken(Terminals.SEMIC); }
	
	"*"         { return newToken(Terminals.MULT); }
	"/"         { return newToken(Terminals.DIV); }
	"+"         { return newToken(Terminals.PLUS); }
	"-"         { return newToken(Terminals.MINUS); }
	"%"			{ return newToken(Terminals.MOD); }
	"["			{ return newToken(Terminals.LBRACKET); }
	"]"			{ return newToken(Terminals.RBRACKET); }
	
	"="			{ return newToken(Terminals.EQ); }
	
	"BEGIN"		{ return newToken(Terminals.BEGIN); }
	"END"		{ return newToken(Terminals.END); }
	"READ"		{ return newToken(Terminals.READ); }
	"WRITE"		{ return newToken(Terminals.WRITE); }
	"SKIP"		{ return newToken(Terminals.SKIP); }
	"VAR"		{ return newToken(Terminals.VAR); }
	"IF"		{ return newToken(Terminals.IF); }
	"THEN"		{ return newToken(Terminals.THEN); }
	"ELSE"		{ return newToken(Terminals.ELSE); }
	"ENDIF"		{ return newToken(Terminals.ENDIF); }
	"WHILE"		{ return newToken(Terminals.WHILE); }
	"DO"		{ return newToken(Terminals.DO); }
	"ENDWHILE"	{ return newToken(Terminals.ENDWHILE); }
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }
