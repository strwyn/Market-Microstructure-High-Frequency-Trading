package clusters;

import java.util.LinkedList;


public class Test_Point extends junit.framework.TestCase {

	/**
	 * Test constructor
	 */
	public void test1() {
		double[] vector = new double[ 2 ];
		double x = Math.PI;
		double y = Math.PI * 2;
		vector[ 0 ] = x;
		vector[ 1 ] = y;
		Point p = new Point( vector );
		assertEquals( p.getCoordinate( 0 ), Math.PI, 0.0001 ); // Make sure constructor stored x
		assertEquals( p.getCoordinate( 1 ), 2.0 * Math.PI, 0.0001 ); // Make sure constructor stored y
		assertTrue( p.getNDims() == 2 ); // Test method that returns number of dimensions
		assertFalse( p._coordinates == vector ); // Make sure constructor copied by value
	}
	
	/**
	 * Test copy constructor
	 */
	public void test2() {
		double[] vector = new double[ 2 ];
		double x = Math.PI;
		double y = Math.PI * 2;
		vector[ 0 ] = x;
		vector[ 1 ] = y;
		Point p = new Point( vector );
		Point p1 = new Point( p );
		assertTrue( p1._coordinates != p._coordinates ); // Test copy by value not reference
	}
	
	/**
	 * Test calculation of distance between two points
	 */
	public void test3() {
		double[] vector1 = { 1.0, 2.0 };
		Point p1 = new Point( vector1 );
		double[] vector2 = { 5.0, 7.0 };
		Point p2 = new Point( vector2 );
		assertEquals(
			p1.distanceFrom( p2 ),
			Math.sqrt(
				( ( vector2[0] - vector1[0] ) * ( vector2[0] - vector1[0] ) )
				+
				( ( vector2[1] - vector1[1] ) * ( vector2[1] - vector1[1] ) )
			),
			0.0001
		);
	}
	
	/**
	 * Test addAsVector method and divideByValue method. These are methods that we will use
	 * to find the centroids of a whole bunch of points.
	 */
	public void test4() {
		double[] vector1 = { 1.0, 2.0 };
		Point p1 = new Point( vector1 );
		double[] vector2 = { 5.0, 7.0 };
		Point p2 = new Point( vector2 );
		p1.addAsVector( p2 );
		p1.rescaleVector( 2.0 );
		assertEquals( p1.getCoordinate( 0 ), 3.0, 0.001 );
		assertEquals( p1.getCoordinate( 1 ), 4.5, 0.001 );
	}
	
	/**
	 * Test static centroid calculation method
	 */
	public void test5() {
		double[] vector1 = { 1.0, 2.0 };
		Point p1 = new Point( vector1 );
		double[] vector2 = { 2.0, 4.0 };
		Point p2 = new Point( vector2 );
		double[] vector3 = { 3.0, 8.0 };
		Point p3 = new Point( vector3 );
		LinkedList<Point> pointsList = new LinkedList<Point>();
		pointsList.add( p1 );
		pointsList.add( p2 );
		pointsList.add( p3 );
		Point p4 = Point.computeCentroids( pointsList );
		assertEquals( p4.getCoordinate( 0 ), ( 1.0 + 2.0 + 3.0 ) / 3.0, 0.0001 );
		assertEquals( p4.getCoordinate( 1 ), ( 2.0 + 4.0 + 8.0 ) / 3.0, 0.0001 );
	}
	
}
