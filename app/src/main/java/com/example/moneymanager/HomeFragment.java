package com.example.moneymanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Model.Transaction;
import com.example.moneymanager.SQLite.TransactionDb;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView list_totalAmount;
    private TransactionDb transactionDb;
    private TransactionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.list);
        list_totalAmount = view.findViewById(R.id.list_totalAmount);

        transactionDb = new TransactionDb(getContext());

        List<Transaction> transactions = transactionDb.getAllTransactions();

        double totalAmount = list_totalAmount(transactions);
        list_totalAmount.setText(String.format("%.2f", totalAmount));

        adapter = new TransactionAdapter(transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private double list_totalAmount(List<Transaction> transactions) {
        double total = 0;
        for(Transaction transaction : transactions) {
            if("Expense".equals(transaction.getType())) {
                total -= transaction.getAmount();
            } else {
                total += transaction.getAmount();
            }
        }
        return total;
    }
}



