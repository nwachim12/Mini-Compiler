import java.util.*;

public class Interpreter  {
    public List<String> dataCollection;
    public HashMap<String, StatementNode> labeledMap;
    public HashMap<String, Integer> integerHashMap;
    public HashMap<String, Float> floatHashMap;
    public HashMap<String, String> StringHashMap;

    public List<String>InputList;
    public List<String>PrintList;
    private boolean test = false;
    public StatementNode currentStatement;
    public Stack<StatementNode>statementNodes;
    public boolean loop = true;

    public void setTest(boolean test){
        this.test = test;
    }
    public void setInputList(List<String>InputList){
        this.InputList = InputList;
    }
    public void setPrintList(List<String>PrintList){
        this.PrintList = PrintList;
    }

    public Interpreter(StatementNode node) {
        dataCollection = new ArrayList<>();
        labeledMap = new HashMap<>();
        integerHashMap = new HashMap<>();
        floatHashMap = new HashMap<>();
        StringHashMap = new HashMap<>();
        statementNodes = new Stack<>();
        traverse((StatementsNode) node);
        buildList(node);
        currentStatement = node;
    }

    /*
    This method is used to build the list of statement nodes,
                so that it could to store it for later use especially for
                                FOR, GOSUB, IF commands
     */
    public void buildList(StatementNode s) {
        if(s instanceof StatementsNode) {
            List<StatementNode>list = ((StatementsNode) s).getStatementNodeList();
                for (int i = 0; i < list.size() -1; i++) {
                   StatementNode currentNode = list.get(i);
                   StatementNode nextNode = list.get(i + 1);
                   currentNode.setNext(nextNode);
                }
        }else{
            throw new RuntimeException("Invalid StatementNode type");
        }
    }

    /*
    This method is used to run the text file as if it was running actual code
                        and make sure that it doesn't go for infinity
     */
    public void runProgram(StatementNode node){
        loop = true;
        currentStatement = node;
        while(loop && currentStatement != null){
            Interpret(currentStatement);
            if(currentStatement instanceof EndNode){
                loop = false;
                System.out.println("End of program");
            }else {
                currentStatement = currentStatement.getNext();
            }
        }
    }

    /*
    This method is used to determine the type of node and locate the string value in
                    any of the hashmaps and remove it from the list collected from DATA or
                                store it in the value in the correct hashmap
     */
    public void Interpret(StatementNode node){
        this.currentStatement = node;
            if (node instanceof ReadNode) {
                ReadNode read = (ReadNode) node;
                for (Node variableNode : read.getList()) {
                    String name = variableNode.toString();
                    if (integerHashMap.containsKey(name)) {
                        integerHashMap.put(name, Integer.parseInt(dataCollection.remove(0)));
                    } else if (floatHashMap.containsKey(name)) {
                        floatHashMap.put(name, Float.parseFloat(dataCollection.remove(0)));
                    } else if (StringHashMap.containsKey(name)) {
                        StringHashMap.put(name, dataCollection.remove(0));
                    }
                }
            } else if (node instanceof AssignmentNode) {
                AssignmentNode assignmentNode = (AssignmentNode) node;
                String variableNode = String.valueOf(assignmentNode.getVariableNode());
                if (integerHashMap.containsKey(variableNode)) {
                    integerHashMap.put(variableNode, evaluateInt(assignmentNode.getAssignedValue()));
                } else if (floatHashMap.containsKey(variableNode)) {
                    floatHashMap.put(variableNode, evaluateFloat(assignmentNode.getAssignedValue()));
                } else if (StringHashMap.containsKey(variableNode)) {
                    StringHashMap.put(variableNode, variableNode.toString());
                }
            } else if (node instanceof InputNode) {
                InputNode inputNode = (InputNode) node;
                for (Node variable : inputNode.getVariableNodeList()) {
                    String name = variable.toString();
                    if (test = true) {
                        setTest(test);
                        String input = InputList.get(0);
                        InputList.remove(0);
                        InputList.add(input);
                        setInputList(InputList);
                    }
                    Scanner scanner = new Scanner(System.in);
                    if (integerHashMap.containsKey(name)) {
                        int value = scanner.nextInt();
                        AssignmentNode assignmentNode = new AssignmentNode(new VariableNode(name), new IntegerNode(value));
                        Interpret(assignmentNode);
                    } else if (floatHashMap.containsKey(name)) {
                        float value = scanner.nextFloat();
                        AssignmentNode assignmentNode = new AssignmentNode(new VariableNode(name), new FloatNode(value));
                        Interpret(assignmentNode);
                    } else if (StringHashMap.containsKey(name)) {
                        String value = scanner.toString();
                        AssignmentNode assignmentNode = new AssignmentNode(new VariableNode(name), new StringNode(value));
                        Interpret(assignmentNode);
                    }
                    scanner.close();
                }
            } else if (node instanceof PrintNode) {
                PrintNode printNode = (PrintNode) node;
                for (Node child : printNode.getPrintNodes()) {
                    if (test) {
                        setTest(test);
                        String output = String.valueOf(PrintList);
                        PrintList.add(output);
                        setPrintList(PrintList);
                    } else {
                        if (child instanceof VariableNode) {
                            String name = ((VariableNode) child).getVariableName();
                            if (integerHashMap.containsKey(name)) {
                                System.out.println(integerHashMap.get(name));
                            } else if (floatHashMap.containsKey(name)) {
                                System.out.println(floatHashMap.get(name));
                            } else if (StringHashMap.containsKey(name)) {
                                System.out.println(StringHashMap.get(name));
                            }
                        } else if (child instanceof IntegerNode) {
                            System.out.println(((IntegerNode) child).getNumber());
                        } else if (child instanceof FloatNode) {
                            System.out.println(((FloatNode) child).getaFloat());
                        } else if (child instanceof StringNode) {
                            System.out.println(((StringNode) child).toString());
                        } else if (child instanceof MathOpNode) {
                            System.out.println(evaluateFloat(child));
                        }
                    }
                }
            } else if (node instanceof IFNode) {
                IFNode ifNode = (IFNode) node;
                int left = evaluateInt(ifNode.getLeftExpression());
                int right = evaluateInt(ifNode.getRightExpression());
                Token.TokenType tokenType = null;
                boolean conditionIf = evaluateBoolean(left, right, tokenType);
                if (conditionIf) {
                    String label = ifNode.getLabel();
                    if (labeledMap.containsKey(label)) {
                        currentStatement = labeledMap.get(label);
                    } else {
                        throw new RuntimeException("This label is invalid or doesn't exist");
                    }
                }

            } else if (node instanceof GoSubNode) {
                GoSubNode goSubNode = (GoSubNode) node;
                statementNodes.push(goSubNode);
                String label = String.valueOf(goSubNode.getWord());
                if (labeledMap.containsKey(label)) {
                    currentStatement = labeledMap.get(label);
                } else {
                    throw new RuntimeException("Label isn't in hashmap for GOSUB");
                }
            } else if (node instanceof ReturnNode) {
                if (!statementNodes.isEmpty()) {
                    currentStatement = statementNodes.pop();
                } else {
                    throw new RuntimeException("Return invalid");
                }
            } else if (node instanceof ForNode) {
                ForNode forNode = (ForNode) node;
                String name = String.valueOf(forNode.getAssignmentNode());
                int assigned = evaluateInt(forNode.getAssignmentNode());
                int finalValue = evaluateInt(forNode.getFinalValue());
                int stepValue = evaluateInt(forNode.getStepValue());
                if(!integerHashMap.containsKey(name)){
                    integerHashMap.put(name,assigned);
                }else {
                    integerHashMap.get(name);
                }
                int currentValue = integerHashMap.get(name);
                if(currentValue <= finalValue){
                    statementNodes.push(forNode.getNext());
                }else{
                    while(currentStatement != null){
                        while(!(currentStatement instanceof NextNode)){
                            currentStatement = currentStatement.getNext();
                        }
                    }
                    currentStatement = currentStatement.getNext();
                }
            } else if (node instanceof NextNode) {
                if (!statementNodes.isEmpty()) {
                    currentStatement = statementNodes.pop();
                } else {
                    throw new RuntimeException("Next invalid");
                }
            } else if (node instanceof EndNode) {
                if (statementNodes.isEmpty()) {
                    loop = false;
                }
        }else{
                throw new RuntimeException("Invalid Node type");
            }
    }

    /*
    This method is used to evaluate the assigned value
                    which could an integer, float or math operation and returns it
                            but if it's a variable then it only returns the integer value
     */
    public int evaluateInt(Node node) {
        if (node instanceof VariableNode) {
            String name = ((VariableNode) node).getVariableName();
            return getIntergerValue(Integer.parseInt(name));
        } else if (node instanceof IntegerNode) {
            return ((IntegerNode) node).getNumber();
        } else if (node instanceof MathOpNode) {
            MathOpNode math = (MathOpNode) node;
            int left = evaluateInt(math.getLeftOperand());
            int right = evaluateInt(math.getRightOperand());
            if (math.getOperations() != null) {
                switch (math.getOperations()) {
                    case PLUS -> {
                        return left + right;
                    }
                    case DIVIDE -> {
                        if (right == 0) {
                            throw new RuntimeException("Can't divide by zero");
                        } else {
                            return left / right;
                        }
                    }
                    case MULTIPLY -> {
                        return left * right;
                    }
                    case SUBTRACT -> {
                        return left - right;
                    }
                    default -> {
                        throw new RuntimeException("Isn't a real operator");
                    }
                }
            }
        } else if (node instanceof FunctionNode) {
            FunctionNode functionNode = (FunctionNode) node;
            if (functionNode != null) {
                switch (functionNode.getName().toUpperCase()) {
                    case "RANDOM":
                        return RANDOM();
                    case "VAL":
                        return Val(evaluateString(functionNode.getParameters().get(0)));
                    case "VAL%":
                        return (int) ValFloat(evaluateString(functionNode.getParameters().get(0)));
                    case "NUM":
                        return Integer.parseInt(NUM(evaluateInt(functionNode.getParameters().get(0))));
                    case "NUM%":
                        return (int) Float.parseFloat(NUMFloat(evaluateFloat(functionNode.getParameters().get(0))));
                    case "LEFT$":
                        String leftSide = evaluateString(functionNode.getParameters().get(0));
                        int leftInt = evaluateInt(functionNode.getParameters().get(0));
                        return Integer.parseInt(LEFT(leftSide, leftInt));
                    case "RIGHT$":
                        String rightSide = evaluateString(functionNode.getParameters().get(0));
                        int rightInt = evaluateInt(functionNode.getParameters().get(0));
                        return Integer.parseInt(RIGHT(rightSide, rightInt));
                    case "MID$":
                        String midString = evaluateString(functionNode.getParameters().get(0));
                        int startIndex = evaluateInt(functionNode.getParameters().get(0));
                        int endIndex = evaluateInt(functionNode.getParameters().get(0));
                        return Integer.parseInt(MID(midString, startIndex, endIndex));

                }
            }
        } else{
            throw new RuntimeException("Invalid Math Operation Node");
        }
        return 0;
    }

    /*
    This method is used to evaluate the assigned value
                    which could a float or math operation and returns it
                            but if it's a variable then it only returns the float value
     */
    public float evaluateFloat(Node node){
        if(node instanceof VariableNode){
            String name = ((VariableNode)node).getVariableName();
            return getFloatValue(Integer.parseInt(name));
        }else if(node instanceof IntegerNode){
            return ((IntegerNode)node).getNumber();
        }else if(node instanceof MathOpNode) {
            MathOpNode math = (MathOpNode) node;
            float left = evaluateFloat(math.getLeftOperand());
            float right = evaluateFloat(math.getRightOperand());
            if (math.getOperations() != null) {
                switch (math.getOperations()) {
                    case PLUS -> {
                        return left + right;
                    }
                    case DIVIDE -> {
                        if (right == 0) {
                            throw new RuntimeException("Can't divide by zero");
                        } else {
                            return left / right;
                        }
                    }
                    case MULTIPLY -> {
                        return left * right;
                    }
                    case SUBTRACT -> {
                        return left - right;
                    }
                    default -> {
                        throw new RuntimeException("Isn't a real operator");
                    }
                }
            }
        }else if(node instanceof FunctionNode){
                    FunctionNode functionNode = (FunctionNode) node;
                    if(functionNode != null){
                        switch(functionNode.getName().toUpperCase()){
                            case "RANDOM":
                                return RANDOM();
                            case "VAL":
                                return Val(evaluateString(functionNode.getParameters().get(0)));
                            case "VAL%":
                                return ValFloat(evaluateString(functionNode.getParameters().get(0)));
                            case "NUM":
                                return Integer.parseInt(NUM(evaluateInt(functionNode.getParameters().get(0))));
                            case "NUM%":
                                return  Float.parseFloat(NUMFloat(evaluateFloat(functionNode.getParameters().get(0))));
                            case "LEFT$":
                                String leftSide = evaluateString(functionNode.getParameters().get(0));
                                int leftInt = evaluateInt(functionNode.getParameters().get(0));
                                return Float.parseFloat(LEFT(leftSide,leftInt));
                            case "RIGHT$":
                                String rightSide = evaluateString(functionNode.getParameters().get(0));
                                int rightInt = evaluateInt(functionNode.getParameters().get(0));
                                return Float.parseFloat(RIGHT(rightSide,rightInt));
                            case "MID$":
                                String midString = evaluateString(functionNode.getParameters().get(0));
                                int startIndex = evaluateInt(functionNode.getParameters().get(0));
                                int endIndex = evaluateInt(functionNode.getParameters().get(0));
                                return Float.parseFloat(MID(midString,startIndex,endIndex));

                        }
                    }
        }else{
            throw new RuntimeException("Invalid Math Operation Node");
        }
        return 0;
    }
    /*
    This method is used to evaluate a String variable to decide if it's just a normal string
                        or a variable
     */
    public String evaluateString(Node node){
        if(node instanceof VariableNode){
            String name = ((VariableNode)node).getVariableName();
            return getStringValue(name);
        }else if(node instanceof StringNode){
            return (node).toString();
        }else{
            throw new RuntimeException("Invalid String Node");
        }
    }

    /*
    This method is used to evaluate a boolean expression and decide what each
                    token does in the expression
     */
    public boolean evaluateBoolean(int left, int right, Token.TokenType tokenType){
            switch(tokenType){
                case EQUALS -> {
                    return left == right;
                }
                case NOTEQUAL -> {
                    return left != right;
                }
                case LESSSIGN -> {
                    return left < right;
                }
                case LESSTHAN -> {
                    return left <= right;
                }
                case GREATERSIGN -> {
                    return left > right;
                }
                case GREATEREQUAL -> {
                    return left >= right;
                }
                default ->{
                    throw new RuntimeException("Invalid operator comparison or invalid boolean expression");
                }
        }
    }


    interface StatementVisit {
        //void visit(LabeledStatement node);
        void visit(AssignmentNode node);
        void visit(DataNode node);
        void visit(EndNode node);
        void visit(ForNode node);
        void visit(GoSubNode node);
        void visit(IFNode node);
        void visit(InputNode node);
        void visit(LabeledStatementNode node);
        void visit(NextNode node);
        void visit(PrintNode node);
        void visit(ReadNode node);
        void visit(ReturnNode node);
        void visit(StatementsNode node);
    }

    interface StatementAccept {
        void accept(StatementVisit visit);
    }


/*
    This method is used to look through if it sees a DATA node
                            for the 3 different types that can be assigned to data,
                                    int, float or string and store it within a list that we can use different use
     */
    public void processData(StatementNode node) {
        if (node instanceof DataNode) {
            DataNode dataNode = (DataNode) node;
            List<Node> data = dataNode.getData();
            for (Node n : data) {
                if (n instanceof IntegerNode) {
                    int value = ((IntegerNode) n).getNumber();
                    integerHashMap.put(n.toString(), value);
                    dataCollection.add(String.valueOf(value));
                } else if (n instanceof FloatNode) {
                    float value = ((FloatNode) n).getaFloat();
                    floatHashMap.put(n.toString(), value);
                    dataCollection.add(String.valueOf(value));
                } else if (n instanceof StringNode) {
                    String value = ((StringNode) n).toString();
                    StringHashMap.put(n.toString(), value);
                    dataCollection.add(String.valueOf(value));
                } else {
                    throw new RuntimeException("This is null or isn't the right type for DATA");
                }
            }
        } else if (node instanceof LabeledStatementNode) {
            LabeledStatementNode labelNode = (LabeledStatementNode) node;
            String label = labelNode.getLabel();
            StatementNode statement = labelNode.getStatement();
            labeledMap.put(label, statement);
            processData(statement);
        }
    }

    /*
    This method is used to when not null looks through the list of statements and
                            looks for a labeled statement or data assigned to the DATA command
     */
    public void traverse(StatementsNode node) {
        if (node == null) {
            return;
        } else {
            buildList(node);
            for (StatementNode statment : node.getStatementNodeList()) {
                VisitorPattern pattern = new VisitorPattern();
                node.accept(pattern);
                processData(statment);
                Interpret(statment);
            }
        }
    }

    public int getIntergerValue(int value) {
        return integerHashMap.get(value);
    }

    public Float getFloatValue(int value) {
        return floatHashMap.get(value);
    }

    public void setStringHashMap(String name, String value) {
        StringHashMap.put(name, value);
    }

    public String getStringValue(String value) {
        return StringHashMap.get(value);
    }

    /*
    This method is used to implement the function RANDOM,
                    which returns a random number
     */
    public static int RANDOM() {
        Random random = new Random();
        int randomInt = random.nextInt();
        return randomInt;
    }

    /*
    This method is used to implement the function VAL,
                which converts a String value to an int value
     */
    public static int Val(String value) {
        return Integer.parseInt(value);
    }

    /*
    This method is used to implement the function VAL%,
                        which converts a String value to a float value
     */
    public static float ValFloat(String value) {
        return Float.parseFloat(value);
    }

    /*
    This method is used to implement the function NUM,
                             which takes the int value of a String
     */
    public static String NUM(int value) {
        return String.valueOf(value);
    }

    /*
    This method is used to implement the function NUM,
                             which takes the float value of a String
     */
    public static String NUMFloat(float value){
        return String.valueOf(value);
    }

    /*
    This method is used to implement the function LEFT,
                    which takes the left most of a string
     */
    public static String LEFT(String string, int index){
        return string.substring(0, index);
    }

    /*
    This method is used to implement the function RIGHT,
                    which takes the right most of a string
     */
    public static String RIGHT(String string, int index){
        return string.substring(string.length() - index);
    }
    /*
    This method is used to implement the function MID,
                which takes the mid of a string seperated by 2 indexes
     */
    public static String MID(String string, int index1, int index2){
        return string.substring(index1, index1+index2);
    }
}