/*
This class handles the GOSUB command in BASIC and makes sure that it has at least one
                    identifier
 */
public class GoSubNode extends StatementNode  {
    private Node word;
    private Interpreter.StatementVisit visit;


    public GoSubNode(Node word){
        this.word = word;
    }

    public Node getWord(){
        return word;
    }


    @Override
    public String toString() {
        return "GOSUB: " + word;
    }


    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }
}
