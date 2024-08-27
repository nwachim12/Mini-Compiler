/*
This class handles the NEXT command has it make sure that there's only one value
                        after the NEXT command is called
 */
public class NextNode extends StatementNode  {
    private Node value;

    private StatementNode next;

    public NextNode(Node value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NEXT: " + value;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

}
