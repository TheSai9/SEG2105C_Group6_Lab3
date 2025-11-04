package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_PRICE + " DOUBLE" + ")";
        db.execSQL(create_table_cmd);

        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_PRODUCT_NAME, "Product_1");
        values1.put(COLUMN_PRODUCT_PRICE, 10.00);
        db.insert(TABLE_NAME, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_PRODUCT_NAME, "Product_2");
        values2.put(COLUMN_PRODUCT_PRICE, 20.00);
        db.insert(TABLE_NAME, null, values2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_PRODUCT_PRICE, product.getProductPrice());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor findProduct(String productName, String productPrice) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ";
        ArrayList<String> args = new ArrayList<>();
        boolean hasName = productName != null && !productName.trim().isEmpty();
        boolean hasPrice = productPrice != null && !productPrice.trim().isEmpty();

        if (!hasName && !hasPrice) {
            return getData();
        }

        if (hasName) {
            query += COLUMN_PRODUCT_NAME + " LIKE ?";
            args.add(productName + "%");
        }

        if (hasPrice) {
            if (hasName) {
                query += " AND ";
            }
            query += COLUMN_PRODUCT_PRICE + " = ?";
            args.add(productPrice);
        }

        return db.rawQuery(query, args.toArray(new String[0]));
    }

    public boolean deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_PRODUCT_NAME + " = ?";
        int result = db.delete(TABLE_NAME, whereClause, new String[]{productName});
        db.close();
        return result > 0;
    }
}