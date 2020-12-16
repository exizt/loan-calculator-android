package kr.asv.loancalculator.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.asv.loancalculator.app.MyScheduleRecyclerViewAdapter
import kr.asv.loancalculator.app.Services
import kr.asv.loancalculator.app.databinding.FragmentScheduleListBinding
import kr.asv.loancalculator.calculator.PaymentSchedules

/**
 * ReportActivity 의 하위 프래그먼트
 * 리포트 결과 Summary 와 관련된 Fragment
 */
class ReportScheduleFragment : Fragment() {
    // view binding
    private var _binding: FragmentScheduleListBinding? = null
    private val binding get() = _binding!!

    /**
     * onCreateView 는 매번 호출되는 메서드 이다.
     * 순서상 onCreate 이후에 호출되고, UI 와 연관성을 갖게 된다.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //val view = inflater.inflate(R.layout.fragment_schedule_list, container, false)
        _binding = FragmentScheduleListBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set the adapter
        val context = view.context
        val schedules : PaymentSchedules = Services.calculator.schedules!!
        view.layoutManager = LinearLayoutManager(context)
        view.adapter = MyScheduleRecyclerViewAdapter(schedules)

        return binding.root
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
        fun newInstance(): ReportScheduleFragment {
            val fragment = ReportScheduleFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
