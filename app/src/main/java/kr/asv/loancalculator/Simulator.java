package kr.asv.loancalculator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 진입점
 */
class Simulator
{
	public static void main(String[] args)
	{
		LoanCalculator calculator = new LoanCalculator();
		
		LoanCalculatorOptions options = calculator.getOptions();
		options.setPrincipal(BigInteger.valueOf(4700000));//원금
		options.setAmortizationPeriod(36);//상환기간
		options.setInterestRate(BigDecimal.valueOf(19.45));//이자율
		options.setAmortizationMethod(LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL);
		options.setDebug(true);
		
		calculator.run();
	}
}
