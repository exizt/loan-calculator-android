package kr.asv.apps.loancalculator.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_full_amortization.*
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.activities.ReportActivity
import kr.asv.apps.loancalculator.Services
import kr.asv.loancalculator.LoanCalculator

class FullAmortizationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_full_amortization, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.menu_title_full_amortization)
        //setActionBarTitle(R.string.menu_title_full_amortization)

        // 계산하기 버튼 클릭시
        id_btn_calculate.setOnClickListener {
            calculate()// 계산하기 버튼 클릭시
        }
    }

    fun calculate() {
        val principal = java.lang.Double.parseDouble(id_input_principal.text.toString())
        val interestRate = java.lang.Double.parseDouble(id_input_interest_rate.text.toString())
        val amortizationPeriod = Integer.parseInt(id_input_term.text.toString())

        Services.getInstance().calculatorMethod = Services.CalculatorMethods.FULL_AMORTIZATION

        val calculator = Services.getInstance().calculator
        calculator.options.principal = principal
        calculator.options.interestRate = interestRate
        calculator.options.amortizationPeriod = amortizationPeriod
        calculator.options.amortizationMethod = LoanCalculator.AmortizationMethods.FULL_AMORTIZATION
        calculator.run()

        //결과 화면 호출
        val intent = Intent(activity, ReportActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @Suppress("unused")
        fun newInstance(param1: String, param2: String): FullAmortizationFragment {
            val fragment = FullAmortizationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
