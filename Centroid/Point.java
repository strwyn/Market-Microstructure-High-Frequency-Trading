package lecture3;

import java.util.Iterator;
import java.util.LinkedList;

public class Point {

	protected double[] _coordinates;
	
	public Point( double[] coordinates ) {
		_coordinates = coordinates;
	}
	
	public double distanceFrom( Point otherPoint ) {
		int length = _coordinates.length;
		double distance = 0.0D;
		for( int i = 0; i < length; i++ ) {
			distance = distance + Math.pow( _coordinates[ i ] - otherPoint.getCoordinate( i ), 2 );
		}
		return( Math.sqrt( distance ) );
	}
	
	public Centroid findClosest( LinkedList<Centroid> list ) {
		
		// Make first one best for now
		Iterator<Centroid> centroidIterator = list.iterator();
		Centroid bestCentroid = centroidIterator.next();
		double bestDistance = this.distanceFrom( bestCentroid );
		
		// Now look for something better
		while( centroidIterator.hasNext() ) {
			Centroid centroid = centroidIterator.next();
			double distance = this.distanceFrom( centroid );
			if( distance < bestDistance ) {
				bestCentroid = centroid;
				bestDistance = distance;
			}
		}
		
		// Return best
		return( bestCentroid );
	}
	
	public double getCoordinate( int i ) {
		return _coordinates[ i ];
	}
}
