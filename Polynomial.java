import java.util.Collection;
import java.util.*;
/**
 * Class of polynomial based on a TreeMap.
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class Polynomial implements Token
{
    SortedMap<Integer,Double> poly = new TreeMap<Integer,Double>(Collections.reverseOrder()); 
    //Set<Map.Entry<Integer,Double>> entrysetPoly = poly.entrySet(); 
    //Iterator<Map.Entry<Integer,Double>> itr = entrysetPoly.iterator();

    /**
     * Empty constructor for objects in class Polynomial
     */
    public Polynomial()
    {
    }

    /**
     * Constructor for each monomial for the objects in class Polynomial
     * @param exp the exponent of the monomial added
     * @param coef the coefficent of the monomial added
     */
    public Polynomial(int exp, double coef)
    {
        poly.put(exp,coef);  
    }

    /**
     * Constructor for objects in class Polynomial by cloning a given monomial
     * @param polyA the poly given to be cloned
     */
    public Polynomial(Polynomial polyA)
    {
        Set<Integer> keySetA = polyA.getPoly().keySet(); 
        for (Integer keyA : keySetA) 
            poly.put(keyA, polyA.getPoly().get(keyA)); 
    }

    /**
     * create the visual representation of a polynomial
     * @return a string representing a polynomial
     */
    public String toString() 
    {
        String s = ""; 
        Set<Integer> keySet = poly.keySet();
        List<Integer> listSet = new ArrayList<Integer>(keySet); 
        if (poly.isEmpty()){
            s += "" + 0;
        }
        else if (listSet.size() == 1){
            int exp = listSet.get(0); 
            double coef = poly.get(exp);
            if (coef == 0)
                s += "" + 0; 
            else if (coef > 0){
                if (coef == 1) {
                    if (exp == 1) 
                        s += "x" + " ";
                    else if (exp == 0) 
                        s += "" + coef;
                    else 
                        s += "x" + "^" + exp + " "; 
                }
                else{
                    if (exp == 0)
                        s += "" + coef;
                    else if (exp == 1)
                        s += "" + coef + "x" + " ";
                    else 
                        s += "" + coef + "x" + "^" + exp + " "; 
                }
            }
            else{ // for coef <0
                if (coef == -1){
                    if (exp == 1) 
                        s += "-" + "x" + " ";
                    else if (exp == 0) 
                        s += "" + coef;
                    else 
                        s += "" + coef + "x" + "^" + exp + " ";
                }
                else{
                    if (exp == 0)
                        s += "" + coef;
                    else if (exp == 1)
                        s += "" + coef + "x" + " ";
                    else 
                        s += "" + coef + "x" + "^" + exp + " ";
                }
            }
        }

        else{
            for (int i = 0; i < listSet.size(); i++){
                int exp = listSet.get(i); 
                double coef = poly.get(exp); 
                if (i == 0){// first monomial in the poly
                    if (coef == 0) 
                        s += ""; 
                    if (exp == 0)
                        s += "" + coef;
                    else if (exp == 1){
                        if (coef == 1)
                            s += "x" + " "; 
                        else if (coef == -1)
                            s += "-x" + " "; 
                        else if (coef == 0) 
                            s += ""; 
                        else 
                            s += "" + coef + "x" + " ";
                    }
                    else{ 
                        if (coef == 1)
                            s = "x" + "^" + exp + " ";
                        else if (coef == -1)
                            s += "-x" + "^" + exp + " ";
                        else if (coef == 0) 
                            s += ""; 
                        else 
                            s += "" + coef + "x" + "^" + exp + " "; 
                    }
                }
                else{//check the rest of the monomials 
                    if (coef > 0){
                        if (coef == 1) {
                            if (exp == 1) 
                                s += "+ " + "x" + " ";
                            else if (exp == 0) 
                                s += "+ " + coef;
                            else 
                                s += "+ " + "x" + "^" + exp + " "; 

                        }
                        else{
                            if (exp == 0)
                                s += "+ " + coef;
                            else if (exp == 1)
                                s += "+ " + coef + "x" + " ";
                            else 
                                s += "+ " + coef + "x" + "^" + exp + " "; 
                        }
                    }
                    else if (coef == 0) 
                        s += "";
                    else{ // for coef <0
                        if (coef == -1){
                            if (exp == 1) 
                                s += "- " + "x" + " ";
                            else if (exp == 0) 
                                s += "- " + (-1 * coef);
                            else 
                                s += "- " + "x" + "^" + exp + " ";
                        }
                        else{
                            if (exp == 0)
                                s += "- " + (-1 * coef);
                            else if (exp == 1)
                                s += "- " + (-1 * coef) + "x" + " ";
                            else 
                                s += "- " + (-1 * coef) + "x" + "^" + exp + " ";
                        }
                    }
                }
            }
        }
        return s; 
    }

    /**
     * get the map type of the polynomial
     * @return the polynomial in the form of a map
     */
    public SortedMap<Integer,Double> getPoly(){
        return poly; 
    }

    /**
     * Method for putting a monomial to a poly
     * @param exp the exponent of the monomial added
     * @param coef the coefficient of the monomial added
     * @return the previous coeffient if the added monomial's coeffient has already existed,
     * null otherwise
     */
    public Double put(int exp, double coef){
        //Set<Integer> keySet = poly.keySet();
        if  (coef == 0) 
            return null; 
        if (poly.containsKey(exp)){
            Double prevCoef = poly.get(exp);
            Double tmpCoef = coef + prevCoef; 
            poly.put(exp, tmpCoef); 
            return prevCoef; 
        }
        else{
            poly.put(exp, coef); 
            return null; 
        }
    }

    /**
     * Method to compare two polynomials
     * @param polyCmp the polynomial given to be compared
     * @return true if the two polynomials are equal, false otherwise
     */
    public boolean equals(Polynomial polyCmp) 
    {
        if (poly.entrySet().equals(polyCmp.getPoly().entrySet()))
            return true;
        else 
            return false; 
    }

    /**
     * Method to check if the polynomial is equal to zero
     * @return true if the polynomial is equal to zero, false otherwise
     */
    public boolean checkPolyZero(){
        Set<Integer> keySet = poly.keySet();
        boolean checkPoly = true; 
        if (poly.size() == 0)
            return checkPoly; 
        else{
            for (Integer key : keySet){
                if (poly.get(key) != 0) 
                    checkPoly = false; 
            }
            return checkPoly; 
        }
    }

    /**
     * Method to add two polynomials
     * @param polyA the polynomial to be added to the original polynomial
     * @return the polynomial that is the sum of the two polynomials
     */
    public Polynomial add(Polynomial polyA)
    {
        Polynomial polySum = new Polynomial(); 
        Set<Integer> keySetA = polyA.getPoly().keySet(); 
        Set<Integer> keySetPoly = poly.keySet();
        for(Integer keyA : keySetA) 
            polySum.put(keyA, polyA.getPoly().get(keyA)); 
        for(Integer keyPoly : keySetPoly) 
            polySum.put(keyPoly, poly.get(keyPoly)); 

        return polySum;
    }

    /**
     * Method to substract two polynomials
     * @param polyA the polynomial to be substracted from the original polynomial
     * @return the polynomial that is the difference between the original polynomial 
     * and the given polynomial 
     */
    public Polynomial sub(Polynomial polyA)
    {
        Polynomial polySub = new Polynomial(); 
        Set<Integer> keySetA = polyA.getPoly().keySet();
        Set<Integer> keySetPoly = poly.keySet();
        for(Integer keyPoly : keySetPoly) 
            polySub.put(keyPoly, poly.get(keyPoly));  
        for(Integer keyA : keySetA) 
            polySub.put(keyA, (-1 * polyA.getPoly().get(keyA))); 
        return polySub;   
    }

    /**
     * Method to multiply two polynomials
     * @param polyA the polynomial to be multiplied with the original polynomial
     * @return the polynomial that is the multiplication of the two polynomials
     */
    public Polynomial mul(Polynomial polyA) 
    {
        Polynomial polyMulByPoly = new Polynomial(); 
        Set<Integer> keySetA = polyA.getPoly().keySet();
        Set<Integer> keySetPoly = poly.keySet();
        for (Integer keyPoly : keySetPoly) 
            for (Integer keyA : keySetA){
                double tmpCoef = poly.get(keyPoly) * polyA.getPoly().get(keyA);
                int tmpExp = keyPoly + keyA; 
                polyMulByPoly.put(tmpExp,tmpCoef); 
            }
        return polyMulByPoly; 
    }

    /**
     * Method to divide the original polynomial by a given polynomial
     * @param polyA the polynomial given for the original to be divided by that polynomial
     * @return the polynomial that is the division of the two polynomials
     * @throws ArithmeticExeption if the divisor polynomial is equal to zero 
     * or if the remainder of the division is not equal to zero
     */
    public Polynomial div(Polynomial polyA)
    {
        Polynomial divideByPoly = new Polynomial();
        Polynomial polyRemainder = new Polynomial(this); 
        if (polyA.checkPolyZero()){
            throw new ArithmeticException("Polynomial divide by zero"); 

        }

        while (!polyRemainder.checkPolyZero() && polyRemainder.getPoly().firstKey() >= polyA.getPoly().firstKey()){
            int firstKeyR = polyRemainder.getPoly().firstKey();
            double coefR = polyRemainder.getPoly().get(firstKeyR);
            int firstKeyB = polyA.getPoly().firstKey();
            double coefB = polyA.getPoly().get(firstKeyB);

            double tmpCoef = coefR / coefB; 
            int tmpExp = firstKeyR - firstKeyB;

            Polynomial polyDiv = new Polynomial(tmpExp,tmpCoef); 
            divideByPoly = divideByPoly.add(polyDiv);
            polyRemainder = polyRemainder.sub(polyDiv.mul(polyA));
        }     
        if (polyRemainder.checkPolyZero()) 
            return divideByPoly; 
        else 
            throw new ArithmeticException("Polynomial indivisible");
    } 

}