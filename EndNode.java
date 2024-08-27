/*
This class handles the END command, as it has no parameters so it makes sure that it's by itself
 */
public class EndNode extends StatementNode  {
    private StatementNode next;

    @Override
    public String toString() {
        return "END";
    }

    public void setNext(){
        this.next = next;
    }
    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }
}
