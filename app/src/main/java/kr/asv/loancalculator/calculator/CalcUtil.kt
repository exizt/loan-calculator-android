package kr.asv.loancalculator.calculator

import java.util.*

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