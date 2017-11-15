package kr.asv.apps.loancalculator.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_equal_principal.*
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.activities.ReportActivity
import kr.asv.apps.loancalculator.Services
import kr.asv.loancalculator.LoanCalculator


class EqualPrincipalFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_equal_principal, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.menu_title_equal_principal)
        //setActionBarTitle()


        // 계산하기 버튼 클릭시
        id_btn_calculate.setOnClickListener{
            calculate()// 계산하기 버튼 클릭시
        }
    }

    /**
     * 계산 기능 호출
     */
    private fun calculate() {
        val principal = if(id_input_principal.text.toString() == ""){
            0.0
        } else {
            java.lang.Double.parseDouble(id_input_principal.text.toString())
        }
        val interestRate = if(id_input_interest_rate.text.toString() == ""){
            0.0
        } else {
            java.lang.Double.parseDouble(id_input_interest_rate.text.toString())
        }
        val amortizationPeriod = if(id_input_term.text.toString() == ""){
            0
        } else {
            Integer.parseInt(id_input_term.text.toString())
        }

        Services.getInstance().calculatorMethod = Services.CalculatorMethods.EQUAL_PRINCIPAL

        val calculator = Services.getInstance().calculator
        calculator.options.principal = principal
        calculator.options.interestRate = interestRate
        calculator.options.amortizationPeriod = amortizationPeriod
        calculator.options.amortizationMethod = LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL
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
