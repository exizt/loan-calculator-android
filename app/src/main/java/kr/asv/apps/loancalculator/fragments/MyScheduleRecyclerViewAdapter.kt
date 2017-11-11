package kr.asv.apps.loancalculator.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import kr.asv.apps.loancalculator.R
import kr.asv.calculators.loan.PaymentSchedules

class MyScheduleRecyclerViewAdapter(private val mValues: PaymentSchedules) : RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = Integer.toString(position + 1)
        holder.mPayment.text = java.lang.Double.toString(mValues[position].payment)
        holder.mPaidPrincipal.text = java.lang.Double.toString(mValues[position].paidPrincipal)
        holder.mPaidInterest.text = java.lang.Double.toString(mValues[position].paidInterest)
        holder.mLoanBalance.text = java.lang.Double.toString(mValues[position].loanBalance)

        holder.mView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mPayment: TextView
        val mPaidPrincipal: TextView
        val mPaidInterest: TextView
        val mLoanBalance: TextView

        var mItem: PaymentSchedules.Schedule? = null

        init {
            mIdView = mView.findViewById<View>(R.id.id) as TextView
            mPayment = mView.findViewById<View>(R.id.payment) as TextView
            mPaidPrincipal = mView.findViewById<View>(R.id.paid_principal) as TextView
            mPaidInterest = mView.findViewById<View>(R.id.paid_interest) as TextView
            mLoanBalance = mView.findViewById<View>(R.id.loan_balance) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mPayment.text + "'"
        }
    }
}
