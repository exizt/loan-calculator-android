package kr.asv.loancalculator

import kr.asv.loancalculator.LoanCalculator.AmortizationMethods
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class LoanCalculatorOptions {
    /**
     * 원금
     */
    var principal: BigInteger = BigInteger.ZERO
        set(principal) {
            // 음수 방지
            field = if (principal > BigInteger.ONE) {
                principal
            } else {
                BigInteger.ONE
            }
        }

    /**
     * 이자율
     */
    @Suppress("RedundantGetter")
    var interestRate :BigDecimal = BigDecimal.ZERO
        set(interestRate) {
            // 음수 방지
            field = if (interestRate > BigDecimal.ZERO) {
                interestRate
            } else {
                BigDecimal.ZERO
            }
        }
        get() {
            //return CalcUtil.divide(interestRate,100, 6, RoundingMode.DOWN);
            return field
        }

    /**
     * 이자 절삭 자릿수. 0 은 원단위, 1은 십단위.
     */
    var interestDigits : Int = 0

    /**
     * 상환기간
     */
    var amortizationPeriod : Int = 0
        set(amortizationPeriod) {
            // 1 이하 방지.
            field = if (amortizationPeriod > 1) {
                amortizationPeriod
            } else {
                1
            }
        }

    /**
     * 상환방법
     */
    var amortizationMethod = AmortizationMethods.EQUAL_PRINCIPAL

    /**
     * 디버깅 여부
     */
    var isDebug = false

    /**
     * 이자율
     * 소수점 4자리 까지는 가능하게. (백분율로 처리하면서 소수점 6자리)
     * @return BigDecimal
     */
    val interestRate2: BigDecimal
        get() = CalcUtil.divide(interestRate, 100, 6, RoundingMode.DOWN)

    override fun toString(): String {
        return ("CalculatorOptions [principal=" + principal + ", interestRate=" + interestRate + ", amortizationPeriod="
                + amortizationPeriod + ", amortizationMethod=" + amortizationMethod + "]")
    }
}