package kr.asv.apps.loancalculator.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.asv.apps.loancalculator.R;
import kr.asv.calculators.loan.PaymentSchedules;

public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder> {

    private final PaymentSchedules mValues;

    public MyScheduleRecyclerViewAdapter(PaymentSchedules items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Integer.toString(position+1));
        holder.mPayment.setText(Double.toString(mValues.get(position).payment));
        holder.mPaidPrincipal.setText(Double.toString(mValues.get(position).paidPrincipal));
        holder.mPaidInterest.setText(Double.toString(mValues.get(position).paidInterest));
        holder.mLoanBalance.setText(Double.toString(mValues.get(position).loanBalance));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mPayment;
        public final TextView mPaidPrincipal;
        public final TextView mPaidInterest;
        public final TextView mLoanBalance;

        public PaymentSchedules.Schedule mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mPayment = (TextView) view.findViewById(R.id.payment);
            mPaidPrincipal = (TextView) view.findViewById(R.id.paid_principal);
            mPaidInterest = (TextView) view.findViewById(R.id.paid_interest);
            mLoanBalance = (TextView) view.findViewById(R.id.loan_balance);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPayment.getText() + "'";
        }
    }
}
