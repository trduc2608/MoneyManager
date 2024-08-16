package com.example.moneymanager;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionViewHolder extends RecyclerView.ViewHolder {
    TextView tvDate, tvNote, tvAmount, tvEdit;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.list_date);
        tvNote = itemView.findViewById(R.id.list_note);
        tvAmount = itemView.findViewById(R.id.list_amount);
        tvEdit = itemView.findViewById(R.id.edit_list);
    }
}
