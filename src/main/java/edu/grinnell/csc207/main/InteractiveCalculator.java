package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.BigFraction;
import edu.grinnell.csc207.util.BFCalculator;

import java.util.Objects;
import java.util.Scanner;
import java.io.PrintWriter;

public class InteractiveCalculator {

  private static BFCalculator calculator;
  private static PrintWriter pen;

  public static void main(String[] args) {
    calculator = new BFCalculator();
    pen = new PrintWriter(System.out, true);
    Scanner scanner = new Scanner(System.in);
    
    pen.println("Welcome to Interactive Calculator. Type QUIT to exit.");

    while (true) {
      pen.print("> ");
      pen.flush();

      String line = scanner.nextLine();

      if (line.equalsIgnoreCase("QUIT")) {
        pen.println("Exiting calculator.");
        scanner.close();
        return;
      }

      if (line.startsWith("STORE") || line.startsWith("store")) {
        storeValue(line);
        continue;
      }
      
      try{
          String[] values = line.split(" ");

          if(values.length % 2 == 0) {
              pen.println("*** ERROR [Invalid expression] ***");
              continue;
          }
          compute(values);
      } catch (Exception e){
          pen.println("*** ERROR [Invalid expression] ***");
        continue;
      }
      
      pen.println(calculator.get());     
    }
  }

  private static boolean isOperator(String token) {
    return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
  }

  private static boolean isRegister(String token) {
    return token.length() == 1 && Character.isLetter(token.charAt(0));
  }

  private static void applyOperation(BigFraction right, String operator) {
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
      default:
        pen.println("Unknown operator: (" + operator+")");
    }
  }

  private static BigFraction getFractionByString(String value){
      String[] values = value.split("/");
      return (values.length == 1) ?
              new BigFraction(Integer.parseInt(values[0]), 1) : new BigFraction(value);
  }
  
  private static void storeValue(String line)
  {
    String[] parts = line.split(" ");
    if (parts.length == 2) {
        calculator.store(parts[1].charAt(0));
        if(!Objects.equals(calculator.getRegister(parts[1].charAt(0)).toString(), "0")){
            pen.println("STORED");
        }
    } else {
        pen.println("Invalid STORE command.");
    }
  }
  
  private static void compute(String[] tokens)
  {
    calculator.clear();

    String operator = null;
    for (String token : tokens) {
        if (isOperator(token)) {
            operator = token;
        } else if (isRegister(token)) {
           if (operator == null) {
                calculator.setByRegister(token.charAt(0));
            } else {
                applyOperation(calculator.getRegister(token.charAt(0)), operator);
                operator = null;
            }
        } else {
            if (operator == null) {
                calculator.clear();
                calculator.add(getFractionByString(token));
            } else {
                applyOperation(getFractionByString(token), operator);
                operator = null;
            }
        }
    }
  }  
}

/// Me tiene de un bate
