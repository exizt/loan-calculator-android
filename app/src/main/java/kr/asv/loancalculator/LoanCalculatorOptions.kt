package kr.asv.loancalculator

import kr.asv.loancalculator.LoanCalculator.AmortizationMethods
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class LoanCalculatorOptions
/**
 * 생성자
 */
internal constructor() {
    /**
     * 원금
     */
    private var principal = BigInteger.ZERO

    /**
     * 이자율
     */
    private var interestRate = BigDecimal.ZERO

    /**
     * 상환기간
     */
    private var amortizationPeriod = 0

    /**
     * 상환방법
     */
    var amortizationMethod = AmortizationMethods.EQUAL_PRINCIPAL

    /**
     * 디버깅 여부
     */
    var isDebug = false

    /**
     * 원금
     * @return BigInteger
     */
    fun getPrincipal(): BigInteger {
        return principal
    }

    /**
     * 원금
     * 0 보다 작은 값이 들어오면 0 으로 치환. (0보다 작은 값은 절대적으로 계산하지 않도록 함)
     * @param principal BigInteger
     */
    fun setPrincipal(principal: BigInteger) {
        if (principal.compareTo(BigInteger.ONE) > 0) {
            this.principal = principal
        } else {
            this.principal = BigInteger.ONE
        }
    }

    /**
     * 이자율
     * @return BigDecimal
     */
    fun getInterestRate(): BigDecimal {
        //return CalcUtil.divide(interestRate,100, 6, RoundingMode.DOWN);
        return interestRate
    }

    /**
     * 이자율
     * 소수점 4자리 까지는 가능하게. (백분율로 처리하면서 소수점 6자리)
     * @return BigDecimal
     */
    val interestRate2: BigDecimal?
        get() = CalcUtil.divide(interestRate, 100, 6, RoundingMode.DOWN)

    /**
     * 이자율
     * 기본적으로 음수는 방지한다.
     * @param interestRate BigDecimal
     */
    fun setInterestRate(interestRate: BigDecimal) {
        if (interestRate.compareTo(BigDecimal.ZERO) > 0) {
            this.interestRate = interestRate
        } else {
            this.interestRate = BigDecimal.ZERO
        }
    }

    fun getAmortizationPeriod(): Int {
        return amortizationPeriod
    }

    /**
     * 상환 기간
     * 기본적으로 음수값은 방지함.
     * @param amortizationPeriod int
     */
    fun setAmortizationPeriod(amortizationPeriod: Int) {
        if (amortizationPeriod > 1) {
            this.amortizationPeriod = amortizationPeriod
        } else {
            this.amortizationPeriod = 1
        }
    }

    override fun toString(): String {
        return ("CalculatorOptions [principal=" + principal + ", interestRate=" + interestRate + ", amortizationPeriod="
                + amortizationPeriod + ", amortizationMethod=" + amortizationMethod + "]")
    }
}