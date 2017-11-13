package kr.asv.calculators.loancalculator;

public interface Amortization
{
	void calculate(LoanCalculatorOptions options);
	PaymentSchedules getSchedules();
	double getSummaryInterest();
}
