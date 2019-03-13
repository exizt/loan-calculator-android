package kr.asv.loancalculator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 원리금 균등분할 상환 방식
 * 
 * @author EXIZT
 *
 */
public class FullAmortization implements Amortization
{
    /**
     * Options 값들. 가족수, 자녀수, 비과세액 등
     */
    private LoanCalculatorOptions options;

    /**
     * 스케쥴 변수
     */
    private PaymentSchedules schedules = new PaymentSchedules();
    private BigInteger summaryInterest;

    public FullAmortization()
    {
    }

    @SuppressWarnings("unused")
    public FullAmortization(LoanCalculatorOptions options)
    {
        this.setOptions(options);
    }

    /**
     * @param options LoanCalculatorOptions
     */
    private void setOptions(LoanCalculatorOptions options)
    {
        this.options = options;
    }

    /**
     * 생성자
     * @param options LoanCalculatorOptions
     */
    public void calculate(LoanCalculatorOptions options)
    {
        setOptions(options);
        calculateSchedule();
    }

    /**
     * 원리금 균등 분할 방식.
     * payment 가 고정이 되고, interest 와 principal 은 계속 변경된다.
     * 원리금 = 이자 + 원금
     * 원리금이 동일하다.
     * 처음에 원금을 적게 상환하다가, 점차 이자를 적게 내고 원금을 많이 상환하게 된다.
     */
    private void calculateSchedule()
    {
        //ArrayList<PaymentSchedule> schedules = new ArrayList<>();
        //schedules = new ArrayList<>();
        schedules = new PaymentSchedules();

        // 월지불액
        BigInteger payment = getPaymentMonthly();
        //BigInteger payment = CalcUtil.roundUp(getPaymentMonthly(),1);

        // 원금잔액
        BigInteger loanBalance = options.getPrincipal();

        // 상환기간
        int period = options.getAmortizationPeriod();

        // 이자율
        BigDecimal rate = options.getInterestRate2();

        // 월 지불 이자액
        BigInteger paidInterest;

        // 연간 지불 이자액을 잠깐 계산하기 위한 변수
        BigDecimal loanBalanceMultiplyRate;

        // 월 지불 원금
        BigInteger paidPrincipal;
        summaryInterest = BigInteger.ZERO;
        for (int i = 0; i < period; i++)
        {
            //상환이자 계산
            loanBalanceMultiplyRate = CalcUtil.multiply(loanBalance,rate);
            paidInterest = CalcUtil.divide(loanBalanceMultiplyRate,12);
            //paidInterest = (loanBalance * rate) / 12;
            //paidInterest = CalcUtil.roundUp(paidInterest, 1);// 올림

            //상환원금 계산 (이번달 원금 납부액 = 월 총 납부액 - 이번달 이자 납부액)
            paidPrincipal = CalcUtil.minus(payment,paidInterest);
            //paidPrincipal = payment - paidInterest;

            //원금 잔액 (원금 잔액 = 원금 잔액 - 이번 달 원금 납부액)
            loanBalance = CalcUtil.minus(loanBalance, paidPrincipal);
            //loanBalance = loanBalance - paidPrincipal;

            // 총 납부 이자액 (계속 더하면서 진행)
            summaryInterest = CalcUtil.plus(summaryInterest, paidInterest);
            //summaryInterest += paidInterest;

            // 스케쥴 리스트에 추가
            //PaymentSchedule schedule = new PaymentSchedule();
            //schedule.paidPrincipal = paidPrincipal;
            //schedule.paidInterest = paidInterest;
            //schedule.payment = payment;
            //schedule.loanBalance = loanBalance;
            //PaymentSchedule schedule = new PaymentSchedule(payment,paidPrincipal,paidInterest,loanBalance);
            //schedules.add(schedule);
            schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance);
        }

        if(options.isDebug()){
            System.out.println("===원리금균등분할====");
            for (PaymentSchedules.Schedule s : schedules)
            {
                System.out.println(s.toString());
            }
        }
    }

    /**
     * 월별로 납부할 원리금 계산
     * 납부금 = 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간 ÷((1 + 이자율 ÷ 12)^기간 -1)
     *
     * @return double
     */
    private BigInteger getPaymentMonthly()
    {
        // 이자율
        BigDecimal rate = options.getInterestRate2();

        // 상환기간
        int period = options.getAmortizationPeriod();

        // 원금
        BigInteger principal = options.getPrincipal();

        // 가장 문제가 되는 구간. 소수점 자리를 몇 자리까지 계산하느냐 에 따라서 결과가 크게 달라진다.
        // 일단 double 로 처리.
        double calc = CalcUtil.pow((1 + rate.doubleValue() / 12), period);
        // double calc = CalcUtil.pow((1 + rate.doubleValue() / 12), period);

        return BigDecimal.valueOf((CalcUtil.multiply(principal, rate).doubleValue() / 12) * (calc / (calc - 1))).toBigInteger();
        //return (principal * rate / 12) * (calc / (calc - 1));
    }

    public PaymentSchedules getSchedules()
    {
        return schedules;
    }
    public BigInteger getSummaryInterest() {
        return summaryInterest;
    }
}
