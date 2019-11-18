package lecture1;

public class ArraysExample {

	public static void main( String[] args ) {
		
		// Create a 2 dimensional array
		int[][] i;
		
		// The first dimension contains 5 elements.
		// of an as yet unspecified length.
		i = new int[ 5 ][];

		// What are those elements? 
		// They are integer arrays.
		
		// Specify that the zero-th element of the 1st
		// dimension of our 2 dimensional int array is
		// an array of 7 integers.
		i[ 0 ] = new int [ 7 ];
		
		// Fill the integer array at i[ 0 ] with 7
		// integers.
		for( int j = 0; j < 7; j++ ) {
			i[ 0 ][ j ] = 3;
		}
		
		// Attempt to put a value at the location
		// specified by first dimension index 1
		// and second dimension index 0. This is
		// an illegal operation because that space
		// has not been allocated.
		@SuppressWarnings("unused")
		int k = 0;
		try {
			k = i[ 1 ][ 0 ];
		} catch( Exception e ) {
			// This is how an error is handled without
			// allowing it to stop execution.
			System.out.println( "We caught an error!" );
		}
		
		// Preallocate space for a two-dimensional
		// array of 5 by 7 elements = 35 integer values.
		i = new int[5][7];
		
		// We can now write to any of the allocated
		// spaces.
		k = i[ 1 ][ 0 ];
	}
}
