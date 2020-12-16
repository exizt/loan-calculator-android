package kr.asv.loancalculator.app.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.asv.loancalculator.app.Services
import kr.asv.loancalculator.app.databinding.FragmentReportSummaryBinding
import java.text.NumberFormat
import java.util.*


/**
 * ReportActivity 의 하위 프래그먼트
 * 리포트 결과 Summary 와 관련된 Fragment
 */
class ReportSummaryFragment : Fragment() {
    // view binding
    private var _binding: FragmentReportSummaryBinding? = null
    private val binding get() = _binding!!

    /**
     * onCreateView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentReportSummaryBinding.inflate(inflater, container, false)
        return binding.root
        //inflater.inflate(R.layout.fragment_report_summary, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showResult()
    }

    @SuppressLint("SetTextI18n")
    private fun showResult() {
        val calculator = Services.calculator
        //val formatA = java.text.DecimalFormat("###,##0 원")

        val locale = activity?.let { getCurrentLocale(it) }

        // 원금
        binding.summaryPrincipal.text = getMoneyFormatString(calculator.options.principal, locale)

        // 상환 기간
        binding.summaryTerm.text = calculator.options.amortizationPeriod.toString()

        // 상환 이자율
        binding.summaryInterestRate.text = String.format("%.2f %%", calculator.options.interestRate)
        //val interestRate = BigDecimal(calculator.options.interestRate.toString())
        //summary_interest_rate.text = interestRate.multiply(BigDecimal("100")).toString()

        // 상환 이자 금액
        binding.summaryInterest.text = getMoneyFormatString(calculator.summaryInterest, locale)

    }

    private fun getMoneyFormatString(number: Any?, locale: Locale?): String{
        if(locale==null){
            return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(number)
        }
        return if(locale == Locale.KOREA){
            val formatA = java.text.DecimalFormat("###,##0 원")
            formatA.format(number)
        } else {
            NumberFormat.getCurrencyInstance(locale).format(number)
        }
    }

    private fun getCurrentLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }

    /**
    * view 소멸 이벤트
    * view binding 메모리 해제 구문 추가.
    */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(): ReportSummaryFragment = ReportSummaryFragment()
    }
}
