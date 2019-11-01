/**
 * Class representing an open parenthesis, implementing the Token Interface
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class OpenParenthesis implements Token
{
    char c; 
    /**
     * Constructor to create an open parenthesis
     */
    public OpenParenthesis()
    {
        c = '(';
    }
    
    /**
     * Make a string representation of an open parenthesis 
     * that is useful for testing 
     * @return a string representation of an open parenthesis 
     */
    public String toString(){
        return "("; 
    }
}
