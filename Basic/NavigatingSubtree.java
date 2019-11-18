package lecture3;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class NavigatingSubtree extends junit.framework.TestCase {

	public static void test1() {
		
		// Put some test values into a TreeMap
		
			TreeMap<Integer,Integer> testMap = new TreeMap<Integer,Integer>();
			for( int i = 0; i < 10; i++ )
				testMap.put( i, i*100 );
			
		// Get a sub-map of the above tree map with key values only 
		// greater than or equal to 5. Remove key value 8.
			
			NavigableMap<Integer,Integer> sm = testMap.tailMap( 5, true );
			Iterator<Entry<Integer,Integer>> it = sm.entrySet().iterator();
			while( it.hasNext() ) {
				Entry<Integer,Integer> entry = it.next();
				if( entry.getKey() == 8 )
					it.remove();
			}
			
		// Get the same sub-map again to make sure the entry with key = 8
		// was removed
			
			int sum = 0;
			it = sm.entrySet().iterator();
			while( it.hasNext() ) {
				Entry<Integer,Integer> entry = it.next();
				sum += entry.getValue();
			}
			assertTrue( sum == 500 + 600 + 700 + 900 ); // 800 should be missing

	}
	
}
