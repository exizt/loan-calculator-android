package kr.asv.loancalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class LoanCalculatorOptions
{
	/**
	 * 원금
	 */
	private BigInteger principal = new BigInteger("0");

	/**
	 * 이자율
	 */
	private BigDecimal interestRate = new BigDecimal("0");

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

	public BigInteger getPrincipal()
	{
		return principal;
	}

	@SuppressWarnings("SameParameterValue")
	public void setPrincipal(BigInteger principal)
	{
		this.principal = principal;
	}

	/**
	 * 소수점 4자리 까지는 가능하게. (백분율로 처리하면서 소수점 6자리)
	 * @return BigDecimal
	 */
	public BigDecimal getInterestRate()
	{
		return CalcUtil.divide(interestRate,100, 6, RoundingMode.DOWN);
	}

	@SuppressWarnings("SameParameterValue")
	public void setInterestRate(BigDecimal interestRate)
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
