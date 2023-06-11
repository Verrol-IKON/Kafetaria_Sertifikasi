package com.example.kafetaria_sertifikasi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kafetaria_sertifikasi.model.CartItem;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Kafetaria.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table kafe(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("create Table menu(id INTEGER PRIMARY KEY AUTOINCREMENT, kafe_id INTEGER, jenis TEXT, nama TEXT, harga INTEGER, FOREIGN KEY (kafe_id) REFERENCES kafe (id))");
        db.execSQL("create Table transactions(id INTEGER PRIMARY KEY AUTOINCREMENT, total_item INTEGER, total_harga INTEGER)");
        db.execSQL("create Table transaction_item(id INTEGER PRIMARY KEY AUTOINCREMENT, transaction_id INTEGER, menu_id INTEGER, total_item INTEGER, total_harga INTEGER, FOREIGN KEY (transaction_id) REFERENCES transactions (id), FOREIGN KEY (menu_id) REFERENCES menu (id))");
        db.execSQL("create Table cart(id INTEGER PRIMARY KEY AUTOINCREMENT, menu_id INTEGER, total_item INTEGER, total_harga INTEGER, FOREIGN KEY (menu_id) REFERENCES menu (id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists kafe");
        db.execSQL("drop Table if exists menu");
        db.execSQL("drop Table if exists transactions");
        db.execSQL("drop Table if exists transaction_item");
        db.execSQL("drop Table if exists cart");
    }

    public Cursor getMenu(Integer kafe_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from menu where kafe_id=" + kafe_id.toString(), null);
        return cursor;
    }

    public Cursor getMenuItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from menu where id=" + id.toString(), null);
        return cursor;
    }

    public Boolean addToCart(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("menu_id", cartItem.getMenu_id());
        contentValues.put("total_item", cartItem.getTotal_item());
        contentValues.put("total_harga", cartItem.getTotal_harga());
        long result = db.insert("cart", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from cart", null);
        return cursor;
    }

    public Boolean deleteCartItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("cart", "id=?", new String[]{id.toString()});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean emptyCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("cart", null, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public long makeTransaction(Integer total_item, Integer total_harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total_item", total_item);
        contentValues.put("total_harga", total_harga);
        long result = db.insert("transactions", null, contentValues);
        if (result == -1) {
            return -1;
        } else {
            return result;
        }
    }

    public Boolean makeTransactionItem(Integer transaction_id, Integer menu_id, Integer total_item, Integer total_harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("transaction_id", transaction_id);
        contentValues.put("menu_id", menu_id);
        contentValues.put("total_item", total_item);
        contentValues.put("total_harga", total_harga);
        long result = db.insert("transaction_item", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from transactions", null);
        return cursor;
    }

    public Cursor getTransactionItems(Integer transaction_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from transaction_item where transaction_id=" + transaction_id.toString(), null);
        return cursor;
    }
}
