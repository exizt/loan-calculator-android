package kr.asv.loancalculator

import java.math.BigInteger

internal interface Amortization {
    fun calculate(options: LoanCalculatorOptions)
    val schedules: PaymentSchedules
    val summaryInterest: BigInteger?
}