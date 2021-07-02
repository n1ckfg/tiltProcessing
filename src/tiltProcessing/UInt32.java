// http://www.java2s.com/Code/Java/Data-Type/Classtorepresent16bitunsignedintegers.htm      
package tiltProcessing;

import processing.core.*;
import java.text.MessageFormat;

/**
 * Class to represent 16-bit unsigned integers.
 */
@SuppressWarnings("serial")
public class UInt32 extends Number implements Comparable<UInt32> {
  /** Maximum possible value. */
  public static final int MAX_VALUE = 4294967295; //65535;
  /** Minimum possible value. */
  public static final int MIN_VALUE = 0;
  private int value;

  /**
   * Create a UInt32 from an int.
   * 
   * @param value
   *          Must be within MIN_VALUE&ndash;MAX_VALUE
   * @throws NumberFormatException
   *           if value is not between MIN_VALUE and MAX_VALUE
   */
  public UInt32(int value) {
    
    this.value = value;
  }

  /**
   * Create a UInt32 from a String.
   * 
   * @param value
   *          Must parse to a valid integer within MIN_VALUE&ndash;MAX_VALUE
   * @throws NumberFormatException
   *           if value is not an integer between MIN_VALUE and MAX_VALUE
   */
  public UInt32(String value) {
    this(Integer.parseInt(value));
  }

  /** The value of this as a byte. */
  @Override
  public byte byteValue() {
    return (byte) value;
  }

  /**
   * Compare two UInt32s.
   * 
   * @return 0 if equal, -ve or +ve if they are different.
   */
  public int compareTo(UInt32 other) {
    return /* (int) */(value - other.value);
  }

  /** The value of this as a double. */
  @Override
  public double doubleValue() {
    return value;
  }

  /** Test two UInt32s for equality. */
  @Override
  public boolean equals(Object o) {
    return (o instanceof UInt32) && (((UInt32) o).value == value);
  }

  /** The value of this as a float. */
  @Override
  public float floatValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return /* (int) */value;
  }

  /** The value of this as a int. */
  @Override
  public int intValue() {
    return /* (int) */value;
  }

  /** The value of this as a long. */
  @Override
  public long longValue() {
    return value;
  }

  /** The value of this as a short. */
  @Override
  public short shortValue() {
    return (short) value;
  }

  /** The value of this as a string. */
  @Override
  public String toString() {
    return "" + value;
  }
}

