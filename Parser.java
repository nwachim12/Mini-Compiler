import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/*
This class is used to implement the parser and its methods
 */
public class Parser {
    private TokenManager manager;

    private Node assignedValue;


    public Parser(LinkedList<Token> tokens) {
        this.manager = new TokenManager(tokens);
    }

/*
This method is used to print out the expressions and statements that represents the AST tree
        since it was the last step of the PEMDAS rules
 */
    public Node parse() {
         return statements();
    }

    /*
    This method is used to handle the list of nodes that are statements
     */
    public Node statements() {
        List<StatementNode>statementsList = new ArrayList<>();
        StatementNode statement = statement();
        while(statement != null){
            statementsList.add(statement);
            acceptSeperators();
            statement = statement();
        }
         return new StatementsNode(statementsList);
    }

    /*
    This method is used to decide which type of token the Statement command and
                calls the following methods accordingly
     */
    public StatementNode statement() {
        if (manager.MoreTokens()) {
            if (manager.peek(1).get().getType() == Token.TokenType.LABEL) {
                manager.MatchAndRemove(Token.TokenType.LABEL);
                String label = manager.peek(0).orElseThrow().getValue();
                StatementNode statement = statement();
                return new LabeledStatementNode(label, statement);
                }
            else {
                if (manager.peek(0).get().getType() == Token.TokenType.PRINT) {
                    return printStatements();
                } else if (manager.peek(0).get().getType() == Token.TokenType.WORD) {
                        return assignment();
                } else if (manager.peek(0).get().getType() == Token.TokenType.STRINGLITERAL) {
                    return printString();
                } else if (manager.peek(0).get().getType() == Token.TokenType.Read) {
                    return printRead();
                } else if (manager.peek(0).get().getType() == Token.TokenType.Data) {
                    return printData();
                } else if (manager.peek(0).get().getType() == Token.TokenType.Input) {
                    return inputList();
                } else if (manager.peek(0).get().getType() == Token.TokenType.Gosub) {
                    return parseGOSUB();
                } else if (manager.peek(0).get().getType() == Token.TokenType.Return) {
                    return parseReturn();
                } else if (manager.peek(0).get().getType() == Token.TokenType.For) {
                    return parseFor();
                } else if (manager.peek(0).get().getType() == Token.TokenType.Next) {
                    return parseNext();
                } else if (manager.peek(0).get().getType() == Token.TokenType.IF) {
                    return parseIf();
                } else if (manager.peek(0).get().getType() == Token.TokenType.While) {
                    return parseWhile();
                } else if (manager.peek(0).get().getType() == Token.TokenType.End) {
                    return parseEnd();
                }
            }
        }
        return null;
    }

    /*
    This method handles a function and handles the function name
                    and the parameters inside the function
     */
    public StatementNode parseFunction(){
        String name = null;
        if(manager.peek(0).get().getType() == Token.TokenType.RANDOM){
            manager.MatchAndRemove(Token.TokenType.RANDOM);
            name = "RANDOM";
        }else if(manager.peek(0).get().getType() == Token.TokenType.VAL){
            manager.MatchAndRemove(Token.TokenType.VAL);
            name = "VAL";
        }else if(manager.peek(0).get().getType() == Token.TokenType.VAL_PERCENT){
            manager.MatchAndRemove(Token.TokenType.VAL_PERCENT);
            name = "VAL%";
        }else if(manager.peek(0).get().getType() == Token.TokenType.LEFT){
            manager.MatchAndRemove(Token.TokenType.LEFT);
            name = "LEFT$";
        }else if(manager.peek(0).get().getType() == Token.TokenType.RIGHT){
            manager.MatchAndRemove(Token.TokenType.RIGHT);
            name = "RIGHT$";
        }else if(manager.peek(0).get().getType() == Token.TokenType.MID){
            manager.MatchAndRemove(Token.TokenType.MID);
            name = "MID$";
        }else if(manager.peek(0).get().getType() == Token.TokenType.NUM){
            manager.MatchAndRemove(Token.TokenType.NUM);
            name = "NUM$";
        }
        else {
            return null;
        }
            manager.MatchAndRemove(Token.TokenType.LPAREN);
            List<Node> parameters = parameters();
            manager.MatchAndRemove(Token.TokenType.RPAREN);
            return new FunctionNode(name, parameters);
        }

    /*
    This method handles the list of parameters inside the function
     */
    public List<Node>parameters(){
        List<Node>parameters = new ArrayList<>();
        if (manager.peek(0).get().getType() == Token.TokenType.RPAREN) {
            manager.MatchAndRemove(Token.TokenType.RPAREN);
            return parameters;
        }
        if(manager.peek(0).get().getType() == Token.TokenType.WORD){
            parameters.add(expression());
            while(manager.MatchAndRemove(Token.TokenType.COMA).isPresent()){
                parameters.add(expression());
            }
            return parameters;
        }else {
            return (List<Node>) new RuntimeException("This has to be a variable type");
        }
    }
    /*
    This method is used to identify the type of function that's being parsed based off the name
     */
    private boolean FunctionName(String name){
        return name.equals("RANDOM") || name.equals("LEFT$") || name.equals("RIGHT$") ||
                name.equals("MID$") || name.equals("NUM$") || name.equals("VAL") ||
                name.equals("VAL%");
    }

    /*
    This method handles END command and make sure command is by itself on a line
     */
    public StatementNode parseEnd(){
        manager.MatchAndRemove(Token.TokenType.End);
        return new EndNode();
    }

    /*
    This method handles the WHILE command in Basic,
                as it handles the boolean expression and end label
     */
    public StatementNode parseWhile(){
        Node left;
        Token.TokenType operator;
        Node right;
        manager.MatchAndRemove(Token.TokenType.While);
        left = expression();
        operator = parseOperator();
        right = expression();
        String label = manager.peek(0).orElseThrow().getValue();
        manager.MatchAndRemove(Token.TokenType.WORD);
        manager.MatchAndRemove(Token.TokenType.LABEL);
        StatementNode body = statement();
        return new LabeledStatementNode(label, new WHILENode(left, operator, right,label));
    }

    /*
    This method handles the IF command in Basic,
                    as it handles the boolean expression and label after the THEN command
     */
    public StatementNode parseIf(){
        Node left;
        Token.TokenType operator;
        Node right;
        manager.MatchAndRemove(Token.TokenType.IF);
        left = expression();
        operator = parseOperator();
        right = expression();
        manager.MatchAndRemove(Token.TokenType.Then);
        String label = manager.peek(0).orElseThrow().getValue();
        manager.MatchAndRemove(Token.TokenType.WORD);
        return new IFNode(left, operator, right,label);
    }

    /*
    This method handles the boolean operator in the boolean expression in IF, WHILE commands in BASIC,
                    as it returns the proper token based off the input in the text file
     */
    public Token.TokenType parseOperator(){
        Token.TokenType token = manager.peek(0).get().getType();
        manager.MatchAndRemove(token);
        return token;
    }

    /*
    This method handles the NEXT command in Basic, as it handles the expression after the command
     */
    public StatementNode parseNext(){
        manager.MatchAndRemove(Token.TokenType.Next);
        Node value = expression();
        return new NextNode(value);
    }

    /*
    This method handles the FOR command in Basic, as it handles the boolean expression,
                    and handles the STEP command as well and returns based off certain cases
     */
    public StatementNode parseFor() {
        Node intial;
        Node finalValue;
        Node stepValue = new IntegerNode(1);
        manager.MatchAndRemove(Token.TokenType.For);
        if(manager.peek(0).get().getType() == Token.TokenType.WORD) {
            intial = assignment();
            manager.MatchAndRemove(Token.TokenType.To);
            finalValue = expression();
            if (manager.peek(0).get().getType() == Token.TokenType.Step) {
                manager.MatchAndRemove(Token.TokenType.Step);
                stepValue = expression();
            }
        }else{
            throw new RuntimeException("This isn't a word");
        }
        return new ForNode(intial,finalValue,stepValue);
    }

    /*
    This method handles GOSUB command in basic as it handles the GOTO label
                    and make sure that it's a word
     */
    public StatementNode parseGOSUB(){
        manager.MatchAndRemove(Token.TokenType.Gosub);
        Node left = null;
        if(manager.MoreTokens()) {
            if(manager.peek(0).get().getType() == Token.TokenType.WORD){
             left = expression();
            }else{
                throw new RuntimeException("This isn't a word");
            }
            manager.MatchAndRemove(Token.TokenType.To);
            return new GoSubNode(left);
        }else
            throw new RuntimeException("There's not at least one identifier");
    }

    /*
    This method handles the RETURN command in basic,
                    as it makes sure that RETURN command is by itself
     */
    public StatementNode parseReturn(){
        manager.MatchAndRemove(Token.TokenType.Return);
        if(manager.peek(0).get().getType() == Token.TokenType.ENDOFLINE){
            return new ReturnNode();
        }else{
            throw new RuntimeException("This return isn't alone");
        }
    }

    /*
    This method handles the assignment nodes, so it sees variable and handles
                        the equal and the expression or value that its getting assigned to
     */
    public AssignmentNode assignment(){
        Optional<Token>word = manager.MatchAndRemove(Token.TokenType.WORD);
        if(word.isPresent()) {
            VariableNode variableNode = new VariableNode(word.get().getValue());
            if (variableNode != null) {
                manager.MatchAndRemove(Token.TokenType.EQUALS);
                if(manager.peek(0).get().getType() == Token.TokenType.RANDOM){
                  assignedValue = parseFunction();
                  return new AssignmentNode(variableNode, assignedValue);
                }else if(manager.peek(0).get().getType() == Token.TokenType.VAL){
                    assignedValue = parseFunction();
                    return new AssignmentNode(variableNode, assignedValue);
                }else if(manager.peek(0).get().getType() == Token.TokenType.VAL_PERCENT){
                    assignedValue = parseFunction();
                    return new AssignmentNode(variableNode, assignedValue);
                }else if(manager.peek(0).get().getType() == Token.TokenType.LEFT){
                    assignedValue = parseFunction();
                    return new AssignmentNode(variableNode, assignedValue);
                }else if(manager.peek(0).get().getType() == Token.TokenType.RIGHT){
                    assignedValue = parseFunction();
                    return new AssignmentNode(variableNode, assignedValue);
                }else if(manager.peek(0).get().getType() == Token.TokenType.MID){
                    assignedValue = parseFunction();
                    return new AssignmentNode(variableNode, assignedValue);
                }else if(manager.peek(0).get().getType() == Token.TokenType.NUM){
                    assignedValue = parseFunction();
                    return new AssignmentNode(variableNode, assignedValue);
                }
                assignedValue = expression();
                if (assignedValue != null) {
                    return new AssignmentNode(variableNode, assignedValue);
                }
            }
        }
        return null;
    }

    /*
    This method is used to print out the list created by printlist method
                    and prints out the nodes
     */
    private PrintNode printStatements(){
        manager.MatchAndRemove(Token.TokenType.PRINT);
        List<Node>printList = printList();
            return new PrintNode(printList);
    }
    /*
    This method is used for String literals and creating a new string node with them
     */
    private StringNode printString(){
        String stringLiteral = manager.MatchAndRemove(Token.TokenType.STRINGLITERAL).get().getValue();
        return new StringNode(stringLiteral);
    }
    /*
    This method is used to print out the output of the read node after parsing the list of
                variables
     */
    private StatementNode printRead(){
        manager.MatchAndRemove(Token.TokenType.Read);
        List<Node> printRead = readList();
        return new ReadNode(printRead);
    }
    /*
    This method is used to create a list of variables that represented when the command
                    Read is called in BASIC code
     */
    private List<Node> readList() {
        List<Node> readExpressions = new ArrayList<>();
        if(manager.peek(0).get().getType() == Token.TokenType.WORD) {
            Node expression = expression();
            readExpressions.add(expression);
            while (manager.MatchAndRemove(Token.TokenType.COMA).isPresent()) {
                if(manager.peek(0).get().getType() == Token.TokenType.WORD) {
                    expression = expression();
                    readExpressions.add(expression);
                }else{
                    return (List<Node>) new RuntimeException("Should be a word");
                }
            }
        }else{
            return (List<Node>) new RuntimeException("Should be a word");
        }
        return readExpressions;
    }

    /*
    This method is used to print out a list of either integers, floats numbers or constant strings
                    when the command DATA is called
     */
    private StatementNode printData() {
        manager.MatchAndRemove(Token.TokenType.Data);
        List<Node>nodeList = new ArrayList<>();
        Node node;
        node = expression();
        nodeList.add(node);
        while(manager.MatchAndRemove(Token.TokenType.COMA).isPresent()){
            if(manager.peek(0).get().getType() == Token.TokenType.NUMBER){
                node = expression();
                nodeList.add(node);
            }else if(manager.peek(0).get().getType() == Token.TokenType.FLOAT){
                node = expression();
                nodeList.add(node);
            }else if(manager.peek(0).get().getType() == Token.TokenType.STRINGLITERAL){
                node = expression();
                nodeList.add(node);
            }
        }
        return new DataNode(nodeList);
    }
    /*
    This method is used to create a list of an input and it's parameters
     */
    public InputNode inputList(){
        manager.MatchAndRemove(Token.TokenType.Input);
        Node input = manager.peek(0).get().getType() == Token.TokenType.STRINGLITERAL ? printString() :
                new VariableNode(manager.MatchAndRemove(Token.TokenType.WORD).get().getValue());
        List<Node> variables = printVariables();
        return new InputNode(input, variables);
    }
    /*
    This method is used to create a list of the variables (parameters) of an input when the command
                Input is called
     */
    public List<Node> printVariables(){
        List<Node> expressions = new ArrayList<>();
        if(manager.peek(0).get().getType() == Token.TokenType.WORD) {
            Node expression = expression();
            expressions.add(expression);
            while (manager.MatchAndRemove(Token.TokenType.COMA).isPresent()) {
                if(manager.peek(0).get().getType() == Token.TokenType.WORD) {
                    expression = expression();
                    expressions.add(expression);
                }else{
                    return (List<Node>) new RuntimeException("Should be a word");
                }
            }
        }else{
            return (List<Node>) new RuntimeException("Should be a word");
        }
        return expressions;
    }

    /*
    This method is used to create the list seperated by comas as
                        the list can be a variable or expression represented by node
     */
    public List<Node> printList(){
            List<Node> expressions = new ArrayList<>();
        if (manager.peek(0).get().getType() == Token.TokenType.STRINGLITERAL) {
            expressions.add(printString());
        } else {
            Node expression = expression();
            expressions.add(expression);
            while (manager.MatchAndRemove(Token.TokenType.COMA).isPresent()) {
                expression = expression();
                expressions.add(expression);
            }
        }
        return expressions;
    }
    /*
    This method is used to skip the empty lines in the text file so that the parser doesn't get confused
     */
    private boolean acceptSeperators() {
        boolean seperator = false;
        if(manager.MatchAndRemove(Token.TokenType.ENDOFLINE).isPresent()) {
            seperator = true;
        }
        return seperator;
    }
/*
This method is used to print out the AST tree as it also sees if the expression as a adding or subtracting symbol
 */
    private Node expression() {
        Node leftSide = term();

        while(manager.MoreTokens() && (manager.MatchAndRemove(Token.TokenType.ADD).isPresent())
                || (manager.MatchAndRemove(Token.TokenType.SUBTRACT).isPresent())){
                Token.TokenType token = manager.peek(0).orElseThrow().getType();
                leftSide = new MathOpNode(token == Token.TokenType.ADD ? Operations.PLUS : Operations.SUBTRACT, leftSide, term());
            }
        return leftSide;
    }

//This method is used to see if there's a multiply or division symbol and return it within the AST tree
    private Node term() {
        Node leftSide = factor();
        while(manager.MoreTokens() && (manager.MatchAndRemove(Token.TokenType.MULTIPLY).isPresent())
                || (manager.MatchAndRemove(Token.TokenType.DIVIDE).isPresent())){
            Token.TokenType token = manager.peek(0).orElseThrow().getType();
            leftSide = new MathOpNode(token == Token.TokenType.MULTIPLY ? Operations.MULTIPLY : Operations.DIVIDE, leftSide, factor());
        }
        return leftSide;
    }
/*
This method is used to see if it's a number or a symbol and then returns the start of the AST tree
        for it matches the conditions
 */
    private Node factor() {
        if(manager.MoreTokens()) {
            Optional<Token> optionalToken = manager.peek(0);
            if (optionalToken.isPresent()) {
                Token.TokenType token = manager.peek(0).get().getType();
                if (token == Token.TokenType.NUMBER) {
                    String number = manager.MatchAndRemove(Token.TokenType.NUMBER).get().getValue();
                    if(number.contains(".")){
                        float FloatNumber = Float.parseFloat(number);
                        return new FloatNode(FloatNumber);
                    }else{
                        int IntNumber = Integer.parseInt(number);
                        return new IntegerNode(IntNumber);
                    }
                }
                else if (token == Token.TokenType.UNARY_MINUS) {
                    manager.MatchAndRemove(Token.TokenType.UNARY_MINUS);
                    return new MathOpNode(Operations.SUBTRACT, new IntegerNode(0),factor());
                }

                else if(token == Token.TokenType.WORD){
                   String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                        if (FunctionName(word)) {
                            return parseFunction();
                        }else {
                            return new VariableNode(word);
                        }
                }
                else if(token == Token.TokenType.STRINGLITERAL){
                    String string = manager.MatchAndRemove(Token.TokenType.STRINGLITERAL).get().getValue();
                    return new StringNode(string);
                }
                else if (token == Token.TokenType.LPAREN) {
                    manager.MatchAndRemove(Token.TokenType.LPAREN);
                    Node inner = expression();
                   manager.MatchAndRemove(Token.TokenType.RPAREN);
                    return inner;
                }else if(token == Token.TokenType.RANDOM) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    }else{
                        return null;
                    }
                }else if(token == Token.TokenType.VAL) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    } else {
                        return null;
                    }
                }else if(token == Token.TokenType.VAL_PERCENT) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    } else {
                        return null;
                    }
                }else if(token == Token.TokenType.LEFT) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    } else {
                        return null;
                    }
                }else if(token == Token.TokenType.RIGHT) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    } else {
                        return null;
                    }
                }else if(token == Token.TokenType.MID) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    } else {
                        return null;
                    }
                }else if(token == Token.TokenType.NUM) {
                    String word = manager.MatchAndRemove(Token.TokenType.WORD).get().getValue();
                    if (FunctionName(word)) {
                        return parseFunction();
                    } else {
                        return null;
                    }
                }
                else
                    throw new NullPointerException("This token is null");
            }else
                throw new RuntimeException("There's no token");
        }else
            throw new NullPointerException("This is null");
    }

}
