import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;


public class Parser2UnitTests {
    @Test
    public void testStatements(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.WORD, 0,0, "x"));
        tokens.add(new Token(Token.TokenType.EQUALS,1,1, "="));
        tokens.add(new Token(Token.TokenType.NUMBER, 2,2, "5"));
        tokens.add(new Token(Token.TokenType.PRINT, 3,3, "print"));
        tokens.add(new Token(Token.TokenType.WORD, 4,4, "x"));
        tokens.add(new Token(Token.TokenType.WORD,5,5, "y"));
        tokens.add(new Token(Token.TokenType.WORD, 6,6,"c"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),"Statements([AssignmentNode(VariableNode(x)5), PrintNodes([VariableNode(x), VariableNode(y), VariableNode(c)])])");
    }

    @Test
    public void testAssignments(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.WORD, 0,0, "x"));
        tokens.add(new Token(Token.TokenType.EQUALS,1,1, "="));
        tokens.add(new Token(Token.TokenType.NUMBER, 2,2, "5"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "Statements([AssignmentNode(VariableNode(x)5");
    }

    @Test
    public void testStatement(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.PRINT, 0,0, "print"));
        tokens.add(new Token(Token.TokenType.WORD, 1,1, "x"));
        tokens.add(new Token(Token.TokenType.WORD,2,2, "y"));
        tokens.add(new Token(Token.TokenType.WORD, 3,3,"c"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),"PrintNodes([VariableNode(x), VariableNode(y), VariableNode(c)])])");
    }

}
