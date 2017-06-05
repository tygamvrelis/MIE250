import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FizzBuzz {
	double fizz, buzz, start, end;
	boolean f_fizz, f_buzz; // Computation flags
	
	// Constructor
	FizzBuzz () {
		// Set the fields
		setFizz(project1.getNonnegDouble("Enter the first number (fizz): "));
		setBuzz(project1.getNonnegDouble("Enter the second number (buzz): "));
		setBounds("Enter the start and end numbers as a comma-separated pair: ");
	}
	
	// Set & Get methods for the fields
	void setFizz (double f) {
		this.fizz = f;
	}
	double getFizz () {
		return this.fizz;
	}
	
	void setBuzz (double b) {
		this.buzz = b;
	}
	double getBuzz () {
		return this.buzz;
	}
	
	void setStart (double b) {
		this.start = b;
	}
	double getStart () {
		return this.start;
	}
	
	void setEnd (double b) {
		this.end = b;
	}
	double getEnd () {
		return this.end;
	}
	
	// Method to retrieve the start and end values from the user
	void setBounds (String prompt) {
		double start = 0, end = 0;
		boolean valid;
		String line;
		Pattern pattern = Pattern.compile("\\d+[,]\\d+");
		
		do {
			valid = true;
			System.out.print(prompt);
			try {
				line = project1.cin.readLine().replaceAll("\\s+","");
				Matcher matcher = pattern.matcher(line);
				
				if (matcher.find()) {
					String[] splitString = line.split(",");
					try {
						start = Double.parseDouble(splitString[0]);
						end = Double.parseDouble(splitString[1]);
					}
					catch (NumberFormatException e) {
						System.out.print("ERROR: Number format exception!\n");
						valid = false;
					}
				}
				else {
					valid = false;
					System.out.print("ERROR: Wrong pattern entered!\n");
				}
			}
			catch (IOException e) {
				System.out.print("ERROR: IO exception!\n");
				valid = false;
			}
			
			if (valid) {
				if (end < start) {
					valid = false;
					System.out.print("ERROR: Ending number cannot be less than starting number! Try again: ");
				}
				else {
					setStart(start);
					setEnd(end);
				}
			}
			
		} while (!valid);
	}
	
	// Method to run the FIzzBuzz game
	void run () {
		for(double i = this.start; i < this.end + 1; i++){
			this.f_fizz = (i % this.fizz == 0);
			this.f_buzz = (i % this.buzz == 0);
			
			if (this.f_fizz && this.f_buzz) {
				System.out.println("FizzBuzz");
			}
			else if (this.f_fizz || this.f_buzz) {
				if (this.f_fizz) {
					System.out.println("Fizz");
				}
				else {
					System.out.println("Buzz");
				}	
			}
			else {
				System.out.println(i);
			}
		}
	}
}