package kr.asv.apps.loancalculator.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.ReportActivity
import kr.asv.apps.loancalculator.Services
import kr.asv.calculators.loan.LoanCalculator

class EqualPrincipalFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_equal_principal, container, false)
        setFragmentView(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEventListener()
    }

    fun initEventListener() {
        setActionBarTitle(R.string.menu_title_equal_principal)

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

        fun newInstance(param1: String, param2: String): EqualPrincipalFragment {
            val fragment = EqualPrincipalFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
