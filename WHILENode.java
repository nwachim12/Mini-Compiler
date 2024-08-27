/*
This class handles the WHILE command when the token is identified in the parser
                as it make sure that it has a boolean expression and an end label
 */
public class WHILENode extends StatementNode{
    private Node leftExpression;
    private Node rightExpression;
    private Token.TokenType operator;
    private String endLabel;
    private StatementNode next;

    public WHILENode(Node leftExpression, Token.TokenType operator, Node rightExpression, String endLabel){
        this.leftExpression = leftExpression;
        this.operator = operator;
        this.rightExpression = rightExpression;
        this.endLabel = endLabel;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {

    }

    @Override
    public String toString() {
        return "WHILE: Left Expression: " + leftExpression + " operator: " + operator +
                " Right Expression: " + rightExpression + " End Label: " + endLabel + " Body :";
    }

}

