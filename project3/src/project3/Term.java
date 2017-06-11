package project3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

public class Term {
	
	// For term "2.1*x^4*y*z^2" we would have the following member assignments...
	private double _coef; // = 2.1
	private ArrayList<String>  _vars; // = ["x", "y", "z"]
	private ArrayList<Integer> _pows; // = [4, 1, 2]

	// Constructor that only takes a coefficient
	public Term(double coef) {
		_coef = coef;
		_vars = new ArrayList<String>();
		_pows = new ArrayList<Integer>();
	}
	
	// Constructor that parses a String representation of a term
	public Term(String s) throws Exception {
		
		// Initialize this term
		_coef = 1.0d; // Will multiply any constants by this
		_vars = new ArrayList<String>();
		_pows = new ArrayList<Integer>();

		String[] factors = s.split("\\*");
		for (String factor : factors) {
			factor = factor.trim(); // Get rid of leading and trailing whitespace
			try {
				// If successful, multiplies in a constant (multiple constants in a product allowed)
				_coef *= Double.parseDouble(factor); 					
			} catch (NumberFormatException e) {
				// If not a coefficient, must be a factor "<var>^<pow>"
				// Must be a variable to a power -- parse the factor and add to list
				int pow = 1; // If no power, defaults to 1
				String[] var_pow = factor.split("\\^");
				String var = var_pow[0];
				if (var_pow.length == 2) {
					try { // Second part must be exponent
						pow = Integer.parseInt(var_pow[1]);
					} catch (NumberFormatException f) {
						throw new Exception("ERROR: could not parse " + factor);
					}
				} else if (var_pow.length > 2) 
					throw new Exception("ERROR: could not parse " + factor);
				
				// Successfully parsed variable and power, add to list
				if (_vars.contains(var))
					throw new Exception("ERROR: " + var + " appears twice in " + s);
				_vars.add(var);
				_pows.add(pow);
			}
		}
	}

	// Returns all variables in this term.  They are stored in an ArrayList<String>
	// and one can construct a TreeSet<String> from an ArrayList<String> (and vice versa)
	// by passing one to the constructor of the other.  Familiarize yourself with
	// TreeSet operations (Google "Java TreeSet").
	public TreeSet<String> getAllVars() {
		return new TreeSet<String>(_vars);
	}
	
	// Returns a String representation of this term (can re-parse into same term)
	public String toString() {
		// Using "+" to append Strings involves a lot of String copies since Strings are 
		// immutable.  StringBuilder is much more efficient for append.
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%01.3f", _coef));
		for (int i = 0; i < _vars.size(); i++) {
			String var = _vars.get(i);
			int pow = _pows.get(i);
			sb.append("*" + var + (pow == 1 ? "" : "^" + pow));
		}
		return sb.toString();
	}
	
	// Sets the coefficient class member
	public void setCoef(double coef) {
		this._coef = coef;
	}

	// Returns the coefficient class member
	public double getCoef() {
		return this._coef;
	}
	
	// Evaluate this term for the given variable assignments
	public double evaluate(HashMap<String, Double> assignments) throws Exception {	
		if (!this._vars.isEmpty()){
			double res = 1;
			
			for (String k : this._vars) {
				if (!assignments.keySet().contains(k)) {
					throw new Exception("ERROR: term contains a variable that is not assigned!");
				}
			}
			
			for (String k : assignments.keySet()) {
				if (!this._vars.contains(k)) {
					// For example, if in x^2 + y^2 you assign (x, y) = (1, 2), there will not be a term with both x and y
					continue;
				}
				else {
					res *= Math.pow(assignments.get(k), this._pows.get(this._vars.indexOf(k)));
				}
			}
			
			return _coef * res;
		}
		else {
			// If no variables in the term, return the coefficient
			return _coef;
		}
	}

	// Provide the symbolic form resulting from differentiating this term w.r.t. var
	public Term differentiate(String var) {
		Term t = new Term(0); // Instantiate new term initialized with coefficient 0, and empty _vars and _pows lists
		
		// If the term contains the variable that it is being differentiated with respect to,
		// enter "if" control block. Else, return the 0 term.
		if (this._vars.contains(var)) {
			int index = this._vars.indexOf(var);
			int power = this._pows.get(index); // Get the power of the variable being differentiated
			
			if (power != 0) {
				// Copy ArrayLists since they are reference type
				t._vars.addAll(this._vars);
				t._pows.addAll(this._pows);
				//t._vars = (ArrayList<String>) this._vars.clone();
				//t._pows = (ArrayList<Integer>) this._pows.clone();
				
				if (power == 1) {
					// Coefficient doesn't change
					// Delete variable and its power from the term
					t._coef = this._coef;
					t._vars.remove(var);
					t._pows.remove(index);
				}
				else {
					// Multiple coefficient by power and decrease power by 1
					t._coef = this._coef * power;
					t._pows.set(index, power - 1);
				}
			}
		}
		return t;
	}
}
