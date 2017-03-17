import jflex.Main;
import jflex.Parser;

import java.io.File;

/**
 * Created by anail on 08/03/2017.
 */
public class App {

    private static final String ROOT = new File("").getAbsolutePath();
    private static final String PATH = "/ExemploInteliJ/JFlexExample/jflex";
    private static final String LEXER_FILE = "/Lexer.flex";

    private static void generateLexer(){
        File file = new File(ROOT + PATH + LEXER_FILE);
        Main.generate(file);
    }

    public static void main(String[]args){

        generateLexer();

        Parser parser = new Parser();
        String sourceCode = ". , .. ... --comentario de linha\n" +
                "--[[comentário em bloco" +
                "segunda linha do comentario ]]--\n" +
                "\'literal simples\'\n" +
                "\"literal duplo\"\n" +
                "( ) [ ] { } = # + - * / ^  %\n" +
                " > < > <= == ~= \n" +
                "10 -10 10.10 -10.10 \n" +
                "x va_r val_or10 -10valor\n" +
                "¨& _ §";
        //sourceCode = "-- Author.\n\n" + "--[[This code was generated automatically]]";
        String out = parser.lexical(sourceCode);
        System.out.println(out);
    }
}
