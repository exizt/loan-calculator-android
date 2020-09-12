package kr.asv.loancalculator

/**
 * 계산기 에서 지불 스케쥴 과 관련된 클래스. 하나의 로우. 데이터 클래스 인가?
 * Created by exizt on 2016-06-08.
 */
internal class PaymentSchedule {
    private var payment //합계지불
            = 0.0
    private var paidPrincipal //상환원금
            = 0.0
    private var paidInterest //상환이자
            = 0.0
    private var loanBalance //잔액
            = 0.0

    constructor() {}
    constructor(payment: Double, paidPrincipal: Double, paidInterest: Double, loanBalance: Double) {
        this.payment = payment
        this.paidPrincipal = paidPrincipal
        this.paidInterest = paidInterest
        this.loanBalance = loanBalance
    }

    override fun toString(): String {
        return ("PaymentSchedule [payment=" + payment + ", paidPrincipal=" + paidPrincipal + ", paidInterest="
                + paidInterest + ", loanBalance=" + loanBalance + "]")
    }
}