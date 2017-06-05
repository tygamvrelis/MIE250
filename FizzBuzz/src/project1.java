import java.io.*;

public class project1 {
	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		// Instantiate a new FizzBuzz object
		FizzBuzz fb = new FizzBuzz();

		// Run the game
		fb.run();
	}
	
	public static double getNonnegDouble(String prompt) {
	// Prompts user with a string and returns a double of the first valid entry
		
		double retVal = 0;
		boolean valid;
		
		do {
			valid = true;
			System.out.print(prompt);
			try {
				retVal = Double.parseDouble(cin.readLine());
			}
			catch (NumberFormatException e) {
				System.out.print("ERROR: Number format exception!\n");
				valid = false;
			}
			catch (IOException e) {
				System.out.print("ERROR: IO exception!\n");
				valid = false;
			}
			if (valid && retVal < 0) {
				System.out.print("ERROR: Negative numbers are not allowed! Try again: \n");
				valid = false;
			}
			
		} while (!valid);
		
		return retVal;
	}
}