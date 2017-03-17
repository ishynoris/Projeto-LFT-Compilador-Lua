package jflex;

import java.util.ArrayList;
import java.util.Formatter;

import static jflex.Token.Type.*;

public class Token {

    static int nCols = 0;

    public enum Type{
        SPACE, ANY,

        //Keywords
        AND, BREAK, DO, ELSE, ELSEIF, END, FALSE, FOR, FUNCTION, IF, IN, LOCAL, NIL,
        NOT, OR, REPEAT, RETURN, THEN, TRUE, UNTIL, WHERE,

        // Special char, relational operators && math operators
        POINT, SEPARATOR, CONCATENATE, VARARG, PARENTESIS_ESQ, PARENTESIS_DIR, COLCHETE_ESQ,
        COLCHETE_DIR, CHAVE_ESQ, CHAVE_DIR, ATTRIBUTION, SIZER, MATH_OPERATOR, RELATIONAL_OPERATOR,

        //Another structures
        INLINE_COMMENT, BLOCK_COMMENT, SIMPLE_LITERAL_TEXT, DOUBLE_LITERAL_TEXT, INTEGER, FLOAT, VARIABLE
    }

    private Token.Type type;
    private Object lexem;
    private int initialPos, line, col;

    public Token(int initialPos, Token.Type type, Object lexem, int line, int col){
        this.initialPos = initialPos;
        this.type = type;
        this.lexem = lexem;
        this.line = line;
        this.col = col;
    }

    public String getLexem(){
        return lexem.toString();
    }

    public int getInitialPos() {
        return initialPos;
    }

    public int length(){
        return getLexem().length();
    }

    public boolean isKeyword(){

        return type == AND || type == BREAK || type == DO || type == ELSE || type == ELSEIF
                || type == END || type == FALSE || type == FOR || type == FUNCTION
                || type == IF || type == IN || type == LOCAL || type == NIL || type == NOT
                || type == OR || type == REPEAT || type == RETURN || type == THEN
                || type == TRUE || type == UNTIL || type == WHERE;
    }

    public boolean isComent(){
        return type == INLINE_COMMENT || type == BLOCK_COMMENT;
    }

    public boolean isLiteral(){
        return type == SIMPLE_LITERAL_TEXT || type == DOUBLE_LITERAL_TEXT;
    }

    public boolean isNumber(){
        return type == INTEGER || type == FLOAT;
    }

    public boolean isVariable(){
        return type == VARIABLE;
    }

    public boolean isUnexpected(){
        return type == ANY;
    }

    public String toString(){
        Formatter out = new Formatter();
        out.format("[Row: %d, Col: %d, Lenght: %d]\t[%s] --> [%s]",
                line, col, length(), type.name(), getLexem());
        return out.toString();
    }
}