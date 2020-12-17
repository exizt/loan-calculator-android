package kr.asv.loancalculator.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigInteger

import java.text.DecimalFormat

/**
 * MoneyTextEditor 확장하는 클래스
 * Created by EXIZT on 2016-05-03.
 */
class MoneyTextWatcher(private val editText: EditText) : TextWatcher {
    override fun afterTextChanged(s: Editable) {

        //입력이 끝났을 때
        editText.removeTextChangedListener(this)

        try {
            var givenString = s.toString()
            if (givenString.contains(",")) {
                givenString = givenString.replace(",".toRegex(), "")
            }
            //val longValue: Long
            //longValue = java.lang.Long.parseLong(givenString)
            //longValue = givenString.toLong()
            val formatter = DecimalFormat("#,###,###")
            //val formattedString = formatter.format(java.lang.Long.parseLong(givenString))

            //val formattedString = formatter.format(givenString.toLong())
            val formattedString = formatter.format(BigInteger(givenString))
            editText.setText(formattedString)
            editText.setSelection(editText.text.length)
            // to place the cursor at the end of text
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        //입력하기 전에
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //입력되는 텍스트에 변화가 있을 때
    }

    companion object {

        @Suppress("MemberVisibilityCanBePrivate")
        fun getNumberString(editText: EditText): String{
            return try {
                var text = editText.text.toString()

                if (text.contains(",")) {
                    text = text.replace(",".toRegex(), "")
                }
                return text
            } catch (e: Exception) {
                "0"
            }
        }
        /**
         * long 타입으로 반환하는 메서드
         *
         * @param editText EditText
         * @return long
         */
        fun getValue(editText: EditText): Long {
            return try {
                //double result =  Double.parseDouble();
                java.lang.Long.parseLong(getNumberString(editText))
            } catch (e: Exception) {
                0
            }
        }

        @Suppress("unused")
        fun getBigInteger(editText: EditText): BigInteger{
            return try {
                //double result =  Double.parseDouble();
                BigInteger(getNumberString(editText))
            } catch (e: Exception) {
                BigInteger("0")
            }
        }
    }
}
