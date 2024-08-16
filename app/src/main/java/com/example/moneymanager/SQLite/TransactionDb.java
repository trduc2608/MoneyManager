package com.example.moneymanager.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.moneymanager.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "asm_expense";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "transactions";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_TYPE = "type";

    public TransactionDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_NOTE + " TEXT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_TYPE + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addTransaction(Transaction transaction)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, transaction.getDate());
        values.put(COLUMN_NOTE, transaction.getNote());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_TYPE, transaction.getType());

        long result = -1;
        try {
            result = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the error
        } finally {
            db.close(); // Ensure the database is closed after the operation
        }

        return result != -1;
    }

//    public boolean deleteTransaction(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int result = -1;
//
//        try {
//            result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle or log the error
//        }
//
//        return result > 0; // Return true if at least one row was deleted
//    }


    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM transactions", null);

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                String note = cursor.getString(2);
                double amount = cursor.getDouble(3);
                String type = cursor.getString(4);
                transactions.add(new Transaction(id, date, note, amount, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public boolean updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, transaction.getDate());
        values.put(COLUMN_NOTE, transaction.getNote());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_TYPE, transaction.getType());

        int result = -1;

        try {
            result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(transaction.getId())});
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the error
        } finally {
            db.close(); // Ensure the database is closed after the operation
        }

        return result > 0; // Return true if at least one row was updated
    }
}
