package lecture2;

public class JavaMemoryModel {
	
	public static void modifierMethod( int i1, String s1, int[] j1 ) {
		i1 = 59; // Doesn't affect i
		s1 = "Bob Smith"; // Doesn't affect s
		j1[ 2 ] = 59; // Doesn't affect j but...
		// ..does affect the values on the heap to which j is a reference!!!
	}

	public static void main( String[] args ) {
		int i;
		String s;
		int[] j;
		
		i = 7;
		
		s = "Lee Maclin";
		
		j = new int[ 3 ];
		j[ 0 ] = 1;
		j[ 1 ] = 2;
		j[ 2 ] = 3;
		
		modifierMethod( i, s, j );
	}
	
}
