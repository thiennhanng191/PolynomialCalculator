
/**
 * Class representing a close parenthesis, implementing the Token Interface
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class CloseParenthesis implements Token
{
    char c; 
    /**
     * Constructor to create a close parenthesis
     */
    public CloseParenthesis()
    {
        c = ')';
    }
    
    /**
     * Make a string representation of a close parenthesis 
     * that is useful for testing 
     * @return a string representation of a close parenthesis 
     */
    public String toString(){
        return ")"; 
    }
}
