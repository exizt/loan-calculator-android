package kr.asv.loancalculator

import android.annotation.SuppressLint
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*

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
     * 외부에서 호출되는 메서드
     * 옵션 을 넣고, calculateSchedule 을 호출
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
     * '원금 균등 분할'을 계산하는 메서드
     */
    @SuppressLint("SimpleDateFormat")
    private fun calculateSchedule() {

        // 상환 기간
        val period: Int = options!!.amortizationPeriod

        // 연 이자율
        val rate: BigDecimal = options!!.interestRate2

        // 원금 잔액
        var loanBalance: BigInteger = options!!.principal

        // 월 원금 상환액. '원금 균등'방식에서는 매월(또는 회차별) 상환원금은 동일.
        var paidPrincipal: BigInteger = CalcUtil.divide(loanBalance, period)

        // 월 이자 납부액
        var paidInterest: BigInteger = 0.toBigInteger()

        // 월 납부액
        var payment: BigInteger

        // 이자 총합계
        summaryInterest = BigInteger.ZERO


        // 원리금 상환일. 처음에는 최초 상환일부터.
        //var currentPaidDate = CalcUtil.getDate(2016,12,6)
        var currentPaidDate = CalcUtil.getDate(2017,10,10)

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

                // 월 이자 납부액 (소수점 절삭 / 원 단위 절삭)
                //paidInterest = (loanBalance * rate) / 12;// 이자 금액
                //paidInterest = CalcUtil.roundUp(paidInterest, 1);// 소수점 이하 절삭
                //paidInterest = CalcUtil.divide(loanBalanceMultiplyRate, 12) // 이자 금액
                //paidInterest = CalcUtil.divide(loanBalance.toBigDecimal() * rate, 12, options!!.interestDigits, RoundingMode.HALF_EVEN).toBigInteger()
                paidInterestDecimal = CalcUtil.divide(loanBalance.toBigDecimal() * rate, 12, 6, RoundingMode.DOWN)

            }

            // 소수점 처리 (은행마다 방식이 다름. HALF_UP:반올림/DOWN:절삭/HALF_EVEN:외국계 은행)
            paidInterest = paidInterestDecimal.setScale(0, RoundingMode.DOWN).toBigInteger()

            // 10^n 원 단위 절삭이 필요한 경우.
            paidInterest = CalcUtil.round(paidInterest, options!!.interestDigits)

            // 잔여 원금 계산 (직전 잔여 원금 - 월 원금 상환액)
            //loanBalance = CalcUtil.minus(loanBalance, paidPrincipal)
            loanBalance -= paidPrincipal


            // 마지막 차수일 때, 남은 잔액이 있다면. 잔액을 합침.
            if (period - i == 1 && loanBalance > BigInteger.ZERO) {
                //paidPrincipal = CalcUtil.plus(paidPrincipal, loanBalance)
                paidPrincipal += loanBalance
                loanBalance = BigInteger.ZERO
            }

            // 0 보다 작으면 0 을 대입 (음수 방지). 의미 없을 듯? 그냥 버그 방지용.
            if (loanBalance < BigInteger.ZERO) {
                loanBalance = BigInteger.ZERO
            }

            // 이번달 납부액 (원금 납부액 + 이자 납부액)
            //payment = CalcUtil.plus(paidPrincipal, paidInterest)
            payment = paidPrincipal + paidInterest

            // 총 납부 이자액 (계속 더하면서 진행)
            //summaryInterest = CalcUtil.plus(summaryInterest, paidInterest)
            summaryInterest += paidInterest

            // 스케쥴 리스트에 추가
            schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance, days)
            //PaymentSchedules.Schedule schedule = new PaymentSchedules.Schedule(payment,paidPrincipal,paidInterest,loanBalance);
            //schedules.add(schedule);
        }
        if (options!!.isDebug) {
            debug("===원금균등 ====")
            for (s in schedules) {
                debug(s.toString())
            }
        }
    }

    /**
     * 이자 납부액을 산출하는 메서드.
     */
    private fun calculatePaidInterestAdvanced(loanBalance: BigInteger, rate: BigDecimal, currentPaidDate: Date, days: Int): BigDecimal{
        // 일일 기준 이자액을 산출함.
        // 소수점 6자리 이하에서 Half_even 반올림.
        val interestOfDay = CalcUtil.divide(loanBalance.toBigDecimal() * rate, CalcUtil.getDaysOfYear(currentPaidDate), 6, RoundingMode.HALF_EVEN)

        // 일일 기준 이자액 * 일자 수
        return (interestOfDay * days.toBigDecimal())
    }

    private fun debug(obj: Any){
        if (options!!.isDebug) {
            println(obj)
        }
    }
}