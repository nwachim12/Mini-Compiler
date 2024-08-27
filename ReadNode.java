import java.util.List;
/*
This class is used to represent a Node that represents what the READ command does in BASIC,
                as it when calls it prints out a list of variables
 */
public class ReadNode extends StatementNode  {
    private  List<Node> variables;
    private StatementNode next;

    public ReadNode(List<Node> variables) {
        this.variables = variables;
    }

    public List<Node> getList(){
        return variables;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "ReadNode variables: ( " + variables + " )";
    }


}
