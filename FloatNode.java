//This represents our floats within our AST tree
public class FloatNode extends Node{

    private float aFloat;
    public FloatNode(float aFloat){
        this.aFloat = aFloat;
    }

    public float getaFloat(){
        return aFloat;
    }

//This method is used to print out the floats correctly
    @Override
    public String toString() {
       return Float.toString(aFloat);
    }
}
