package kr.asv.apps.loancalculator.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_equal_principal.*
import kr.asv.androidutils.InputFilterDoubleMinMax
import kr.asv.androidutils.MoneyTextWatcher
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services
import kr.asv.apps.loancalculator.activities.ReportActivity
import kr.asv.loancalculator.LoanCalculator

class EqualPrincipalFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? =
			inflater.inflate(R.layout.fragment_equal_principal, container, false)


	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		//(activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.menu_title_equal_principal)

		val title : String
		val subtitle : String
		if(Services.calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL){
			title = getString(R.string.menu_title_equal_principal)
			subtitle = getString(R.string.calculator_title_equal_principal)
		} else {
			title = getString(R.string.menu_title_full_amortization)
			subtitle = getString(R.string.calculator_title_full_amortization)
		}
		(activity as AppCompatActivity).supportActionBar!!.title = title
		textView_subtitle.text = subtitle

		initEventListener()

	}

	private fun initEventListener(){

		// 계산하기 버튼 클릭시
		id_btn_calculate.setOnClickListener {
			calculate()// 계산하기 버튼 클릭시
		}

		id_input_principal.addTextChangedListener(MoneyTextWatcher(id_input_principal))
		//id_input_principal.filters = arrayOf<InputFilter>(InputFilterDoubleMinMax(0.0,10000000000.0))
		id_input_term.filters  = arrayOf<InputFilter>(InputFilterDoubleMinMax(0,1200))
		id_input_interest_rate.filters = arrayOf<InputFilter>(InputFilterDoubleMinMax(0.0,100.0))
	}
	/**
	 * 계산 기능 호출
	 */
	private fun calculate() {
		/*
		val principal = if (id_input_principal.text.toString() == "") {
			0.0
		} else {
			java.lang.Double.parseDouble(id_input_principal.text.toString())
		}
		*/
		val principal = MoneyTextWatcher.getValue(id_input_principal).toDouble()

		val interestRate = if (id_input_interest_rate.text.toString() == "") {
			0.0
		} else {
			java.lang.Double.parseDouble(id_input_interest_rate.text.toString())
		}
		val amortizationPeriod = if (id_input_term.text.toString() == "") {
			0
		} else {
			Integer.parseInt(id_input_term.text.toString())
		}

		val calculator = Services.calculator
		if(Services.calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL){
			calculator.options.amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL
		} else {
			calculator.options.amortizationMethod = LoanCalculator.AmortizationMethods.FULL_AMORTIZATION
		}
		calculator.options.principal = principal
		calculator.options.interestRate = interestRate
		calculator.options.amortizationPeriod = amortizationPeriod
		calculator.run()

		//결과 화면 호출
		val intent = Intent(activity, ReportActivity::class.java)
		startActivity(intent)
	}

	companion object {
		@Suppress("unused", "UNUSED_PARAMETER")
		fun newInstance(param1: String, param2: String): EqualPrincipalFragment {
			val fragment = EqualPrincipalFragment()
			val args = Bundle()
			fragment.arguments = args
			return fragment
		}
	}
}
