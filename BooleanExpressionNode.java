enum BooleanOp{
    GREATERTHAN, LESSTHAN, LESSSIGN, GREATERSIGN,EQUAL, NOTEQUAL,
}

public class BooleanExpressionNode extends Node{

    public Node leftSide;
    public BooleanOp operation;
    public Node rightSide;

    public Node getLeftSide(){
        return leftSide;
    }

    public Node getRightSide(){
        return rightSide;
    }
    public BooleanOp getOperation(){
        return operation;
    }

    @Override
    public String toString() {
        return "Left: " + leftSide + " Operation: " + operation + " Right: " + rightSide;
    }
}
