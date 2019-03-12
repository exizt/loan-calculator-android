package kr.asv.apps.loancalculator

import android.content.Context
import android.util.Log

import kr.asv.loancalculator.LoanCalculator

/**
 * 어플 전체적으로 활용되는 기능들을 모아두는 클래스
 * Created by EXIZT on 2016-04-27.
 */
object Services {
	val calculator = LoanCalculator()
	var calculatorMethod: CalculatorMethods? = null

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
	@Suppress("unused")
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
