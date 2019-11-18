package clusters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import utils1309.MinValuesMap;

/**
 * This class represents a cluster with a centroid,
 * a list of points that currently belong to it,
 * and a list of points that are being accumulated
 * in some kind of evaluation.
 * 
 */
public class Cluster {
	
	protected Point             _centroid;
	protected LinkedList<Point> _points; // Currently assigned points
	protected Point             _vectorSum; // Points being accumulated in an iteration

	/**
	 * Instantiate a new cluster and set its centroid.
	 * 
	 * @param centroid A point representing the current assigned centroid of this cluster
	 */
	public Cluster( Point centroid ) {
		_centroid = centroid;
		resetPointsList();
	}

	/**
	 * @return Centroid of this cluster
	 */
	public Point getCentroid() { return _centroid; }
	
	/**
	 * @return Number of points in this cluster
	 */
	public int size() { return _points.size(); }
	
	/**
	 * Return underlying array of points in this cluster
	 * @return Array of points in this cluster
	 */
	public List<Point> getPoints() { return _points; }
	
	/**
	 * Use the accumulated vector sum variable - modified as
	 * points were added to this cluster - to compute the
	 * geometric centroid of this cluster.
	 */
	public void computeCentroid() {
		
		// If there are no points in this cluster, do not
		// recompute centroid
		
			if( _points.size() == 0 )
				return;
			
		_vectorSum.rescaleVector( _points.size() );
		_centroid = _vectorSum;
	}
	
	/**
	 * Add a point to this cluster and update its vector
	 * sum to make centroid calculation trivial later
	 * 
	 * @param point The point to be added
	 */
	public void addPoint( Point point ) {
		_points.add( point );
		_vectorSum.addAsVector( point );
	}
	
	/**
	 * Replace all points in this cluster with a new list of points.
	 * 
	 * @param points List of points to use to replace points in this cluster
	 */
	protected void replaceAllPoints( List<Point> points ) {
		resetPointsList();
		for( Point point : points ) {
			addPoint( point ); // Add this point to list of points for this cluster
			point.setNewCluster( this );
		}
	}
	
	/**
	 * Find specified number of nearest unallocated points. Unallocated points are points
	 * for which the newCluster variable is null
	 *  
	 * @param nPointsToFind Number of nearest points to find
	 * @param pointsList List of points to search for nearest unallocated points
	 * @return List of points found by the search
	 */
	protected List<Point> findNearestNPoints( int nPointsToFind, List<Point> pointsList ) {
		MinValuesMap<Double, Point> mvp = new MinValuesMap<Double, Point>( nPointsToFind );
		for( Point point : pointsList )
			if( point.getNewCluster() == null ) // Has not been allocated
				mvp.addPoint( point.distanceFrom( _centroid ), point );
		return mvp.getValues();
	}

	/**
	 * Replaces this cluster's list of points with the nearest nPoint points from pointsList.
	 * 
	 * @param nPointsToFind Number of closest points to use
	 * @param pointsList List of points to evaluate for closest nPoints
	 */
	public void replaceWithNearest( 
		int nPointsToFind, 
		ArrayList<Point> pointsList
	) {
		List<Point> nearestPointsFound = findNearestNPoints( nPointsToFind, pointsList );
		replaceAllPoints( nearestPointsFound );
	}
	
	/**
	 * Compute the sum of distances of all points in this cluster from 
	 * its centroid
	 * 
	 * @return Sum of distances or NaN if the cluster is empty
	 */
	public double getDistanceMetric() {
		if( _points.size() == 0 )
			return Double.NaN;
		double distanceSum = 0;
		for( Point point : _points )
			distanceSum += point.distanceFrom( _centroid );
		return distanceSum;
	}

	/**
	 * Remove all points from the points list of this cluster in
	 * preparation to receive more points. Reset vectorSum to zero
	 * values so it can be used to compute centroid later.
	 */
	public void resetPointsList() {
		_points = new LinkedList<Point>();
		_vectorSum = new Point( _centroid.getNDims() );
	}
	
}
