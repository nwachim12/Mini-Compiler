/*
This method is used to create a node that represents when we assign
                        a variable to a value in the program
 */
public class AssignmentNode extends StatementNode  {
    private VariableNode variableNode;
    private Node assignedValue;
    private StatementNode next;
    public AssignmentNode(VariableNode variableNode, Node assignedValue){
        this.variableNode = variableNode;
        this.assignedValue = assignedValue;
    }

    public VariableNode getVariableNode(){
        return variableNode;
    }

    public StatementNode getAssignedValue(){
        return (StatementNode) assignedValue;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "Assignment Node ( " + variableNode + " = " + assignedValue + " )";
    }


}
