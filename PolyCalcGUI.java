import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*; 
import java.io.*;
/**
 * Graphical User Interface for Polynomial Calculator
 * @author Nhan Nguyen
 * @version CMPU-102-HW3
 */
public class PolyCalcGUI
{
    private static FSM polyFSM = new FSM();

    private static JFrame window;

    private static JPanel content; 

    private static JLabel text;

    private static JTextField textInput; 

    private static JButton eval;

    private static JButton chooseFile; 

    private static JTextArea textResult;

    /**
     * Constructing the main menu graphical user interface
     */
    public static void mainMenuGUI(){
        window = new JFrame("Polynomial Calculator");
        window.setSize(500,250); 
        window.setLocationRelativeTo(null); 

        content = new JPanel(); 
        text = new JLabel("Welcome to Polynomial Calculator!"); 
        content.add(text); 

        textInput = new JTextField(30); 

        content.add(textInput); 

        chooseFile = new JButton("Choose File"); 
        content.add(chooseFile); 

        eval = new JButton("Evaluate"); 
        content.add(eval); 

        textResult = new JTextArea(6,40);
        textResult.setEditable(false); 

        textResult.setLineWrap(true);
        textResult.setWrapStyleWord(true);

        JScrollPane sp = new JScrollPane(textResult);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        content.add(sp); 

        window.setContentPane(content); 
        window.setVisible(true);
        window.toFront(); 
    }

    /**
     * Constructing the evaluation button of the graphical user interface
     */
    public static void interactiveModeGUI(){
        eval.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String input = textInput.getText(); 

                    try{
                        java.util.List<Token> tokenListInteractive = polyFSM.ReadPoly(input);
                        java.util.List<Token> postfixListInteractive = polyFSM.InfixToPostfixConverter(tokenListInteractive);
                        Polynomial polyInteractive = polyFSM.postfixEval(postfixListInteractive); 

                        String result = "" + polyInteractive;

                        textResult.setText(result);

                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE); 
                    }

                }
            });
    }

    /**
     * Constructing the Choose File button of the graphical user interface
     */
    public static void fileModeGUI(){
        chooseFile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    final JFileChooser fc = new JFileChooser(); 
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
                    fc.setFileFilter(filter); 

                    int returnVal = fc.showOpenDialog(window); 

                    int n = 1; 
                    if (returnVal == JFileChooser.APPROVE_OPTION){
                        File file = fc.getSelectedFile(); 
                        StringBuilder stringResult = new StringBuilder();

                        if (file.length() == 0)    
                            stringResult.append("This is an empty file");     
                        else{    
                            try {
                                BufferedReader r = new BufferedReader(new FileReader(file)); 
                                String line; 

                                while ((line = r.readLine()) != null){
                                    try{
                                        //System.out.println("File line: " + line); 
                                        java.util.List<Token> tokenListFile= polyFSM.ReadPoly(line);
                                        //System.out.println(tokenListFile); 
                                        java.util.List<Token> postfixListFile = polyFSM.InfixToPostfixConverter(tokenListFile);
                                        //System.out.println(postfixListFile);
                                        Polynomial polyFile = polyFSM.postfixEval(postfixListFile);
                                        String result = " " + polyFile; 

                                        stringResult.append("Line " + n + ": "+ line + " = " + result); 
                                        stringResult.append("\n"); 
                                    }
                                    catch (Exception ex){
                                        JOptionPane.showMessageDialog(null, line + ": " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE); 
                                        stringResult.append("Line " + n + ": " + line + " - " + ex.getMessage()); 
                                        stringResult.append("\n");
                                    }
                                    n++;
                                }
                                r.close(); 

                            }
                            catch (IOException ex){
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE); 
                            }
                        }

                        textResult.setText(stringResult.toString());

                    }
                }});
    }

    /**
     * main method to run the polynomial calculator graphical user interface
     * @param args unused
     */
    public static void main(String[] args){
        mainMenuGUI();
        interactiveModeGUI(); 
        fileModeGUI(); 
    }
}
