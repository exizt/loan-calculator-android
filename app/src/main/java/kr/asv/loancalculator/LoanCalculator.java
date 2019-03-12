package kr.asv.loancalculator;

/**
 * 대출이자 계산기
 */
public class LoanCalculator
{
	/**
	 * Options 값들. 가족수, 자녀수, 비과세액 등
	 */
	private LoanCalculatorOptions options;

	@SuppressWarnings("unused")
	public enum AmortizationMethods {
		FULL_AMORTIZATION, EQUAL_PRINCIPAL
	}

	private PaymentSchedules schedules;

	private double summaryInterest;
	/**
	 * 생성자
	 */
	public LoanCalculator()
	{
		this.initialize();
	}

	/**
	 * 생성자
	 * 
	 * @param options LoanCalculatorOptions
	 */
	public LoanCalculator(LoanCalculatorOptions options)
	{
		this.options = options;
		this.initialize();
	}

	/**
	 * 초기화 메서드.
	 * 생성시에 한번만 호출되는 부분.
	 */
	private void initialize()
	{
		if (this.options == null)
			this.options = new LoanCalculatorOptions();
		//equalPrincipalAmortization = new EqualPrincipalAmortization();
		//fullAmortization = new FullAmortization();
	}

	/**
	 * 
	 * @param options LoanCalculatorOptions
	 */
	@SuppressWarnings("unused")
	public void run(LoanCalculatorOptions options)
	{
		this.options = options;
		this.run();
	}

	/**
	 * 계산 실행
	 */
	public void run()
	{
		if (options.isDebug())
		{
			debug(options);
		}
		schedules = new PaymentSchedules();
		Amortization amortization;
		if (options.getAmortizationMethod() == AmortizationMethods.EQUAL_PRINCIPAL)
		{
			amortization = new EqualPrincipalAmortization();
			//schedules = equalPrincipalAmortization.calculate(options);// 원금 균등 분할 상환
		}
		else
		{
			amortization = new FullAmortization();
			//schedules = fullAmortization.calculate(options);// 원리금 균등 분할 상환
		}
		amortization.calculate(options);
		schedules = amortization.getSchedules();
		summaryInterest = amortization.getSummaryInterest();
	}

	private void debug(Object obj)
	{
		System.out.println(obj);
	}

	public LoanCalculatorOptions getOptions()
	{
		return this.options;
	}

	@SuppressWarnings("UnusedReturnValue")
	public PaymentSchedules getSchedules()
	{
		return schedules;
	}

	@SuppressWarnings("UnusedReturnValue")
	public double getSummaryInterest()
	{
		return summaryInterest;
	}
	
}