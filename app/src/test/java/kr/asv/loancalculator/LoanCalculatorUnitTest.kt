package kr.asv.loancalculator

import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class LoanCalculatorUnitTest {
    @Test
    fun simulation() {
        val calculator = LoanCalculator()
        val options = calculator.options
        options.principal = BigInteger.valueOf(10000000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(5.00) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL
        options.interestDigits = -1 //이자액 반올림 자릿수
        options.isDebug = true
        calculator.run()
    }

    @Test
    fun fullAmortizationTest() {
        val calculator = LoanCalculator()
        val options = calculator.options
        options.principal = BigInteger.valueOf(3000000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(18.22) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.FULL_AMORTIZATION
        options.isDebug = true
        calculator.run()
    }
}