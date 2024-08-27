import java.util.List;
/*
This class is used to represent what the INPUT command does in BASIC,
                    as when called it prints out the input variable or constant string
                                    and prints out a list of variables only (parameters)
 */
public class InputNode extends StatementNode  {

    private  Node input;
    private  List<Node> variableNodeList;
    private StatementNode next;

    public InputNode(Node input, List<Node> variableNodeList) {
        this.input = input;
        this.variableNodeList = variableNodeList;
    }

    public Node getInput(){
        return input;
    }

    public List<Node>getVariableNodeList(){
        return variableNodeList;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "Input Node ( Input = " + input + " Variables = " + variableNodeList + " )";
    }


}
