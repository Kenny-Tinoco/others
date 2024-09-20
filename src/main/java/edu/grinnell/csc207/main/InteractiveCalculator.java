package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.BigFraction;
import edu.grinnell.csc207.util.BFCalculator;

import java.util.Objects;
import java.util.Scanner;
import java.io.PrintWriter;

public class InteractiveCalculator {

    public static void main(String[] args) {
        InteractiveCalculator ic = new InteractiveCalculator();
        Scanner scanner = new Scanner(System.in);

        ic.println("Welcome to Interactive Calculator. Type QUIT to exit.");
        while (true) {
            ic.initPen();
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("QUIT")) {
                ic.println("Exiting calculator.");
                scanner.close();
                return;
            }

            if (line.startsWith("STORE") || line.startsWith("store")) {
                ic.storeValue(line);
                continue;
            }

            if (!line.contains(" ")) {
                BigFraction value = null;
                if (ic.isRegister(line)) {
                    value = ic.calculator.getRegister(line.charAt(0));
                }else{
                    //ic.calculator.clear();
                    //ic.calculator.add(new BigFraction(line));
                    ic.getCalculator().clear();
                    ic.geCalculator().add(new BigFraction(line));
                    //value = ic.calculator.get();
                    value = ic.getCalculator().get();
                }
                ic.println(""+value);
                continue;
            }

            String[] values = line.split(" ");
            boolean isSuccessful = ic.compute(values);
            if(!isSuccessful){
                ic.println("*** ERROR [Invalid expression] ***");
                continue;
            }

            ic.println(""+ic.calculator.get());
        }
    }

    //public BFCalculator calculator;
    private PrintWriter pen;

    private BFCalculator calculator;
    // Método de acceso
    public BFCalculator getCalculator(){
        return calculator;
    }





    public InteractiveCalculator() {
        calculator = new BFCalculator();
        pen = new PrintWriter(System.out, true);
    }

    public void println(String str){
        pen.println(str);
    }

    public void initPen(){
        pen.print("> ");
        pen.flush();
    }

    public void storeValue(String line) {
        String[] parts = line.split(" ");

        if(parts.length != 2 || !isRegister(parts[1])){
            pen.println("Invalid STORE command.");
            return;
        } else if (parts[1].charAt(0) < 'a' || parts[1].charAt(0) > 'z') {
            pen.println("*** ERROR [STORE command received invalid register] ***");
            return;
        }

        calculator.store(parts[1].charAt(0));
        System.err.println("STORED");
    }

    public boolean compute(String[] tokens) {
        if(tokens.length % 2 == 0) {//### Una cantidad par de argumentos no puede ser valida
            return false;
        }

        calculator.clear();

        String operator = null;
        BigFraction firstOperando = null;
        BigFraction secondOperando = null;

        for (String token : tokens) {
            BigFraction value = null;
            if (isOperator(token)) {
                operator = token;
            } else if (!isRegister(token)) {//############################ si no es un registro
                value = new BigFraction(token);
            } else {
                value = calculator.getRegister(token.charAt(0));
                if(value == null || value.toString() == "0") {
                    return false; //############################ Si no existe el registro o es cero
                }
            }

            if (operator == null) {
                firstOperando = value;// Se inicializ el primer operando, luego este if no se ejecuta nunca más
                calculator.add(firstOperando);
            } else {
                secondOperando = value;
            }

            if(secondOperando != null) {
                if(operator == null || firstOperando == null) {
                    return false;//############################ Si no hay operador
                }
                applyOperation(secondOperando, operator);
            }
        }

        if(secondOperando == null){
            return false;
        }

        return true;
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public boolean isRegister(String token) {
        return token.length() == 1 && Character.isLetter(token.charAt(0));
    }

    ////////////////// si el operador es null no se hace ninguna operación
    private void applyOperation(BigFraction right, String operator) {
        switch (operator) {
            case "+":
                calculator.add(right);
                break;
            case "-":
                calculator.subtract(right);
                break;
            case "*":
                calculator.multiply(right);
                break;
            case "/":
                calculator.divide(right);
                break;
        }
    }
}
