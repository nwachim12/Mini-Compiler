/*
This class handles the labeled statements and makes sure that it has a label and
                statement
 */
public class LabeledStatementNode extends StatementNode{

    private String label;
    private StatementNode statement;

    public LabeledStatementNode(String label, StatementNode statement){
        this.label = label;
        this.statement = statement;
    }

    public String getLabel(){
        return label;
    }

    public StatementNode getStatement(){
        return statement;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "Label: " + label + " Statement: " + statement;
    }

}

