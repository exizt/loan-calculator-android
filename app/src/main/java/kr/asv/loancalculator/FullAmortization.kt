package kr.asv.loancalculator

import java.math.BigDecimal
import java.math.BigInteger

/**
 * 원리금 균등분할 상환 방식
 *
 * @author EXIZT
 */
class FullAmortization : Amortization {
    /**
     * Options 값들. 가족수, 자녀수, 비과세액 등
     */
    private var options: LoanCalculatorOptions? = null

    /**
     * 스케쥴 변수
     */
    override var schedules = PaymentSchedules()
        private set
    override var summaryInterest: BigInteger? = null
        private set

    constructor() {}
    constructor(options: LoanCalculatorOptions) {
        setOptions(options)
    }

    /**
     * @param options LoanCalculatorOptions
     */
    private fun setOptions(options: LoanCalculatorOptions) {
        this.options = options
    }

    /**
     * 생성자
     * @param options LoanCalculatorOptions
     */
    override fun calculate(options: LoanCalculatorOptions) {
        setOptions(options)
        calculate()
    }

    override fun calculate(){
        calculateSchedule()
    }

    /**
     * 원리금 균등 분할 방식.
     * payment 가 고정이 되고, interest 와 principal 은 계속 변경된다.
     * 원리금 = 이자 + 원금
     * 원리금이 동일하다.
     * 처음에 원금을 적게 상환하다가, 점차 이자를 적게 내고 원금을 많이 상환하게 된다.
     */
    private fun calculateSchedule() {
        //ArrayList<PaymentSchedule> schedules = new ArrayList<>();
        //schedules = new ArrayList<>();
        schedules = PaymentSchedules()

        // 월지불액
        val payment = paymentMonthly
        //BigInteger payment = CalcUtil.roundUp(getPaymentMonthly(),1);

        // 원금잔액
        var loanBalance = options!!.principal

        // 상환기간
        val period = options!!.amortizationPeriod

        // 이자율
        val rate = options.getInterestRate2()

        // 월 지불 이자액
        var paidInterest: BigInteger?

        // 연간 지불 이자액을 잠깐 계산하기 위한 변수
        var loanBalanceMultiplyRate: BigDecimal?

        // 월 지불 원금
        var paidPrincipal: BigInteger?
        summaryInterest = BigInteger.ZERO
        for (i in 0 until period) {
            //상환이자 계산
            loanBalanceMultiplyRate = CalcUtil.multiply(loanBalance, rate)
            paidInterest = CalcUtil.divide(loanBalanceMultiplyRate, 12)
            //paidInterest = (loanBalance * rate) / 12;
            //paidInterest = CalcUtil.roundUp(paidInterest, 1);// 올림

            //상환원금 계산 (이번달 원금 납부액 = 월 총 납부액 - 이번달 이자 납부액)
            paidPrincipal = CalcUtil.minus(payment, paidInterest)
            //paidPrincipal = payment - paidInterest;

            //원금 잔액 (원금 잔액 = 원금 잔액 - 이번 달 원금 납부액)
            loanBalance = CalcUtil.minus(loanBalance, paidPrincipal)
            //loanBalance = loanBalance - paidPrincipal;

            // 총 납부 이자액 (계속 더하면서 진행)
            summaryInterest = CalcUtil.plus(summaryInterest, paidInterest)
            //summaryInterest += paidInterest;

            // 스케쥴 리스트에 추가
            //PaymentSchedule schedule = new PaymentSchedule();
            //schedule.paidPrincipal = paidPrincipal;
            //schedule.paidInterest = paidInterest;
            //schedule.payment = payment;
            //schedule.loanBalance = loanBalance;
            //PaymentSchedule schedule = new PaymentSchedule(payment,paidPrincipal,paidInterest,loanBalance);
            //schedules.add(schedule);
            schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance)
        }
        if (options!!.isDebug) {
            println("===원리금균등분할====")
            for (s in schedules) {
                println(s.toString())
            }
        }
    }// 이자율

    // 상환기간

    // 원금

    // 가장 문제가 되는 구간. 소수점 자리를 몇 자리까지 계산하느냐 에 따라서 결과가 크게 달라진다.
    // 일단 double 로 처리.
    // double calc = CalcUtil.pow((1 + rate.doubleValue() / 12), period);
    //return (principal * rate / 12) * (calc / (calc - 1));
    /**
     * 월별로 납부할 원리금 계산
     * 납부금 = 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간 ÷((1 + 이자율 ÷ 12)^기간 -1)
     *
     * @return double
     */
    private val paymentMonthly: BigInteger
        private get() {
            // 이자율
            val rate = options.getInterestRate2()

            // 상환기간
            val period = options!!.amortizationPeriod

            // 원금
            val principal = options!!.principal

            // 가장 문제가 되는 구간. 소수점 자리를 몇 자리까지 계산하느냐 에 따라서 결과가 크게 달라진다.
            // 일단 double 로 처리.
            val calc = CalcUtil.pow(1 + rate!!.toDouble() / 12, period.toDouble())
            // double calc = CalcUtil.pow((1 + rate.doubleValue() / 12), period);
            return BigDecimal.valueOf(CalcUtil.multiply(principal, rate).toDouble() / 12 * (calc / (calc - 1))).toBigInteger()
            //return (principal * rate / 12) * (calc / (calc - 1));
        }
}