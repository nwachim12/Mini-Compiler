//This class represents our integers within our AST tree
public class IntegerNode extends Node{

    private int number;
    public IntegerNode(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
//This method is used to print out the number correctly
    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
