package com.uottawa.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accounts;

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public AccountViewHolder(TextView v) {
            super(v);
            textView = v;
        }

    }

    private OnClick click;

    public AccountAdapter(List<Account> accounts, OnClick click) {
        this.click = click;
        this.accounts = accounts;
    }

    @Override
    public AccountAdapter.AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.accounts_text_view, parent, false);
        AccountViewHolder vh = new AccountViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, final int position) {
        holder.textView.setText(this.accounts.get(position).getUsername());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.clicked(accounts.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.accounts.size();
    }
}
