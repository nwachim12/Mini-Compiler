import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * This class is our code handler and it's job is to read and return each character
 * 				in our text file
 * The methods are used to do different jobs to characters in the text file
 */

public class CodeHandler {

    private String doc;
    private int index;

    /*
     * This constructor is used to be able to create a string that represents our text file
     * 			so that were able to use the lexer on our text file
     */

    public CodeHandler(String filename) {
        try {
            Path myPath = Paths.get(filename);
            doc = new String(Files.readAllBytes(myPath));

        } catch (Exception e) {
            System.err.print(false);
            System.out.println("Error in code");
            e.printStackTrace();
        }

        index = 0;
    }

    /*
     * This method is used to look at the next character while there's still another character to read
     *
     */

    public char Peek(int i) {
        if (index < doc.length()) {
            return doc.charAt(index + i);
        } else throw new IndexOutOfBoundsException("Peek index is out of bounds.");
    }

    /*
     * This method is used to look at the next character till the end of the string
     * 			while there's still another string to read in the text file
     */

    public String PeekString(int i) {
        if(index + i < doc.length()) {
            return doc.substring(index, index + i);
        }else {
            return doc.substring(index);
        }
    }
    /*
     * This method is used to get the character in the text file
     */

    public char GetChar() {
        char current = doc.charAt(index);
        index++;
        return current;
    }

    /*
     * This method is used to in context to swallow characters so that they can have access to strings
     * 			thats not in the front of the text file
     */

    public void Swallow(int i) {
        index += i;
    }

    /*
     * The method is used to make the code handler stops reading at the last character
     * 			and doesn't run for infinity
     */

    public boolean isDone() {
        return index >= doc.length()-1;
    }

    /*
     * This method prints out the remaining characters in the text file
     */

    public String Remainder() {
        int peekRemain = index;
        if(peekRemain < doc.length()) {
            doc = doc.substring(peekRemain);
        }
        return doc;
    }
}