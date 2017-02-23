package com.ooo.ops;

import java.math.BigDecimal;

public interface IOperations {
	
	public BigDecimal calAddition(String prevElem, String nextElem);
	public BigDecimal calSubtraction(String prevElem, String nextElem);
	public BigDecimal calMultiplication(String prevElem, String nextElem);
	public BigDecimal calDivision(int scale, String prevElem, String nextElem);
	public BigDecimal calPow(String prevElem, String nextElem);
	public BigDecimal calOR(String prevElem, String nextElem);

}
