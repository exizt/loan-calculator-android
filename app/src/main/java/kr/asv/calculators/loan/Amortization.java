package kr.asv.calculators.loan;

public interface Amortization
{
	void calculate(LoanCalculatorOptions options);
	PaymentSchedules getSchedules();
	double getSummaryInterest();
}
