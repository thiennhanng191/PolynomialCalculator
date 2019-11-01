
/**
 * Class representing the Addition Operator implementing Operator interface
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class AddOperator implements Operator
{
    /**
     * Empty constructor for addition operator
     */
    public AddOperator(){
    }
    
    /**
     * Method returning the number representing the priority of the addition operator
     * @return 1 for the number representing the priority of the addition operator
     */
    public int getPriority(){
        return 1; 
    }
    
    /**
     * Method to execute the addition operation on two polynomial
     * @param polyA one of the two polynomials to be added together
     * @param polyB one of the two polynomials to be added together
     * @return the polynomial sum of the two input polynomials
     */
    public Polynomial operate(Polynomial polyA, Polynomial polyB){
        return polyA.add(polyB); 
    }

    /**
     * Create the visual representation of the addition operator (a '+' sign)
     * that is useful for testing
     * @return a string representing a + sign
     */
    public String toString(){
        return "+"; 
    }
}
