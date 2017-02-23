package com.ooo.opsImpl;

import java.math.BigDecimal;
import java.util.List;

import com.ooo.ops.IOperations;

public class OperationsImpl implements IOperations {

	protected boolean hasPow(List<String> contents, int counter) {
		return contents.get(counter).equalsIgnoreCase("^");
	}

	protected boolean hasSubtract(List<String> contents, int counter) {
		return contents.get(counter).equals("-");
	}

	protected boolean hasAddition(List<String> contents, int counter) {
		return contents.get(counter).equals("+");
	}

	protected boolean hasDivision(List<String> contents, int counter) {
		return contents.get(counter).equals("/");
	}

	protected boolean hasMultiplier(List<String> contents, int counter) {
		return contents.get(counter).equals("*");
	}

	protected boolean hasOr(List<String> contents, int counter) {
		return contents.get(counter).equals("|");
	}

	protected boolean isDigit(String sequence, int k) {
		return Character.isDigit(readCharAt(sequence, k));
	}

	protected char readCharAt(String sequence, int k) {
		return sequence.charAt(k);
	}

	protected boolean isDigit(String prevElem) {
		return Character.isDigit(prevElem.charAt(0));
	}

	protected List<String> processItemsData(List<String> contents, String operand1, String operand2) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		for (int counter = 0; counter < contents.size(); counter++) {
			String oItem = contents.get(counter);
			if (hasAnyOperand(operand1, operand2, oItem)) {
				result = calculateValue(contents, 10, result, counter);
				prepareResult(contents, result, counter);
			} else {
				continue;
			}
			counter = 0; // reset counter
		}
		return contents;
	}

	private BigDecimal calculateValue(List<String> contents, int scale, BigDecimal result, int idx) {

		String prevElem = contents.get(idx - 1);
		String nextElem = contents.get(idx + 1);

		sanityCheckForDigit(prevElem, nextElem);

		if (hasPow(contents, idx)) {
			result = calPow(prevElem, nextElem);
		}

		if (hasOr(contents, idx)) {
			result = calOR(nextElem, prevElem);
		}

		if (hasMultiplier(contents, idx)) {
			result = calMultiplication(prevElem, nextElem);
		}

		if (hasDivision(contents, idx)) {
			result = calDivision(scale, prevElem, nextElem);
		}

		if (hasAddition(contents, idx)) {
			result = calAddition(prevElem, nextElem);
		}

		if (hasSubtract(contents, idx)) {
			result = calSubtraction(prevElem, nextElem);
		}
		return result;
	}

	private void prepareResult(List<String> contents, BigDecimal result, int index) throws Exception {
		try {
			contents.set(index, result.toPlainString());
			// remove the previous and next values in the list
			contents.remove(index + 1);
			contents.remove(index - 1);
		} catch (Exception e) {
			throw new Exception("Exception occured in processing the contents", e);
		}
	}

	protected void sanityCheckForDigit(String prevElem, String nextElem) {
		if (!isDigit(prevElem) || !isDigit(nextElem)) {
			System.out.println("Invalid input given.");
			System.exit(0);
		}
	}

	protected boolean hasAnyOperand(String operand1, String operand2, String oItem) {
		return oItem.equals(operand1) || oItem.equals(operand2);
	}

	@Override
	public BigDecimal calAddition(String prevElem, String nextElem) {
		return new BigDecimal(prevElem).add(new BigDecimal(nextElem));
	}

	@Override
	public BigDecimal calSubtraction(String prevElem, String nextElem) {
		return new BigDecimal(prevElem).subtract(new BigDecimal(nextElem));
	}

	@Override
	public BigDecimal calMultiplication(String prevElem, String nextElem) {
		return new BigDecimal(prevElem).multiply(new BigDecimal(nextElem));
	}

	@Override
	public BigDecimal calDivision(int scale, String prevElem, String nextElem) {
		return new BigDecimal(prevElem).divide(new BigDecimal(nextElem), 10, BigDecimal.ROUND_DOWN);
	}

	@Override
	public BigDecimal calPow(String prevElem, String nextElem) {
		return new BigDecimal(prevElem).pow(Integer.parseInt(nextElem));
	}

	@Override
	public BigDecimal calOR(String prevElem, String nextElem) {
		return new BigDecimal(Math.sqrt(Double.parseDouble(nextElem)));
	}

}