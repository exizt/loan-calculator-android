package kr.asv.apps.loancalculator.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_report_summary.*
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
        val calculator = Services.calculator
        val formatA = java.text.DecimalFormat("###,##0")

        // 원금
        summary_principal.text = formatA.format(calculator.options.principal)

        // 상환 기간
        summary_term.text = Integer.toString(calculator.options.amortizationPeriod)

        // 상환 이자율
        summary_interest_rate.text = String.format("%.2f %%",calculator.options.interestRate)
        //val interestRate = BigDecimal(calculator.options.interestRate.toString())
        //summary_interest_rate.text = interestRate.multiply(BigDecimal("100")).toString()

        // 상환 이자 금액
        summary_interest.text = formatA.format(calculator.summaryInterest)
    }

    companion object {

        fun newInstance(): ReportSummaryFragment = ReportSummaryFragment()
    }
}
