package kr.asv.loancalculator;

class CalculatorUtils
{
	/**
	 * 내림 함수
	 * @param value 값
	 * @param pow 자리수
	 * @return double
	 */
	@SuppressWarnings("SameParameterValue")
	static double rounddown(double value, int pow)
	{
		return Math.floor(value/pow) * pow;
	}

	/**
	 * 올림 함수
	 * @param value 값
	 * @param pow 자리수
	 * @return double
	 */
	@SuppressWarnings("SameParameterValue")
	static double roundup(double value, int pow)
	{
		return Math.ceil(value/pow) * pow;
	}

	@SuppressWarnings("unused")
	public static double round(double value, int pow)
	{
		return Math.round(value/pow) + pow;
	}

	static double pow(double a, double b)
	{
		return Math.pow(a, b);
	}
}
