public class Token {

    private final TokenType type;
    private String value;
    private int lineNumber;
    private int charPosition;

     enum TokenType{RANDOM, WORD, NUMBER, ENDOFLINE, IF, End, Step, Next,
        Return, Then, Function, While, To, For, Gosub, Data, Input, Read, PRINT, COMA,
        GREATEREQUAL, LESSTHAN, NOTEQUAL,
        EQUALS, LESSSIGN, GREATERSIGN,LPAREN, RPAREN, MULTIPLY, SUBTRACT, ADD, DIVIDE, LABEL, STRINGLITERAL, FLOAT,
         LEFT, RIGHT, MID, NUM, VAL, VAL_PERCENT, UNARY_MINUS
     }

    public Token(TokenType type, int lineNumber, int charPosition) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.charPosition = charPosition;
    }


    public Token(TokenType type, int lineNumber, int charPosition, String value) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.charPosition = charPosition;
        this.value = value;
    }

    public String toString() {
        if (value != null) {
            if (type == TokenType.STRINGLITERAL) {
                return "(" + type + "," + "\"" + value + "\"" + ")";
            } else {
                return "(" + type + "," + value + ")";
            }
        } else {
            return " " + type.toString() + " ";
        }
    }

    public TokenType getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
}
