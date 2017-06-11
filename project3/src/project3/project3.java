package project3;
// Find minimum of polynomial using steepest descent
import java.io.*;
import java.util.HashMap;

public class project3 {

	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	
	// Main entry point for project
	public static void main(String[] args) throws IOException {

		String choice;
		Polynomial p = null;
		Minimizer  m = new Minimizer();

		do {
			displayMenu();
			choice = cin.readLine().toUpperCase();
			if (choice.equals("R")) {
				try {
					p = readPolynomial();
					System.out.println("Read polynomial: " + p + "\n");
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage() + "\nCould not read polynomial; polynomial unchanged.\n");
				}
			}
			
			else if (choice.equals("V")) {
				if (p == null) 
					System.out.println("\nERROR: Polynomial function has not been entered!\n");
				else
					System.out.println("\n" + p + "\n");
			}
			
			else if (choice.equals("S")) {
				getParamsUser(m);
			}
			
			else if (choice.equals("M")) {
				if (p == null) 
					System.out.println("\nERROR: Polynomial function has not been entered!\n");
				else {
					try {
						System.out.println("\nMinimizing: " + p + "...\n");
						m.minimize(p);
						printResults(m);
					} catch (Exception e) {
						System.out.println("\n" + e.getMessage() + "\nERROR: Could not complete optimization.\n");
					}
				}
			}
			
			else if (choice.equals("P")) {
				printParams(m);
			}
			
			else if (!choice.equals("Q")) {
				System.out.println("\nERROR: Invalid menu choice!\n");
			}
			
		} while (!choice.equals("Q"));

		System.out.println("\nGoodbye!");
	}

	public static void displayMenu() throws IOException {
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("R - Read polynomial function");
		System.out.println("V - View polynomial function");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("M - Minimize polynomial by gradient descent");
		System.out.println("Q - Quit");
		System.out.print("\nEnter choice: ");
	}
	
	// Asks the user for a filename, retrieves first line, and makes a new Polynomial with it.
	// Anything that goes wrong is thrown here as an Exception and caught and handled by the 
	// calling method.
	private static Polynomial readPolynomial() throws Exception {	
		String filename = getString("\nEnter filename for reading polynomial: ");
		BufferedReader fin = new BufferedReader(new FileReader(filename));
		String line1 = fin.readLine();
		fin.close();
		
		if (line1 != null) {
			Polynomial p = new Polynomial(line1);
			return p;
		}
		else {
			throw new Exception("ERROR: " + filename + " is empty");
		}
	}

	// get algorithm parameters from user
	public static void getParamsUser(Minimizer m) {
		
		System.out.println();
		double eps   = project3.getDouble("Enter tolerance epsilon [0.000001,infinity]: ", 0.000001, Double.MAX_VALUE);
		m.setEps(eps);

		int max_iter = project3.getInteger("Enter maximum number of iterations [1,1000]: ", 1, 1000);
		m.setMaxIter(max_iter);

		double alpha = project3.getDouble("Enter step size alpha [0.000001,1]: ", 0.000001, 1.0);		
		m.setStepSize(alpha);
		
		System.out.println();
		HashMap<String,Double> x0 = new HashMap<String,Double>();
		while (true) {
			String var = getString("Enter variable name or empty line when done: ");
			var = var.trim(); // Remove excess whitespace
			if (var.length() == 0)
				break; // Done entering points
			double val = project3.getDouble("Enter starting value for variable " + var + ": ", -Double.MAX_VALUE, Double.MAX_VALUE);	
			x0.put(var, val);
		}
		m.setX0(x0);
		
		System.out.println("\nAlgorithm parameters set!\n");
	}
	
	// print algorithm details
	public static void printResults(Minimizer m) {
		System.out.println();
		System.out.println("Number of iterations: " + m.getNIter());
		System.out.println("Last gradient norm:   " + m.getLastGradNorm());
		System.out.println("Point at termination: " + m.getLastPoint());
		System.out.println("Time elapsed:         " + m.getCompTime() + " ms");
		System.out.println();
	}
	
	// print algorithm parameters
	public static void printParams(Minimizer m) {
		System.out.println();
		System.out.println("Tolerance (epsilon): " + m.getEps());
		System.out.println("Maximum iterations:  " + m.getMaxIter());
		System.out.println("Step size (alpha):   " + m.getStepSize());
		System.out.println("Starting point (x0): " + m.getX0());
		System.out.println();
	}
	
	// get a String from console (no error checking to do)
	public static String getString(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return cin.readLine();
			}
			catch (IOException e) { 
				System.out.print("ERROR: please reenter\n\n");
			}
		}
		//return null; // Compiler should know this line is never reached, but just in case
	} // end getString

	// get an integer in [LB, UB]
	public static int getInteger(String prompt, int LB, int UB) {
		int x = 0;
		boolean valid;
		do {
			valid = true;
			System.out.print(prompt);
			try {
				x = Integer.parseInt(cin.readLine());
			}
			catch (IOException e) { 
				valid = false;
			}
			catch (NumberFormatException e) {
				valid = false;
			}
			
			if (valid && (x < LB || x > UB)) {
				valid = false;
			}
			
			if (!valid) {
				if (UB == Integer.MAX_VALUE && LB == -Integer.MAX_VALUE)
					System.out.format("ERROR: Input must be an integer in [-infinity, infinity]!\n\n");
				else if (UB == Integer.MAX_VALUE)
					System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
				else if (LB == -Integer.MAX_VALUE)
					System.out.format("ERROR: Input must be an integer in [-infinity, %d]!\n\n", UB);
				else	
					System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
			}
		} while (!valid);
		return x;	
	} // end getInteger
	
	// get a double in [LB, UB]
	public static double getDouble(String prompt, double LB, double UB) {
		double x = 0;
		boolean valid;
		do {
			valid = true;
			System.out.print(prompt);
			try {
				x = Double.parseDouble(cin.readLine());
			}
			catch (IOException e) { 
				valid = false;
			}
			catch (NumberFormatException e) {
				valid = false;
			}
			
			if (valid && (x < LB || x > UB)) {
				valid = false;
			}
			
			if (!valid) {
				if (UB == Double.MAX_VALUE && LB == -Double.MAX_VALUE)
					System.out.format("ERROR: Input must be a real number in [-infinity, infinity]!\n\n");
				else if (UB == Double.MAX_VALUE)
					System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB);
				else if (LB == -Double.MAX_VALUE)
					System.out.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n\n", UB);
				else	
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
			}
		} while (!valid);
		return x;	
	} // end getDouble
}
