

public class IFNode extends StatementNode  {
    private Node leftExpression;
    private Node rightExpression;
    private Token.TokenType operator;
    private String label;

    public IFNode(Node leftExpression, Token.TokenType operator, Node rightExpression, String label){
        this.leftExpression = leftExpression;
        this.operator = operator;
        this.rightExpression = rightExpression;
        this.label = label;
    }

    public Node getLeftExpression(){
        return leftExpression;
    }
    public Node getRightExpression(){
       return rightExpression;
    }

    public String getLabel(){
        return label;
    }


    @Override
    public void accept(Interpreter.StatementVisit visit) {
        visit.visit(this);
    }

    @Override
    public String toString() {
        return "IF: Left Expression: " + leftExpression + " operator: " + operator + " Right Expression: " + rightExpression + " Label: " + label;
    }

}
