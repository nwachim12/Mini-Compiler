//This class is used to print out the numbers and symbols correctly
enum Operations{ PLUS, SUBTRACT, MULTIPLY, DIVIDE}

public class MathOpNode extends Node {
    private final Operations operation;
    private final Node leftOperand;
    private final Node rightOperand;
  /*
  This constructor is used to print out the symbols and numbers in the right order
        starting with the symbol followed by the left and right like in a tree
   */
    public MathOpNode(Operations operation, Node leftOperand, Node rightOperand) {
        this.operation = operation;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        return "(" + operation.toString() + " " + leftOperand.toString() + " , " + rightOperand.toString() + ")";
    }


    public Operations getOperations(){
        return operation;
    }
    public Node getLeftOperand(){
        return leftOperand;
    }
    public Node getRightOperand(){
        return rightOperand;
    }
}
