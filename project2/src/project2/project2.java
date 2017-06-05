package project2;
import java.io.*;

public class project2 {
	static final double double_LB = -Double.MAX_VALUE;
	static final double double_UB = Double.MAX_VALUE;
	static final int int_LB = -Integer.MAX_VALUE;
	static final int int_UB = Integer.MAX_VALUE;
	
	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	public static String[] _prevConversions = new String[1];
	public static int _numConversions = 0;
	
	
	public static void main(String[] args) {
		int selection = 0;
		boolean valid, quit = false;
		
		do {
			valid = true;
			displayMenu();
			try {
				selection = Integer.parseInt(cin.readLine());
			}
			catch (NumberFormatException e) {
				System.out.print("ERROR: Number format exception!\n");
				valid = false;
			}
			catch (IOException e) {
				System.out.print("ERROR: IO exception!\n");
				valid = false;
			}
			
			if (valid) {
				switch (selection) {
				case 0 : quit = true;
					break;
				case 1: addConversion(feetInchesToCm());
					System.out.println("\n" + _prevConversions[_numConversions - 1].toString() + "\n");
					break;
				case 2: addConversion(cmToFeetInches());
					System.out.println("\n" + _prevConversions[_numConversions - 1].toString() + "\n");
					break;
				case 3: System.out.println();
					for (String s : _prevConversions) { System.out.println(s); }
					System.out.println();
					break;
				}
			}
		} while (!quit);
	}

	public static void displayMenu() {
		System.out.println("0 - Quit");
		System.out.println("1 - Convert feet and inches to centimeters");
		System.out.println("2 - Convert centimeters to feet and inches");
		System.out.println("3 - Print previous conversions");
	}
	
	public static String feetInchesToCm() {
		int feet;
		double inches;
		
		feet = getInteger("Please enter the number of feet: \n", int_LB, int_UB);
		inches = getDouble("Please enter the number of inches: \n", double_LB, double_UB);
		
		FeetInches fn = new FeetInches(feet, inches);
		return Double.toString(fn.convertToCm()) + " cm";
	}
	
	public static String cmToFeetInches() {
		double feet;
		double cm, inches;
		
		cm = getDouble("Please enter the number of centimeters: \n", double_LB, double_UB);
		
		FeetInches conversion = FeetInches.convertToIn(cm);
		feet = conversion.getFeet();
		inches = conversion.getInches();
		return Double.toString(feet) + " feet, " + Double.toString(inches) + " inches";
	}
	
	 public static int getInteger(String prompt, int LB, int UB) {
		 int retVal = 0;
		 boolean valid, f_LB, f_UB;
		 
		 do {
			 valid = true;
			 System.out.print(prompt);
				try {
					retVal = Integer.parseInt(cin.readLine());
				}
				catch (NumberFormatException e) {
					System.out.print("ERROR: Number format exception!\n");
					valid = false;
				}
				catch (IOException e) {
					System.out.print("ERROR: IO exception!\n");
					valid = false;
				}
				if (valid && ((retVal < LB) || (retVal > UB))) {
					f_LB = (LB == -Integer.MAX_VALUE);
					f_UB = (UB == Integer.MAX_VALUE);
					
					if (f_LB && f_UB) {
						System.out.printf("ERROR: Input must be an integer in [-infinity, infinity]");
					}
					else if (f_LB) {
						System.out.printf("ERROR: Input must be an integer in [-infinity, %d]!", UB);
					}
					else if (f_UB) {
						System.out.printf("ERROR: Input must be an integer in [%d, infinity]!", LB);
					}
					else {
						System.out.printf("ERROR: Input must be an integer in [%d, %d]!", LB, UB);
					}

					valid = false;
				}
		 } while (!valid);
		 
		 return retVal;
	 }
	 
	 public static double getDouble(String prompt, double LB, double UB) {
		 double retVal = 0;
		 boolean valid, f_LB, f_UB;
		 
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
				if (valid && ((retVal < LB) || (retVal > UB))) {
					f_LB = (LB == -Double.MAX_VALUE);
					f_UB = (UB == Double.MAX_VALUE);
					
					if (f_LB && f_UB) {
						System.out.printf("ERROR: Input must be a real number in [-infinity, infinity]");
					}
					else if (f_LB) {
						System.out.printf("ERROR: Input must be a real number in [-infinity, %d]!", UB);
					}
					else if (f_UB) {
						System.out.printf("ERROR: Input must be a real number in [%d, infinity]!", LB);
					}
					else {
						System.out.printf("ERROR: Input must be a real number in [%d, %d]!", LB, UB);
					}

					valid = false;
				}
		 } while (!valid);
		 
		 return retVal;
	 }
	 
	 public static void addConversion(String s) {
	 /* Adds the String representing the output of the current conversion to the
	 prevConversions array and increments numConversions */
		 
		 if (_prevConversions.length > _numConversions) {
			 _prevConversions[_numConversions] = s;
		 }
		 else {
			 String[] temp = new String[_prevConversions.length * 2];
			 for (int i = 0; i < _prevConversions.length; i++) {
				 temp[i] = _prevConversions[i];
			 }
			 temp[_prevConversions.length] = s;
			 _prevConversions = temp;
		 }
		 
		 _numConversions++;
	 }
}