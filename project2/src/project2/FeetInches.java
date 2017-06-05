package project2;

public class FeetInches {
	static final double IN_TO_CM = 2.54;
	private double _feet, _inches;
	
	public FeetInches(double feet, double inches) {
		_feet = feet;
		_inches = inches;
	}
	
	public double getFeet() { return _feet; }
	public double getInches() {return _inches; }
	
	public String toString() {
		StringBuilder result = new StringBuilder();
	    String NEW_LINE = System.getProperty("line.separator");

	    result.append(this.getClass().getName() + " Object {" + NEW_LINE);
	    result.append(" Feet: " + _feet + NEW_LINE);
	    result.append(" Inches: " + _inches + NEW_LINE);
	    result.append("}");

	    return result.toString();
	}
	
	public double convertToCm() {
		return (_feet * 12 + _inches) * IN_TO_CM;
	}
	
	public static FeetInches convertToIn(double cm) {
		double feet = 0, inches = 0;
		
		inches = cm / IN_TO_CM;
		while (inches - 12 >= 0) {
			feet++;
			inches = inches - 12;
		}
		
		FeetInches fn = new FeetInches(feet, inches);
		return fn;
	}
}
