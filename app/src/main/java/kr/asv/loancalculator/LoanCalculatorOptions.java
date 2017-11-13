package kr.asv.loancalculator;

public class LoanCalculatorOptions
{
	/**
	 * 원금
	 */
	private double principal = 0;
	/**
	 * 이자율
	 */
	private double interestRate = 0;
	/**
	 * 상환기간
	 */
	private int amortizationPeriod = 0;
	/**
	 * 상환방법
	 */
	private LoanCalculator.AmortizationMethods amortizationMethod;
	/**
	 * 디버깅 여부
	 */
	private boolean debug = false;


	/**
	 * 생성자
	 */
	public LoanCalculatorOptions(){
	}

	@Override
	public String toString()
	{
		return "CalculatorOptions [principal=" + principal + ", interestRate=" + interestRate + ", amortizationPeriod="
				+ amortizationPeriod + ", amortizationMethod=" + amortizationMethod + "]";
	}
	public double getPrincipal()
	{
		return principal;
	}
	@SuppressWarnings("SameParameterValue")
	public void setPrincipal(double principal)
	{
		this.principal = principal;
	}
	public double getInterestRate()
	{
		return interestRate * 0.01;
	}
	@SuppressWarnings("SameParameterValue")
	public void setInterestRate(double interestRate)
	{
		this.interestRate = interestRate;
	}
	public int getAmortizationPeriod()
	{
		return amortizationPeriod;
	}
	@SuppressWarnings("SameParameterValue")
	public void setAmortizationPeriod(int amortizationPeriod)
	{
		this.amortizationPeriod = amortizationPeriod;
	}

	public LoanCalculator.AmortizationMethods getAmortizationMethod()
	{
		return amortizationMethod;
	}

	@SuppressWarnings("SameParameterValue")
	public void setAmortizationMethod(LoanCalculator.AmortizationMethods amortizationMethod)
	{
		this.amortizationMethod = amortizationMethod;
	}

	public boolean isDebug()
	{
		return debug;
	}

	@SuppressWarnings("SameParameterValue")
	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	
}
