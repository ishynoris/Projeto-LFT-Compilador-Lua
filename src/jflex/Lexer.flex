package jflex;

%%

%public
%class Lexer
%type Token
%line
%column
%function nextToken

%{

    private int initialPos;

    public int getNCols(){
        return initialPos;
    }

    private Token token(Token.Type type){
        return token(type, " ");
    }

    private Token token(Token.Type type, Object val){
        Token t = new Token(initialPos, type, val, yyline, yycolumn);
        addPos(val.toString().length());
        return t;
    }

    private Token error(Token.Type type, Object val){
        Token token = token(type, val);
        System.err.println("CARACTER NÃO IDENTIFICADO <"+ val.toString() +"> em Lin: " + yyline + ", Col:" + yycolumn);
        return token;
    }

    private void addPos(int n){
        initialPos += n;
    }
%}

ANY                     = .

POINT                   = "."
SEPARATOR               = ","
CONCATENATE             = ".."
VARARG                  = "..."
UNDERLINE               = "_"
OPEN_COMMENT_INLINE     = "--"
OPEN_COMMENT_BLOCK      = "--[["
CLOSE_COMMENT_BLOCK     = "]]"
SIMPLE_LITERAL          = \'
DOUBLE_LITERAL          = \"

PARENTESIS_ESQ          = "("
PARENTESIS_DIR          = ")"
COLCHETE_ESQ            = "["
COLCHETE_DIR            = "]"
CHAVE_ESQ               = "{"
CHAVE_DIR               = "}"
ATTRIBUTION             = "="
SIZER                   = "#"
MATH_OPERATOR           = "+" | "-" | "*" | "/" | "%" | "^"
RELATIONAL_OPERATOR     = ">" | "<" | ">=" | "<=" | "==" | "~="
SPACE                   = [\ \n\t\r]

SIMPLE_LITERAL_TEXT     = {SIMPLE_LITERAL}~{SIMPLE_LITERAL}
DOUBLE_LITERAL_TEXT     = {DOUBLE_LITERAL}~{DOUBLE_LITERAL}
BLOCK_COMMENT           = {OPEN_COMMENT_BLOCK}~{CLOSE_COMMENT_BLOCK}
INLINE_COMMENT          = {OPEN_COMMENT_INLINE}{ANY}*
DIGIT                   = [0-9]
NUMBER                  = {DIGIT}*
INTEGER                 = {DIGIT}+ | -{DIGIT}+
FLOAT                   = {INTEGER}\.{DIGIT}+
LETTER                  = [a-zA-Z]
VARIABLE                = ({UNDERLINE}|{LETTER})({UNDERLINE}| {NUMBER} | {LETTER})*

%%
{SPACE}                 {return token(Token.Type.SPACE);}

"and"                   {return token(Token.Type.AND, yytext());}
"break"                 {return token(Token.Type.BREAK, yytext());}
"do"                    {return token(Token.Type.DO, yytext());}
"else"                  {return token(Token.Type.ELSE, yytext());}
"elseif"                {return token(Token.Type.ELSEIF, yytext());}
"end"                   {return token(Token.Type.END, yytext());}
"false"                 {return token(Token.Type.FALSE, yytext());}
"for"                   {return token(Token.Type.FOR, yytext());}
"function"              {return token(Token.Type.FUNCTION, yytext());}
"if"                    {return token(Token.Type.IF, yytext());}
"in"                    {return token(Token.Type.IN, yytext());}
"local"                 {return token(Token.Type.LOCAL, yytext());}
"nil"                   {return token(Token.Type.NIL, yytext());}
"not"                   {return token(Token.Type.NOT, yytext());}
"or"                    {return token(Token.Type.OR, yytext());}
"repeat"                {return token(Token.Type.REPEAT, yytext());}
"return"                {return token(Token.Type.RETURN, yytext());}
"then"                  {return token(Token.Type.THEN, yytext());}
"true"                  {return token(Token.Type.TRUE, yytext());}
"until"                 {return token(Token.Type.UNTIL, yytext());}
"where"                 {return token(Token.Type.WHERE, yytext());}

{POINT}                 {return token(Token.Type.POINT, yytext());}
{SEPARATOR}             {return token(Token.Type.SEPARATOR, yytext());}
{CONCATENATE}           {return token(Token.Type.CONCATENATE, yytext());}
{VARARG}                {return token(Token.Type.VARARG, yytext());}
{PARENTESIS_ESQ}        {return token(Token.Type.PARENTESIS_ESQ, yytext());}
{PARENTESIS_DIR}        {return token(Token.Type.PARENTESIS_DIR, yytext());}
{COLCHETE_ESQ}          {return token(Token.Type.COLCHETE_ESQ, yytext());}
{COLCHETE_DIR}          {return token(Token.Type.COLCHETE_DIR, yytext());}
{CHAVE_ESQ}             {return token(Token.Type.CHAVE_ESQ, yytext());}
{CHAVE_DIR}             {return token(Token.Type.CHAVE_DIR, yytext());}

{INLINE_COMMENT}        {return token(Token.Type.INLINE_COMMENT, yytext());}
{BLOCK_COMMENT}         {return token(Token.Type.BLOCK_COMMENT, yytext());}
{SIMPLE_LITERAL_TEXT}   {return token(Token.Type.SIMPLE_LITERAL_TEXT, yytext());}
{DOUBLE_LITERAL_TEXT}   {return token(Token.Type.DOUBLE_LITERAL_TEXT, yytext());}
{VARIABLE}              {return token(Token.Type.VARIABLE, yytext());}
{INTEGER}               {return token(Token.Type.INTEGER, yytext());}
{FLOAT}                 {return token(Token.Type.FLOAT, yytext());}
{ATTRIBUTION}           {return token(Token.Type.ATTRIBUTION, yytext());}
{SIZER}                 {return token(Token.Type.SIZER, yytext());}
{MATH_OPERATOR}         {return token(Token.Type.MATH_OPERATOR, yytext());}
{RELATIONAL_OPERATOR}   {return token(Token.Type.RELATIONAL_OPERATOR, yytext());}
{ANY}                   {return error(Token.Type.ANY, yytext());}