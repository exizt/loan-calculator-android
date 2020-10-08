package kr.asv.apps.loancalculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services
import kr.asv.loancalculator.PaymentSchedules

/**
 * ReportActivity 의 하위 프래그먼트
 * 리포트 결과 Summary 와 관련된 Fragment
 */
class ReportScheduleFragment : Fragment() {

    /**
     * onCreateView 는 매번 호출되는 메서드 이다.
     * 순서상 onCreate 이후에 호출되고, UI 와 연관성을 갖게 된다.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule_list, container, false)

        val schedules : PaymentSchedules = Services.calculator.schedules!!

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.adapter = MyScheduleRecyclerViewAdapter(schedules)
        }
        return view
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
