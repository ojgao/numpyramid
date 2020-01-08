import java.util.*;
//Owen Gao
/* A number pyramid is a sequence of lines that count the number of times a number appears. 
For example: if input was 14

		start 14
		one 1, one 4  11,14
		three 1s, one 4  31,14
		two 1s, one 3, one 4  21,13,14
		three 1s, one 2, one 3, one 4  31,12,13,14
		four 1s, one 2, two 3s, one 4  41,12,23,14
		and so on

Take in a number input up to ten digits long that may include leading zeros, 
should be robust against bogus user input such as a negative number, 
length greater than 10 digits, and/or non-numerical input. 
*/
public class NumberPyramids{
	public static void main(String[]args){
		Scanner console= new Scanner(System.in);
		String again;
		List<String> previousInput = new ArrayList<>(); 
		List<String> previousPattern = new ArrayList<>();
		do{ //have to run through program at least once    
			System.out.print("Enter an integer <= 10 digits long: ");
			String input = console b.next(); //taking input as string to verify as long and to take into account for leading zeros
			if(!cont(input)){ //if input is not valid repeat 
				System.out.println("Invalid input. Please enter valid input.");
				input = console.next();
				while(cont(input) == false){ //keep repeating until input is valid
					System.out.println("Invalid input. Please enter valid input.");
					input = console.next();
				}
			}
			String firstPatternOccurrence = checkRepeat(input);
			if(firstPatternOccurrence == "") {
				System.out.println("No repeating pattern detected up to index 100.");
			} else {
				for(int i = 0; i < previousPattern.size(); i++) {
					if(firstPatternOccurrence.equals(previousPattern.get(i))) {
						System.out.println("PATTERN RECOGNIZED: This pattern is the same as the pattern produced by the input: " + previousInput.get(i));
					}
				}
				previousInput.add(input);
				previousPattern.add(firstPatternOccurrence);
			}
			System.out.println("Again? ('yes' or 'no')");
			again = console.next();
		}
		while(again.equals("yes")); // if user chooses yes, repeat until user chooses no
		System.out.println("Exiting."); 
	}
	//method to check validity of input
	public static boolean cont(String x){
		if(x.length() > 10) { //input can not be greater than ten digits long
			return false;
		}
		try {
			long n = Long.parseLong(x); // must parse to long since interger is around 2 billion and higest input is 999999999
			if(n < 0) { //input can not take negative numbers
				return false;
		   	}
		   	return true; //if input parses to long and is postive can continue

		} catch(NumberFormatException e) { //input is not a number
		 	return false;
		}
	}

	public static String checkRepeat(String input) { //checking for repeating in the sequence
		String pattern[] = new String[100]; //no more searching after 100 lines of no repeating so array size of 100
		int counter = 0; //start to check pattern at index 0
		pattern[0] = input; //first row is input
		System.out.println("0. " + pattern[0]); //print input at zero index
		while(counter < 100) { 
			String current = digitCount(pattern[counter]); //creating new lines for checking
			counter++; // increment counter to check all index
			if(checkInArray(current, pattern, counter) != -1) { // if line is a repeat, continue
				int index = checkInArray(current, pattern, counter); //index equals index of first repeating line
				for(int i = 0; i < counter - index; i++) { //printing lines of number pyramid
					int addIndex = counter + i; //index of number pyramid is 1+ index of String array
					pattern[addIndex] = pattern[index + i]; // line where first repeats is the same as line above
					System.out.print(addIndex + ". "); //printing index of number pyramid
					printString(pattern[addIndex]); // goes to method to print String of number pyramid
					System.out.println();
				}
				System.out.println("Repeating pattern detected starting at index " + index + "."); //identifies repeating and where it stats
				int length = counter - index; //indicates how many lines repeate is 
				System.out.println("The repeating pattern is " + length + " step(s) long.");
				return current; //return string at repeating line
			}
			pattern[counter] = current; //String array at index is repeating string
			System.out.print(counter + ". ");
			printString(current); //
			System.out.println();
		}
		return ""; //if no contitions match, return empty string
	}

	public static void printString(String s) {
		int index = 0;
		while(index < s.length()) {
			System.out.print(s.charAt(index));
			index++;
			int num = s.charAt(index) - '0'; 
			if(num == 0 && index != s.length() - 1) {
				System.out.print(s.charAt(index));
				index++;
			}
			System.out.print(s.charAt(index));
			if(index != s.length() - 1) {
				System.out.print(", ");
			}
			index++;
		}
	}

	public static String digitCount(String s) {
		// have a array to store the # of times inter appears index 0 is "1" and index 9 is'"0"
		int digitCount[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 
		for(int i = 0; i < s.length(); i++) { // for loop to find the number at index of string
			int digit = s.charAt(i) - '0'; 
			if(digit != 0) {
				digitCount[digit - 1]++; // storing in array 
			} else {
				digitCount[9]++; // storing zeros in array 
			}
		}
		String res = ""; //creating next line of number pyramid
		for(int i = 0; i < 9; i++) { 
			if(digitCount[i] > 0) {
				res += digitCount[i]; // digitCount[i] is the number of times integer appears
				res += i+1; //index+1 is the integer acounted for
			}
		}
		if(digitCount[9] > 0) { //account for zeros in the end
			res += digitCount[9]; //add num number of zeros that appear in string
			res += "0";
		}
		return res; //return the new string
	}
	// checking for repeating line already present
	public static int checkInArray(String s, String[] arr, int counter) {
		for(int i = 0; i < counter; i++) {
			if(s.equals(arr[i])) { 
				return i; //if string is present in array return index of first repeating line
			}
		}
		return -1; //if not present return invalid array index
	}
}