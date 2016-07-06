package kr.asv.apps.loancalculator.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import kr.asv.apps.loancalculator.R;
import kr.asv.apps.loancalculator.Services;
import kr.asv.calculators.loan.LoanCalculator;


public class ReportSummaryFragment extends BaseFragment {

    public ReportSummaryFragment() {
    }

    public static ReportSummaryFragment newInstance() {
        ReportSummaryFragment fragment = new ReportSummaryFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_summary, container, false);
        setFragmentView(view);
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showResult();
    }
    private void showResult() {
        LoanCalculator calculator = Services.getInstance().getCalculator();

        TextView summary_principal = (TextView) findViewById(R.id.summary_principal);
        summary_principal.setText(Double.toString(calculator.getOptions().getPrincipal()));

        TextView summary_term = (TextView) findViewById(R.id.summary_term);
        summary_term.setText(Integer.toString(calculator.getOptions().getAmortizationPeriod()));

        TextView summary_interest_rate = (TextView) findViewById(R.id.summary_interest_rate);

        BigDecimal interestRate = new BigDecimal(Double.toString(calculator.getOptions().getInterestRate()));
        summary_interest_rate.setText(interestRate.multiply(new BigDecimal("100")).toString());

        TextView summary_interest = (TextView) findViewById(R.id.summary_interest);
        summary_interest.setText(Double.toString(calculator.getSummaryInterest()));

    }
}
