package kr.asv.loancalculator

import java.math.BigDecimal
import java.math.BigInteger

/**
 * 원금 균등 분할 상환 방식
 *
 * @author EXIZT
 */
class EqualPrincipalAmortization : Amortization {
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
     * 외부에서 호출되는 메서드
     * 옵션 을 넣고, calculateSchedule 을 호출
     * @param options LoanCalculatorOptions
     */
    override fun calculate(options: LoanCalculatorOptions) {
        //schedules.clear();
        //schedules = new ArrayList<>();
        setOptions(options)
        calculateSchedule()
    }

    /**
     * '원금 균등 분할'을 계산하는 메서드
     */
    private fun calculateSchedule() {
        //ArrayList<PaymentSchedule> schedules = new ArrayList<>();
        schedules = PaymentSchedules()

        // 원금잔액
        var loanBalance = options!!.principal

        // 상환기간
        val period = options!!.amortizationPeriod

        // 이자율
        val rate = options.getInterestRate2()

        // 상환 원금. 원금 균등방식에서는 매월(또는 회차별) 상환원금은 동일하다.
        //val principal = MoneyTextWatcher.getValue(id_input_principal).toDouble()
        //BigInteger paidPrincipal = loanBalance.divide(BigInteger.valueOf(period));
        var paidPrincipal = CalcUtil.divide(loanBalance, period)

        // 월지불 이자액
        var paidInterest: BigInteger?

        // 연간 지불 이자액을 잠깐 계산하기 위한 변수
        var loanBalanceMultiplyRate: BigDecimal?

        // 월지불액
        var payment: BigInteger?
        summaryInterest = BigInteger.ZERO
        for (i in 0 until period) {
            /*
             * 연이자 금액 = 원금잔액 * 연이자율
             * 월이자 금액 = 연이자 금액 / 12 (좀 더 정확하게 할 때에는 월일자/365 와 같은 식으로 계산)
             * 이자금액이 월마다 달라지게 된다. 더 정확하게 보자면, 매년 연이자율만큼 이자를 계산해서 납부를 해야하지만,
             * 원금납부로 인하여 원금잔액이 줄어들었으므로,
             * 이에 맞춰서 월이자 금액을 매달 재계산하는 셈이다.
             *
             * 여기서 중요한 부분이 있는데, 소수점인 이자율을 곱하면 결과값이 소수점까지 나오게 된다.. 이것을 어떻게 처리해야하는지 궁금..
             * CASE 1 : 원금 * 이자율 = 이자액 에서 소수점 절삭 후 / 12
             * CASE 2 : 원금 * 이자율 / 12 = 결과 에서 소수점 절삭 (이게 정답이라고 함) (그래서 중간에 있는 값을 BigDecimal 로 변경함)
             */
            loanBalanceMultiplyRate = CalcUtil.multiply(loanBalance, rate)
            paidInterest = CalcUtil.divide(loanBalanceMultiplyRate, 12) // 이자 금액 (소수점 이하는 절삭)

            //paidInterest = (loanBalance * rate) / 12;// 이자 금액
            //paidInterest = CalcUtil.roundUp(paidInterest, 1);// 소수점 이하 절삭
            loanBalance = CalcUtil.minus(loanBalance, paidPrincipal)
            //loanBalance = loanBalance - paidPrincipal;

            // 0 보다 작으면 0 을 대입 (음수 방지)
            if (loanBalance.compareTo(BigInteger.ZERO) < 0) {
                loanBalance = BigInteger.ZERO
            }

            // 마지막차수일때, 남은 잔액이 있다면. 잔액을 합침.
            if (period - i == 1 && loanBalance!!.compareTo(BigInteger.ZERO) > 0) {
                paidPrincipal = CalcUtil.plus(paidPrincipal, loanBalance)
                //paidPrincipal += loanBalance;
                loanBalance = BigInteger.ZERO
            }

            // 이번달 납부액 (원금 납부액 + 이자 납부액)
            payment = CalcUtil.plus(paidPrincipal, paidInterest)
            //payment = paidPrincipal + paidInterest;

            // 총 납부 이자액 (계속 더하면서 진행)
            summaryInterest = CalcUtil.plus(summaryInterest, paidInterest)
            //summaryInterest += paidInterest;

            // 스케쥴 리스트에 추가
            schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance)
            //PaymentSchedules.Schedule schedule = new PaymentSchedules.Schedule(payment,paidPrincipal,paidInterest,loanBalance);
            //schedules.add(schedule);
        }
        if (options!!.isDebug) {
            println("===원금균등 ====")
            for (s in schedules) {
                println(s.toString())
            }
        }
    }
}