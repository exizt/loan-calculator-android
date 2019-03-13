package kr.asv.loancalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class LoanCalculatorOptions
{
	/**
	 * 원금
	 */
	private BigInteger principal = BigInteger.ZERO;

	/**
	 * 이자율
	 */
	private BigDecimal interestRate = BigDecimal.ZERO;

	/**
	 * 상환기간
	 */
	private int amortizationPeriod = 0;

	/**
	 * 상환방법
	 */
	private LoanCalculator.AmortizationMethods amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL;

	/**
	 * 디버깅 여부
	 */
	private boolean debug = false;

	/**
	 * 생성자
	 */
	LoanCalculatorOptions(){
	}

	/**
	 * 원금
	 * @return BigInteger
	 */
	public BigInteger getPrincipal()
	{
		return principal;
	}

	/**
	 * 원금
	 * 0 보다 작은 값이 들어오면 0 으로 치환. (0보다 작은 값은 절대적으로 계산하지 않도록 함)
	 * @param principal BigInteger
	 */
	public void setPrincipal(BigInteger principal)
	{
		if(principal.compareTo(BigInteger.ONE) > 0){
			this.principal = principal;
		} else {
			this.principal = BigInteger.ONE;
		}
	}

	/**
	 * 이자율
	 * @return BigDecimal
	 */
	public BigDecimal getInterestRate()
	{
		//return CalcUtil.divide(interestRate,100, 6, RoundingMode.DOWN);
        return interestRate;
	}

    /**
     * 이자율
     * 소수점 4자리 까지는 가능하게. (백분율로 처리하면서 소수점 6자리)
     * @return BigDecimal
     */
	public BigDecimal getInterestRate2(){
        return CalcUtil.divide(interestRate,100, 6, RoundingMode.DOWN);
    }

	/**
	 * 이자율
	 * 기본적으로 음수는 방지한다.
	 * @param interestRate BigDecimal
	 */
	public void setInterestRate(BigDecimal interestRate)
	{
		if(interestRate.compareTo(BigDecimal.ZERO) > 0){
			this.interestRate = interestRate;
		} else {
			this.interestRate = BigDecimal.ZERO;
		}
	}

	public int getAmortizationPeriod()
	{
		return amortizationPeriod;
	}

	/**
	 * 상환 기간
	 * 기본적으로 음수값은 방지함.
	 * @param amortizationPeriod int
	 */
	public void setAmortizationPeriod(int amortizationPeriod)
	{
		if(amortizationPeriod > 1){
			this.amortizationPeriod = amortizationPeriod;
		} else {
			this.amortizationPeriod = 1;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public LoanCalculator.AmortizationMethods getAmortizationMethod()
	{
		return amortizationMethod;
	}

	@SuppressWarnings("SameParameterValue")
	public void setAmortizationMethod(LoanCalculator.AmortizationMethods amortizationMethod)
	{
		this.amortizationMethod = amortizationMethod;
	}

	boolean isDebug()
	{
		return debug;
	}

	@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	@Override
	public String toString()
	{
		return "CalculatorOptions [principal=" + principal + ", interestRate=" + interestRate + ", amortizationPeriod="
				+ amortizationPeriod + ", amortizationMethod=" + amortizationMethod + "]";
	}
}
