package edu.grinnell.csc207.util;

import java.math.BigInteger;

public class BFRegisterSet {

    // Array to store the values of registers 'a' through 'z'
    private BigFraction[] registers;

    // Constructor - Initializes the registers array
    public BFRegisterSet() {
        registers = new BigFraction[26]; // 26 letters from 'a' to 'z'
    }

    /**
     * Stores the given value in the specified register.
     * @param register The register letter ('a' to 'z')
     * @param val The value to store in the register
     */
    public void store(char register, BigFraction val) {
        if (register < 'a' || register > 'z') {
            System.err.println("*** ERROR [STORE command received invalid register] ***");
            return;
        }

        int index = register - 'a';
        registers[index] = val;
    }

    /**
     * Retrieves the value from the given register.
     * @param register The register letter ('a' to 'z')
     * @return The value stored in the register, or 0/1 if the register is empty
     */
    public BigFraction get(char register) {
        if (register < 'a' || register > 'z') {
            return new BigFraction(BigInteger.ZERO, BigInteger.ONE); // Default return value
        }

        int index = register - 'a';
        return registers[index];
    }
}
