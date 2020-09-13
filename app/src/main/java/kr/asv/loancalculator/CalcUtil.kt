package kr.asv.loancalculator

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.pow

/**
 * 계산 관련 클래스
 *
 * 금융쪽으로 정교하게 계산하려면 Double 대신에 BigDecimal (또는 BigInteger) 을 사용해야 한다.
 * 이것을 사용하려면 기존의 operator 로는 문제가 발생하며, 맞는 메서드 add, subtract, multiply 등을 사용해야한다.
 * 그런데 이러면 다른 숫자 타입과 혼란이 생기므로, 이것을 하나로 묶어서 클래스화 하였다.
 *
 * 기억하기 쉽게 plus, minus, divide, multiply 로 명칭을 주었다.
 *
 * (사칙연산에서 인수가 혼합인 경우, 복잡한 것 (소수점 포함된 것) 이 앞에 인수)
 * (BigInteger, BigDecimal 의 나눗셈과 곱셈은 전부 BigDecimal 로 리턴)
 *
 *  사용되지 않는 메서드는 제거함. 안드로이드는 메서드 수가 많으면 별로 안 좋음...
 */
internal object CalcUtil {
    /*
    나눗셈 연산
    digits (소수점 자릿수)
    RoundingMode (소수점 처리)
      : CEILING 올림 (음수일 때 -5.5 -> -5)
        FLOOR 내림 (버림)
        UP 올림 (음수일 때 -5.5 -> -6)
        DOWN 내림 (음수일 때 -5.5 -> -5), HALF_UP, HALF_DOWN, HALF_EVEN
     */
    // (완료)
    fun divide(a: BigInteger, b: Int): BigInteger {
        return a.divide(b.toBigInteger())
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigInteger, b: Int, digits: Int, mode: RoundingMode): BigDecimal {
        return divide(a.toBigDecimal(), b.toBigDecimal(), digits, mode)
    }

    /**
     * BigInteger 끼리의 나눗셈은 소수점 이하는 버림.
     */
    @Suppress("unused")
    fun divide(a: BigInteger, b: BigInteger): BigInteger {
        return a.divide(b)
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigInteger, b: BigInteger, digits: Int, mode: RoundingMode): BigDecimal {
        return divide(a.toBigDecimal(), b.toBigDecimal(), digits, mode)
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigInteger, b: BigDecimal): BigDecimal {
        return convertBigDecimal(a).divide(b)
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigInteger, b: BigDecimal, digits: Int, mode: RoundingMode): BigDecimal {
        return divide(a.toBigDecimal(), b, digits, mode)
    }

    // (완료)
    fun divide(a: BigDecimal, b: Int): BigInteger {
        return divide(a, b.toBigDecimal(), 0, RoundingMode.DOWN).toBigInteger()
    }

    // (완료)
    fun divide(a: BigDecimal, b: Int, digits: Int, mode: RoundingMode): BigDecimal {
        return divide(a, b.toBigDecimal(), digits, mode)
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigDecimal, b: BigInteger): BigDecimal {
        return divide(a, b.toBigDecimal())
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigDecimal, b: BigInteger, digits: Int, mode: RoundingMode): BigDecimal {
        return divide(a, b.toBigDecimal(), digits, mode)
    }

    /**
     * 나눗셈 메서드.
     * 디테일한 옵션을 주지 않음. 기본값으로 처리.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun divide(a: BigDecimal, b: BigDecimal): BigDecimal {
        return a.divide(b)
    }

    /**
     * 나눗셈 메서드.
     * RoundingMode 를 통해서 디테일하게 옵션을 줄 수 있다.
     * digits : 자릿수.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun divide(a: BigDecimal, b: BigDecimal, digits: Int, mode: RoundingMode): BigDecimal {
        return a.divide(b, digits, mode)
    }

    /*
    더하기 연산
     */
    fun plus(a: Double, b: Double): Double {
        return a + b
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
    fun plus(a: BigInteger, b: Double): BigDecimal {
        return convertBigDecimal(a).add(convertBigDecimal(b))
    }

    // (완료)
    fun plus(a: BigInteger, b: BigInteger): BigInteger {
        return a.add(b)
    }

    // (완료)
    fun plus(a: BigInteger, b: BigDecimal): BigDecimal {
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



    // (완료)
    fun multiply(a: BigInteger?, b: BigDecimal?): BigDecimal {
        return multiply(b, a)
    }

    // (완료)
    @Suppress("MemberVisibilityCanBePrivate")
    fun multiply(a: BigDecimal?, b: BigInteger?): BigDecimal {
        return a!!.multiply(convertBigDecimal(b))
    }

    /**
     * 내림 함수
     * @param value 값
     * @param num_digits 자리수
     * @return double
     */
    fun roundDown(value: Double, num_digits: Int): Double {
        return floor(value / num_digits) * num_digits
    }

    fun roundDown(value: Int, num_digits: Int): Double {
        return floor(value / num_digits.toDouble()) * num_digits
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
     * @param value double
     * @param pow double
     * @return double
     */
    fun pow(value: Double, pow: Double): Double {
        return value.pow(pow)
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