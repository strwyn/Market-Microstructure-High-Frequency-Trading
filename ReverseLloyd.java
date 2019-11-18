package clusters;

import java.util.ArrayList;

public class ReverseLloyd extends AbstractClusterAlgorithm {
	
	protected int _clusterSize;
	
	/**
	 * Instantiate 
	 * @param clusters Initial guess of clusters
	 * @param points List of points to assign to clusters
	 * @throws Exception Thrown if numbers of points do not divide evenly by number of clusters desired
	 */
	public ReverseLloyd( ArrayList<Cluster> clusters, ArrayList<Point> points ) throws Exception {
		super( clusters, points );
		_clusterSize = points.size() / clusters.size();
		if( _clusterSize * clusters.size() != points.size() )
			throw new Exception( "Number of points must be a multiple of cluster size" );
	}
	
	@Override
	public void initializeClusters() {}
	
	@Override
	public void postProcessResults() {}
	
	/** 
	 * Main processing method for this algorithm.
	 * Each cluster seeks the nearest 20 points and adds them
	 * to itself.
	 */
	@Override
	public void performOneIteration() {
		// Shuffle clusters - Uncomment to see that it doesn't help
			/*
			LinkedList<Pair<Cluster,Double>> clustersList = new LinkedList<Pair<Cluster,Double>>();
			Random random = new Random();
			for( Cluster cluster : _clusters ) clustersList.add( new Pair<Cluster,Double>( cluster, random.nextDouble() ) );
			Comparator<Pair<Cluster,Double>> comparator = new Comparator<Pair<Cluster,Double>>() {
				@Override
				public int compare(
					Pair<Cluster, Double> o1,
					Pair<Cluster, Double> o2
				) {
					return o1.getThing2().compareTo( o2.getThing2() );
				}
			};
			Collections.sort( clustersList, comparator );
			_clusters.clear();
			for( Pair<Cluster,Double> pair : clustersList ) _clusters.add( pair.getThing1() );
			*/
		for( Cluster cluster : _clusters ) {
			cluster.replaceWithNearest( _clusterSize, _points );
			cluster.computeCentroid();
		}
		for( Point point : _points ) {
			if( point.getCluster() != point.getNewCluster() ) {
				_nChanges++;
				point.setCluster( point.getNewCluster() );
			}
			point.setNewCluster( null );
		}
	}

}
