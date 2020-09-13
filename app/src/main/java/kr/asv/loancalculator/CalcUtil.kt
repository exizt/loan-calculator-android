package kr.asv.loancalculator

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*
import kotlin.math.abs
import kotlin.math.ceil
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
        return divide(a.toBigDecimal(),b)
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigInteger, b: BigDecimal, digits: Int, mode: RoundingMode): BigDecimal {
        return divide(a.toBigDecimal(), b, digits, mode)
    }

    // (완료)
    @Suppress("unused")
    fun divide(a: BigDecimal, b: Int): BigDecimal {
        return divide(a, b.toBigDecimal())
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

    /**
     * 버림 함수.
     * @param digits 0 : 소수점 이하 처리(정수화) / -n : 정수 단위 (-1 는 10 단위로, -2 는 100 단위로) / n : 소수점 단위. (1 은 소수점 1자리로, 2 는 소수점 2자리로)
     */
    @Suppress("unused")
    fun round(v: Double, digits: Int): Double {
        var multiplier = 1
        repeat( abs(digits) ) {
            multiplier *= 10
        }

        return if (digits > 0){
            // 소수점 이하 처리.
            floor(v * multiplier) / multiplier
        } else {
            // 소수점 이상 처리
            floor(v / multiplier) * multiplier
        }
    }

    fun round(v: BigInteger, digits: Int): BigInteger {
       return round(v.toDouble(), digits).toBigDecimal().toBigInteger()
    }

    /**
     * BigDecimal 의 Round 처리 메서드.
     * (미완성 메서드)
     *
     * BigDecimal 은 setScale 메서드를 사용하면 반올림/올림 처리를 할 수 있다.
     * 그런데 가끔 안 될 때가 있는데... 그 경우에 사용하는 메서드이다.
     * RoundingMode 에 따라서 처리한다.
     * @param digits 0 : 소수점 이하 처리(정수화) / -n : 정수 단위 (-1 는 10 단위로, -2 는 100 단위로) / n : 소수점 단위. (1 은 소수점 1자리로, 2 는 소수점 2자리로)
     */
    fun round(v: BigDecimal, digits: Int, mode: RoundingMode): BigDecimal{
        var multiplier = 1
        repeat( abs(digits) ) {
            multiplier *= 10
        }

        return if (digits > 0){
            // 소수점 이하 처리.
            //floor(v * multiplier) / multiplier
            multiplier *= 1000 // 세 자리 정도 더 여유를 남겨두기 위함.
            println("v [$v]")
            val t = (v * multiplier.toBigDecimal()).toBigInteger().toBigDecimal()
            println("t [$t]")
            t.divide(multiplier.toBigDecimal(), digits, mode)
        } else {
            // 정수 부분 이상 처리
            //floor(v / multiplier) * multiplier
            val tt = v.divide(multiplier.toBigDecimal(), digits, mode).toBigInteger()
            tt.toBigDecimal() * multiplier.toBigDecimal()
        }
    }

    /**
     * 올림 함수
     * @param value 값
     * @param pow 자리수
     * @return double
     */
    @Suppress("unused")
    fun roundUp(value: Double, pow: Int): Double {
        return ceil(value / pow) * pow
    }

    /**
     * 승수 (제곱)
     * @param value double
     * @param pow double
     * @return double
     */
    @Suppress("unused")
    fun pow(value: Double, pow: Double): Double {
        return value.pow(pow)
    }

    @Suppress("unused")
    fun pow(v: Number, pow:Number):Number{
        return if(v is Float){
            v.toFloat().pow(pow.toFloat())
        } else {
            v.toDouble().pow(pow.toDouble())
        }
    }

    /**
     * 다음달까지의 날짜를 계산. (LocalDate 는 API 26 이상에서 가능하므로 Calendar 사용함.)
     */
    @Suppress("MemberVisibilityCanBePrivate", "unused")
    fun betweenDaysAfterMonth(year: Int, month: Int, day: Int): Int{
        val startDate = getDate(year, month, day)
        val datePlusOneMonth = getPlusOneMonth(startDate)
        return betweenDays(startDate, datePlusOneMonth)
    }

    /**
     * Date 타입을 반환. (LocalDate 는 API 26 이상에서 가능하므로 Calendar 사용함.)
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getDate(year: Int, month: Int, day: Int): Date{
        return Calendar.getInstance().run {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month -1) // Calendar.JUN 같은 상수값이 실제 값과 1 차이가 나므로 오차 보정.
            set(Calendar.DAY_OF_MONTH, day)
            time
        }
    }

    @Suppress("MemberVisibilityCanBePrivate", "unused")
    fun getMonth(date: Date): Int{
        return Calendar.getInstance().run {
            this.time = date
            this.get(Calendar.MONTH)+1
        }
    }

    /**
     * 두 Date 간의 날짜를 구함. (LocalDate 는 API 26 이상에서 가능하므로 Calendar 사용함.)
     * 날짜수가 21억 이상 나오기는 힘드므로 리턴 타입은 Int 로 하기로 함.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun betweenDays(start: Date, end: Date): Int{
        return ((end.time - start.time) / (60*60*24*1000)).toInt()
    }

    /**
     * 한 달 뒤의 날짜 계산. (LocalDate 는 API 26 이상에서 가능하므로 Calendar 사용함.)
     */
    @Suppress("MemberVisibilityCanBePrivate", "unused")
    fun getPlusOneMonth(date: Date): Date{
        return Calendar.getInstance().run {
            this.time = date
            this.add(Calendar.MONTH, 1)
            this.time
        }
    }

    /**
     * 한 달 전의 날짜 계산. (LocalDate 는 API 26 이상에서 가능하므로 Calendar 사용함.)
     */
    @Suppress("MemberVisibilityCanBePrivate", "unused")
    fun getMinusOneMonth(date: Date): Date{
        return Calendar.getInstance().run {
            this.time = date
            this.add(Calendar.MONTH, -1)
            this.time
        }
    }

    /**
     * Date 에서 올해 몇 번째 날인지 구함.
     */
    @Suppress("MemberVisibilityCanBePrivate", "unused")
    fun getDayOfYear(date: Date): Int{
        return Calendar.getInstance().run {
            time = date
            get(Calendar.DAY_OF_YEAR)
        }
    }

    /**
     * Date 에서 year 를 기준으로 해당 해의 날짜 수를 구함.
     */
    fun getDaysOfYear(date: Date): Int{
        return Calendar.getInstance().run {
            time = date
            getActualMaximum(Calendar.DAY_OF_YEAR)
        }
    }

    /**
     * 해당 연도의 날짜 수를 구함.
     */
    @Suppress("unused")
    fun getDaysOfYear(year: Int): Int{
        return Calendar.getInstance().run {
            set(Calendar.YEAR, year)
            getActualMaximum(Calendar.DAY_OF_YEAR)
        }
    }

}