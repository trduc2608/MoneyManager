package com.example.moneymanager;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneymanager.Model.Transaction;
import com.example.moneymanager.SQLite.TransactionDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputFragment extends Fragment {
    private TextView tvExpense;
    private Button btnExpense, btnIncome, btnPreviousDate, btnSelectedDate, btnNextDate, submitBtn;
    private EditText etNote, etAmount;
    private Calendar calendar;
    private TransactionDb transactionDb;

    private int transactionId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        tvExpense = view.findViewById(R.id.tvExpense);
        btnExpense = view.findViewById(R.id.btnExpense);
        btnIncome = view.findViewById(R.id.btnIncome);
        btnPreviousDate = view.findViewById(R.id.btnPreviousDate);
        btnSelectedDate = view.findViewById(R.id.btnSelectedDate);
        btnNextDate = view.findViewById(R.id.btnNextDate);
        submitBtn = view.findViewById(R.id.submit_btn);
        etNote = view.findViewById(R.id.etNote);
        etAmount = view.findViewById(R.id.etAmount);

        transactionDb = new TransactionDb(getContext());

        // Check if we are in edit mode
        if (getArguments() != null) {
            transactionId = getArguments().getInt("id", -1);
            String date = getArguments().getString("date", "");
            String note = getArguments().getString("note", "");
            double amount = getArguments().getDouble("amount", 0.0);
            String type = getArguments().getString("type", "");

            // Set data to the fields
            btnSelectedDate.setText(date);
            etNote.setText(note);
            etAmount.setText(String.format(Locale.getDefault(), "%.2f", amount));
            if ("Expense".equals(type)) {
                tvExpense.setText(R.string.expense_tv);
                btnExpense.performClick();
            } else {
                tvExpense.setText(R.string.income_tv);
                btnIncome.performClick();
            }
        }

        // Set Listeners
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvExpense.setText(R.string.expense_tv);

                btnExpense.setTextColor(Color.parseColor("#FFFFFF"));
                btnExpense.setBackgroundColor(Color.parseColor("#FFC0CB"));
                btnIncome.setBackgroundColor(Color.parseColor("#DFD7D7"));
                btnIncome.setTextColor(Color.parseColor("#F88398"));
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvExpense.setText(R.string.income_tv);

                btnIncome.setTextColor(Color.parseColor("#FFFFFF"));
                btnIncome.setBackgroundColor(Color.parseColor("#FFC0CB"));
                btnExpense.setBackgroundColor(Color.parseColor("#DFD7D7"));
                btnExpense.setTextColor(Color.parseColor("#F88398"));
            }
        });

        calendar = Calendar.getInstance();
        updateDateButton();

        btnPreviousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                updateDateButton();
            }
        });

        btnNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                updateDateButton();
            }
        });

        btnSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        return view;
    }

    private void onSubmit() {
        String note = etNote.getText().toString();
        String amoutStr = etAmount.getText().toString();
        String date = btnSelectedDate.getText().toString();
        String selectedType = tvExpense.getText().toString();
        double amount = 0.0;

        if (!TextUtils.isEmpty(amoutStr)) {
            amount = Double.parseDouble(amoutStr);
        }

        boolean isSuccess;

        if (transactionId == -1) {
            // New transaction
            Transaction transaction = new Transaction(0, date, note, amount, selectedType);
            isSuccess = transactionDb.addTransaction(transaction);
        } else {
            // Update existing transaction
            Transaction transaction = new Transaction(transactionId, date, note, amount, selectedType);
            isSuccess = transactionDb.updateTransaction(transaction);
        }

        if(isSuccess) {
            Toast.makeText(getActivity(), "Transaction saved successfully!", Toast.LENGTH_SHORT).show();
            navigateToHomeFragment();
        } else {
            Toast.makeText(getActivity(), "Failed to save transaction!", Toast.LENGTH_SHORT).show();
        }

        // Optionally clear the inputs after submission for new transactions
        if (transactionId == -1) {
            etNote.setText("");
            etAmount.setText("");
        }
    }

    private void navigateToHomeFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateButton();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateDateButton() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        btnSelectedDate.setText(sdf.format(calendar.getTime()));
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
