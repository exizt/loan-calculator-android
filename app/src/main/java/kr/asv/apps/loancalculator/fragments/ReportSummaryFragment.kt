package kr.asv.apps.loancalculator.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_report_summary.*

import java.math.BigDecimal

import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services


/**
 * ReportActivity 의 하위 프래그먼트
 * 리포트 결과 Summary 와 관련된 Fragment
 */
class ReportSummaryFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? =
			inflater.inflate(R.layout.fragment_report_summary, container, false)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		showResult()
	}

	@SuppressLint("SetTextI18n")
	private fun showResult() {
		val calculator = Services.instance.calculator
		summary_principal.text = calculator.options.principal.toString()
		summary_term.text = Integer.toString(calculator.options.amortizationPeriod)
		val interestRate = BigDecimal(calculator.options.interestRate)
		summary_interest_rate.text = interestRate.multiply(BigDecimal("100")).toString()
		summary_interest.text = calculator.summaryInterest.toString()
	}

	companion object {

		fun newInstance(): ReportSummaryFragment = ReportSummaryFragment()
	}
}
