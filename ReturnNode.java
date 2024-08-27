/*
This class handles the RETURN command and make sure that RETURN is by itself since
                    it has no parameters in the constructor
 */
public class ReturnNode extends StatementNode {

    private StatementNode next;
    @Override
    public String toString() {
        return "RETURN";
    }

    public StatementNode getNext(){
        this.next = next;
        return null;
    }
    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }
}
