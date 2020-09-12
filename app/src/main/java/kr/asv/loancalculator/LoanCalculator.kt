package kr.asv.loancalculator

import java.math.BigInteger

/**
 * 대출이자 계산기 클래스
 */
class LoanCalculator {
    /**
     * Options 값들. 가족수, 자녀수, 비과세액 등
     */
    val options = LoanCalculatorOptions()

    enum class AmortizationMethods {
        FULL_AMORTIZATION, EQUAL_PRINCIPAL
    }

    var schedules: PaymentSchedules? = null
        private set

    var summaryInterest: BigInteger? = null
        private set

    /**
     * 계산 실행
     */
    fun run() {
        if (options.isDebug) {
            debug(options)
        }
        //schedules = PaymentSchedules()
        schedules?.clear() // 계산 전에 남아있는 것이 있다면 clear


        val amortization: Amortization = if (options.amortizationMethod == AmortizationMethods.EQUAL_PRINCIPAL) {
            EqualPrincipalAmortization()
            //schedules = equalPrincipalAmortization.calculate(options);// 원금 균등 분할 상환
        } else {
            FullAmortization()
            //schedules = fullAmortization.calculate(options);// 원리금 균등 분할 상환
        }
        amortization.calculate(options)

        // 결과
        schedules = amortization.schedules
        summaryInterest = amortization.summaryInterest
    }

    private fun debug(obj: Any) {
        println(obj)
    }
}