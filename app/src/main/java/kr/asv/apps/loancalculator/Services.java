package kr.asv.apps.loancalculator;

import android.content.Context;
import android.util.Log;

import kr.asv.loancalculator.LoanCalculator;

/**
 * 어플 전체적으로 활용되는 기능들을 모아두는 클래스
 * Created by EXIZT on 2016-04-27.
 */
public class Services {
	private static final Services instance = new Services();
	private final LoanCalculator calculator = new LoanCalculator();
	public CalculatorMethods calculatorMethod;

	public enum CalculatorMethods {EQUAL_PRINCIPAL, FULL_AMORTIZATION}

	/**
	 * 생성자
	 */
	private Services() {
		init();
	}

	/**
	 * @return instance:Services
	 */
	@SuppressWarnings("unused")
	public static Services getInstance() {
		return instance;
	}

	@SuppressWarnings("unused")
	public static Services getInstance(Context context) {
		instance.load(context);
		return instance;
	}

	@SuppressWarnings({"unused", "EmptyMethod"})
	private void init() {
	}

	@SuppressWarnings({"unused", "EmptyMethod"})
	private void load(Context context) {

	}

	public LoanCalculator getCalculator() {
		return calculator;
	}

	/**
	 * 디버깅
	 *
	 * @param msg string
	 */
	@SuppressWarnings("unused")
	public void debug(String msg) {
		Log.e("[EXIZT-DEBUG]", msg);
	}
}
