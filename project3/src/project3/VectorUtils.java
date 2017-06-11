package project3;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class VectorUtils {

	// Computes the sum of two HashMap representations of vectors: see main() for examples
	public static HashMap<String,Double> sum(HashMap<String,Double> x, HashMap<String,Double> y) {
		HashMap<String, Double> res = new HashMap<String, Double>();
		
		for (String k : x.keySet()) {
			if (!y.containsKey(k)) {
				res.put(k, x.get(k));
			}
			else {
				res.put(k, x.get(k) + y.get(k));
			}
		}
		for (String k : y.keySet()) {
			if (!x.containsKey(k)) {
				res.put(k, y.get(k));
			}
		}
		
		return res;
	}
		
	// Multiplies a scalar (single real number) into a vector: see main() for examples
	public static HashMap<String,Double> scalarMult(double scalar, HashMap<String,Double> x) {
		HashMap<String, Double> res = new HashMap<String, Double>();
		
		for (Entry<String, Double> e : x.entrySet()) {
			String k = e.getKey();
			Double d = e.getValue();
			d *= scalar;
			res.put(k, d);
		}
		return res;
	}

	// Computes the L_2 norm of a vector x: sqrt( sum_i x_i^2 ): see main() for examples
	public static double computeL2Norm(HashMap<String,Double> x) {
		double retVal = 0;
		
		for (Double d : x.values()) {
			retVal += Math.pow(d, 2);
		}
		return Math.sqrt(retVal);
	}

	// Testing function
	public static void main(String[] args) throws Exception {

		// Make vector: vec1[x y z] = [1 2 3]
		HashMap<String,Double> vec1 = new HashMap<String,Double>();
		vec1.put("x", 1.0);
		vec1.put("y", 2.0);
		vec1.put("z", 3.0);
		
		// Make vector: vec2[x y z] = [-3 -2 -1]
		HashMap<String,Double> vec2 = new HashMap<String,Double>();
		vec2.put("x", -3.0);
		vec2.put("y", -2.0);
		vec2.put("z", -1.0);
		
		// Test cases
		System.out.println(sum(vec1,vec2));        // Should print "{x=-2.0, y=0.0, z=2.0}"
		System.out.println(sum(vec1,vec1));        // Should print "{x=2.0, y=4.0, z=6.0}"
		System.out.println(scalarMult( 0.5,vec1)); // Should print "{x=0.5, y=1.0, z=1.5}"
		System.out.println(scalarMult(-1.0,vec2)); // Should print "{x=3.0, y=2.0, z=1.0}"
		System.out.println(sum(vec1, scalarMult(-1.0,vec2))); // Should print "{x=4.0, y=4.0, z=4.0}"
		System.out.format("%01.3f\n", computeL2Norm(vec1));   // Should print "3.742"
		System.out.format("%01.3f\n", computeL2Norm(vec2));   // Should print "3.742"
	}
}
