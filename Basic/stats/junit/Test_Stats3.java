package lecture3;

public class Test_Stats3 extends junit.framework.TestCase {
	
	// This is the instance of StatsQ that we will use in all
	// of the unit tests in this class
	
		protected StatsQ _statsQ;
	
	// In junit tests, the following method is automatically called 
	// before every test method is run

		@Override
		protected void setUp() throws Exception {
			super.setUp();
			
			// We want to make a new instance of stats queue
			// for every test we run
			
				_statsQ = new StatsQ();
				
		}

		/**
		 * Tests asking for standard deviation before queue is ready
		 */
		public void test1() {
			
			Stats3 stats3 = new Stats3( _statsQ );
			for( int i = 0; i < 899; i++ )
				stats3.add( i );
			String errorMsg = null;
			try {
				double std = stats3.getSampleStd();
			} catch( Exception e ) {
				errorMsg = e.getMessage();
				assertEquals( errorMsg, "Queue is not ready" );
			}
			assertFalse( errorMsg == null );
			errorMsg = null;
			try {
				double mean = stats3.getMean();
			} catch( Exception e ) {
				errorMsg = e.getMessage();
				assertEquals( errorMsg, "Queue is not ready" );
			}
			assertFalse( errorMsg == null );
		}
		
		/**
		 * Tests asking for mean and standard deviation when queue is ready
		 */
		public void test3() throws Exception {
			
			Stats3 stats3 = new Stats3( _statsQ );
			for( int i = 0; i < 900; i++ )
				stats3.add( i );
			double std = stats3.getSampleStd();
			/* R code for calculation
			> sd( 0:899 )
			[1] 259.9519
			>
			*/
			assertEquals( std, 259.9519, 0.001 );
			double mean = stats3.getMean();
			/* R code for mean
			>
			[1] 449.5
			>
			*/
			assertEquals( mean, 449.5, 0.0001 );
		}
		
		/**
		 * Tests asking for standard deviation after queue automatically
		 * drops one data point
		 */
		public void test4() throws Exception {
			
			Stats3 stats3 = new Stats3( _statsQ );
			for( int i = 0; i < 901; i++ )
				stats3.add( i );
			double std = stats3.getSampleStd();
			/* R code for calculation of standard deviation
			> sd( 1:900 )
			[1] 259.9519
			>
			*/
			assertEquals( std, 259.9519, 0.001 );
			double mean = stats3.getMean();
			/* R code for calculation of mean
			> mean( 1:900 )
			[1] 450.5
			> 
			*/
			assertEquals( mean, 450.5, 0.0001 );
		}
}
