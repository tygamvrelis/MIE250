package project3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Polynomial {

	private ArrayList<Term> _terms;

	// Empty polynomial constructor
	public Polynomial() {
		_terms = new ArrayList<Term>();
	}

	// Parse a string into a Polynomial
	public Polynomial(String s) throws Exception {

		_terms = new ArrayList<Term>();
		String[] terms = s.split("\\+");
		for (String term : terms)
			_terms.add(new Term(term));
	}

	// Returns a String representation of this term (can re-parse into same term)
	public String toString() {
		// Using "+" to append Strings involves a lot of String copies since Strings are 
		// immutable.  StringBuilder is much more efficient for append.
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Term term : _terms) {
			sb.append((first ? "" : " + ") + term);
			first = false;
		}
		return sb.toString();
	}
	
	// Returns all vars in this polynomial... this has been done for you, but
	// you should be able to produce this code on your own own.  Familiarize 
	// yourself with TreeSet operations (Google "Java TreeSet").
	public TreeSet<String> getAllVars() {
		TreeSet<String> all_vars = new TreeSet<String>();
		for (Term t : _terms)
			all_vars.addAll(t.getAllVars());
		return all_vars;
	}
	
	// Evaluate this polynomial for the given variable assignments
	public double evaluate(HashMap<String, Double> assignments) throws Exception {
		// Argument passed in is a HashMap of key-value pairs corresponding to variables and their value (e.g. x = 2, y = -3,...)
		double result = 0;
		
		for (Term t : _terms) {
			result += t.evaluate(assignments);
		}
		
		return result;
	}

	// Provide the symbolic form resulting from differentiating this polynomial w.r.t. var
	public Polynomial differentiate(String var) {
		Polynomial p = new Polynomial();
		
		for (Term t : _terms) {
			Term differentiatedTerm = t.differentiate(var);
			if (!(differentiatedTerm.getAllVars().isEmpty() && differentiatedTerm.getCoef() == 0)) {
				p._terms.add(differentiatedTerm);
			}
		}
		// remove 0 terms
		return p;
	}

	public static void main(String[] args) throws Exception {
		Polynomial p  = new Polynomial("x^2 + y^2 + -4*x*y + 8");
		Polynomial p2 = new Polynomial(p.toString()); // See if we can reparse p.toString()
		Polynomial dp_dx = p.differentiate("x");
		Polynomial dp_dy = p.differentiate("y");
		
		

		// Build a point vector (HashMap) of numerical assignments for variables
		HashMap<String,Double> x0 = new HashMap<String,Double>();
		x0.put("x", 1.0);
		x0.put("y", 2.0);
		
		
		System.out.println("Polynomial: " + p);     // Should print "1.000*x^2 + 1.000*y^2 + -4.000*x*y + 8.000"
		System.out.println("Re-parsed:  " + p2);    // Should print "1.000*x^2 + 1.000*y^2 + -4.000*x*y + 8.000"
		System.out.println("dp/dx:      " + dp_dx); // Should print "2.000*x + -4.000*y"
		System.out.println("dp/dy:      " + dp_dy); // Should print "2.000*y + -4.000*x"
		System.out.println("Free vars:  " + p.getAllVars()); // Should print "[x, y]"
		System.out.println("p(x0)     = " + p.evaluate(x0));     // Should print "5.0"
		System.out.println("dp/dx(x0) = " + dp_dx.evaluate(x0)); // Should print "-6.0"
		System.out.println("dp/dy(x0) = " + dp_dy.evaluate(x0)); // Should print "0.0"
	}
}
