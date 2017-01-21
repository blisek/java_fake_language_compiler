package com.blisek.compiler_jftt.scanner;

import beaver.Symbol;
import beaver.Scanner;

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
%}
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

Number = [:digit:] [:digit:]*

%%

{WhiteSpace}+   { /* ignore */ }

<YYINITIAL> {
	{Number}    { return newToken(Terminals.NUMBER, new Integer(yytext())); }

	"("         { return newToken(Terminals.LPAREN, yytext()); }
	")"         { return newToken(Terminals.RPAREN, yytext()); }
	"*"         { return newToken(Terminals.MULT,   yytext()); }
	"/"         { return newToken(Terminals.DIV,    yytext()); }
	"+"         { return newToken(Terminals.PLUS,   yytext()); }
	"-"         { return newToken(Terminals.MINUS,  yytext()); }
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }
