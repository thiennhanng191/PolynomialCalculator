
/**
 * Class representing the Division Operator implementing Operator interface
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class DivOperator implements Operator
{
    /**
     * Empty constructor for division operator
     */
    public DivOperator(){
    }

    /**
     * Method returning the number representing the priority of the division operator
     * @return 2 for the number representing the priority of the division operator
     */
    public int getPriority(){
        return 2; 
    }

    /**
     * Method to execute the substraction operation on two polynomial
     * @param polyA the dividend polynomial
     * @param polyB the divisor polynomial
     * @return the polynomial difference of the two input polynomials
     */
    public Polynomial operate(Polynomial polyA, Polynomial polyB) throws ArithmeticException{

        Polynomial poly = polyA.div(polyB);

        return polyA.div(polyB); 
    }

    public String toString(){
        return "/"; 
    }
}
