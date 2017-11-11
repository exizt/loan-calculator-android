package kr.asv.calculators.loan;

/**
 * 원금 균등 분할 상환 방식
 * 
 * @author Administrator
 *
 */
public class EqualPrincipalAmortization implements Amortization
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
	
	public EqualPrincipalAmortization()
	{
	}
	
	public EqualPrincipalAmortization(LoanCalculatorOptions options)
	{
		this.setOptions(options);
	}
	/**
	 * @param options
	 */
	private void setOptions(LoanCalculatorOptions options)
	{
		this.options = options;
	}
	/**
	 * 생성자
	 * @param options
     */
	public void calculate(LoanCalculatorOptions options)
	{
		//schedules.clear();
		//schedules = new ArrayList<>();
		setOptions(options);
		calculateSchedule();
	}

	/**
	 * 원금 균등 분할 방식
	 */
	private void calculateSchedule()
	{
		//ArrayList<PaymentSchedule> schedules = new ArrayList<>();
		schedules = new PaymentSchedules();

		// 원금잔액
		double loanBalance = options.getPrincipal();

		// 상환기간
		int period = options.getAmortizationPeriod();

		// 이자율
		double rate = options.getInterestRate();

		// 상환 원금. 원금 균등방식에서는 매월(또는 회차별) 상환원금은 동일하다.
		double paidPrincipal = CalculatorUtils.rounddown(loanBalance / period, 1);
		
		// 월지불 이자액
		double paidInterest;
		
		// 월지불액
		double payment;
		summaryInterest = 0;
		for (int i = 0; i < period; i++)
		{
			/*
			 * 연이자 금액 = 원금잔액 * 연이자율
			 * 월이자 금액 = 연이자 금액 / 12 (좀 더 정확하게 할 때에는 월일자/365 와 같은 식으로 계산)
			 * 이자금액이 월마다 달라지게 된다. 더 정확하게 보자면, 매년 연이자율만큼 이자를 계산해서 납부를 해야하지만,
			 * 원금납부로 인하여 원금잔액이 줄어들었으므로,
			 * 이에 맞춰서 월이자 금액을 매달 재계산하는 셈이다.
			 */
			paidInterest = (loanBalance * rate) / 12;// 이자 금액
			paidInterest = CalculatorUtils.roundup(paidInterest, 1);// 올림

			loanBalance = loanBalance - paidPrincipal;
			if (loanBalance < 0)
				loanBalance = 0;
			if (period - i == 1 && loanBalance > 0)
			{
				// 마지막차수일때, 잔액이 있다면. 잔액을 합침.
				paidPrincipal += loanBalance;
				loanBalance = 0;
			}
			payment = paidPrincipal + paidInterest;
			
			summaryInterest += paidInterest;
			
			// 스케쥴 리스트에 추가
			schedules.addSchedule(payment, paidPrincipal, paidInterest, loanBalance);
			
			//PaymentSchedules.Schedule schedule = new PaymentSchedules.Schedule(payment,paidPrincipal,paidInterest,loanBalance);
			//schedules.add(schedule);
		}
		if(options.isDebug()){
			System.out.println("===원금균등 ====");
			for (PaymentSchedules.Schedule s : schedules)
			{
				System.out.println(s.toString());
			}
		}
	}
	public PaymentSchedules getSchedules()
	{
		return schedules;
	}
	public double getSummaryInterest() {
		return summaryInterest;
	}	
}
