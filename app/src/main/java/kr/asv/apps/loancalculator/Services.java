package kr.asv.apps.loancalculator;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import kr.asv.calculators.loan.LoanCalculator;

/**
 * 어플 전체적으로 활용되는 기능들을 모아두는 클래스
 * Created by Administrator on 2016-04-27.
 */
public class Services {
    //default
    private static Services instance = new Services();
    private Context applicationContext = null;
    private FragmentActivity mainActivity = null;

    //objects
    private LoanCalculator calculator = new LoanCalculator();

    public enum CalculatorMethods{EQUAL_PRINCIPAL,FULL_AMORTIZATION}

    public CalculatorMethods calculatorMethod;

    public static Services getInstance() {
        return instance;
    }

    public static Services getInstanceWithInit(FragmentActivity mainActivity)
    {
        if(instance.mainActivity == null){
            instance.initialize(mainActivity);
        }
        return instance;
    }
    private Services() {
    }

    /**
     * 최초 한번만 실행
     * @param mainActivity
     */
    public void initialize(FragmentActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    public void onResume(Context context)
    {

    }

    public LoanCalculator getCalculator()
    {
        return calculator;
    }
    public FragmentActivity getMainActivity()
    {
        return mainActivity;
    }
    /**
     * get Application Context
     * @return
     */
    private Context getApplicationContext()
    {
        return mainActivity.getApplicationContext();
    }

    public void replaceFragments(Fragment fragment) {
        replaceFragments(R.id.fragment_container,fragment, true);
    }
    public void replaceFragments(Fragment fragment,boolean backStack) {
        replaceFragments(R.id.fragment_container,fragment, backStack);
    }
    public void replaceFragments(int resourceId, Fragment fragment) {
        replaceFragments(resourceId,fragment, true);
    }

    public void replaceFragments(int resourceId, Fragment fragment, boolean backStack) {
        FragmentManager fragmentManager;
        fragmentManager = mainActivity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(resourceId, fragment);

        if (backStack) {
            fragmentTransaction.addToBackStack(null);//히스토리에 남긴다.
        }
        fragmentTransaction.commit();
    }
}
