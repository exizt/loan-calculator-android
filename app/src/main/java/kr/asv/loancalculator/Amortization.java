package kr.asv.loancalculator;

import java.math.BigInteger;

interface Amortization
{
    void calculate(LoanCalculatorOptions options);
    PaymentSchedules getSchedules();
    BigInteger getSummaryInterest();
}
