public class VisitorPattern implements Interpreter.StatementVisit {
    private StatementNode prev;
    private void setNext(StatementNode node){
        if(prev != null){
            prev.setNext(node);
        }
        prev = node;
    }
    @Override
    public void visit(AssignmentNode node) {
            setNext(node);
    }

    @Override
    public void visit(DataNode node) {
        setNext(node);
    }

    @Override
    public void visit(EndNode node) {
        setNext(node);
    }

    @Override
    public void visit(ForNode node) {
        setNext(node);
    }

    @Override
    public void visit(GoSubNode node) {
        setNext(node);
    }

    @Override
    public void visit(IFNode node) {
        setNext(node);
    }

    @Override
    public void visit(InputNode node) {
        setNext(node);
    }

    @Override
    public void visit(LabeledStatementNode node) {
        setNext(node);
    }

    @Override
    public void visit(NextNode node) {
        setNext(node);
    }

    @Override
    public void visit(PrintNode node) {
        setNext(node);
    }

    @Override
    public void visit(ReadNode node) {
        setNext(node);
    }

    @Override
    public void visit(ReturnNode node) {
        setNext(node);
    }

    @Override
    public void visit(StatementsNode node) {
        setNext(node);
    }
}
