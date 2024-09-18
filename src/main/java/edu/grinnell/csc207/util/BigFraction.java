package edu.grinnell.csc207.util;

import java.math.BigInteger;
import java.util.Objects;

/**
 *  No se xd 
 */

public class BigFraction {
  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The numerator of the fraction. Can be positive, zero or negative. */
  BigInteger num;

  /** The denominator of the fraction. Must be non-negative. */
  BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   * @param numerator of the fraction.
   * @param denominator of the fraction.
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    this.num = numerator;
    this.denom = denominator;
    simplify();
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   * @param numerator of the fraction.
   * @param denominator of the fraction.
   */
  public BigFraction(int numerator, int denominator) {
    //#################################Cambio#################################################
    this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
  } // BigFraction(int, int)

  /**
   * Build a new fraction by parsing a string.
   * @param str
   * The fraction in string form
   */

  public BigFraction(String str) {
    //#################################Cambio#################################################
    String[] values = str.split("/");
    this.num = new BigInteger(values[0]);
    this.denom = (values.length == 1) ? BigInteger.ONE : new BigInteger(values[1]);
    simplify();
  } // BigFraction

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  public void simplify() {
    BigInteger gcd = num.gcd(denom); // Find the greatest common divisor
    this.num = this.num.divide(gcd); // Divide the numerator by the GCD
    this.denom = this.denom.divide(gcd); // Divide the denominator by the GCD

    // Ensure the denominator is positive
    if (denom.compareTo(BigInteger.ZERO) < 0) {  // If the denominator is negative
        this.num = this.num.multiply(BigInteger.valueOf(-1));  // Make numerator negative
        this.denom = this.denom.multiply(BigInteger.valueOf(-1));  // Make denominator positive
    }
  }

  /**
   * Express this fraction as a double.
   * @return the fraction approxiamted as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Add another faction to this fraction.
   * @param addend The fraction to add.
   * @return the result of the addition.
   */
  public BigFraction add(BigFraction addend) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;
    // The denominator of the result is the product of this object's
    // denominator and addend's denominator
    resultDenominator = this.denom.multiply(addend.denom);
    // The numerator is more complicated
    resultNumerator =
      (this.num.multiply(addend.denom)).add(addend.num.multiply(this.denom));
    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // add(BigFraction)

  /** Subtract another fraction from this fraction. */
  public BigFraction subtract(BigFraction subtrahend) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;
  
    resultDenominator = this.denom.multiply(subtrahend.denom);
    resultNumerator = (this.num.multiply(subtrahend.denom)).subtract(subtrahend.num.multiply(this.denom));
    return new BigFraction(resultNumerator, resultDenominator);
  } // method  subtract

  /**
   * Get the denominator of this fraction.
   * @return the denominator
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   * @return the numerator
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Convert this fraction to a string for ease of printing.
   * @return a string that represents the fraction.
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0";
    } // if it's zero
    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    // return this.num.toString().concat("/").concat(this.denom.toString());



    //#################################Cambio#################################################
    return Objects.equals(this.denom, BigInteger.ONE) ? this.num.toString()
            : this.num + "/" + this.denom;
  } // toString()

  /**
   * Multiply two BigFractions, yielding anothr BigFraction
   * @param multiple
   * @return A new BigFraction
   */
  public BigFraction multiply(BigFraction multiple) {
    return new BigFraction(this.num.multiply(multiple.num), this.denom.multiply(multiple.denom));
  } // multiply
  
  public BigFraction divide(BigFraction divisor) {
    if (divisor.num.equals(BigInteger.ZERO)) {
      System.err.println("Error: Division by zero.");
    }
    return new BigFraction(this.num.multiply(divisor.denom), this.denom.multiply(divisor.num));
  }

  /**
   * Get the fractional remainder of the BigFraction.
   * @return If the BigFraction is represented a/b, we return (a mod b)/b.
   */
  public BigFraction fractional() {
    return new BigFraction(this.num.mod(this.denom), this.denom);
  } // fractional
} // class BigFraction