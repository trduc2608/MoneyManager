package com.example.moneymanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Model.Transaction;
import com.example.moneymanager.SQLite.TransactionDb;

import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    private List<Transaction> transactions;
    private TransactionDb transactionDb;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
        this.transactionDb = transactionDb;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.tvDate.setText(transaction.getDate());
        holder.tvNote.setText(transaction.getNote());
//        holder.tvAmount.setText(String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));

        if ("Expense".equals(transaction.getType())) {
            holder.tvAmount.setTextColor(Color.RED);
            holder.tvAmount.setText("-" + String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));
        } else {
            holder.tvAmount.setTextColor(Color.GREEN);
            holder.tvAmount.setText("+" + String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));
        }

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the fragment
                InputFragment inputFragment = new InputFragment();

                // Create a bundle to pass the data
                Bundle args = new Bundle();
                args.putInt("id", transaction.getId());
                args.putString("date", transaction.getDate());
                args.putString("note", transaction.getNote());
                args.putDouble("amount", transaction.getAmount());
                args.putString("type", transaction.getType());

                // Set the arguments to the fragment
                inputFragment.setArguments(args);

                // Replace the fragment (assuming you have a FragmentActivity or a fragment manager)
                ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, inputFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

}
