package kr.asv.loancalculator

import kr.asv.loancalculator.PaymentSchedules.Schedule
import java.math.BigInteger
import java.util.*

/**
 * 데이터만 갖고 있을 스케쥴 모델 클래스
 */
class PaymentSchedules : ArrayList<Schedule?>() {
    private fun createSchedule(payment: BigInteger?, paidPrincipal: BigInteger?, paidInterest: BigInteger?, loanBalance: BigInteger?): Schedule {
        return Schedule(payment, paidPrincipal, paidInterest, loanBalance)
    }

    fun addSchedule(payment: BigInteger?, paidPrincipal: BigInteger?, paidInterest: BigInteger?, loanBalance: BigInteger?) {
        this.add(createSchedule(payment, paidPrincipal, paidInterest, loanBalance))
    }

    /**
     * Created by EXIZT on 2016-06-08.
     */
    inner class Schedule(//합계지불
            var payment: BigInteger?, //상환원금
            var paidPrincipal: BigInteger?, //상환이자
            var paidInterest: BigInteger?, //잔액
            var loanBalance: BigInteger?) {
        override fun toString(): String {
            return ("PaymentSchedule [payment=" + payment + ", paidPrincipal=" + paidPrincipal + ", paidInterest="
                    + paidInterest + ", loanBalance=" + loanBalance + "]")
        }
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = -3698533479210025025L
    }
}