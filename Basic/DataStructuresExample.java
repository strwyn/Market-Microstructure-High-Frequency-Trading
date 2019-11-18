package lecture3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class DataStructuresExample extends junit.framework.TestCase {

	public void testLinkedList() {
		LinkedList<String> linkedList = new LinkedList<String>();
		linkedList.add( "Lee" ); // Always cheap
		try {
			// Only the zero-th element has data, so this is an error
			// All sets and gets are expensive because they require
			// list traversal
			linkedList.set( 5, "Bob" ); 
		} catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
		// Use getFirst and getLast to get at the front and back of the list
		assertTrue( linkedList.getFirst().equals( "Lee" ) ); 
		assertTrue( linkedList.getLast().equals( "Lee" ) ); 
		for( int i = 0; i < 20; i++ ) { // Very cheap here
			linkedList.add( ( new Integer( i ) ).toString() );
		}
		assertTrue( linkedList.get( 20 ).equals( "19" ) ); // Cheap because near end
		linkedList.set( 1, "Lee" ); // Cheap because near beginning
		
		assertTrue( linkedList.removeFirst().equals( "Lee" ) );
		assertTrue( linkedList.removeLast().equals( "19" ) );
		
		// Prepare to sort elements
		Collections.sort( linkedList );
		
		// This works because strings know how to compare
		// themselves to each other. But what about sorting 
		// backwards?
		
		Comparator<String> comp = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return( o2.compareTo( o1 ) );
			}
		};
		
		Collections.sort( linkedList, comp );
		
		// Traversing elements
		Iterator<String> stringIterator = linkedList.iterator();
		while( stringIterator.hasNext() ) {
			String value = stringIterator.next();
			System.out.println( value );
		}
	}
	
	public void testArrayList() {
		ArrayList<String> al = new ArrayList<String>( 10 ); // Up to 10 places
		al.add( "Lee" ); // Cheap when there's room
		try {
			// Only the zero-th element has data, so this is an error
			al.set( 5, "Bob" ); 
		} catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
		assertTrue( al.get( 0 ).equals( "Lee" ) );
		for( int i = 0; i < 20; i++ ) {
			al.add( ( new Integer( i ) ).toString() ); // Very expensive when re-allocating
		}
		assertTrue( al.get( 20 ).equals( "19" ) ); // Cheap because array is contiguous
		al.set( 1, "Lee" ); // Cheap for same reason
		@SuppressWarnings("unused")
		String removedString = al.remove( 10 ); // Expensive
		
		// Traversing elements
		Iterator<String> stringIterator = al.iterator();
		while( stringIterator.hasNext() ) {
			String value = stringIterator.next();
			System.out.println( value );
		}
	}
	
	public void testHashMap() {
		class Person {
			protected String _name;
			protected int    _age;
			public Person( String name, int age ) { _name = new String( name ); _age = age; }
			public String getName() { return _name; }
			public int    getAge() { return _age; }
		}
		HashMap<String,Person> personMap = new HashMap<String,Person>();
		Person p = new Person( "Lee", 50 );
		personMap.put( p.getName(), p );

		// Replace Lee with new Lee - Same key so data will be overwritten
		p = new Person( "Lee", 51 );
		personMap.put( p.getName(), p );

		Person p1 = new Person( "Bob", 47 );
		personMap.put( p1.getName(), p1 );
		
		// Make sure we can retrieve data
		assertTrue( personMap.get( "Lee" ).getAge() == 51 );
		assertTrue( personMap.get( "Bob" ).getAge() == 47 );
		
		// Iterating over values in map
		Iterator<Person> personIterator = personMap.values().iterator();
		while( personIterator.hasNext() ) {
			@SuppressWarnings("unused")
			Person aPerson = personIterator.next();
		}
	}
	
	public void testTreeMap() {
		class Person {
			protected String _name;
			protected int    _age;
			public Person( String name, int age ) { _name = new String( name ); _age = age; }
			public String getName() { return _name; }
			public int    getAge() { return _age; }
		}
		Person p = new Person( "Lee", 51 );
		Person p1 = new Person( "Bob", 47 );
		Person p2 = new Person( "Steve", 49 );
		TreeMap<Integer,Person> personMap = new TreeMap<Integer,Person>();
		personMap.put( p.getAge(), p );
		personMap.put( p1.getAge(), p1 );
		personMap.put( p2.getAge(), p2 );
		
		// The tree map keeps the contents in ascending order of the key, so
		// the traversal will not be in the order that the above objects were
		// added
		int i = 0;
		for(Map.Entry<Integer,Person> entry : personMap.entrySet()) {
			@SuppressWarnings("unused")
			Integer key = entry.getKey();
			Person value = entry.getValue();
			if( ++i == 1 ) {
				assertTrue( value.getName().equals( p1.getName() ) );
			} else if( i == 2 ) {
				assertTrue( value.getName().equals( p2.getName() ) );
			} else if( i == 3 ) {
				assertTrue( value.getName().equals( p.getName() ) );
			}
			System.out.printf( "Person %s, age %d\n", value.getName(), value.getAge() );
		}
	}
	
}
