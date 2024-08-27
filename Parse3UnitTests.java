import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class Parse3UnitTests {
    @Test
    public void testRead(){
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.Read, 0,0, "Read"));
        tokens.add(new Token(Token.TokenType.WORD,1,1,"x"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),"Statements([ReadNode variables: ([VariableNode(x)] )])");
    }

    @Test
    public void testData(){
        Lexer lexer = new Lexer();
    LinkedList<Token>tokens = lexer.lex("Numbertest.txt");
    Parser parser = new Parser(tokens);
    Node n = parser.parse();
    assertEquals(n.toString(),"Statements([Data: ( [5.5, 5] )])");
    }

    @Test
    public void testInput(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.Input, 0,0,"Input"));
        tokens.add(new Token(Token.TokenType.WORD,1,1,"x"));
        tokens.add(new Token(Token.TokenType.WORD,2,2,"x"));
        tokens.add(new Token(Token.TokenType.COMA,3,3,","));
        tokens.add(new Token(Token.TokenType.WORD,4,4,"y"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),"Statements([Input Node ( Input = VariableNode(x) Variables = [VariableNode(x), VariableNode(y)] )])");
    }

    @Test
    public void testEmptyError(){
        LinkedList<Token>tokens = null;
        Parser parser = new Parser(tokens);
        assertThrows(NullPointerException.class, () ->{
            Node n = parser.parse();
        });
    }

    @Test
    public void testDataWithStingIntFloat(){
        Lexer lexer = new Lexer();
        LinkedList<Token>tokens = lexer.lex("Numbertest.txt");
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),"Statements([Data: ( [5.5, 5, String Literal ( This is )] )])");
    }

    @Test
    public void testInputStringLiteralInput(){
    LinkedList<Token>tokens = new LinkedList<>();
    tokens.add(new Token(Token.TokenType.Input,0,0,"Input"));
    tokens.add(new Token(Token.TokenType.STRINGLITERAL,1,1,"this is"));
    tokens.add(new Token(Token.TokenType.WORD,2,2,"x"));
    tokens.add(new Token(Token.TokenType.COMA,3,3,","));
    tokens.add(new Token(Token.TokenType.WORD,4,4,"y"));
    Parser parser = new Parser(tokens);
    Node n = parser.parse();
    assertEquals(n.toString(), "Statements([Input Node ( Input = String Literal ( this is ) Variables = [VariableNode(x), VariableNode(y)] )])");
    }

    @Test
    public void testReadWithConstantStrings(){
    LinkedList<Token>tokens = new LinkedList<>();
    tokens.add(new Token(Token.TokenType.Read,0,0,"Read"));
    tokens.add(new Token(Token.TokenType.STRINGLITERAL,1,1,"This is"));
    tokens.add(new Token(Token.TokenType.COMA,2,2,","));
    tokens.add(new Token(Token.TokenType.STRINGLITERAL,3,3,"me"));
    Parser parser = new Parser(tokens);
    Node n = parser.parse();
    assertEquals(n.toString(), "Statements([ReadNode variables: ([String Literal ( This is ), String Literal ( me )] )])");
    }

    @Test
    public void testDataWithWrongInput(){
    LinkedList<Token>tokens = new LinkedList<>();
    assertThrows(NullPointerException.class, () ->{
        tokens.add(new Token(Token.TokenType.Data,0,0,"Data"));
        tokens.add(new Token(Token.TokenType.WORD,1,1,"x"));
        tokens.add(new Token(Token.TokenType.COMA,2,2,","));
        tokens.add(new Token(Token.TokenType.WORD,3,3,"y"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),"Statements([Data: ( [VariableNode(x), VariableNode(y)] )");
        });
    }

    @Test
    public void testInputwithNumbers(){
    LinkedList<Token>tokens = new LinkedList<>();
    assertThrows(NoSuchElementException.class, () ->{
        tokens.add(new Token(Token.TokenType.Input,0,0,"Input"));
        tokens.add(new Token(Token.TokenType.NUMBER,1,1,"3"));
        tokens.add(new Token(Token.TokenType.WORD,2,2,"x"));
        tokens.add(new Token(Token.TokenType.COMA,3,3,","));
        tokens.add(new Token(Token.TokenType.WORD,4,4,"y"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "Statements([Input Node ( Input = 3) Variables = [VariableNode(x), VariableNode(y)] )])");
        });
    }

}
