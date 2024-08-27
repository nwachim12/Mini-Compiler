import java.util.LinkedList;

/*
 * This class is used to print out the results of the tokens created from the text file
 * 			as we use a for loop to print out each token that is made using the code handler and the lexer
 */

public class Basic {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error message");
            System.exit(1);
        }
        String filename = args[0];
        Lexer lexer = new Lexer();
        LinkedList<Token> tokens = lexer.lex(filename);
        for(Token token: tokens)
            System.out.print(token.toString());
        Parser parser = new Parser(tokens);
        Node ast = parser.parse();
        Interpreter interpreter = new Interpreter((StatementNode) ast);
        interpreter.runProgram((StatementNode) ast);
        System.out.println("\n");
        System.out.println("AST:");
        System.out.println(ast.toString());
    }
}