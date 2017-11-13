package kr.asv.calculators.loancalculator;

/**
 * 계산기 에서 지불 스케쥴 과 관련된 클래스. 하나의 로우. 데이터 클래스 인가?
 * Created by exizt on 2016-06-08.
 */
public class PaymentSchedule {
    public double payment;//합계지불
    public double paidPrincipal;//상환원금
    public double paidInterest;//상환이자
    public double loanBalance;//잔액

    public PaymentSchedule()
    {

    }
    public PaymentSchedule(double payment,double paidPrincipal,double paidInterest,double loanBalance)
    {
        this.payment = payment;
        this.paidPrincipal = paidPrincipal;
        this.paidInterest = paidInterest;
        this.loanBalance = loanBalance;
    }
    @Override
    public String toString()
    {
        return "PaymentSchedule [payment=" + payment + ", paidPrincipal=" + paidPrincipal + ", paidInterest="
                + paidInterest + ", loanBalance=" + loanBalance + "]";
    }
}
