package kr.asv.loancalculator

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*

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
    override var summaryInterest: BigInteger = BigInteger.ZERO
        private set

    constructor()

    @Suppress("unused")
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
        //schedules = PaymentSchedules()

        // 원금잔액
        var loanBalance: BigInteger = options!!.principal

        // 상환기간
        val period: Int = options!!.amortizationPeriod

        // 이자율
        val rate: BigDecimal = options!!.interestRate2

        // 월지불액
        var payment: BigInteger = paymentMonthly

        // 월 지불 이자액
        var paidInterest: BigInteger

        // 월 지불 원금
        var paidPrincipal: BigInteger

        // 이자 총합계
        summaryInterest = BigInteger.ZERO

        // 원리금 상환일. 처음에는 최초 상환일부터.
        var currentPaidDate = CalcUtil.getDate(2018,1,8)

        for (i in 0 until period) {
            var days = 1 // 각 회차에 해당된 날짜 수
            val paidInterestDecimal: BigDecimal

            // 월 이자 납부액 계산.
            // 옵션에 따라서 상세 계산을 할지 간이 계산을 할지 분기를 나눔.
            if(options!!.isIncludeDays) {
                // <상세 계산 ROUTE>

                // 직전 월 계산
                val dateMinusOneMonth = CalcUtil.getMinusOneMonth(currentPaidDate)

                // 지난 한달 간의 날짜 수
                days = CalcUtil.betweenDays(dateMinusOneMonth, currentPaidDate)

                paidInterestDecimal = if(CalcUtil.getMonth(currentPaidDate) == 1){
                    // 1월인 경우 전년도와 계산법이 달라지는 경우가 있음.
                    val daysNew = CalcUtil.getDayOfYear(currentPaidDate) - 1
                    val daysLast = days - daysNew

                    (calculatePaidInterestAdvanced(loanBalance, rate, dateMinusOneMonth, daysLast) +
                            calculatePaidInterestAdvanced(loanBalance, rate, currentPaidDate, daysNew))

                } else {
                    // 월 이자 납부액 계산.
                    calculatePaidInterestAdvanced(loanBalance, rate, currentPaidDate, days)
                }

                // 다음달로 날짜 변경.
                currentPaidDate = CalcUtil.getPlusOneMonth(currentPaidDate)

            } else {
                // <간이 계산 ROUTE>

                // 연 납부 이자액 계산
                //loanBalanceMultiplyRate = CalcUtil.multiply(loanBalance, rate)
                //loanBalanceMultiplyRate = loanBalance.toBigDecimal() * rate

                // 월 납부 이자액 계산
                //paidInterest = CalcUtil.divide(loanBalance.toBigDecimal() * rate, 12, 6, RoundingMode.HALF_EVEN).toBigInteger()
                paidInterestDecimal = CalcMath.divide(loanBalance.toBigDecimal() * rate, 12, 6, RoundingMode.FLOOR)

            }

            // 소수점 처리 (은행마다 방식이 다름. HALF_UP:반올림/DOWN:절삭/HALF_EVEN:외국계 은행)
            paidInterest = paidInterestDecimal.setScale(0, RoundingMode.DOWN).toBigInteger()

            //상환원금 계산 (이번달 원금 납부액 = 월 총 납부액 - 이번달 이자 납부액)
            paidPrincipal = payment - paidInterest

            //원금 잔액 (원금 잔액 = 원금 잔액 - 이번 달 원금 납부액)
            loanBalance -= paidPrincipal

            // 마지막 차수일 때, 남은 잔액이 있다면. 잔액을 합침.
            if (period - i == 1 && loanBalance > BigInteger.ZERO) {
                payment += loanBalance
                paidPrincipal += loanBalance
                loanBalance = BigInteger.ZERO
            }

            // 총 납부 이자액 (계속 더하면서 진행)
            summaryInterest += paidInterest

            // 스케쥴 리스트에 추가
            //PaymentSchedule schedule = new PaymentSchedule();
            //schedule.paidPrincipal = paidPrincipal;
            //schedule.paidInterest = paidInterest;
            //schedule.payment = payment;
            //schedule.loanBalance = loanBalance;
            //PaymentSchedule schedule = new PaymentSchedule(payment,paidPrincipal,paidInterest,loanBalance);
            //schedules.add(schedule);
            schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance, days)
        }
        if (options!!.isDebug) {
            debug("===원리금균등분할====")
            for (s in schedules) {
                debug(s)
            }
        }
    }

    /**
     * 이자 납부액을 산출하는 메서드.
     */
    private fun calculatePaidInterestAdvanced(loanBalance: BigInteger, rate: BigDecimal, currentPaidDate: Date, days: Int): BigDecimal{
        // 일일 기준 이자액을 산출함.
        // 소수점 6자리 이하에서 Half_even 반올림.
        val interestOfDay = CalcMath.divide(loanBalance.toBigDecimal() * rate, CalcUtil.getDaysOfYear(currentPaidDate), 6, RoundingMode.HALF_EVEN)

        // 일일 기준 이자액 * 일자 수
        return (interestOfDay * days.toBigDecimal())
    }

    /**
     * 월별로 납부할 원리금 계산
     * 월 납부금 = 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간 ÷((1 + 이자율 ÷ 12)^기간 -1)
     *
     * @return double
     */
    private val paymentMonthly: BigInteger
        get() {
            val isDebug = false

            // 이자율
            val rate: BigDecimal = options!!.interestRate2

            // 상환기간
            val period: Int = options!!.amortizationPeriod

            // 원금
            val principal: BigInteger = options!!.principal

            // 가장 문제가 되는 구간. 소수점 자리를 몇 자리까지 계산하느냐 에 따라서 결과가 크게 달라진다.
            // 일단 double 로 처리.
            // double calc = CalcUtil.pow((1 + rate.doubleValue() / 12), period);
            //val calc = CalcUtil.pow(1 + rate.toDouble() / 12, period.toDouble())
            // 월별 이자율 연산
            //val monthlyRate: BigDecimal = CalcUtil.divide(rate, 12, 6 , RoundingMode.DOWN)

            // '(1 + 이자율  ÷ 12)^ 기간' 을 연산
            //val calc: BigDecimal = (BigDecimal.valueOf(1) + monthlyRate).pow(period)
            val calcT = (1.toBigDecimal() + CalcMath.divide(rate, 12, 6 , RoundingMode.HALF_EVEN))
            calcT.setScale(6, RoundingMode.HALF_EVEN)

            val calc = calcT.pow(period)
            calc.setScale(6, RoundingMode.HALF_EVEN)

            if(isDebug){
                debug("calc_t [$calcT] calc [$calc]")
            }

            // 대출원금 x 이자율 ÷ 12 계산
            val monthlyPaymentInterest = CalcMath.divide(principal.toBigDecimal() * rate, 12, 6 , RoundingMode.HALF_EVEN)

            if(isDebug){
                debug("monthlyPaymentInterest [$monthlyPaymentInterest]")
            }

            // 1 + 이자율 ÷ 12 계산
            val calcA = 1.toBigDecimal() + CalcMath.divide(rate, 12, 36 , RoundingMode.HALF_EVEN)
            if(isDebug){
                debug("calcA [$calcA]")
            }

            // (1 + 이자율 ÷ 12)^차수
            val calcPowA = calcA.pow(period)
            if(isDebug){
                debug("calcPowA [$calcPowA]")
            }

            // 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간
            var res = monthlyPaymentInterest * calcPowA
            if(isDebug){
                debug("res [$res]")
            }

            // 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간 ÷ ((1 + 이자율 ÷ 12)^기간 -1)
            res /= (calcPowA - 1.toBigDecimal())
            if(isDebug){
                debug("res [$res]")
            }

            // 소수점 절삭. (은행마다 다름)
            val r2 = res.divide(BigDecimal.ONE, 0, RoundingMode.DOWN)
            if(isDebug){
                debug("r2 [$r2]")
            }

            return r2.toBigInteger()
            //return BigDecimal.valueOf(CalcUtil.multiply(principal, rate).toDouble() / 12 * (calc / (calc - 1))).toBigInteger()
            //return (principal * rate / 12) * (calc / (calc - 1));
        }

    /**
     * 테스트용
     */
    fun testPaymentMonthly(options: LoanCalculatorOptions) : BigInteger{
        setOptions(options)
        return paymentMonthly
    }

    private fun debug(obj: Any){
        if (options!!.isDebug) {
            println(obj)
        }
    }
}