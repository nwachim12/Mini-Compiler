/*
This class handles the FOR command in Basic, as it makes sure it has an assignment expression,
                a final value and if there's a STEP command the value after (default value is 1)
 */
public class ForNode extends StatementNode  {
    private Node assignmentNode;
    private Node finalValue;
    private Node stepValue;
    private Interpreter.StatementVisit visit;
    private StatementNode next;

    public ForNode(Node assignmentNode, Node finalValue, Node stepValue){
        this.assignmentNode = assignmentNode;
        this.finalValue = finalValue;
        this.stepValue = stepValue;
    }

    public Node getAssignmentNode(){
        return assignmentNode;
    }
    public Node getFinalValue(){
        return finalValue;
    }
    public Node getStepValue(){
        return stepValue;
    }


    @Override
    public String toString() {
        return "For: Loop variable: " + assignmentNode + " Final: " + finalValue + " STEP: increments " + stepValue;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }


}
