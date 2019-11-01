
/**
 * Class representing the Multiplication Operator implementing Operator interface
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class MulOperator implements Operator
{
    /**
     * Empty constructor for multiplication operator
     */
    public MulOperator(){
    }
    
    /**
     * Method returning the number representing the priority of the multiplication operator
     * @return 2 for the number representing the priority of the multiplication operator
     */
    public int getPriority(){
        return 2; 
    }
    
    /**
     * Method to execute the multiplication operation on two polynomial
     * @param polyA one of the two polynomials to be multiplied together
     * @param polyB one of the two polynomials to be multiplied together
     * @return the polynomial multiplication of the two input polynomials
     */
    public Polynomial operate(Polynomial polyA, Polynomial polyB){
        return polyA.mul(polyB); 
    }
    
    /**
     * Create the visual representation of the addition operator (a '*' sign)
     * that is useful for testing
     * @return a string representing a * sign
     */
    public String toString(){
        return "*"; 
    }
}
