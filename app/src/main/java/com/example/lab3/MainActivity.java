package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText productName, productPrice;
    Button addBtn, findBtn, deleteBtn;
    ListView productListView;

    ArrayList<String> productList;
    ArrayAdapter<String> adapter;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);

        addBtn = findViewById(R.id.addBtn);
        findBtn = findViewById(R.id.findBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        productListView = findViewById(R.id.productListView);

        dbHandler = new MyDBHandler(this);

        addBtn.setOnClickListener(v -> {
            String name = productName.getText().toString();
            String priceStr = productPrice.getText().toString();

            if (name.trim().isEmpty() || priceStr.trim().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both name and price.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                Product product = new Product(name, price);
                dbHandler.addProduct(product);

                productName.setText("");
                productPrice.setText("");
                Toast.makeText(MainActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                loadProductList();
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid price format.", Toast.LENGTH_SHORT).show();
            }
        });

        findBtn.setOnClickListener(v -> {
            String name = productName.getText().toString();
            String price = productPrice.getText().toString();

            if (name.trim().isEmpty() && price.trim().isEmpty()) {
                loadProductList();
                Toast.makeText(MainActivity.this, "Displaying all products.", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = dbHandler.findProduct(name, price);
                displayData(cursor);
                Toast.makeText(MainActivity.this, "Search complete.", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(v -> {
            String name = productName.getText().toString();
            if (name.trim().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a product name to delete.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHandler.deleteProduct(name)) {
                productName.setText("");
                productPrice.setText("");
                Toast.makeText(MainActivity.this, "Product Deleted.", Toast.LENGTH_SHORT).show();
                loadProductList();
            } else {
                Toast.makeText(MainActivity.this, "Product not found for deletion.", Toast.LENGTH_SHORT).show();
            }
        });

        loadProductList();
    }

    private void loadProductList() {
        Cursor cursor = dbHandler.getData();
        displayData(cursor);
    }

    private void displayData(Cursor cursor) {
        productList.clear();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            int nameIndex = cursor.getColumnIndex("name");
            int priceIndex = cursor.getColumnIndex("price");
            while (cursor.moveToNext()) {
                productList.add(cursor.getString(nameIndex) + " ($" + cursor.getString(priceIndex) + ")");
            }
        }

        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
            productListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        
        cursor.close();
    }
}