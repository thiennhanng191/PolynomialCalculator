import java.util.*;
/**
 * Finite State Machine Class to read and evaluate polynomial arithmetic calculations
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class FSM
{
    /**
     * Enum class for the states of reading a given polynomial
     */
    enum ReadPolyState{
        WSIGN(false), 

        WINT(false), 

        INT(true), 

        WDEC(false),

        DEC(true), 

        X(true), 

        WEXP(false), 

        VAR(true),

        EXP(true), 

        CLOSEPAREN(true), 

        ERR(false); 

        private final boolean isAccept; //field
        /**
         * Constructor for the states of the finite state machine
         * @param isAccept the boolean value of the state
         */
        ReadPolyState(boolean isAccept){
            this.isAccept = isAccept; 
        }

        /**
         * Method returning the boolean value of the states of the finite state machine
         * @return true is the state is acceptable for the finite state machine to end on, 
         * false otherwise
         */
        public boolean isAccept(){
            return isAccept; 
        }
    }

    Map <Character, Polynomial> memory = new HashMap<Character, Polynomial>(); 
    char storingVar = ' '; // variable for storing polynomial

    /**
     * Method checking if a character is an operator and return the operator value
     * @param c the given character to be checked 
     * @return a new operator object, either addition, substraction, multiplication and division; 
     * null if the character is not an operator
     */
    private Operator checkOperator(char c){
        if (c == '+'){
            return new AddOperator(); 
        }
        else if (c == '-'){
            return new SubOperator(); 
        }
        else if (c == '*'){
            return new MulOperator(); 
        }
        else if (c == '/'){
            return new DivOperator(); 
        }
        else 
            return null;
    }

    /**
     * Method constructing a polynomial (a monomial to be precise) given two strings representing the coeffient and exponent
     * @param doubleStrCoef a string representation of the number value of the coefficient (a double)
     * @param intStrExp a string representation of the number value of the exponent (an integer)
     * @return a polynomial (a monomial to be precise) resulted
     */
    private Polynomial inputPoly(String doubleStrCoef, String intStrExp){
        double coef = 0; 
        int exp = 0; 
        if (doubleStrCoef == null || doubleStrCoef.equals("")) 
            coef = 0;
        else 
            coef = Double.parseDouble(doubleStrCoef); 
        if (intStrExp == null || intStrExp.equals(""))
            exp = 1; 
        else 
            exp = Integer.parseInt(intStrExp); 
        Polynomial inputPoly = new Polynomial(exp, coef);
        return inputPoly; 
    }

    /**
     * Method representing the finite state machine to read the input polynomial calculation
     * @param input a string representation of a polynomial calculation
     * @return the list of tokens of the input polynomial calculation with the infix notation
     */
    public List<Token> ReadPoly(String input){
        List<Token> tokenList = new ArrayList<Token>(); 
        ReadPolyState curState = ReadPolyState.WSIGN;

        input = input.replace(" ",""); 

        int i = 0;
        if (input.contains("=")){
            String[] splitInput = input.split("=",2); 
            if (splitInput.length > 2)
                throw new PolyFormatException("Invalid Polynomial"); 
            else{
                input = splitInput[1]; 
                if (splitInput[0].length() == 1){
                    storingVar = splitInput[0].charAt(0);
                    if (!Character.isLetter(storingVar) || storingVar == 'x' || storingVar == 'x') 
                        throw new IllegalArgumentException("Variable must be a letter different from x");
                }
                else 
                    throw new IllegalArgumentException("Invalid storing variable");
            }
        }

        int inputLen = input.length(); 

        String doubleStrCoef = ""; // string that holds the coef of a monomial
        String intStrExp = ""; //string that holds the exp of a monomial 
        double coef = 0; 
        int exp = 0; 
        while (curState != ReadPolyState.ERR && i < inputLen){ 
            char c = input.charAt(i++);// find char at i and THEN increment i
            //System.out.println(i);
            //char checkSign = input.charAt(0);  
            // System.out.println("c = " + c);  
            if (((Character) c).equals(','))
                throw new PolyFormatException("Polynomial cannot contain a comma"); 

            switch (curState){
                case WSIGN: //waiting for a sign 
                if (c == '('){
                    tokenList.add(new OpenParenthesis()); 
                }
                else if (c == '+' || c == '-'){
                    tokenList.add(new Polynomial()); 
                    tokenList.add(checkOperator(c)); 
                    curState=ReadPolyState.WINT;
                }
                else if (c == '.'){
                    doubleStrCoef += c; 
                    curState = ReadPolyState.WDEC; 
                }
                else if (Character.isDigit(c)){
                    doubleStrCoef += c; 
                    curState = ReadPolyState.INT; 
                }
                else if (c == 'x' || c == 'X'){
                    doubleStrCoef += "1"; //when there's a x but not coef => coef = 1 
                    curState = ReadPolyState.X;
                }
                else if (Character.isLetter(c)){
                    if (memory.containsKey(c)){
                        Polynomial poly = memory.get(c);
                        if (doubleStrCoef.equals("-"))
                            poly = poly.mul(new Polynomial(0,-1));
                        tokenList.add(poly); 
                        doubleStrCoef = "";
                        curState = ReadPolyState.VAR; 
                    }
                    else 
                        throw new NullPointerException("Variable doesn't contain a polynomial"); 
                }
                else if (c == ')')
                    throw new PolyFormatException("Misplaced close parenthesis");
                else{
                    curState = ReadPolyState.ERR;
                }
                break; 

                case WINT: 
                if (c == '('){ // check for '(' when looping back after a polynomial has been added 
                    tokenList.add(new OpenParenthesis());;
                    curState = ReadPolyState.WSIGN; 
                }
                else if (Character.isDigit(c)){
                    doubleStrCoef += c; 
                    curState = ReadPolyState.INT; 
                }
                else if (c == '.'){
                    doubleStrCoef += c; 
                    curState = ReadPolyState.WDEC; 
                }
                else if (c == 'x' || c == 'X'){
                    doubleStrCoef += "1"; //when there's a x but not coef => coef = 1 
                    curState = ReadPolyState.X;
                }
                else if (Character.isLetter(c)){
                    if (memory.containsKey(c)){
                        Polynomial poly = memory.get(c);
                        if (doubleStrCoef.equals("-"))
                            poly = poly.mul(new Polynomial(0,-1));
                        tokenList.add(poly); 
                        doubleStrCoef = "";
                        curState = ReadPolyState.VAR; 
                    }
                    else 
                        throw new NullPointerException("Variable doesn't contain a polynomial"); 
                }

                else
                    curState = ReadPolyState.ERR;
                break; 

                case INT: //reading integer digits
                if (Character.isDigit(c)){
                    doubleStrCoef += c; 
                }
                else if (c == '.'){
                    doubleStrCoef += c; 
                    curState = ReadPolyState.WDEC; 
                }
                else if (c == 'x' || c == 'X'){
                    curState = ReadPolyState.X; 
                }
                else if (checkOperator(c) != null){
                    if (intStrExp.equals("")) 
                        intStrExp+="0";
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp)); 
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(checkOperator(c)); 
                    curState = ReadPolyState.WINT;
                }
                else if (c == ')'){
                    if (intStrExp.equals("")) 
                        intStrExp+="0";
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp));
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(new CloseParenthesis());

                    curState = ReadPolyState.CLOSEPAREN;
                }
                else 
                    curState = ReadPolyState.ERR; 
                break; 

                case WDEC: 
                if (Character.isDigit(c)){
                    doubleStrCoef += c; 
                    curState = ReadPolyState.DEC;
                }
                else 
                    curState = ReadPolyState.ERR; 
                break; 

                case DEC: 
                if (Character.isDigit(c)){
                    doubleStrCoef += c; 
                }
                else if (c == 'x' || c == 'X'){
                    curState = ReadPolyState.X; 
                }
                else if (checkOperator(c) != null){
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp));
                    // after a poly is added, renew the doubleStrCoef and intStrCoef to add new poly
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(checkOperator(c)); 
                    curState = ReadPolyState.WINT;
                }
                else if (c == ')'){
                    if (intStrExp.equals("")) 
                        intStrExp+="0";
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp));
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(new CloseParenthesis());

                    curState = ReadPolyState.CLOSEPAREN;
                }
                else 
                    curState = ReadPolyState.ERR; 
                break; 

                case X: 
                if (c == '^'){
                    curState = ReadPolyState.WEXP;
                }
                else if (checkOperator(c) != null){
                    if (intStrExp.equals("")) 
                        intStrExp += "1";
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp)); 
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(checkOperator(c)); 
                    curState = ReadPolyState.WINT;
                }
                else if (c == ')'){
                    if (intStrExp.equals("")) 
                        intStrExp+="1";
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp));
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(new CloseParenthesis());

                    curState = ReadPolyState.CLOSEPAREN;
                }
                else
                    curState = ReadPolyState.ERR; 
                break; 

                case WEXP: 
                if (Character.isDigit(c)){
                    intStrExp += c; 
                    curState = ReadPolyState.EXP;
                }
                else if (c == '-'){
                    throw new PolyFormatException("Polynomial does not support negative exponent");
                }
                else if (c == '('){
                    throw new PolyFormatException("Cannot have open parenthesis after caret");
                }
                else{
                    throw new PolyFormatException("Unsupported character after caret");
                }
                break; 

                case VAR: 
                if (checkOperator(c) != null){
                    tokenList.add(checkOperator(c)); 
                    curState = ReadPolyState.WINT;
                }
                else if (c == ')'){
                    tokenList.add(new CloseParenthesis());
                    curState = ReadPolyState.CLOSEPAREN;
                }
                else 
                    curState = ReadPolyState.ERR; 
                break;    

                case EXP: 
                if (Character.isDigit(c)){ // when the exp has more than 1 digit
                    intStrExp += c;
                }
                else if (c == ')'){
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp));
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(new CloseParenthesis());

                    curState = ReadPolyState.CLOSEPAREN;
                }
                else if (checkOperator(c) != null){
                    tokenList.add(inputPoly(doubleStrCoef,intStrExp)); 
                    doubleStrCoef = ""; 
                    intStrExp = ""; 
                    tokenList.add(checkOperator(c)); 
                    curState = ReadPolyState.WINT;
                }
                else 
                    curState = ReadPolyState.ERR; 
                break; 

                case CLOSEPAREN: 
                if (c == ')') 
                    tokenList.add(new CloseParenthesis()); 
                else if (checkOperator(c) != null){
                    tokenList.add(checkOperator(c)); 
                    curState = ReadPolyState.WINT;
                }
                else 
                    curState = ReadPolyState.ERR; 
                break; 
            }

        }
        if (curState == ReadPolyState.CLOSEPAREN)
            return tokenList; 
        else if (curState == ReadPolyState.VAR) 
            return tokenList;
        else{
            // check if not accepted state
            if (i == 0)
                throw new PolyFormatException("No input given"); 
            else if (curState == ReadPolyState.WSIGN){
                throw new PolyFormatException("Polynomial cannot end with an open parenthesis"); 
            }
            else if (curState == ReadPolyState.WINT){ 
                throw new PolyFormatException("Polynomial cannot end with a sign, missing a monomial"); 
            }
            else if (curState == ReadPolyState.WDEC){
                throw new PolyFormatException("Missing decimal");
            }
            else if (curState == ReadPolyState.WEXP){
                throw new PolyFormatException("Missing exponent"); 
            }
            else if (curState == ReadPolyState.ERR){
                throw new PolyFormatException("Invalid Polynomial");
            }
        }

        if (intStrExp.equals("")){ // add the last monomial
            if (curState == ReadPolyState.INT) 
                intStrExp += "0"; 
            else if (curState == ReadPolyState.X)
                intStrExp += "1";
        }
        tokenList.add(inputPoly(doubleStrCoef,intStrExp)); //add the last monomial because when the while loop ended it has not met any of the signal to add the last mono
        return tokenList;
    }

    /**
     * Method converting the polynomial calculation's infix notation into 
     * postfix notation
     * @param tokenList the list of tokens of polynomial's calculation's infix notation
     * @return a list of tokens of polynomial's calculation's postfix notation
     */
    public List<Token> InfixToPostfixConverter(List<Token> tokenList){
        ArrayDeque<Token> stack = new ArrayDeque<Token>(); 
        List<Token> output = new ArrayList<Token>(); 

        for (Token token : tokenList){
            if (token instanceof Polynomial)
                output.add(token); 
            else if (token instanceof OpenParenthesis)
                stack.push(token); 
            else if (token instanceof CloseParenthesis){
                while (!stack.isEmpty() && !(stack.peek() instanceof OpenParenthesis))
                    output.add(stack.pop()); 
                if (stack.isEmpty()){ 
                    throw new PolyFormatException("Mismatched right parenthesis");
                }
                stack.pop(); // throw away left parenthesis
            }
            else if (token instanceof Operator){
                Operator tokenOperator = (Operator) token; // cast Token to Operator
                Operator peekStackOp = new AddOperator();
                if (!(stack.peek() instanceof OpenParenthesis))
                    peekStackOp = (Operator) stack.peek(); // cast Token to Operator
                while (!stack.isEmpty() && !(stack.peek() instanceof OpenParenthesis)
                &&  (peekStackOp.getPriority() >= tokenOperator.getPriority()))
                    output.add(stack.pop()); 
                stack.push(token);
            }
        }
        // empty out the stack at the end 

        while (!stack.isEmpty()){
            Token token = stack.pop(); 
            if (token instanceof OpenParenthesis){
                throw new PolyFormatException("Mismatched left parenthesis");
            }
            output.add(token); 
        }

        return output;
    } 

    /**
     * Method evaluating the polynomial calculation based on the postfix notation 
     * of the polynomial calculation
     * @param postfixtokenList a list of tokens of the polynomial calculation with 
     * postfix notation
     * @return the result polynomial 
     */
    public Polynomial postfixEval(List<Token> postfixtokenList){
        ArrayDeque<Token> stack = new ArrayDeque<Token>(); 

        Polynomial result = new Polynomial(); 
        for (Token token : postfixtokenList){
            if (token instanceof Polynomial)
                stack.push(token); 
            else if (token instanceof Operator){
                Polynomial op2 = (Polynomial) stack.pop(); 
                Polynomial op1 = (Polynomial) stack.pop(); 
                result = ((Operator) token).operate(op1, op2);
                if (result == null) //for division by 0
                    return null;
                stack.push(result); 
            }
        }
        result = (Polynomial) stack.pop(); 
        if (storingVar != ' '){
            memory.put(storingVar, result); 
            storingVar = ' ';
        }
        return result; 
    }
}
