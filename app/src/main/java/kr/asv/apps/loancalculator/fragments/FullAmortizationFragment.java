package kr.asv.apps.loancalculator.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kr.asv.apps.loancalculator.R;
import kr.asv.apps.loancalculator.ReportActivity;
import kr.asv.apps.loancalculator.Services;
import kr.asv.calculators.loan.LoanCalculator;

public class FullAmortizationFragment extends BaseFragment {
    public FullAmortizationFragment() {
    }
    public static FullAmortizationFragment newInstance(String param1, String param2) {
        FullAmortizationFragment fragment = new FullAmortizationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_amortization, container, false);
        setFragmentView(view);
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEventListener();
    }

    public void initEventListener()
    {
        setActionBarTitle(R.string.menu_title_full_amortization);

        // 계산하기 버튼 클릭시
        findViewById(R.id.id_btn_calculate).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonCalculate(v);// 계산하기 버튼 클릭시
            }
        });
    }
    public void onClickButtonCalculate(View v) {
        double principal = Double.parseDouble(((EditText) findViewById(R.id.id_input_principal)).getText().toString());
        double interestRate = Double.parseDouble(((EditText) findViewById(R.id.id_input_interest_rate)).getText().toString());
        int amortizationPeriod = Integer.parseInt(((EditText) findViewById(R.id.id_input_term)).getText().toString());

        Services.getInstance().calculatorMethod = Services.CalculatorMethods.FULL_AMORTIZATION;

        LoanCalculator calculator = Services.getInstance().getCalculator();
        calculator.getOptions().setPrincipal(principal);
        calculator.getOptions().setInterestRate(interestRate);
        calculator.getOptions().setAmortizationPeriod(amortizationPeriod);
        calculator.getOptions().setAmortizationMethod(LoanCalculator.AmortizationMethods.FULL_AMORTIZATION);
        calculator.run();

        //결과 화면 호출
        Intent intent=new Intent(getActivity(),ReportActivity.class);
        startActivity(intent);
    }
}
