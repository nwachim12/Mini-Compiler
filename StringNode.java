import java.util.List;
/*
This class is used to represent a Node that represents a String literal
                        and is used to idenitify a string literal and properly print out the value of the string
 */
public class StringNode extends StatementNode {
    private final String string;
    private StatementNode next;
    public StringNode(String string) {
        this.string = string;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {

    }

    @Override
    public String toString() {
        return "String Literal ( " + string + " )";
    }



}
