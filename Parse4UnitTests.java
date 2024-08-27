import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
public class Parse4UnitTests {
    @Test
    public void testFor(){
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.For, 0,0, "FOR"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "A"));
        tokens.add(new Token(Token.TokenType.EQUALS,2,2,"="));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"0"));
        tokens.add(new Token(Token.TokenType.To,4,4,"To"));
        tokens.add(new Token(Token.TokenType.NUMBER,5,5,"10"));
        tokens.add(new Token(Token.TokenType.Step,6,6,"Step"));
        tokens.add(new Token(Token.TokenType.NUMBER,7,7,"2"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),
                "For: Loop variable: AssignmentNode(VariableNode(A) = 0) Final: 10 STEP: increments 2\n");
    }

    @Test
    public void testForWithoutStep(){
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.For, 0,0, "FOR"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "A"));
        tokens.add(new Token(Token.TokenType.EQUALS,2,2,"="));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"0"));
        tokens.add(new Token(Token.TokenType.To,4,4,"To"));
        tokens.add(new Token(Token.TokenType.NUMBER,5,5,"10"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "For: Loop variable: AssignmentNode(VariableNode(A) = 0) Final: 10 STEP: increments 1\n\n" );
    }

    @Test
    public void testForWithoutEquals(){
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.For, 0,0, "FOR"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "A"));
        tokens.add(new Token(Token.TokenType.LESSSIGN,2,2,"<"));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"0"));
        tokens.add(new Token(Token.TokenType.To,4,4,"To"));
        tokens.add(new Token(Token.TokenType.NUMBER,5,5,"10"));
        tokens.add(new Token(Token.TokenType.Step,6,6,"Step"));
        tokens.add(new Token(Token.TokenType.NUMBER,7,7,"2"));
        Parser parser = new Parser(tokens);
        assertThrows(NullPointerException.class, () ->{
            Node n = parser.parse();
        });

    }

    @Test
    public void testGoSub(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.Gosub,0,0,"Gosub"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "F"));
        tokens.add(new Token(Token.TokenType.To,2,2,"To"));
        tokens.add(new Token(Token.TokenType.WORD,3,3, "C"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "GOSUB: VariableNode(F) TO: VariableNode(C)\n");
    }

    @Test
    public void testIfWithoutThen(){
    LinkedList<Token>tokens = new LinkedList<>();
    tokens.add(new Token(Token.TokenType.IF,0,0,"If"));
    tokens.add(new Token(Token.TokenType.WORD,1,1, "x"));
    tokens.add(new Token(Token.TokenType.LESSSIGN,2,2,"<"));
    tokens.add(new Token(Token.TokenType.NUMBER,3,3,"5"));
    Parser parser = new Parser(tokens);
        assertThrows(NoSuchElementException.class, () ->{
            Node n = parser.parse();
        });
    }
    @Test
    public void testIfThen(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.IF,0,0,"If"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "x"));
        tokens.add(new Token(Token.TokenType.LESSSIGN,2,2,"<"));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"5"));
        tokens.add(new Token(Token.TokenType.Then,4,4,"Then"));
        tokens.add(new Token(Token.TokenType.WORD,5,5,"xisSmall"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "IF: Left Expression: VariableNode(x) operator: LESSSIGN Right Expression: 5 Label: xisSmall\n");
    }

    @Test
    public void testIfWithTwoChar(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.IF,0,0,"If"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "x"));
        tokens.add(new Token(Token.TokenType.LESSSIGN,2,2,"<="));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"5"));
        Parser parser = new Parser(tokens);
        assertThrows(NoSuchElementException.class, () ->{
            Node n = parser.parse();
        });
    }


    @Test
    public void testWhileLabel(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.While,0,0,"While"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "x"));
        tokens.add(new Token(Token.TokenType.LESSSIGN,2,2,"<"));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"5"));
        tokens.add(new Token(Token.TokenType.WORD,4,4,"endLabel"));
        tokens.add(new Token(Token.TokenType.WORD,5,5,"x"));
        tokens.add(new Token(Token.TokenType.EQUALS,6,6,"="));
        tokens.add(new Token(Token.TokenType.WORD,7,7,"x"));
        tokens.add(new Token(Token.TokenType.SUBTRACT,8,8,"-"));
        tokens.add(new Token(Token.TokenType.NUMBER,9,9,"1"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),
                "Label: endLabel Statement: WHILE: Left Expression: VariableNode(x) operator: LESSSIGN Right Expression: 5 " +
                        "End Label: endLabel Body :\n");
    }

    @Test
    public void testWhileWithoutLabel(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.While,0,0,"While"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "x"));
        tokens.add(new Token(Token.TokenType.LESSSIGN,2,2,"<"));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"5"));
        Parser parser = new Parser(tokens);
    }

    @Test
    public void testWhileWithTwoChar(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.While,0,0,"While"));
        tokens.add(new Token(Token.TokenType.WORD,1,1, "x"));
        tokens.add(new Token(Token.TokenType.GREATEREQUAL,2,2,">="));
        tokens.add(new Token(Token.TokenType.NUMBER,3,3,"5"));
        tokens.add(new Token(Token.TokenType.WORD,4,4,"endLabel"));
        tokens.add(new Token(Token.TokenType.WORD,5,5,"x"));
        tokens.add(new Token(Token.TokenType.EQUALS,6,6,"="));
        tokens.add(new Token(Token.TokenType.WORD,7,7,"x"));
        tokens.add(new Token(Token.TokenType.SUBTRACT,8,8,"-"));
        tokens.add(new Token(Token.TokenType.NUMBER,9,9,"1"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(),
                "Label: endLabel Statement: WHILE: Left Expression: VariableNode(x) operator: GREATEREQUAL Right Expression: 5 End Label: endLabel Body :\n");

    }

    @Test
    public void testReturn(){
    LinkedList<Token>tokens = new LinkedList<>();
    tokens.add(new Token(Token.TokenType.Return,0,0,"Return"));
    tokens.add(new Token(Token.TokenType.ENDOFLINE,1,1,""));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "RETURN\n");
    }

    @Test
    public void testNext(){
    LinkedList<Token>tokens = new LinkedList<>();
    tokens.add(new Token(Token.TokenType.Next,0,0,"Next"));
    tokens.add(new Token(Token.TokenType.WORD,1,1,"A"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "NEXT: VariableNode(A)\n");
    }

    @Test
    public void testEnd(){
    LinkedList<Token>tokens = new LinkedList<>();
    tokens.add(new Token(Token.TokenType.End,0,0,"End"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "END\n");
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
    public void testFunction(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.WORD,0,0,"x"));
        tokens.add(new Token(Token.TokenType.EQUALS,1,1, "="));
        tokens.add(new Token(Token.TokenType.RANDOM,2,2,"RANDOM"));
        tokens.add(new Token(Token.TokenType.LPAREN,3,3,"("));
        tokens.add(new Token(Token.TokenType.RPAREN,4,4,")"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "AssignmentNode(VariableNode(x) = Function:  name: RANDOM parameters ( [] ))\n");
    }

    @Test
    public void testParameters(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.WORD,0,0,"x"));
        tokens.add(new Token(Token.TokenType.EQUALS,1,1, "="));
        tokens.add(new Token(Token.TokenType.VAL,2,2,"VAL"));
        tokens.add(new Token(Token.TokenType.LPAREN,3,3,"("));
        tokens.add(new Token(Token.TokenType.WORD,4,4,"string"));
        tokens.add(new Token(Token.TokenType.RPAREN,5,5,")"));
        Parser parser = new Parser(tokens);
        Node n = parser.parse();
        assertEquals(n.toString(), "AssignmentNode(VariableNode(x) = Function:  name: VAL parameters ( [VariableNode(string)] ))\n");
    }

    @Test
    public void testWrongParameters(){
        LinkedList<Token>tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.WORD,0,0,"x"));
        tokens.add(new Token(Token.TokenType.EQUALS,1,1, "="));
        tokens.add(new Token(Token.TokenType.RANDOM,2,2,"VAL"));
        tokens.add(new Token(Token.TokenType.LPAREN,3,3,"("));
        tokens.add(new Token(Token.TokenType.NUMBER,4,4,"4"));
        tokens.add(new Token(Token.TokenType.RPAREN,5,5,")"));
        Parser parser = new Parser(tokens);
        assertThrows(RuntimeException.class, () ->{
            Node n = parser.parse();
        });
    }

}
