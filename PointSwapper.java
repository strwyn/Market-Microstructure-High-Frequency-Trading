package clusters;

import java.util.ArrayList;

public class PointSwapper extends AbstractClusterAlgorithm {

	public PointSwapper( ArrayList<Cluster> clusters, ArrayList<Point> points ) {
		super( clusters, points );
	}

	/**
	 * Point swapper needs to have clusters filled with points before it
	 * starts. The other algorithms just need clusters to have a centroid.
	 * The following method fills clusters with the nearest 20 points, which
	 * results in a bad initial allocation, but there is no obvious way to
	 * improve it. 
	 */
	@Override
	public void initializeClusters() {
		
		for( Point point : _points )
			point.setNewCluster( null ); // This tells 'replaceWithNearest' that this point can be allocated

		// For each cluster, fill it with nearest points
		for( Cluster cluster : _clusters ) {
			cluster.replaceWithNearest( _points.size() / _clusters.size(), _points );
			// All of the points allocated to this cluster have now had their new cluster field set to this cluster
			// Compute this cluster's centroid using the new points
			cluster.computeCentroid();
			// Set the cluster field of each point allocated to the new cluster (the new cluster field is already set)
			for( Point point : cluster.getPoints() )
				point.setCluster( cluster ); // Set cluster of this point
		}
	}
	
	/**
	 * Returns true if the two points are better off swapping - if
	 * the sum of their distances form the centroids of their respective
	 * clusters goes down when they swap clusters.
	 * 
	 * @param p1 First point to consider for a swap with second point
	 * @param p2 Second point to consider for a swap with first point
	 * @return True if the points are better off swapping, otherwise false
	 */
	protected boolean shouldSwap( Point p1, Point p2 ) {
		return (
				( p1.distanceFrom( p1.getCluster().getCentroid() ) + p2.distanceFrom( p2.getCluster().getCentroid() ) )
				>
				( p2.distanceFrom( p1.getCluster().getCentroid() ) + p1.distanceFrom( p2.getCluster().getCentroid() ) )
		);
	}
	
	/**
	 * Swap clusters of first and second points
	 * 
	 * @param p1 First point
	 * @param p2 Second point
	 */
	protected void swapPoints( Point p1, Point p2 ) {
		
		// Swaps cluster assignments
		
			Cluster cluster1 = p1.getCluster();
			Cluster cluster2 = p2.getCluster();
			p1.setCluster( cluster2 );
			p2.setCluster( cluster1 );
			
		// Increment number of changes
			
			++_nChanges;
		
	}

	/**
	 * Main processing method for PointSwapper. Iterates over every
	 * point looking for another point with which it can swap.
	 */
	@Override
	public void performOneIteration() {
		// For every point, set the new cluster field to null to tell optimizer this point can be allocated
		for( Point point : _points )
			point.setNewCluster( null );
		// Empty each cluster's list of points, but do not erase its centroid
		for( Cluster cluster : _clusters )
			cluster.resetPointsList();
		// Iterate to find pairs of points that can be swapped
		int lastIndex1 = _points.size() - 1;
		int lastIndex2 = _points.size();
		for( int i = 0; i < lastIndex1; i++ ) { // For every point
			Point p1 = _points.get( i ); // Retrieve point from list
			if( p1.getNewCluster() == null ) // Has it already been allocated?
				for( int j = i + 1; j < lastIndex2; j++ ) { // No. So find a point to swap with.
					Point p2 = _points.get( j ); // Found a point.
					if( p2.getNewCluster() != null ) // Has it already been allocated?
						continue; // Yes, already allocated, so keep looking.
					if( p1.getCluster() == p2.getCluster() ) // Not yet allocated but is it in same cluster?
						continue; // Yes, in same cluster, nothing to swap
					if( shouldSwap( p1, p2 ) ) { // Does the distance calc tell us these points should be swapped?
						swapPoints( p1, p2 ); // Yes, so swap points
						break; // Stop looking for a second point to swap with firt point
					}
				}
		}
		// All points now have a cluster field set to their allocated cluster and a new cluster field set to same
		// Add points to the clusters to which they have been allocated
		for( Point point : _points )
			point.getCluster().addPoint( point );
		// Compute the centroids of all clusters
		for( Cluster cluster : _clusters )
			cluster.computeCentroid();
	}

	@Override
	public void postProcessResults() {}

}
