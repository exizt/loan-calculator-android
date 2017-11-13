package kr.asv.calculators.loancalculator;

import java.util.ArrayList;

public class PaymentSchedules extends ArrayList<PaymentSchedules.Schedule>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3698533479210025025L;

	public Schedule createSchedule(double payment,double paidPrincipal,double paidInterest,double loanBalance)
	{
		return new Schedule(payment,paidPrincipal,paidInterest,loanBalance);
	}
	public void addSchedule(double payment,double paidPrincipal,double paidInterest,double loanBalance)
	{
		this.add(createSchedule(payment, paidPrincipal, paidInterest, loanBalance));
	}
	/**
	 * Created by Administrator on 2016-06-08.
	 */
	public class Schedule {
	    public double payment;//합계지불
	    public double paidPrincipal;//상환원금
	    public double paidInterest;//상환이자
	    public double loanBalance;//잔액

	    public Schedule()
	    {

	    }
	    public Schedule(double payment,double paidPrincipal,double paidInterest,double loanBalance)
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
