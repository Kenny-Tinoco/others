package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.BigFraction;

import java.io.PrintWriter;

public class QuickCalculator {
    private static InteractiveCalculator ic = new InteractiveCalculator();

    public static void main(String[] args) {
        if (args.length == 0 || isEmpty(args)) {
            ic.println("FAILED [Invalid expression]");
            return;
        }

        for (String command : args) {
            if (command.startsWith("STORE")) {
                storeValue(command);
                continue;
            }

            if (ic.isRegister(command)) {
                ic.println(command + " -> " + ic.calculator.getRegister(command.charAt(0)));
                continue;
            }

            if (!command.contains(" ")) {
                ic.calculator.add(new BigFraction(command));
                ic.println(command + " -> " + ic.calculator.get());
                continue;
            }

            String[] values = command.split(" ");
            boolean isSuccessful = ic.compute(values);
            if(!isSuccessful){
                ic.println(command + ": FAILED [Invalid expression]");
                continue;
            }

            ic.println(command + " -> " + ic.calculator.get());
        }
    }

    private static boolean isEmpty(String[] args) {
        return args.length == 1 && (args[0] == null || args[0].isEmpty());
    }

    private static void storeValue(String line) {
        String[] parts = line.split(" ");

        if(parts.length != 2 || !ic.isRegister(parts[1])){
            ic.println("Invalid STORE command.");
            return;
        } else if (parts[1].charAt(0) < 'a' || parts[1].charAt(0) > 'z') {
            ic.println("*** ERROR [STORE command received invalid register] ***");
            return;
        }

        ic.calculator.store(parts[1].charAt(0));
        ic.println("STORE " + parts[1] + " -> STORED");
    }
}
