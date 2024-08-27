import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ParserUnitTests {
    @Test
    public void testAdding(){
        LinkedList<Token>token = new LinkedList<>();
        Parser parser = new Parser(token);
        Node astR = parser.parse();
        assertEquals("AST: (5+6)", astR.toString());
    }
    @Test
    public void testSubtracting(){
        LinkedList<Token>token = new LinkedList<>();
        Parser parser = new Parser(token);
        Node astR = parser.parse();
        assertEquals("AST: (5-6)", astR.toString());
    }
    @Test
    public void testMulitply(){
        LinkedList<Token>token = new LinkedList<>();
        Parser parser = new Parser(token);
        Node astR = parser.parse();
        assertEquals("AST: (5*6)", astR.toString());
    }
    @Test
    public void testDividing(){
        LinkedList<Token>token = new LinkedList<>();
        Parser parser = new Parser(token);
        Node astR = parser.parse();
        assertEquals("AST: (5/6)", astR.toString());
    }
}
