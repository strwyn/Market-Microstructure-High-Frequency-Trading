package lecture1;

public class ComparisonsAndEquality {
	
	@SuppressWarnings("unused")
	public static void main( String[] args ) {
		
		String s1 = new String( "Hello" );
		String s2 = new String( "Hello" );
				
		boolean isTrue1 = ( s1 == s1 );
		// Always true
		
		boolean isFalse1 = ( s1 == s2 );
		// False because both variables are not references to the same thing
		// even though the values they contain are the same
		
		s1 = "Hel" + "lo";
		s2 = "Hel" + "lo";
		// Note that both variables are references to the same thing!
		// The compiler is smart. It knows that a String is immutable
		// so it didn't make two copies of the above, only one.
		
		boolean isTrue2 = ( s1 == s2 ); 
		// True because both variables are references to the same thing
		
		int comparisonResult = s1.compareTo( s2 );
		// 0 because both strings have the same value, "Hello"
		
		boolean isTrue3 = ( s1.equals( s2 ) );
		// True because both strings reference the same thing
		
		s2 = "Goodbye";
		// Now, s2 no longer references the same piece of memory as s1.
		
		boolean isFalse = ( s1.equals( s2 ) );
		comparisonResult = s1.compareTo( s2 );
		
		// Now, we will put the same value into the space referenced
		// by s1 and s2, but we will do it indirectly, thereby
		// making it difficult for the compiler to do the little
		// optimization - making both variables point to the same
		// space in memory - that we saw before.
		
		StringBuffer sb1 = new StringBuffer("hello");
		s1 = sb1.toString();
		s2 = sb1.toString();
		
		boolean isTrue4 = ( s1 == s2 );
		boolean isTrue5 = ( s1.equals( s2 ) );
		
	}
	
}
