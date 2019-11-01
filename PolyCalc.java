import java.util.*;
import java.io.*;
/**
 * Basic Polynomial Calculator
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class PolyCalc
{
    private static FSM polyFSM = new FSM(); 
    private static char mode;
    static Scanner scanner = new Scanner(System.in);

    /**
     * method printing out the main menu of the Polynomial Calculator
     */
    public static void mainMenu(){
        char mode;

        System.out.println("----------------------Welcome to Polynomial Calculator------------------------------\n"); 

        while(true){
            System.out.println("******************Main Menu******************\n");
            System.out.println("Type 1 for Interacteractive Mode, 2 for File Mode, and 0 to exit the program: ");  
            do {
                mode = scanner.nextLine().charAt(0);

                if (mode != '0' && mode != '1' && mode != '2')
                    System.out.println("Invalid mode. Please choose only either 0, 1 or 2");

                else{
                    if (mode == '1'){
                        interactiveMode(); 
                    }
                    else if (mode == '2'){
                        fileMode();
                    }
                    else 
                        System.exit(0); 
                }
            }while (!Character.isDigit(mode) || (mode != '0' && mode != '1' && mode != '2')); 
        }
    }

    /**
     * method constructing the main menu of the Polynomial Calculator
     */
    public static void interactiveMode(){
        System.out.println("++++++++++++++++Interative Mode++++++++++++++++\n"); 
        while (true){

            System.out.println("Please type in a polynomial calculation (quit to exit to the main menu): ");
            Scanner scannerInput = new Scanner(System.in);
            String inputInteractive = scannerInput.nextLine(); 
            inputInteractive = inputInteractive.trim().replaceAll(" +", " ");
            if (inputInteractive.toLowerCase().equals("quit"))
                return;

            try{
                List<Token> tokenListInteractive = polyFSM.ReadPoly(inputInteractive);
                List<Token> postfixListInteractive = polyFSM.InfixToPostfixConverter(tokenListInteractive);
                Polynomial polyInteractive = polyFSM.postfixEval(postfixListInteractive); 
                if (polyInteractive == null) 
                    continue; 
                System.out.println(inputInteractive + " = " + polyInteractive + "\n"); 
            }
            catch (Exception e){
                System.out.println("Exception: " + e.getMessage() + "\n"); 
            }

        }
    }

    /**
     * method constructing the file mode of the Polynomial Calculator
     */
    public static void fileMode(){
        File file;
        String line; 
        System.out.println("++++++++++++++++++File Mode+++++++++++++++++++\n"); 
        while(true){
            System.out.println("Please type in a file path (quit to exit to the main menu): ");
            Scanner scannerInput = new Scanner(System.in);
            String inputFile = scannerInput.nextLine();

            if (inputFile.toLowerCase().equals("quit"))
                return;  

            file = new File(inputFile); 
            if (!file.isFile())
                System.out.println("Not a legit file, only accept text files"); 
            else if (file.length() == 0)    
                System.out.println("This is an empty file");
            else{
                try {
                    BufferedReader r = new BufferedReader(new FileReader(inputFile));
                    StringBuilder stringResult = new StringBuilder();

                    int n = 1; 
                    while ((line = r.readLine()) != null){
                        try{
                            List<Token> tokenListFile= polyFSM.ReadPoly(line);

                            List<Token> postfixListFile = polyFSM.InfixToPostfixConverter(tokenListFile);

                            Polynomial polyFile = polyFSM.postfixEval(postfixListFile);  

                            System.out.println("Line " + n + ": " + line + " = " + polyFile); 

                        }
                        catch (Exception e){
                            System.out.println("Line " + n + ": " + line + " - " +  e.getMessage());
                             
                        }
                        n++; 
                    }
                    r.close(); 
                }
                catch (IOException e){
                    System.out.println("Exception: " + e.getMessage()); 
                }
                System.out.println(" "); 
            }
        } 
    }

    /** 
     * Construct the basic version of Polynomial Calculator
     * @param args unused
     */
    public static void main(String[] args){
        mainMenu(); 
    }
}
