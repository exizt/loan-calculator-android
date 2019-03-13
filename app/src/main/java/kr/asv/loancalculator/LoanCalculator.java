package kr.asv.loancalculator;

import java.math.BigInteger;

/**
 * 대출이자 계산기
 */
public class LoanCalculator
{
    /**
     * Options 값들. 가족수, 자녀수, 비과세액 등
     */
    private final LoanCalculatorOptions options = new LoanCalculatorOptions();

    @SuppressWarnings("unused")
    public enum AmortizationMethods {
        FULL_AMORTIZATION, EQUAL_PRINCIPAL
    }

    private PaymentSchedules schedules;

    private BigInteger summaryInterest;
    /**
     * 생성자
     */
    public LoanCalculator()
    {
        //this.initialize();
    }

    /**
     * 계산 실행
     */
    public void run()
    {
        if (options.isDebug())
        {
            debug(options);
        }
        schedules = new PaymentSchedules();
        Amortization amortization;
        if (options.getAmortizationMethod() == AmortizationMethods.EQUAL_PRINCIPAL)
        {
            amortization = new EqualPrincipalAmortization();
            //schedules = equalPrincipalAmortization.calculate(options);// 원금 균등 분할 상환
        }
        else
        {
            amortization = new FullAmortization();
            //schedules = fullAmortization.calculate(options);// 원리금 균등 분할 상환
        }
        amortization.calculate(options);
        schedules = amortization.getSchedules();
        summaryInterest = amortization.getSummaryInterest();
    }

    private void debug(Object obj)
    {
        System.out.println(obj);
    }

    public LoanCalculatorOptions getOptions()
    {
        return this.options;
    }

    @SuppressWarnings("UnusedReturnValue")
    public PaymentSchedules getSchedules()
    {
        return schedules;
    }

    @SuppressWarnings("UnusedReturnValue")
    public BigInteger getSummaryInterest()
    {
        return summaryInterest;
    }

}