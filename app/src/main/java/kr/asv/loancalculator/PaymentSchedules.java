package kr.asv.loancalculator;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 데이터만 갖고 있을 스케쥴 모델 클래스
 */
public class PaymentSchedules extends ArrayList<PaymentSchedules.Schedule>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3698533479210025025L;

	private Schedule createSchedule(BigInteger payment, BigInteger paidPrincipal, BigInteger paidInterest, BigInteger loanBalance)
	{
		return new Schedule(payment,paidPrincipal,paidInterest,loanBalance);
	}
	public void addSchedule(BigInteger payment,BigInteger paidPrincipal,BigInteger paidInterest,BigInteger loanBalance)
	{
		this.add(createSchedule(payment, paidPrincipal, paidInterest, loanBalance));
	}
	/**
	 * Created by EXIZT on 2016-06-08.
	 */
	public class Schedule {
	    public BigInteger payment;//합계지불
	    public BigInteger paidPrincipal;//상환원금
	    public BigInteger paidInterest;//상환이자
	    public BigInteger loanBalance;//잔액

	    public Schedule()
	    {

	    }
	    public Schedule(BigInteger payment,BigInteger paidPrincipal,BigInteger paidInterest,BigInteger loanBalance)
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
}
