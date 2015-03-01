package com.android.cashr.adaptors;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.cashr.R;
import com.android.cashr.activities.AccountsListActivity;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> implements Filterable {
    private int mRowLayout;
    private AccountsListActivity mCardActivity;

    private List<String> mAccountsOriginalList;
    private List<String> mAccountsFilteredList;

    private DeviceFilter mDeviceFilter;

    public AccountAdapter(int rowLayout, AccountsListActivity accountsListActivity, List<String> accountList) {
        mRowLayout = rowLayout;
        mCardActivity = accountsListActivity;

        mAccountsOriginalList = accountList;
        mAccountsFilteredList = accountList;
        mDeviceFilter = new DeviceFilter();
    }

    public void clearDevices() {
        int size = mAccountsFilteredList.size();
        for(int i = 0; i < size; ++i) {
            mAccountsFilteredList.remove(0);
        }
        notifyItemRangeRemoved(0, size);
    }

    public void addAccounts(List<String> accounts) {
        mAccountsFilteredList.addAll(accounts);
        notifyItemRangeInserted(0, mAccountsFilteredList.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final String accountInfo = mAccountsFilteredList.get(i);

        Log.d("USERNAME: ", accountInfo);

        viewHolder.mTextName.setText(accountInfo);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardActivity.animateActivity(accountInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mAccountsFilteredList == null) ? 0 : mAccountsFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return mDeviceFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextName;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextName = (TextView) itemView.findViewById(R.id.txt_name);
        }
    }

    private class DeviceFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = mAccountsOriginalList;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                final String accountInfo = list.get(i);
                final String filterableString = list.get(i).toString();

                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(accountInfo);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAccountsFilteredList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }
}
