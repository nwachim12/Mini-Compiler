import java.util.HashMap;
import java.util.LinkedList;
/*
 * This class is used to the create our tokens, as while our code handler is reading each character,
 * 			our lexer is used to create tokens based off certain conditions
 */

public class Lexer {

    private int lineNumber = 1;
    private int charPosition = 0;
    private final HashMap<String, Token.TokenType>knownWords;
    private final HashMap<String, Token.TokenType>twoCharSymbols;
    private final HashMap<String, Token.TokenType>oneCharSymbols;

    public Lexer() {
        knownWords = new HashMap<>();
        oneCharSymbols = new HashMap<>();
        twoCharSymbols = new HashMap<>();
        symbolOneCharMap();
        symbolTwoCharMap();
        knownWordsMap();
    }

    /*
     * HashMaps used to create a token for each known word and symbol used in BASIC code
     * 			and make it easier to store each token as a not a word or number by mistake
     */

    private void symbolOneCharMap() {
        oneCharSymbols.put("=", Token.TokenType.EQUALS);
        oneCharSymbols.put("<", Token.TokenType.LESSSIGN);
        oneCharSymbols.put(">", Token.TokenType.GREATERSIGN);
        oneCharSymbols.put("(", Token.TokenType.LPAREN);
        oneCharSymbols.put(")", Token.TokenType.RPAREN);
        oneCharSymbols.put("*", Token.TokenType.MULTIPLY);
        oneCharSymbols.put("-", Token.TokenType.SUBTRACT);
        oneCharSymbols.put("+", Token.TokenType.ADD);
        oneCharSymbols.put("/", Token.TokenType.DIVIDE);
    }

    private void symbolTwoCharMap() {
        twoCharSymbols.put(">=", Token.TokenType.GREATEREQUAL);
        twoCharSymbols.put("<=", Token.TokenType.LESSTHAN);
        twoCharSymbols.put("<>", Token.TokenType.NOTEQUAL);
    }

    private void knownWordsMap() {
        knownWords.put("PRINT", Token.TokenType.PRINT);
        knownWords.put("Read", Token.TokenType.Read);
        knownWords.put("INPUT", Token.TokenType.Input);
        knownWords.put("Data", Token.TokenType.Data);
        knownWords.put("GOSUB", Token.TokenType.Gosub);
        knownWords.put("FOR", Token.TokenType.For);
        knownWords.put("TO", Token.TokenType.To);
        knownWords.put("END", Token.TokenType.End);
        knownWords.put("STEP", Token.TokenType.Step);
        knownWords.put("NEXT", Token.TokenType.Next);
        knownWords.put("RETURN", Token.TokenType.Return);
        knownWords.put("IF", Token.TokenType.IF);
        knownWords.put("THEN", Token.TokenType.Then);
        knownWords.put("FUNCTION", Token.TokenType.Function);
        knownWords.put("WHILE", Token.TokenType.While);
        knownWords.put(",", Token.TokenType.COMA);
        knownWords.put("DATA", Token.TokenType.Data);
        knownWords.put("READ", Token.TokenType.Read);
        knownWords.put("RANDOM", Token.TokenType.RANDOM);
        knownWords.put("LEFT$", Token.TokenType.LEFT);
        knownWords.put("RIGHT$", Token.TokenType.RIGHT);
        knownWords.put("MID$", Token.TokenType.MID);
        knownWords.put("NUM$", Token.TokenType.NUM);
        knownWords.put("VAL", Token.TokenType.VAL);
        knownWords.put("VAL%", Token.TokenType.VAL_PERCENT);
    }

    public LinkedList<Token> lex(String filename) {
        LinkedList<Token> tokens = new LinkedList<>();
        CodeHandler codeHandler = new CodeHandler(filename);
        /*
         * While loop is used to read to code till the end and
         * 						create different tokens based off certain conditions
         */

        while (!codeHandler.isDone()) {
            char nextChar = codeHandler.Peek(0);
            if(nextChar == ' ' || nextChar == '\t') {
                codeHandler.GetChar();
                charPosition++;
            }
            else if(nextChar == '\n') {
                tokens.add(new Token(Token.TokenType.ENDOFLINE, lineNumber, charPosition));
                codeHandler.GetChar();
                lineNumber++;
                charPosition = 0;
            }
            else if(nextChar == '\r') {
                codeHandler.GetChar();
            }
            else if(nextChar == ':'){
                codeHandler.GetChar();
                tokens.add(new Token(Token.TokenType.LABEL, lineNumber, charPosition));
                charPosition++;
            }
            else if(nextChar == ','){
                codeHandler.GetChar();
                tokens.add(new Token(Token.TokenType.COMA, lineNumber, charPosition));
                charPosition++;
            }
            else if(nextChar == ';'){
                codeHandler.GetChar();
                tokens.add(new Token(Token.TokenType.ENDOFLINE, lineNumber, charPosition));
                charPosition++;
            }
            else if(Character.isLetter(nextChar)) {
                Token word = ProcessWord(codeHandler);
                tokens.add(word);
            }
            else if(Character.isDigit(nextChar)) {
                Token number = ProcessDigit(codeHandler);
                tokens.add(number);
            }
            else if (nextChar == '"') {
                Token stringLiteral = HandleStringLiteral(codeHandler);
                tokens.add(stringLiteral);
            }
            else if(oneCharSymbols.containsKey(String.valueOf(nextChar))) {
                Token symbol = processSymbols(codeHandler);
                tokens.add(symbol);
            }
            else {
                throw new RuntimeException("Error at line " + lineNumber + ", position " + charPosition);
            }
        }
        return tokens;
    }


    /*
     * This method is used to characterize digits based off certain conditions in the while loop
     *    			 and create tokens with correct token types, line number, and character position
     */

    Token ProcessDigit(CodeHandler codeHandler) {
        String accumlatedNumber = "";
        char currentChar = codeHandler.Peek(0);
        while(Character.isDigit(currentChar) || currentChar == '.') {
            accumlatedNumber += currentChar;
            codeHandler.GetChar();
            currentChar = codeHandler.Peek(0);
        }
        Token numberToken = new Token(Token.TokenType.NUMBER, lineNumber, charPosition, accumlatedNumber);
        charPosition += accumlatedNumber.length();
        return numberToken;
    }

    /*
     * This method is used to handle the string literals in the text file and store them as string literal type tokens
     * 				so that the code handler doesn't get confused about " "
     */

    Token HandleStringLiteral(CodeHandler codeHandler) {
        StringBuilder stringBuilder = new StringBuilder();
        codeHandler.GetChar();

        char currentChar = codeHandler.Peek(0);
        while (currentChar != '"') {
            stringBuilder.append(currentChar);
            codeHandler.GetChar();
            currentChar = codeHandler.Peek(0);
        }
        codeHandler.GetChar();
        Token stringToken = new Token(Token.TokenType.STRINGLITERAL, lineNumber, charPosition, stringBuilder.toString());
        return stringToken;
    }

    /*
     * This method is used to characterize letters into words based off certain conditions in the while loop
     *    			 	and create tokens with correct token types, line number, and character position
     *    As well sees if there code keywords inside the text file and creates a separate token for that the character
     */

    Token ProcessWord(CodeHandler codeHandler) {
        String accumlatedWord = "";
        char currentChar = codeHandler.Peek(0);

        while(Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '%' || currentChar == '$') {
            accumlatedWord += currentChar;
            codeHandler.GetChar();
            currentChar = codeHandler.Peek(0);
        }

        if (knownWords.containsKey(accumlatedWord)) {
            charPosition += accumlatedWord.length();
            return new Token(knownWords.get(accumlatedWord), lineNumber, charPosition);
        } else {
            charPosition += accumlatedWord.length();
            return new Token(Token.TokenType.WORD, lineNumber, charPosition, accumlatedWord);
        }
    }

    /*
     * This method is used to see if what type of special token that a character is if it meets a if statement condition
     * 			in the while loop and decides if the symbol is either a one character symbol or
     * 				a two character symbol and stores it as a token by itself
     */

    Token processSymbols(CodeHandler codeHandler) {
        char currentChar = codeHandler.GetChar();
        char nextChar = codeHandler.Peek(0);

        String twoCharSymbol = String.valueOf(currentChar) + nextChar;
        if (twoCharSymbols.containsKey(twoCharSymbol)) {
            codeHandler.GetChar();
            Token.TokenType token = twoCharSymbols.get(twoCharSymbol);
            Token twoSymbol = new Token(token, lineNumber, charPosition);
            return twoSymbol;
        } else {
            String oneCharSymbol = String.valueOf(currentChar);
            if (oneCharSymbols.containsKey(oneCharSymbol)) {
                return new Token(oneCharSymbols.get(oneCharSymbol), lineNumber, charPosition);
            } else {
                throw new RuntimeException("Error at line " + lineNumber + ", position " + charPosition);
            }
        }
    }

}
