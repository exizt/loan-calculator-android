package kr.asv.loancalculator.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.asv.loancalculator.app.databinding.FragmentScheduleBinding
import kr.asv.loancalculator.calculator.PaymentSchedules

class MyScheduleRecyclerViewAdapter(private val mValues: PaymentSchedules) : RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder>() {
    //private lateinit var view : View
    private val formatA = java.text.DecimalFormat("###,##0")

    inner class ViewHolder(private val itemBinding: FragmentScheduleBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private var mItem: PaymentSchedules.Schedule? = null

        //val mIdView: TextView = mView.findViewById<View>(R.id.id) as TextView
        //val mOrderView: TextView = mView.findViewById<View>(R.id.order) as TextView
        //val mPayment: TextView = mView.findViewById<View>(R.id.payment) as TextView
        //val mPaidPrincipal: TextView = mView.findViewById<View>(R.id.paid_principal) as TextView
        //val mPaidInterest: TextView = mView.findViewById<View>(R.id.paid_interest) as TextView
        //val mLoanBalance: TextView = mView.findViewById<View>(R.id.loan_balance) as TextView

        fun bind(item: PaymentSchedules.Schedule, position:Int){
            mItem = item
            itemBinding.payment.text = formatA.format(item.payment)
            itemBinding.paidPrincipal.text = formatA.format(item.paidPrincipal)
            itemBinding.paidInterest.text = formatA.format(item.paidInterest)
            itemBinding.loanBalance.text = formatA.format(item.loanBalance)
            itemBinding.order.text = itemBinding.root.context.getString(R.string.schedule_count_suffix, (position + 1).toString())
        }
        //override fun toString(): String = super.toString() + " '" + mPayment.text + "'"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FragmentScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
        //view = LayoutInflater.from(parent.context)
        //        .inflate(R.layout.fragment_schedule, parent, false)
        //return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.mItem = mValues[position]
        //holder.mIdView.text = Integer.toString(position + 1)
        //holder.mOrderView.text = view.resources.getString(R.string.schedule_count_suffix, (position + 1).toString())
        //holder.mPayment.text = formatA.format(mValues[position].payment)
        //holder.mPaidPrincipal.text = formatA.format(mValues[position].paidPrincipal)
        //holder.mPaidInterest.text = formatA.format(mValues[position].paidInterest)
        //holder.mLoanBalance.text = formatA.format(mValues[position].loanBalance)

        holder.bind(mValues[position], position)
    }

    override fun getItemCount(): Int = mValues.size


}
