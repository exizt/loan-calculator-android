package kr.asv.loancalculator;

/**
 * 진입점
 */
public class Simulator
{
	public static void main(String[] args)
	{
		LoanCalculator calculator = new LoanCalculator();
		
		LoanCalculatorOptions options = calculator.getOptions();
		options.setPrincipal(4700000.0);//원금
		options.setAmortizationPeriod(36);//상환기간
		options.setInterestRate(19.45);//이자율
		options.setAmortizationMethod(LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL);
		options.setDebug(true);
		
		calculator.run();
	}
}
