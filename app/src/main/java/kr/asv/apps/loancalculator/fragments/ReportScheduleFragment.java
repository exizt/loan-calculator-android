package kr.asv.apps.loancalculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.asv.apps.loancalculator.R;
import kr.asv.apps.loancalculator.Services;
import kr.asv.calculators.loan.LoanCalculator;
import kr.asv.calculators.loan.PaymentSchedules;

/**
 * A fragment representing a list of Items.
 */
public class ReportScheduleFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReportScheduleFragment newInstance() {
        ReportScheduleFragment fragment = new ReportScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReportScheduleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyScheduleRecyclerViewAdapter(getSchedules()));
        }
        return view;
    }
    private PaymentSchedules getSchedules()
    {
        LoanCalculator calculator = Services.getInstance().getCalculator();
        //ArrayList<PaymentSchedule> schedules;
        PaymentSchedules schedules;
        schedules = calculator.getSchedules();
        /*
        if(calculator.getOptions().getAmortizationMethod()== LoanCalculator.AmortizationMethods.EQUAL_PRINCIPAL)
        {
            //schedules = calculator.getEqualPrincipalAmortization().getSchedules();
            schedules = calculator.getSchedules();
        } else {
            schedules = calculator.getFullAmortization().getSchedules();
        }
        */
        return schedules;
    }
}
