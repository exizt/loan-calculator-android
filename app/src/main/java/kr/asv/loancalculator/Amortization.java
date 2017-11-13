package kr.asv.loancalculator;

interface Amortization
{
	void calculate(LoanCalculatorOptions options);
	PaymentSchedules getSchedules();
	double getSummaryInterest();
}
