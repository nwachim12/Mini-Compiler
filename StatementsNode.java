import java.util.List;
/*
This method represents the statement list when the program is ask to print
            something out, this well create a node that represnts as list of what needs to be printed
 */
public class StatementsNode extends StatementNode  {
    private final List<StatementNode> statementNodeList;
    private Interpreter.StatementVisit visit;

    public StatementsNode(List<StatementNode> statementNodeList) {
        this.statementNodeList = statementNodeList;
    }

    public List<StatementNode> getStatementNodeList() {
        return statementNodeList;
    }


    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (StatementNode statement : statementNodeList) {
            sb.append(statement.toString()).append("\n");
        }
        String string = sb.toString();
        return string;
    }

}

