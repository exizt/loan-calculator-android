package kr.asv.loancalculator

import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class LoanCalculatorUnitTest {
    @Test
    fun equalPrincipalCalculatorTest() {
        val calculator = LoanCalculator()
        val options = calculator.options
        options.principal = BigInteger.valueOf(5000000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(18.00) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL
        //options.interestDigits = -1 //이자액 반올림 자릿수
        options.isDebug = true
        calculator.run()
    }

    @Test
    fun equalPrincipalCalculatorAdvancedTest() {
        val calculator = LoanCalculator()
        val options = calculator.options
        options.principal = BigInteger.valueOf(5000000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(19.85) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL
        //options.interestDigits = -1 //이자액 반올림 자릿수
        options.isDebug = true
        options.isIncludeDays = true // 날짜 연산.
        calculator.run()
    }

    @Test
    fun fullAmortizationTest() {
        val calculator = LoanCalculator()
        val options = calculator.options
        options.principal = BigInteger.valueOf(5800000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(17.55) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.FULL_AMORTIZATION
        options.isDebug = true
        calculator.run()
    }

    @Test
    fun fullAmortizationAdvancedTest() {
        val calculator = LoanCalculator()
        val options = calculator.options
        options.principal = BigInteger.valueOf(3000000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(19.08) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.FULL_AMORTIZATION
        options.isDebug = true
        options.isIncludeDays = true // 날짜 연산.
        calculator.run()
    }

    @Test
    fun dateCalculate(){

        val date = CalcUtil.getDate(2016,1,12)
        val s = Calendar.getInstance().run {
            this.time = date
            this.get(Calendar.MONTH)+1
        }

        println(s)

    }

    @Test
    fun mathCalculate(){
        val s = 123.615183.toBigDecimal()
        //val r = s.pow(12)
        //val r = s.setScale(6, RoundingMode.HALF_EVEN)
        //r.setScale(6, RoundingMode.HALF_EVEN)
        val r = CalcUtil.round(s, 2, RoundingMode.HALF_EVEN)
        println(r)
    }

    /**
     * 월 납부금 = 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간 ÷((1 + 이자율 ÷ 12)^기간 -1)
     */
    @Test
    fun calculateFullAmortizationPaymentTest(){
        val options = LoanCalculatorOptions()
        options.principal = BigInteger.valueOf(3000000) //원금
        options.amortizationPeriod = 36 //상환기간
        options.interestRate = BigDecimal.valueOf(18.22) //이자율
        options.amortizationMethod = LoanCalculator.AmortizationMethods.FULL_AMORTIZATION
        options.isDebug = true

        val fullAmortization = FullAmortization()
        val r = fullAmortization.testPaymentMonthly(options)

        if(r==108788.toBigInteger()){
            println("success")
        }
        println(r)
    }
}