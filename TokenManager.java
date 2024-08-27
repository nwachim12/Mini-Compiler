import java.util.LinkedList;
import java.util.Optional;

public class TokenManager{
    private LinkedList<Token> tokenLinkedList;

    public TokenManager(LinkedList<Token>tokenLinkedList){
        this.tokenLinkedList = tokenLinkedList;
    }
    /*
    This method is used to look at the next token that can be compared using the other methods
     */
    public Optional<Token> peek(int j){
        if(j >= 0 && j < tokenLinkedList.size()){
            return Optional.of(tokenLinkedList.get(j));
        }else{
            return Optional.empty();
        }
    }
//This method is used to make that the list isn't empty and continues running
    public boolean MoreTokens(){
        return !tokenLinkedList.isEmpty();
    }
    public Optional<Token>MatchAndRemove(Token.TokenType token){
        if(!tokenLinkedList.isEmpty() && tokenLinkedList.peek().getType().equals(token)) {
            return Optional.of(tokenLinkedList.remove());
        }else {
            return Optional.empty();
        }
    }
}

