package kr.asv.loancalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * 계산 관련 클래스
 * 빡이 쳐서 만든다 내가.
 * (사칙연산에서 인수가 혼합인 경우, 복잡한 것 (소수점 포함된 것) 이 앞에 인수)
 * (BigInteger, BigDecimal 의 나눗셈과 곱셈은 전부 BigDecimal 로 리턴)
 */
@SuppressWarnings({"WeakerAccess", "unused"})
class CalcUtil
{
    /*
    더하기 연산
     */
    static double plus(double a, double b){
        return a*b;
    }

    // (완료)
    static BigInteger plus(BigInteger a, int b){
        return a.add(convertBigInteger(b));
    }

    // (완료)
    static BigInteger plus(BigInteger a, long b){
        return a.add(convertBigInteger(b));
    }

    // (완료)
    static BigDecimal plus(BigInteger a, double b){
        return convertBigDecimal(a).add(convertBigDecimal(b));
    }

    // (완료)
    static BigInteger plus(BigInteger a, BigInteger b){
        return a.add(b);
    }

    // (완료)
    static BigDecimal plus(BigInteger a, BigDecimal b){
        return plus(b,a);
    }

    // (완료)
    static BigDecimal plus(BigDecimal a, int b){
        return a.add(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal plus(BigDecimal a, BigInteger b){
        return a.add(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal plus(BigDecimal a, BigDecimal b){
        return a.add(b);
    }

    /*
    마이너스 연산
     */
    // (완료)
    static BigInteger minus(BigInteger a, int b){
        return a.subtract(convertBigInteger(b));
    }

    // (완료)
    static BigInteger minus(BigInteger a, long b){
        return a.subtract(convertBigInteger(b));
    }


    // (완료)
    static BigDecimal minus(BigInteger a, double b){
        return minus(convertBigDecimal(a),b);
    }

    // (완료)
    static BigInteger minus(BigInteger a, BigInteger b){
        return a.subtract(b);
    }

    // (완료)
    static BigDecimal minus(BigInteger a, BigDecimal b){
        return convertBigDecimal(a).subtract(b);
    }

    // (완료)
    static BigDecimal minus(BigDecimal a, int b){
        return a.subtract(BigDecimal.valueOf(b));
    }

    // (완료)
    static BigDecimal minus(BigDecimal a, double b){
        return a.subtract(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal minus(BigDecimal a, BigInteger b){
        return a.subtract(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal minus(BigDecimal a, BigDecimal b){
        return a.subtract(b);
    }

    /*
    나눗셈 연산
    digits (소수점 자릿수)
    RoundingMode (소수점 처리) : CEILING 올림 (음수일 때 -5.5 -> -5), FLOOR 내림 (버림), UP 올림 (음수일 때 -5.5 -> -6), DOWN 내림 (음수일 때 -5.5 -> -5), HALF_UP, HALF_DOWN, HALF_EVEN
     */

    // (완료)
    static BigInteger divide(BigInteger a, int b) {
        return a.divide(convertBigInteger(b));
    }

    // (완료)
    static BigDecimal divide(BigInteger a, int b, int digits, RoundingMode mode) {
        return new BigDecimal(a).divide(convertBigDecimal(b),digits,mode);
    }

    // (완료)
    static BigInteger divide(BigInteger a, BigInteger b){
        return a.divide(b);
    }

    // (완료)
    static BigDecimal divide(BigInteger a, BigInteger b, int digits, RoundingMode mode){
        return new BigDecimal(a).divide(convertBigDecimal(b),digits,mode);
    }

    // (완료)
    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    static BigDecimal divide(BigInteger a, BigDecimal b){
        return convertBigDecimal(a).divide(b);
    }

    // (완료)
    static BigDecimal divide(BigInteger a, BigDecimal b, int digits, RoundingMode mode){
        return convertBigDecimal(a).divide(b,digits,mode);
    }

    // (완료)
    @SuppressWarnings("SameParameterValue")
    static BigInteger divide(BigDecimal a, int b) {
        return a.divide(convertBigDecimal(b),0,RoundingMode.DOWN).toBigInteger();
    }

    // (완료)
    @SuppressWarnings("SameParameterValue")
    static BigDecimal divide(BigDecimal a, int b, int digits, RoundingMode mode){
        return a.divide(convertBigDecimal(b),digits,mode);
    }

    // (완료)
    @SuppressWarnings("SameParameterValue")
    static BigDecimal divide(BigDecimal a, BigInteger b){
        //noinspection BigDecimalMethodWithoutRoundingCalled
        return a.divide(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal divide(BigDecimal a, BigInteger b, int digits, RoundingMode mode){
        return a.divide(convertBigDecimal(b),digits,mode);
    }

    // (완료)
    @SuppressWarnings({"SameParameterValue", "BigDecimalMethodWithoutRoundingCalled"})
    static BigDecimal divide(BigDecimal a, BigDecimal b){
        return a.divide(b);
    }

    // (완료)
    static BigDecimal divide(BigDecimal a, BigDecimal b, int digits, RoundingMode mode){
        return a.divide(b,digits,mode);
    }

    /*
    곱하기 연산
     */
    // (완료)
    static BigInteger multiply(BigInteger a, int b){
        return a.multiply(convertBigInteger(b));
    }

    // (완료)
    static BigInteger multiply(BigInteger a, BigInteger b){
        return a.multiply(b);
    }

    // (완료)
    static BigDecimal multiply(BigInteger a, BigDecimal b){
        return multiply(b,a);
    }

    // (완료)
    static BigDecimal multiply(BigDecimal a, int b){
        return a.multiply(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal multiply(BigDecimal a, BigInteger b){
        return a.multiply(convertBigDecimal(b));
    }

    // (완료)
    static BigDecimal multiply(BigDecimal a, BigDecimal b){
        return a.multiply(b);
    }


    /**
     * 내림 함수
     * @param value 값
     * @param num_digits 자리수
     * @return double
     */
    @SuppressWarnings("SameParameterValue")
    static double roundDown(double value, int num_digits)
    {
        return Math.floor(value/num_digits) * num_digits;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    static double roundDown(int value, int num_digits)
    {
        return Math.floor(value/num_digits) * num_digits;
    }

    /**
     * 올림 함수
     * @param value 값
     * @param pow 자리수
     * @return double
     */
    @SuppressWarnings("SameParameterValue")
    static double roundUp(double value, int pow)
    {
        return Math.ceil(value/pow) * pow;
    }

    @SuppressWarnings("unused")
    public static double round(double value, int pow)
    {
        return Math.round(value/pow) + pow;
    }

    /**
     * 승수 (제곱)
     * @param a double
     * @param b double
     * @return double
     */
    static double pow(double a, double b)
    {
        return Math.pow(a, b);
    }


    static BigInteger convertBigInteger(int a){
        return BigInteger.valueOf(a);
    }

    static BigInteger convertBigInteger(long a){
        return BigInteger.valueOf(a);
    }

    static BigDecimal convertBigDecimal(int a){
        return BigDecimal.valueOf(a);
    }

    static BigDecimal convertBigDecimal(long a){
        return BigDecimal.valueOf(a);
    }

    static BigDecimal convertBigDecimal(double a){
        return BigDecimal.valueOf(a);
    }

    /**
     * longValue 를 뽑아오는 방식 을 선택하거나 new 를 통해서 메모리를 새로 참조하거나... 음..
     *
     * @param a BigInteger
     * @return BigDecimal
     */
    static BigDecimal convertBigDecimal(BigInteger a){
        return new BigDecimal(a);
        //return BigDecimal.valueOf(a.longValue());
    }
}
