package kr.asv.apps.loancalculator.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import kr.asv.apps.loancalculator.R
import kr.asv.loancalculator.PaymentSchedules

class MyScheduleRecyclerViewAdapter(private val mValues: PaymentSchedules) : RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder>() {
    private lateinit var view : View
    private val formatA = java.text.DecimalFormat("###,##0")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_schedule, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        //holder.mIdView.text = Integer.toString(position + 1)
        holder.mOrderView.text = view.resources.getString(R.string.schedule_count_suffix,Integer.toString(position + 1))
        holder.mPayment.text = formatA.format(mValues[position].payment)
        holder.mPaidPrincipal.text = formatA.format(mValues[position].paidPrincipal)
        holder.mPaidInterest.text = formatA.format(mValues[position].paidInterest)
        holder.mLoanBalance.text = formatA.format(mValues[position].loanBalance)

        holder.mView.setOnClickListener { }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //val mIdView: TextView = mView.findViewById<View>(R.id.id) as TextView
        val mOrderView: TextView = mView.findViewById<View>(R.id.order) as TextView
        val mPayment: TextView = mView.findViewById<View>(R.id.payment) as TextView
        val mPaidPrincipal: TextView = mView.findViewById<View>(R.id.paid_principal) as TextView
        val mPaidInterest: TextView = mView.findViewById<View>(R.id.paid_interest) as TextView
        val mLoanBalance: TextView = mView.findViewById<View>(R.id.loan_balance) as TextView
        var mItem: PaymentSchedules.Schedule? = null

        override fun toString(): String = super.toString() + " '" + mPayment.text + "'"
    }
}
