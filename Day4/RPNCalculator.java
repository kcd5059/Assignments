package ssa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class RPNCalculator {

	public static void main(String[] args) {

		// Create a new ArrayList of equations
		List<String[]> strArrayList = new ArrayList<>();
		strArrayList.add(new String[] { "10", "2", "/", "3", "+", "2", "*", "16", "-" });
		strArrayList.add(new String[] { "1", "2", "3", "+", "*" });
		strArrayList.add(new String[] { "100", "30", "/", "8", "+" });

		solveList(strArrayList);

		// Original test array when not calling getInput
		// String[] equation = new String[] {"10", "2", "/", "3", "+", "2", "*",
		// "16", "-"};

		// Get RPN equation to solve as input from user
		// String[] equation = getInput();
		// Pass equation into the function to be solved
		// solve(equation);

	}

	public static String[] getInput() {
		// Initialize the scanner object to catch input
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		// Prompt user for input
		System.out.print("Please type the RPN equation to be solved with spaces between " + "operands and operators: ");
		// Save input as String
		String input = scanner.nextLine();
		// Return a String[] of separate operators and operands by splitting on
		// spaces
		return input.split(" ");
	}

	public static void solveList(List<String[]> list) {

		// Pass each String[] into the solve function to calculate result
		for (String[] str : list) {
			solve(str);
		}
	}

	public static void solve(String[] equation) {

		Stack<Float> stack = new Stack<>();
		Float operand1;
		Float operand2;
		String originalEquation = "";
		DecimalFormat df = new DecimalFormat("#.##");

		for (String str : equation) {
			/*
			 * Check for operators to trigger operation and push result to stack
			 * then continue loop Else parse the Float and push it to the stack,
			 * then continue loop
			 */
			if (str.equals("+")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				stack.push(operand1 + operand2);
			} else if (str.equals("-")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				stack.push(operand1 - operand2);
			} else if (str.equals("*")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				stack.push(operand1 * operand2);
			} else if (str.equals("/")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				stack.push(operand1 / operand2);
			} else {
				stack.push(Float.parseFloat(str));
			}

		}
		// Build a new string of the original equation for output
		for (String str : equation) {
			originalEquation += str + " ";
		}
		// Print the result with proper formatting
		System.out.println("The result of " + "'" + originalEquation.trim() + "'" + " is " + df.format(stack.pop()));

	}

}
