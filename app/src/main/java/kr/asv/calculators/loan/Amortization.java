package kr.asv.calculators.loan;

public interface Amortization
{
	public void calculate(LoanCalculatorOptions options);
	public PaymentSchedules getSchedules();
	public double getSummaryInterest();
}
