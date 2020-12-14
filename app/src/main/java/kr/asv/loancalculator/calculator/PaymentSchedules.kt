package kr.asv.loancalculator.calculator

import kr.asv.loancalculator.calculator.PaymentSchedules.Schedule
import java.math.BigInteger
import java.util.*

/**
 * 데이터만 갖고 있을 스케쥴 모델 클래스
 */
class PaymentSchedules : ArrayList<Schedule>() {
    private fun createSchedule(payment: BigInteger, paidPrincipal: BigInteger, paidInterest: BigInteger, loanBalance: BigInteger, days:Int): Schedule {
        return Schedule(payment, paidPrincipal, paidInterest, loanBalance, days)
    }

    fun addSchedule(payment: BigInteger, paidPrincipal: BigInteger, paidInterest: BigInteger, loanBalance: BigInteger, days:Int) {
        this.add(createSchedule(payment, paidPrincipal, paidInterest, loanBalance, days))
    }

    /**
     * Created by EXIZT on 2016-06-08.
     * payment : 합계 지불액
     * paidPrincipal : 상환 원금액
     * paidInterest : 상환 이자액
     * loanBalance : 잔액
     */
    inner class Schedule(
            var payment: BigInteger,
            var paidPrincipal: BigInteger,
            var paidInterest: BigInteger,
            var loanBalance: BigInteger,
            @Suppress("MemberVisibilityCanBePrivate") var days: Int) {
        override fun toString(): String {
            return ("PaymentSchedule [payment=$payment, paidPrincipal=$paidPrincipal, paidInterest=$paidInterest, loanBalance=$loanBalance, days = $days]")
        }
    }

    companion object {
        private const val serialVersionUID = -3698533479210025025L
    }
}