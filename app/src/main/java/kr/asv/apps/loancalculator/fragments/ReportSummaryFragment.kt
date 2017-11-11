package kr.asv.apps.loancalculator.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.math.BigDecimal

import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services
import kr.asv.calculators.loan.LoanCalculator


class ReportSummaryFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_report_summary, container, false)
        setFragmentView(view)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showResult()
    }

    private fun showResult() {
        val calculator = Services.getInstance().calculator

        val summary_principal = findViewById(R.id.summary_principal) as TextView
        summary_principal.text = java.lang.Double.toString(calculator.options.principal)

        val summary_term = findViewById(R.id.summary_term) as TextView
        summary_term.text = Integer.toString(calculator.options.amortizationPeriod)

        val summary_interest_rate = findViewById(R.id.summary_interest_rate) as TextView

        val interestRate = BigDecimal(java.lang.Double.toString(calculator.options.interestRate))
        summary_interest_rate.text = interestRate.multiply(BigDecimal("100")).toString()

        val summary_interest = findViewById(R.id.summary_interest) as TextView
        summary_interest.text = java.lang.Double.toString(calculator.summaryInterest)

    }

    companion object {

        fun newInstance(): ReportSummaryFragment {
            return ReportSummaryFragment()
        }
    }
}
