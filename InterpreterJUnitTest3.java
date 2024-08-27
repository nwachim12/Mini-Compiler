import org.junit.Test;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class InterpreterJUnitTest3 {


    @Test
    public void testEnd(){
        Interpreter interpreter = new Interpreter(null);
        StatementNode endNode = new EndNode();
        interpreter.runProgram(endNode);
        assertEquals(null, interpreter.currentStatement);
    }

    @Test
    public void testReturn(){

    }

    @Test
    public void testIf(){

    }

    @Test
    public void testGoSub(){
        StatementNode goSubNode = new GoSubNode(new StringNode("name"));
        Interpreter interpreter = new Interpreter(goSubNode);
        StatementNode nextNode = new NextNode(new VariableNode("A")); // Assuming a NextNode after returning from GoSub
        interpreter.labeledMap.put("name", nextNode); // Label1 corresponds to NextNode
        interpreter.runProgram(goSubNode);
        assertEquals(nextNode, interpreter.currentStatement);
        assertFalse(interpreter.statementNodes.isEmpty());
    }

    @Test
    public void testBuildList(){
        StatementNode statement1 = new AssignmentNode(new VariableNode("mike"), new IntegerNode(1));
        StatementNode statement2 = new AssignmentNode(new VariableNode("name"), new IntegerNode(2));
        List<StatementNode> statementList = new ArrayList<>();
        statementList.add(statement1);
        statementList.add(statement2);
        StatementsNode statementsNode = new StatementsNode(statementList);
        Interpreter interpreter = new Interpreter(null);
        interpreter.buildList(statementsNode);
        assertEquals(statement1, statement2.getNext());
        assertNull(statement2.getNext());
    }

    @Test
    public void testFor(){

    }

    @Test
    public void testGetNext(){
        StatementNode statementNode1 = new AssignmentNode(new VariableNode("mike"), new IntegerNode(1));
        StatementNode statementNode2 = new AssignmentNode(new VariableNode("name"), new IntegerNode(2));
        statementNode1.setNext(statementNode2);
        assertEquals(statementNode2, statementNode1.getNext());
        assertNull(statementNode2.getNext());
    }

    @Test
    public void testRunProgram(){

    }

    @Test
    public void testEvaluateString(){
        Interpreter interpreter = new Interpreter(null);
        String stringValue = " mike ";
        StringNode stringNode = new StringNode(stringValue);
        String result = interpreter.evaluateString(stringNode);
        assertEquals("String Literal (  mike  )", result);
    }
    @Test
    public void testEvaluateStringVariable(){
        Interpreter interpreter = new Interpreter(null);
        String name = "name";
        String value = "michael";
        interpreter.setStringHashMap(name, value);
        VariableNode variableNode = new VariableNode(name);
        String result = interpreter.evaluateString(variableNode);
        assertEquals(value, result);
    }

    @Test
    public void testBoolean(){
    Interpreter interpreter = new Interpreter(null);
    int value = 2;
    int value2 = 1;
    boolean finalValue = interpreter.evaluateBoolean(value,value2, Token.TokenType.GREATEREQUAL);
    assertEquals(true, finalValue);
    }

    @Test
    public void testGreaterSign(){
        Interpreter interpreter = new Interpreter(null);
        int value = 2;
        int value2 = 1;
        boolean finalValue = interpreter.evaluateBoolean(value,value2, Token.TokenType.GREATERSIGN);
        assertEquals(true, finalValue);
    }

    @Test
    public void testLessSign(){
        Interpreter interpreter = new Interpreter(null);
        int value = 1;
        int value2 = 2;
        boolean finalValue = interpreter.evaluateBoolean(value, value2, Token.TokenType.LESSSIGN);
        assertEquals(true, finalValue);
    }

    @Test
    public void testLessThan(){
        Interpreter interpreter = new Interpreter(null);
        int value = 1;
        int value2 = 2;
        boolean finalValue = interpreter.evaluateBoolean(value, value2, Token.TokenType.LESSTHAN);
        assertEquals(true, finalValue);
    }

    @Test
    public void testEquals(){
        Interpreter interpreter = new Interpreter(null);
        int value = 1;
        int value2 = 1;
        boolean finalValue = interpreter.evaluateBoolean(value, value2, Token.TokenType.EQUALS);
        assertEquals(true, finalValue);
    }

    @Test
    public void testNotEquals(){
        Interpreter interpreter = new Interpreter(null);
        int value = 1;
        int value2 = 2;
        boolean finalValue = interpreter.evaluateBoolean(value, value2, Token.TokenType.NOTEQUAL);
        assertEquals(true, finalValue);
    }
}

