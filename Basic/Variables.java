package lecture1;

import java.math.BigDecimal;
import java.math.MathContext;

public class Variables {

	@SuppressWarnings("unused")
	public static void main( String[] args ) {
		
		// boolean (true or false, 1 bit)
		boolean trueOrFalse = false;
		trueOrFalse = ( trueOrFalse == false );
		
		// byte (-128 to +127, 1 byte)
		byte b = 127;
		b++;
		
		// short (-32,768 to 32,767, 2 bytes)
		short s = -32768;
		s--;
		
		// int (-2,147,483,648 to 2,147,483,647, 4 bytes)
		int i = -2147483648;
		i = i - 1;
		
		// long (-9,223,372,036,854,775,808 to +9,223,372,036,854,775,807, 8 bytes)
		// When we type in numbers that are out of int range, we have to specify 'L'
		long k = 9223372036854775807L; 
		k = k + 1;

		// float (1.40129846432481707e-45 to 3.40282346638528860e+38, positive or negative, 4 bytes)
		// When we type in floating point numbers, they are assumed to be double unless we specify 'F'
		float f = 3.40282346638528860e+38F;
		
			// Overflow results in Infinity
			f *= 10.0; 
	
			// Overflow results in 0.0
			f = 1.40129846432481707e-45F;
			f /= 10.0;
			
			// Positive or negative
			f = 1.40129846432481707e-45F;
			f = f - 1.40129846432481707e-45F;
			f = -1.40129846432481707e-45F;
			f = f + 1.40129846432481707e-45F;
			f = 3.40282346638528860e+38F;
			f = f - 3.40282346638528860e+38F;
		
		// double (4.94065645841246544e-324 to 1.79769313486231570e+308, positive or negative, 8 bytes)
		// All of the same behavior as float
		double g = 4.94065645841246544e-324;
		g = g / 10.0;
		g = 1.79769313486231570e+308;
		g = g * 10.0;

		// char (0 to 65,535, unsigned, 2 bytes)
		char c = 'c';
		c++;
		
		// Be careful casting down!
		// Consider the limits of precision
		int v1 = 37;
		short v2 = (short)v1; // This is okay
		double v3 = 37.01;
		float v4 = (float)v3; // This is okay
		
		// Loss of precision
		double v5 = Double.MIN_NORMAL;
		float v6 = (float)v5; // Loss of precison
		double v7 = v5 - (double)v6;
		boolean same = ( v7 == (double)0.0 ); // false
		
		// Another example - Not what we expected
		double a1 = 106838.81;
		double b1 = 263970.96;
		double c1 = 879.35;
		double d1 = 366790.80;
		       
		double total = 0;
		       
		total += a1;
		total += b1;
		total -= c1;
		total -= d1;
		
		double whatWeExpected = 3139.62;
		same = ( total == whatWeExpected );

		// If you need arbitrary precision, use BigDecimal
		// Example
		BigDecimal total1 = new BigDecimal(0, MathContext.DECIMAL64);
		total1 = total1.setScale(2);
		total1 = total1.add(new BigDecimal(a1, MathContext.DECIMAL64));
		total1 = total1.add(new BigDecimal(b1, MathContext.DECIMAL64));
		total1 = total1.subtract(new BigDecimal(c1, MathContext.DECIMAL64));
		total1 = total1.subtract(new BigDecimal(d1, MathContext.DECIMAL64));
		double result1 = total1.doubleValue();

		
		// Be careful of round-off errors
		double n1 = 23.30;
		double n2 = 13.21;
		double n3 = ( n1 + ( n2 * 0.30 )) / 45;
		
		// String is not a Java primitive, but it enjoys special privileges
		// among Java's objects. With String, you can use the '+' operator
		// as with primitives.
		String s1 = "hello";
		String s2 = "there!";
		String s3 = s1 + " " + s2;
		String s4 = s3.substring(0,  3);
		String s5 = s3.toUpperCase();
		// ..and much more
		// Note that String is immutable
		// You can reassign the variable, but you can't change its internal content
		
		
	}

}
