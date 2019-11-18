package clusters;

import java.util.ArrayList;

public class Test_PointSwapper extends junit.framework.TestCase {
	
	public void testPointSwapper() throws Exception {
		long randomSeed = 89877;
		ClusterTestProblem ctp = new ClusterTestProblem( randomSeed );
		ArrayList<Point> points = ctp.getPoints();
		ArrayList<Cluster> clusters = ctp.getClusters( 500, points );
		PointSwapper la = new PointSwapper( clusters, points );
		la.solve();
	}

}
