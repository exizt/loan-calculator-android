package kr.asv.calculators.loan;

public class CalculatorUtils
{
	/**
	 * 버림 함수
	 * @param value
	 * @param pow
	 * @return
	 */
	public static double rounddown(double value,int pow)
	{
		return Math.floor(value/pow) * pow;
	}
	/**
	 * 올림 함수
	 * @param value
	 * @param pow
	 * @return
	 */
	public static double roundup(double value, int pow)
	{
		return Math.ceil(value/pow) * pow;
	}
	public static double round(double value,int pow)
	{
		return Math.round(value/pow) + pow;
	}
	public static double pow(double a, double b)
	{
		return Math.pow(a, b);
	}
}
