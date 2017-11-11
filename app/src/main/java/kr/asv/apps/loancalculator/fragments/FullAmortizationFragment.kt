package kr.asv.apps.loancalculator.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.ReportActivity
import kr.asv.apps.loancalculator.Services
import kr.asv.calculators.loan.LoanCalculator

class FullAmortizationFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_full_amortization, container, false)
        setFragmentView(view)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEventListener()
    }

    fun initEventListener() {
        setActionBarTitle(R.string.menu_title_full_amortization)

        // 계산하기 버튼 클릭시
        findViewById(R.id.id_btn_calculate).setOnClickListener(object : Button.OnClickListener {
            override fun onClick(v: View) {
                onClickButtonCalculate(v)// 계산하기 버튼 클릭시
            }
        })
    }

    fun onClickButtonCalculate(v: View) {
        val principal = java.lang.Double.parseDouble((findViewById(R.id.id_input_principal) as EditText).text.toString())
        val interestRate = java.lang.Double.parseDouble((findViewById(R.id.id_input_interest_rate) as EditText).text.toString())
        val amortizationPeriod = Integer.parseInt((findViewById(R.id.id_input_term) as EditText).text.toString())

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
        fun newInstance(param1: String, param2: String): FullAmortizationFragment {
            val fragment = FullAmortizationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
