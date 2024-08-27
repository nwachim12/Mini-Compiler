import java.util.List;
/*
This method is used to represent what happens when the command DATA is called,
                as when called it prints a list of either a float, integer or a constant string (string literal)
 */
public class DataNode extends StatementNode{
    private List<Node> Data;

    public DataNode(List<Node> data) {
        this.Data = data;
    }

    public List<Node> getData(){
        return Data;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "Data: ( " + Data + " )";
    }


}
