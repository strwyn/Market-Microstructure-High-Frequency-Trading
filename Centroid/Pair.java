package lecture3;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Pair<Type1,Type2> {
	
	protected Type1 _o1;
	protected Type2 _o2;
	
	public Pair( Type1 o1, Type2 o2 ){
		_o1 = o1;
		_o2 = o2;
	}
	
	public Type1 getFirstObject() { return _o1; }
	
	public Type2 getSecondObject() { return _o2; }
	
	public static void main( String[] args ) {
		Pair<String,Integer> pair = new Pair<String,Integer>( "Lee", 53 );
		LinkedList<Pair<String,Integer>> list = new LinkedList<Pair<String,Integer>>();
		list.addLast( pair );
		Collections.sort(
			list,
			new Comparator<Pair<String, Integer>>(){
				@Override
				public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
					return o1.getSecondObject().compareTo( o2.getSecondObject() );
				}
			}
		);
	}


}
