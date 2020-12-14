package kr.asv.apps.loancalculator.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kr.asv.androidutils.InputFilterDoubleMinMax
import kr.asv.androidutils.MoneyTextWatcher
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services
import kr.asv.apps.loancalculator.activities.ReportActivity
import kr.asv.apps.loancalculator.databinding.FragmentEqualPrincipalBinding
import kr.asv.loancalculator.calculator.LoanCalculator
import java.math.BigDecimal
import java.math.BigInteger

class EqualPrincipalFragment : Fragment() {
    // view binding
    private var _binding: FragmentEqualPrincipalBinding? = null
    private val binding get() = _binding!!

    /**
     * '원금' 계산 가능 최소값
     */
    private val principalMinimum : Int = 1000

    /**
     * '상환 기간' 계산 가능 최소값
     */
    private val periodMinimum = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentEqualPrincipalBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_equal_principal, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.menu_title_equal_principal)

        // 타이틀 지정
        val title : String
        val subtitle : String
        if(Services.calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL){
            title = getString(R.string.nav_equal_principal)
            subtitle = getString(R.string.calculator_title_equal_principal)
        } else {
            title = getString(R.string.nav_full_amortization)
            subtitle = getString(R.string.calculator_title_full_amortization)
        }
        (activity as AppCompatActivity).supportActionBar!!.title = title
        binding.subtitle.text = subtitle

        initEventListener()

    }

    /**
     * 이벤트 리스너들
     */
    private fun initEventListener(){

        // 계산하기 버튼 클릭시
        binding.idBtnCalculate.setOnClickListener {
            calculate()// 계산하기 버튼 클릭시
        }

        // EditText 처리
        binding.idInputPrincipal.addTextChangedListener(MoneyTextWatcher(binding.idInputPrincipal))
        //id_input_principal.filters = arrayOf<InputFilter>(InputFilterDoubleMinMax(0.0,10000000000.0))
        binding.idInputTerm.filters  = arrayOf<InputFilter>(InputFilterDoubleMinMax(0,1200))
        binding.idInputInterestRate.filters = arrayOf<InputFilter>(InputFilterDoubleMinMax(0.0,100.0))
    }

    /**
     * 계산 기능 호출
     */
    private fun calculate() {
        /*
         * 유효성 체크. 빈 값 여부
         */
        if(binding.idInputPrincipal.text.isEmpty()){
            return
        }
        if(binding.idInputInterestRate.text.isEmpty()){
            return
        }
        if(binding.idInputTerm.text.isEmpty()){
            return
        }

        /*
         * 이상이 있을 경우에는 0으로 대입.
         */
        // 원금 (0 보다 작은 값이 들어와도 0으로 치환)
        val principal : BigInteger = try {
            if(MoneyTextWatcher.getBigInteger(binding.idInputPrincipal).compareTo(BigInteger.ZERO) == 1){
                MoneyTextWatcher.getBigInteger(binding.idInputPrincipal)
            } else {
                BigInteger.ZERO
            }
        } catch (e : Exception){
            BigInteger.ZERO
        }

        // 이자율
        val interestRate : BigDecimal = try{
            BigDecimal(binding.idInputInterestRate.text.toString())
        } catch (e: Exception){
            BigDecimal.ZERO
        }

        // 납부 기간
        val amortizationPeriod : Int = try{
            Integer.parseInt(binding.idInputTerm.text.toString())
        } catch (e: Exception) {
            0
        }

        /*
         * 최소값 체크
         */
        // 최소값 보다 작으면 연산 안 함
        if(principal < BigInteger.valueOf(principalMinimum.toLong())){
            return
        }

        // 최소값 보다 작으면 연산 안 함
        if(amortizationPeriod < periodMinimum){
            return
        }

        // 계산 프로세스
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
