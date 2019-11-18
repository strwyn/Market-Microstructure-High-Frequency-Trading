package clusters;

import java.util.LinkedList;
import java.util.List;

public class Test_Cluster extends junit.framework.TestCase {
	
	public void testCentroidAndDistanceMetric() throws Exception {
		
		double[] vector = { 1.0, 2.0 };
		Point centroid = new Point( vector );

		double[] vector1 = { 5.0, 6.0 };
		Point p1 = new Point( vector1 );
		
		double[] vector2 = { 6.0, 8.0 };
		Point p2 = new Point( vector2 );
		
		Cluster cluster = new Cluster( centroid );
		cluster.addPoint( p1 );
		cluster.addPoint( p2 );

		// Test distance metric calculation
			
			double d1 = Math.sqrt( Math.pow( 5.0 - 1.0, 2.0 ) + Math.pow( 6.0 - 2.0, 2.0 ) ); 
			double d2 = Math.sqrt( Math.pow( 6.0 - 1.0, 2.0 ) + Math.pow( 8.0 - 2.0, 2.0 ) );
			
			double expectedMetric = d1 + d2;
			double actualMetric = cluster.getDistanceMetric(); // Test distance metric 
			assertEquals( actualMetric, expectedMetric );
		
		// Test centroid calculation
			
			cluster.computeCentroid();
			assertEquals(
					( 6.0 + 5.0 ) / 2.0,
					cluster.getCentroid().getCoordinate( 0 ),
					0.001
			);
			assertEquals(
					( 8.0 + 6.0 ) / 2.0,
					cluster.getCentroid().getCoordinate( 1 ),
					0.001
			);
			
	}
	
	public void testFindNearest() throws Exception {
		
		double[] vector = { 5.0, 5.0 };
		Point centroid = new Point( vector );

		double[] vector1 = { 5.0, 6.0 };
		Point p1 = new Point( vector1 );
		
		double[] vector2 = { 6.0, 6.0 };
		Point p2 = new Point( vector2 );
		
		double[] vector3 = { 4.5, 5.5 };
		Point p3 = new Point( vector3 );
		
		double[] vector4 = { 3.5, 2.5 };
		Point p4 = new Point( vector4 );

		LinkedList<Point> pointsList = new LinkedList<Point>();
		pointsList.add( p4 );
		pointsList.add( p1 );
		pointsList.add( p2 );
		pointsList.add( p3 );
		
		Cluster cluster = new Cluster( centroid );
		List<Point> foundPoints = cluster.findNearestNPoints( 2, pointsList );
		assertTrue( foundPoints.size() == 2 );

		assertEquals( foundPoints.get( 0 ).getCoordinate( 0 ), 4.5, 0.0001 );
		assertEquals( foundPoints.get( 0 ).getCoordinate( 1 ), 5.5, 0.0001 );

		assertEquals( foundPoints.get( 1 ).getCoordinate( 0 ), 5.0, 0.0001 );
		assertEquals( foundPoints.get( 1 ).getCoordinate( 1 ), 6.0, 0.0001 );
	}
	
	public void testReplaceAllPoints() {
		
		double[] zeroVector = { 0.0, 0.0 };
		Cluster cluster1 = new Cluster( new Point( zeroVector ) );
		
		double[] vector = { 5.0, 5.0 };
		Point centroid = new Point( vector );

		double[] vector1 = { 5.0, 6.0 };
		Point p1 = new Point( vector1 );
		p1.setCluster( cluster1 );
		p1.setNewCluster( null );
		
		double[] vector2 = { 6.0, 6.0 };
		Point p2 = new Point( vector2 );
		p2.setCluster( cluster1 );
		p2.setNewCluster( null );
		
		double[] vector3 = { 4.5, 5.5 };
		Point p3 = new Point( vector3 );
		p3.setCluster( cluster1 );
		p3.setNewCluster( null );

		double[] vector4 = { 3.5, 2.5 };
		Point p4 = new Point( vector4 );
		p4.setCluster( cluster1 );
		p4.setNewCluster( null );

		LinkedList<Point> pointsList = new LinkedList<Point>();
		pointsList.add( p4 );
		pointsList.add( p1 );
		pointsList.add( p2 );
		pointsList.add( p3 );
		
		Cluster cluster2 = new Cluster( centroid );
		cluster2.addPoint( p1 );
		cluster2.computeCentroid();
		
		assertEquals( cluster2.getCentroid().getCoordinate( 0 ), p1.getCoordinate( 0 ), 0.0001 );
		assertEquals( cluster2.getCentroid().getCoordinate( 1 ), p1.getCoordinate( 1 ), 0.0001 );
		
		cluster2.replaceAllPoints( pointsList );
		List<Point> newPointsList = cluster2.getPoints();
		for( Point point : newPointsList ) {
			assertTrue( point.getNewCluster() == cluster2 );
			assertTrue( point.getCluster() == cluster1 );
		}
		cluster2.computeCentroid();
		
		Point expectedCentroid = Point.computeCentroids( pointsList );
		Point actualCentroid = cluster2.getCentroid();
		double tolerance = 0.0001;
		assertEquals( expectedCentroid.getCoordinate( 0 ), actualCentroid.getCoordinate( 0 ), tolerance );
		assertEquals( expectedCentroid.getCoordinate( 1 ), actualCentroid.getCoordinate( 1 ), tolerance );
		
	}
	
	
}
