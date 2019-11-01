
/**
 * PolynomialFormatException class extending IllegalArgumentException
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class PolyFormatException extends IllegalArgumentException {
    /**
     * Empty constructor of a PolyFormatException
     */
    public PolyFormatException(){ 
        super();
    }

    /**
     * Constructor of a PolyFormatException with given error message
     * @param msg the error message
     */
    public PolyFormatException(String msg){ 
        super(msg); 
    }
}