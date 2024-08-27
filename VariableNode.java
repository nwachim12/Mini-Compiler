/*
This method represents the variable that were going to assign
                to a value in assignment node
 */
public class VariableNode extends StatementNode{
    public String variableName;
    private StatementNode next;
    public VariableNode(String variableName){
        this.variableName = variableName;
    }

    public String getVariableName(){
        return variableName;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {

    }

    @Override
    public String toString() {
        return " Variable Node ( " + variableName + " )";
    }


}
