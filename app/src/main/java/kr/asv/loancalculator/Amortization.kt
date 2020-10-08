package kr.asv.loancalculator

import java.math.BigInteger

internal interface Amortization {
    fun calculate(options: LoanCalculatorOptions)
    fun calculate()
    val schedules: PaymentSchedules
    val summaryInterest: BigInteger?
}