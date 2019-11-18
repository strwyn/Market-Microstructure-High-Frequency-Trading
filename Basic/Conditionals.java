package lecture1;

public class Conditionals {

	@SuppressWarnings("unused")
	public static void main( String[] args ) {
		
		int i = 5;
		int j = 6;
		int k = i + j;
		
		if( k == 11 ) {
			System.out.println( "k = 11" );
		}
		
		if( k == 12 ) {
			System.out.println( "k = 12" );
		} else {
			System.out.println( "k = 11" );
		}

		if( k == 12 ) {
		} else if( ( i == 5 ) && ( j == 6 ) ) {
			System.out.println( "i = 5 and j = 6" );
		}
		
		if( ( i == 7 ) || ( j == 6 ) ) {
			System.out.println( "Either i = 7 or j = 6" );
		}
		
		if( 5 < 7 ) {
			System.out.println( "5 < 7" );
		}
		
		if( 6 <= 6 ) {
			System.out.println( "6 <= 6" );
		}
		
		if( 6 <= 4 ) {
			System.out.println( "Maybe in another language!" );
		} else {
			System.out.println( "!( 6 <= 4 )" );
		}
		
		if( 5 != 10 ) {
			System.out.println( "5 != 10" );
		}
		
		System.out.println( "Finished" );
		
	}
}
