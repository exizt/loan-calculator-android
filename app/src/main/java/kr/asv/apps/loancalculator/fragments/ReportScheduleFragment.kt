package kr.asv.apps.loancalculator.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    // TODO: Customize parameters
    private var mColumnCount = 1

    private//ArrayList<PaymentSchedule> schedules;
            /*
        if(calculator.getOptions().getAmortizationMethod()== LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL)
        {
            //schedules = calculator.getEqualPrincipalAmortization().getSchedules();
            schedules = calculator.getSchedules();
        } else {
            schedules = calculator.getFullAmortization().getSchedules();
        }
        */ val schedules: PaymentSchedules
        get() {
            val calculator = Services.getInstance().calculator
            val schedules: PaymentSchedules
            schedules = calculator.schedules
            return schedules
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            view.adapter = MyScheduleRecyclerViewAdapter(schedules)
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(): ReportScheduleFragment {
            val fragment = ReportScheduleFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
