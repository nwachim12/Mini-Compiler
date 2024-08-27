public abstract class StatementNode extends Node  {

    protected StatementNode next;


    public void setNext(StatementNode next){
        this.next = next;
    }

    public StatementNode getNext(){
      return next;
    }

    public abstract void accept(Interpreter.StatementVisit visit);
    @Override
    public String toString() {
        return null;
    }

}
