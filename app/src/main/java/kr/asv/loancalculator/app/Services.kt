package kr.asv.loancalculator.app

import android.content.Context
import android.util.Log
import kr.asv.loancalculator.calculator.LoanCalculator

/**
 * 어플 전체적으로 활용되는 기능들을 모아두는 클래스
 */
object Services {
    val calculator = LoanCalculator()
    var calculatorMethod: CalculatorMethods = CalculatorMethods.EQUAL_PRINCIPAL

    enum class CalculatorMethods {
        EQUAL_PRINCIPAL, FULL_AMORTIZATION
    }

    init {
        init()
    }

    /**
     *
     */
    private fun init() {}

    /**
     *
     */
    @Suppress("unused", "UNUSED_PARAMETER")
    private fun load(context: Context) {

    }

    /**
     * 디버깅
     * @param msg string
     */
    @Suppress("unused")
    fun debug(msg: String) {
        Log.e("[EXIZT-DEBUG]", msg)
    }
}
