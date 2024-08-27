import java.util.List;
/*
This method represents the node create when we see print and is the node
                that represents the list in statements printed out in statementsNode
 */
public class PrintNode extends StatementNode {
    private final List<Node> printNodes;
    private StatementNode next;
    public PrintNode(List<Node> printNodes) {
        this.printNodes = printNodes;
    }

    public List<Node>getPrintNodes(){
        return printNodes;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "Print Node ( " + printNodes + " )";
    }


}
