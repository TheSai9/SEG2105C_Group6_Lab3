package com.example.lab3;

import android.os.Build;
import androidx.test.core.app.ApplicationProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.TIRAMISU}) // Updated from P to TIRAMISU (API 33)
public class MyDBHandlerTest {

    private MyDBHandler dbHandler;

    @Before
    public void setUp() {
        dbHandler = new MyDBHandler(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        dbHandler.close();
    }

    @Test
    public void testAddProduct_Success() {
        Product product = new Product("Test Product", 10.99);
        dbHandler.addProduct(product);

        // To fail this test, comment out the addProduct line above and uncomment the line below
        // // dbHandler.addProduct(new Product("Wrong Product", 0.0));

        android.database.Cursor cursor = dbHandler.findProduct("Test Product", "");
        assertTrue("Product should be found in the database", cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void testDeleteProduct_Success() {
        Product product = new Product("Deletable Product", 99.99);
        dbHandler.addProduct(product);

        // To fail this test, pass a non-existent product name to deleteProduct()
        // // boolean result = dbHandler.deleteProduct("Non-existent Product");
        boolean result = dbHandler.deleteProduct("Deletable Product");

        assertTrue("Delete operation should return true for an existing product", result);
    }
}
