package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;
    private int pricePerCoffee = 5;
    private int priceForWhippedCream = 1;
    private int priceForChocolate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.checkbox_whipped_cream)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.checkbox_chocolate)).isChecked();
        String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);
    }

    /**
     * This method is called to calculate the total price.
     *
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = pricePerCoffee;
        if (hasWhippedCream) {
            basePrice += priceForWhippedCream;
        }
        if (hasChocolate) {
            basePrice += priceForChocolate;
        }
        return (basePrice * quantity);
    }

    /**
     * This method creates the order summary to be displayed
     */
    private String createOrderSummary(String name, boolean hasWhippedCream, boolean hasChocolate) {
        int total = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = "Name: " + name;
        orderSummary += "\nAdd whipped cream? " + hasWhippedCream;
        orderSummary += "\nAdd chocolate? " + hasChocolate;
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nTotal: " + NumberFormat.getCurrencyInstance().format(total);
        orderSummary += "\nThank you!";
        return orderSummary;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementQuantity(View view) {
        Toast toast = Toast.makeText(this, "Cannot set quantity above 100", Toast.LENGTH_SHORT);
        if (quantity >= 100) {
            quantity = 100;
            toast.show();
        }
        else {
            quantity++;
        }
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrementQuantity(View view) {
        Toast toast = Toast.makeText(this, "Cannot set quantity below 1", Toast.LENGTH_SHORT);
        if (quantity <= 1) {
            toast.show();
        }
        else {
            quantity--;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}