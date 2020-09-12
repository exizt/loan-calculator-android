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
        options.principal = BigInteger.valueOf(4700000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(19.45) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL
        options.isDebug = true
        calculator.run()
    }
}