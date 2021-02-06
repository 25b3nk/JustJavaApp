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
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.checkbox_whipped_cream)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.checkbox_chocolate)).isChecked();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order for " + name);
        String orderSummary = createOrderSummary(name, hasWhippedCream, hasChocolate);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called to calculate the total price.
     *
     * @param hasWhippedCream if whipped cream is chosen
     * @param hasChocolate    if chocolate is chosen
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
     *
     * @param name            of the person ordering
     * @param hasWhippedCream if whipped cream is chosen
     * @param hasChocolate    if chocolate is chosen
     * @return Summary of the order in text
     */
    private String createOrderSummary(String name, boolean hasWhippedCream, boolean hasChocolate) {
        int total = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = getString(R.string.order_summary_name, name);
        orderSummary += "\n" + getString(R.string.add) + " " + getString(R.string.whipped_cream) + "? " + getBooleanString(hasWhippedCream);
        orderSummary += "\n" + getString(R.string.add) + " " + getString(R.string.chocolate) + "? " + getBooleanString(hasChocolate);
        orderSummary += "\n"+ getString(R.string.quantity) + ": " + quantity;
        orderSummary += "\n"+ getString(R.string.total) + ": $" + total;
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    /**
     *
     * @param value of the boolean to be converted to string
     * @return Text format of the boolean in the language based on locale setting
     */
    private String getBooleanString(boolean value) {
        return (value ? getString(R.string.boolean_true): getString(R.string.boolean_false));
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
        displayQuantity(quantity);
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
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}