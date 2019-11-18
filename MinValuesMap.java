package utils1309;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * We often have the problem of comparing something to the
 * n lowest values to determine if it belongs in that list.
 * If it does, and the list already has n value, the max
 * value in the list must be removed. This map-based data
 * structure makes such calculations clean.
 *
 * @param <K> The type of the key by which we want to determine
 *            that something belongs in our list
 * @param <V> The value that we want to store in our list
 */
public class MinValuesMap<K extends Comparable<K>,V> {

	private TreeMap<K,LinkedList<V>> _map;
	private K                        _maxKey;
	private int                      _count;
	private int                      _maxCount;
	private V                        _lastRemoved;
	
	/**
	 * Construct a min values map that will allow at most maxCount entries
	 * @param maxCount Number of entries this min values map should allow
	 */
	public MinValuesMap( int maxCount ) {
		_map = new TreeMap<K,LinkedList<V>>();
		_maxKey = null;
		_count = 0;
		_maxCount = maxCount;
		_lastRemoved = null;
	}
	
	/**
	 * If 'key' belongs in map, adds 'value' at key. If the
	 * map is full, removes value at key '_maxKey'.
	 *  
	 * @param key Key at which to add a value
	 * @param value Vaue to add to list
	 * @return True if the value was added to the map, and false otherwise
	 */
	public boolean addPoint( K key, V value ) {
		_lastRemoved = null;
		if( _count == _maxCount ) {
			if( key.compareTo( _maxKey ) > -1 ) return false;
			_lastRemoved = removePoint( _maxKey );
		}
		addToMap( key, value );
		return true;
	}
	
	/**
	 * It was determined that we must get rid of one entry 
	 * at this key. If the new list of values at this key is 
	 * empty, remove the list and the key itself.
	 * 
	 * @param key Key at which to obtain list from which a value will be removed
	 * @return Value that was removed
	 */
	private V removePoint( K key ) {
		LinkedList<V> values = _map.get( key );
		_count--;
		V value = values.remove();
		if( values.size() == 0 )
			_map.remove( key );
		return value;
	}
	
	/**
	 * Traverse this map to make a collection of values without their keys
	 * @return Collection of values stored in map
	 */
	public List<V> getValues() {
		List<V> values = new LinkedList<V>(); 
		Iterator<LinkedList<V>> firstLevelIterator = _map.values().iterator();
		while( firstLevelIterator.hasNext() )
			values.addAll( firstLevelIterator.next() );
		return values;
	}
	
	/**
	 * Internal add to map method. If necessary, creates a 
	 * new linked list for values at this key. Increments
	 * count of total number of values in map and updates
	 * the max key value which could have been changed
	 * by this call. This method gets called only if we
	 * already know that this value should be in the map,
	 * which is determined elsewhere.
	 * 
	 * @param key Key by which to store value
	 * @param value Value to be stored
	 */
	private void addToMap( K key, V value ) {
		LinkedList<V> values = _map.get( key );
		if( values == null ) {
			values = new LinkedList<V>();
			_map.put( key, values );
		}
		values.add( value );
		_count++;
		_maxKey = _map.lastKey();
	}

	/**
	 * @return The value that was removed when the last point was added
	 */
	public V getLastRemoved() { return _lastRemoved; }
	
}
