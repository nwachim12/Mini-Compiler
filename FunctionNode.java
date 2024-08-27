import java.util.List;
import java.util.Optional;

/*
This class handles the functions, as when it sees a pre-built Basic function name,
            it makes sure it has a function name and a list of parameters
 */
public class FunctionNode extends StatementNode{
    public String name;
    public List<Node>parameters;
    public FunctionNode(String name, List<Node>parameters){
        this.name = name;
        this.parameters = parameters;
    }
    public String getName(){
        return name;
    }

    public List<Node>getParameters(){
        return parameters;
    }

    @Override
    public void accept(Interpreter.StatementVisit visit) {

    }

    @Override
    public String toString() {
        return "Function:  name: " + name + " parameters ( " + parameters + " )";
    }

}
