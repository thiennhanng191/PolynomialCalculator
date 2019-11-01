
/**
 * Interface for the operators, extending Token interface.
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public interface Operator extends Token
{
    /**
     * abstract method returning the number representing the priority of the operator
     * return the number representing the priority of the operator 
     */
    public int getPriority(); 
    
    /**
     * abstract method to run the operation of the operators
     * @param polyA one of the two polynomials to be operated on
     * @param polyB one of the two polynomials to be operated on
     * return the result polynomial 
     */
    public Polynomial operate(Polynomial polyA, Polynomial polyB);
 
}
