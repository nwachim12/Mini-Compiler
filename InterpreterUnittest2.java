import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InterpreterUnittest2 {
    private Interpreter interpreter;

    @Before
    public void setUp() {
        interpreter = new Interpreter(null);
    }
    @Test
    public void testEvaluateInt_IntegerNode() {
        Interpreter interpreter = new Interpreter(null);
        IntegerNode intNode = new IntegerNode(5);
        assertEquals(5, interpreter.evaluateInt(intNode));
    }

    @Test
    public void testEvaluateFloat_FloatNode(){
        Interpreter interpreter = new Interpreter(null);
        FloatNode floatNode = new FloatNode(5.5F);
        assertEquals(5.5F, interpreter.evaluateFloat(floatNode));
    }
    @Test
    public void testEvaluateInt_MathOpNode_Addition() {
        Interpreter interpreter = new Interpreter(null);
        Node left = new IntegerNode(1);
        Node right = new IntegerNode(1);
        MathOpNode mathOpNode = new MathOpNode(Operations.PLUS, left, right);
        assertEquals(2, interpreter.evaluateInt(mathOpNode));
    }

    @Test
    public void testEvaluateInt_MathOpNode_Subtraction() {
        Interpreter interpreter = new Interpreter(null);
        Node left = new IntegerNode(1);
        Node right = new IntegerNode(1);
        MathOpNode mathOpNode = new MathOpNode(Operations.SUBTRACT, left, right);
        assertEquals(0, interpreter.evaluateInt(mathOpNode));
    }

    @Test
    public void testEvaluateInt_MathOpNode_Multiplication() {
        Interpreter interpreter = new Interpreter(null);
        Node left = new IntegerNode(1);
        Node right = new IntegerNode(2);
        MathOpNode mathOpNode = new MathOpNode(Operations.MULTIPLY, left, right);
        assertEquals(2, interpreter.evaluateInt(mathOpNode));
    }

    @Test
    public void testEvaluateInt_MathOpNode_Division() {
        Interpreter interpreter = new Interpreter(null);
        Node left = new IntegerNode(6);
        Node right = new IntegerNode(2);
        MathOpNode mathOpNode = new MathOpNode(Operations.DIVIDE, left, right);
        assertEquals(3, interpreter.evaluateInt(mathOpNode));
    }

    @Test
    public void testEvaluateStringVariableNode() {
        Interpreter interpreter = new Interpreter(null);
        interpreter.setStringHashMap("x", "Hello, World!");
        VariableNode variableNode = new VariableNode("x");
        String result = interpreter.evaluateString(variableNode);
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testEvaluateStringStringNode() {
        Interpreter interpreter = new Interpreter(null);
        StringNode stringNode = new StringNode("Hello, World!");
        String result = interpreter.evaluateString(stringNode);
        assertEquals("String Literal ( Hello, World! )", result);
    }

}

