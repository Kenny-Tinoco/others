package edu.grinnell.csc207.util;

import java.math.BigInteger;

public class BFCalculator {

    // Field to store the last computed value
    public BigFraction lastValue;

    private BFRegisterSet register;

    public BFCalculator(){
        register = new BFRegisterSet();
       clear();
    }

    // Gets the last computed value (returns 0 if there is no such value)
    public BigFraction get() {
        return this.lastValue;
    }

    // Adds a BigFraction to the last computed value
    public void add(BigFraction val) {
        this.lastValue = this.lastValue.add(val);
    }

    // Subtracts a BigFraction from the last computed value
    public void subtract(BigFraction val) {
        this.lastValue = this.lastValue.subtract(val);
    }

    // Multiplies the last computed value by a BigFraction
    public void multiply(BigFraction val) {
        this.lastValue = this.lastValue.multiply(val);
    }

    // Divides the last computed value by a BigFraction
    public void divide(BigFraction val) {
        if (val.numerator().equals(BigInteger.ZERO)) {
            System.err.println("Error: Division by zero.");
        } else {
            this.lastValue = this.lastValue.divide(val);
        }
    }

    // Clears the last computed value (resets to 0/1)
    public void clear() {
        this.lastValue = new BigFraction(BigInteger.ZERO, BigInteger.ONE); // Reset to 0/1
    }

    public void setByRegister(char id) {
        this.lastValue = register.get(id);
    }

    public BigFraction getRegister(char id) {
        return register.get(id);
    }

    public void store(char id){
       register.store(id, this.lastValue);
       clear();
    }

}

/// Como puedo hacer que esto use dos calculadoras simultaneamente. 
