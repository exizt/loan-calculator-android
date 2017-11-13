package kr.asv.apps.loancalculator;

import android.content.Context;

import kr.asv.loancalculator.LoanCalculator;

/**
 * 어플 전체적으로 활용되는 기능들을 모아두는 클래스
 * Created by Administrator on 2016-04-27.
 */
public class Services {
    //default
    private static final Services instance = new Services();
    private Context context = null;
    private final LoanCalculator calculator = new LoanCalculator();
    public CalculatorMethods calculatorMethod;
    public enum CalculatorMethods{EQUAL_PRINCIPAL,FULL_AMORTIZATION}

    /**
     * 생성자
     */
    private Services() {
    }

    /**
     *
     * @return instance:Services
     */
    public static Services getInstance() {
        return instance;
    }

    public static Services getInstanceWithInit(Context context)
    {
        if(instance.context == null){
            instance.initialize(context);
        }
        return instance;
    }

    /**
     * 최초 한번만 실행
     * @param context Context
     */
    private void initialize(Context context)
    {
        this.context = context.getApplicationContext();
    }

    public LoanCalculator getCalculator()
    {
        return calculator;
    }

}
