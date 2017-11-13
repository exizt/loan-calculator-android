package kr.asv.loancalculator;

/**
 * 원리금 균등분할 상환 방식
 * 
 * @author Administrator
 *
 */
public class FullAmortization implements Amortization
{
	/**
	 * Options 값들. 가족수, 자녀수, 비과세액 등
	 */
	private LoanCalculatorOptions options;
	/**
	 * 스케쥴 변수
	 */
	private PaymentSchedules schedules = new PaymentSchedules();
	private double summaryInterest;
	
	public FullAmortization()
	{
	}

	@SuppressWarnings("unused")
	public FullAmortization(LoanCalculatorOptions options)
	{
		this.setOptions(options);
	}	
	/**
	 * @param options LoanCalculatorOptions
	 */
	private void setOptions(LoanCalculatorOptions options)
	{
		this.options = options;
	}
	/**
	 * 생성자
	 * @param options LoanCalculatorOptions
     */
	public void calculate(LoanCalculatorOptions options)
	{
		setOptions(options);
		calculateSchedule();
	}

	/**
	 * 원리금 균등 분할 방식.
	 * payment 가 고정이 되고, interest 와 principal 은 계속 변경된다.
	 * 원리금 = 이자 + 원금
	 * 원리금이 동일하다.
	 * 처음에 원금을 적게 상환하다가, 점차 이자를 적게 내고 원금을 많이 상환하게 된다.
	 */
	private void calculateSchedule()
	{
		//ArrayList<PaymentSchedule> schedules = new ArrayList<>();
		//schedules = new ArrayList<>();
		schedules = new PaymentSchedules();
		
		// 월지불액
		double payment = CalculatorUtils.roundup(getPaymentMonthly(),1);

		// 원금잔액
		double loanBalance = options.getPrincipal();

		// 상환기간
		int period = options.getAmortizationPeriod();
		
		// 이자율
		double rate = options.getInterestRate();
		// 월 지불 이자액
		double paidInterest;

		// 월 지불 원금
		double paidPrincipal;
		summaryInterest = 0;
		for (int i = 0; i < period; i++)
		{
			//상환이자 계산
			paidInterest = (loanBalance * rate) / 12;
			paidInterest = CalculatorUtils.roundup(paidInterest, 1);// 올림
			
			//상환원금 계산
			paidPrincipal = payment - paidInterest;
			//잔액
			loanBalance = loanBalance - paidPrincipal;

			summaryInterest += paidInterest;

			// 스케쥴 리스트에 추가
			//PaymentSchedule schedule = new PaymentSchedule();
			//schedule.paidPrincipal = paidPrincipal;
			//schedule.paidInterest = paidInterest;
			//schedule.payment = payment;
			//schedule.loanBalance = loanBalance;
			//PaymentSchedule schedule = new PaymentSchedule(payment,paidPrincipal,paidInterest,loanBalance);
			//schedules.add(schedule);
			schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance);
		}
		
		if(options.isDebug()){
			System.out.println("===원리금균등분할====");
			for (PaymentSchedules.Schedule s : schedules)
			{
				System.out.println(s.toString());
			}
		}
	}

	/**
	 * 월별로 납부할 원리금 계산
	 * 납부금 = 대출원금 × 이자율 ÷ 12 × (1 + 이자율 ÷ 12)^기간 ÷((1 + 이자율 ÷ 12)^기간 -1)
	 * 
	 * @return double
	 */
	private double getPaymentMonthly()
	{
		// 이자율
		double rate = options.getInterestRate();

		// 상환기간
		int period = options.getAmortizationPeriod();

		double principal = options.getPrincipal();

		double calc = CalculatorUtils.pow((1 + rate / 12), period);
		return (principal * rate / 12) * (calc / (calc - 1));
	}

	public PaymentSchedules getSchedules()
	{
		return schedules;
	}
	public double getSummaryInterest() {
		return summaryInterest;
	}
}
