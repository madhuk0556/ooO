package com.ooo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ooo.opsImpl.OperationsImpl;

public class TestOperations extends OperationsImpl {
	private static final String EMPTY = "";
	List<String> items;
	String item;

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the sequence of operation");
		String a = input.nextLine();
		TestOperations go = new TestOperations();
		a = go.parseInput(a);
		System.out.println("Result: " + a);
		input.close();
	}
	
	/**
	 * Parse the given input sequence
	 * @param sequence
	 * @return
	 * @throws Exception
	 */

	public String parseInput(String sequence) throws Exception{  
		items = new ArrayList<String>(); // capture all items
		item = EMPTY;
		for (int k = sequence.length() - 1; k >= 0; k--) {
			if (isDigit(sequence, k)) {
				handleOperands(sequence, k); // handle operands
			} else {
				handleOperators(sequence, k);
			}
		}
		// calculate based on the order
		items = processItemsData(items, "^", "|");  
		items = processItemsData(items, "*", "/");  
		items = processItemsData(items, "+", "-");  
		return items.size() > 0 ? items.get(0) : item;
	}

	private void handleOperands(String sequence, int k) {
		item = readCharAt(sequence, k) + item; 
		if (k == 0) { 
			recordNumber();
		}
	}

	private void handleOperators(String sequence, int k) {
		// record all operators
		if (readCharAt(sequence, k) == '.') {
			item = readCharAt(sequence, k) + item;
		} else if (readCharAt(sequence, k) == '-' && (k == 0 || (!isDigit(sequence, (k - 1))))) {
			item = readCharAt(sequence, k) + item; 
			recordNumber();  // record negative number for the first value
		} else { // rest of the operators
			recordNumber();  
			item += readCharAt(sequence, k);  
			recordNumber();  
		}
	}

	/**
	 * Insert evert new char at the start of the list
	 */
	public void recordNumber() {
		if (!item.equals(EMPTY)) {
			items.add(0, item); // add the item at first location of the list for the last read input
			item = EMPTY;
		}
	}
}