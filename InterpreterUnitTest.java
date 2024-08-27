import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class InterpreterUnitTest {
    @Test
    public void testTraverse() {
        Interpreter interpreter = new Interpreter(null); // Initialize with null AST for simplicity
        StatementNode assignmentNode = new AssignmentNode(new VariableNode("x"), new IntegerNode(5));
        interpreter.traverse(new StatementsNode(List.of(assignmentNode)));
        assertEquals(5, interpreter.integerHashMap.get("x").intValue());
    }
/*
    @Test
    public void testLabelOp(){
        Interpreter interpreter = new Interpreter();
        StatementNode statementNode = new DataNode(new ArrayList<>()); // You can replace this with actual statements
        LabeledStatementNode labeledStatementNode = new LabeledStatementNode("label1", statementNode);
        interpreter.processData(statementNode);
    }

    @Test
    public void testRANDOM(){
        Interpreter interpreter = new Interpreter();
        int randomInt = interpreter.RANDOM();
        Random random = new Random();
        int expectedInt = randomInt;
        assertEquals(expectedInt, randomInt);
    }

    @Test
    public void testLEFT(){
        Interpreter interpreter = new Interpreter();
        String input = "Hello";
        int index = 2;
        String expected = "He";
        assertEquals(expected, interpreter.LEFT(input, index));
    }

    @Test
    public void testRIGHT(){
        Interpreter interpreter = new Interpreter();
        String input = "Hello";
        int index = 2;
        String expected = "lo";
        assertEquals(expected, interpreter.RIGHT(input, index));
    }

    @Test
    public void testMID(){
        Interpreter interpreter = new Interpreter();
        String input = "Albany";
        int index1 = 2;
        int index2 = 3;
        String expected = "ban";
        assertEquals(expected, interpreter.MID(input, index1, index2));
    }

    @Test
    public void testNUMInt(){
        Interpreter interpreter = new Interpreter();
        int value = 4;
        String expect = "4";
        assertEquals(expect, interpreter.NUM(value));
    }

    @Test
    public void testNUMFloat(){
        Interpreter interpreter = new Interpreter();
        float value = 4.2F;
        String expect = "4.2";
        assertEquals(expect, interpreter.NUMFloat(value));
    }

    @Test
    public void testVAL(){
        Interpreter interpreter = new Interpreter();
        String value = "4";
        int expect = 4;
        assertEquals(expect, interpreter.Val(value));
    }

    @Test
    public void testVALFloat(){
        Interpreter interpreter = new Interpreter();
        String value = "4.2";
        float expect = 4.2F;
        double delta = .0001;
        assertEquals(expect, interpreter.ValFloat(value), delta);
    }

    @Test
    public void processDATA(){
        Interpreter interpreter = new Interpreter();
        List<Node>list = new ArrayList<>();
        list.add(new StringNode("hello"));
        list.add(new FloatNode(5.5f));
        list.add(new IntegerNode(10));
        DataNode dataNode = new DataNode(list);
        interpreter.processData(dataNode);
        assertEquals(3, interpreter.dataCollection.size());
    }

    @Test
    public void testStoreString(){
        Interpreter interpreter = new Interpreter();
        interpreter.setStringHashMap("name", "mike");
        assertEquals("mike", interpreter.getStringValue("name"));
    }

    @Test
    public void testStoreInt(){
   Interpreter interpreter = new Interpreter();
   interpreter.setIntegerHashMap("name", 1);
   assertEquals(1, interpreter.getIntergerName("name"));
    }

    @Test
    public void testStoreFloat(){
        Interpreter interpreter = new Interpreter();
        interpreter.setFloatHashMap("name", 4.2F);
        assertEquals("name", 4.2F, 4.2F, .0001);
        assertNull(interpreter.getFloatName("Hello"));
    }
*/
}
