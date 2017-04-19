package jflex;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;

public class Parser {


    private ArrayList<Token> tokens;

    public Parser(){
        this.tokens = new ArrayList<>();
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public String lexical(String sourceCode){

        String out = " --> Lexical Parser\n\n";
        try {
            Lexer scanner = new Lexer(new StringReader(sourceCode));
            Token token;

            while ((token = scanner.nextToken()) != null) {
                out += token + "\n";
                tokens.add(token);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return out;
    }

    public void syntactic(){

    }
}
