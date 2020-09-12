package kr.asv.loancalculator

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * 계산 관련 클래스
 * 빡이 쳐서 만든다 내가.
 * (사칙연산에서 인수가 혼합인 경우, 복잡한 것 (소수점 포함된 것) 이 앞에 인수)
 * (BigInteger, BigDecimal 의 나눗셈과 곱셈은 전부 BigDecimal 로 리턴)
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
internal object CalcUtil {
    /*
    더하기 연산
     */
    fun plus(a: Double, b: Double): Double {
        return a * b
    }

    // (완료)
    fun plus(a: BigInteger, b: Int): BigInteger {
        return a.add(convertBigInteger(b))
    }

    // (완료)
    fun plus(a: BigInteger, b: Long): BigInteger {
        return a.add(convertBigInteger(b))
    }

    // (완료)
    fun plus(a: BigInteger?, b: Double): BigDecimal {
        return convertBigDecimal(a).add(convertBigDecimal(b))
    }

    // (완료)
    fun plus(a: BigInteger?, b: BigInteger?): BigInteger {
        return a!!.add(b)
    }

    // (완료)
    fun plus(a: BigInteger?, b: BigDecimal): BigDecimal {
        return plus(b, a)
    }

    // (완료)
    fun plus(a: BigDecimal, b: Int): BigDecimal {
        return a.add(convertBigDecimal(b))
    }

    // (완료)
    fun plus(a: BigDecimal, b: BigInteger?): BigDecimal {
        return a.add(convertBigDecimal(b))
    }

    // (완료)
    fun plus(a: BigDecimal, b: BigDecimal?): BigDecimal {
        return a.add(b)
    }

    /*
    마이너스 연산
     */
    // (완료)
    fun minus(a: BigInteger, b: Int): BigInteger {
        return a.subtract(convertBigInteger(b))
    }

    // (완료)
    fun minus(a: BigInteger, b: Long): BigInteger {
        return a.subtract(convertBigInteger(b))
    }

    // (완료)
    fun minus(a: BigInteger?, b: Double): BigDecimal {
        return minus(convertBigDecimal(a), b)
    }

    // (완료)
    fun minus(a: BigInteger?, b: BigInteger?): BigInteger {
        return a!!.subtract(b)
    }

    // (완료)
    fun minus(a: BigInteger?, b: BigDecimal?): BigDecimal {
        return convertBigDecimal(a).subtract(b)
    }

    // (완료)
    fun minus(a: BigDecimal, b: Int): BigDecimal {
        return a.subtract(BigDecimal.valueOf(b.toLong()))
    }

    // (완료)
    fun minus(a: BigDecimal, b: Double): BigDecimal {
        return a.subtract(convertBigDecimal(b))
    }

    // (완료)
    fun minus(a: BigDecimal, b: BigInteger?): BigDecimal {
        return a.subtract(convertBigDecimal(b))
    }

    // (완료)
    fun minus(a: BigDecimal, b: BigDecimal?): BigDecimal {
        return a.subtract(b)
    }

    /*
    나눗셈 연산
    digits (소수점 자릿수)
    RoundingMode (소수점 처리) : CEILING 올림 (음수일 때 -5.5 -> -5), FLOOR 내림 (버림), UP 올림 (음수일 때 -5.5 -> -6), DOWN 내림 (음수일 때 -5.5 -> -5), HALF_UP, HALF_DOWN, HALF_EVEN
     */
    // (완료)
    fun divide(a: BigInteger?, b: Int): BigInteger {
        return a!!.divide(convertBigInteger(b))
    }

    // (완료)
    fun divide(a: BigInteger?, b: Int, digits: Int, mode: RoundingMode?): BigDecimal {
        return BigDecimal(a).divide(convertBigDecimal(b), digits, mode)
    }

    // (완료)
    fun divide(a: BigInteger, b: BigInteger?): BigInteger {
        return a.divide(b)
    }

    // (완료)
    fun divide(a: BigInteger?, b: BigInteger?, digits: Int, mode: RoundingMode?): BigDecimal {
        return BigDecimal(a).divide(convertBigDecimal(b), digits, mode)
    }

    // (완료)
    fun divide(a: BigInteger?, b: BigDecimal?): BigDecimal {
        return convertBigDecimal(a).divide(b)
    }

    // (완료)
    fun divide(a: BigInteger?, b: BigDecimal?, digits: Int, mode: RoundingMode?): BigDecimal {
        return convertBigDecimal(a).divide(b, digits, mode)
    }

    // (완료)
    fun divide(a: BigDecimal?, b: Int): BigInteger {
        return a!!.divide(convertBigDecimal(b), 0, RoundingMode.DOWN).toBigInteger()
    }

    // (완료)
    fun divide(a: BigDecimal, b: Int, digits: Int, mode: RoundingMode?): BigDecimal {
        return a.divide(convertBigDecimal(b), digits, mode)
    }

    // (완료)
    fun divide(a: BigDecimal, b: BigInteger?): BigDecimal {
        return a.divide(convertBigDecimal(b))
    }

    // (완료)
    fun divide(a: BigDecimal, b: BigInteger?, digits: Int, mode: RoundingMode?): BigDecimal {
        return a.divide(convertBigDecimal(b), digits, mode)
    }

    // (완료)
    fun divide(a: BigDecimal, b: BigDecimal?): BigDecimal {
        return a.divide(b)
    }

    // (완료)
    fun divide(a: BigDecimal, b: BigDecimal?, digits: Int, mode: RoundingMode?): BigDecimal {
        return a.divide(b, digits, mode)
    }

    /*
    곱하기 연산
     */
    // (완료)
    fun multiply(a: BigInteger, b: Int): BigInteger {
        return a.multiply(convertBigInteger(b))
    }

    // (완료)
    fun multiply(a: BigInteger, b: BigInteger?): BigInteger {
        return a.multiply(b)
    }

    // (완료)
    fun multiply(a: BigInteger?, b: BigDecimal?): BigDecimal {
        return multiply(b, a)
    }

    // (완료)
    fun multiply(a: BigDecimal, b: Int): BigDecimal {
        return a.multiply(convertBigDecimal(b))
    }

    // (완료)
    fun multiply(a: BigDecimal?, b: BigInteger?): BigDecimal {
        return a!!.multiply(convertBigDecimal(b))
    }

    // (완료)
    fun multiply(a: BigDecimal, b: BigDecimal?): BigDecimal {
        return a.multiply(b)
    }

    /**
     * 내림 함수
     * @param value 값
     * @param num_digits 자리수
     * @return double
     */
    fun roundDown(value: Double, num_digits: Int): Double {
        return Math.floor(value / num_digits) * num_digits
    }

    fun roundDown(value: Int, num_digits: Int): Double {
        return Math.floor(value / num_digits.toDouble()) * num_digits
    }

    /**
     * 올림 함수
     * @param value 값
     * @param pow 자리수
     * @return double
     */
    fun roundUp(value: Double, pow: Int): Double {
        return Math.ceil(value / pow) * pow
    }

    fun round(value: Double, pow: Int): Double {
        return (Math.round(value / pow) + pow).toDouble()
    }

    /**
     * 승수 (제곱)
     * @param a double
     * @param b double
     * @return double
     */
    fun pow(a: Double, b: Double): Double {
        return Math.pow(a, b)
    }

    fun convertBigInteger(a: Int): BigInteger {
        return BigInteger.valueOf(a.toLong())
    }

    fun convertBigInteger(a: Long): BigInteger {
        return BigInteger.valueOf(a)
    }

    fun convertBigDecimal(a: Int): BigDecimal {
        return BigDecimal.valueOf(a.toLong())
    }

    fun convertBigDecimal(a: Long): BigDecimal {
        return BigDecimal.valueOf(a)
    }

    fun convertBigDecimal(a: Double): BigDecimal {
        return BigDecimal.valueOf(a)
    }

    /**
     * longValue 를 뽑아오는 방식 을 선택하거나 new 를 통해서 메모리를 새로 참조하거나... 음..
     *
     * @param a BigInteger
     * @return BigDecimal
     */
    fun convertBigDecimal(a: BigInteger?): BigDecimal {
        return BigDecimal(a)
        //return BigDecimal.valueOf(a.longValue());
    }
}