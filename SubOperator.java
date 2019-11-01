
/**
 * Class representing the Substraction Operator implementing Operator interface
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class SubOperator implements Operator 
{
    /**
     * Empty constructor for substraction operator
     */
    public SubOperator(){
    }

    /**
     * Method returning the number representing the priority of the substraction operator
     * @return 1 for the number representing the priority of the addition operator
     */
    public int getPriority(){
        return 1; 
    }

    /**
     * Method to execute the substraction operation on two polynomial
     * @param polyA the minuend polynomial
     * @param polyB the subtrahend polynomial
     * @return the polynomial difference of the two input polynomials
     */
    public Polynomial operate(Polynomial polyA, Polynomial polyB){
        return polyA.sub(polyB); 
    }

    /**
     * Create the visual representation of the substraction operator (a '-' sign)
     * that is useful for testing
     * @return a string representing a - sign
     */
    public String toString(){
        return "-"; 
    }
}
