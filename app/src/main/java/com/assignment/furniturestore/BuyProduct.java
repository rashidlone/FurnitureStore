package com.assignment.furniturestore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BuyProduct extends AppCompatActivity {

    private ImageView productImageView;
    private TextView nameTextView, descriptionTextView, priceTextView, quantityTextView;
    private ImageView increaseButton, decreaseButton;
    private Button checkoutButton;

    private int currentQuantity = 1;
    private double pricePerUnit;
    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

        // Initialize views
        productImageView = findViewById(R.id.productImageView);
        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        priceTextView = findViewById(R.id.priceTextView);
        quantityTextView = findViewById(R.id.quantityTextView);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Retrieve data from Intent
        quantity = getIntent().getIntExtra("quantity", 1);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        pricePerUnit = getIntent().getDoubleExtra("price",0.0);
        int defaultImageResource = R.drawable.ic_image;
        int receivedImageResource = getIntent().getIntExtra("image", defaultImageResource);


        // Set data to views

        nameTextView.setText(name);
        descriptionTextView.setText(description);
        productImageView.setImageResource(receivedImageResource);
        updatePriceAndQuantity();

        // Set click listeners
        increaseButton.setOnClickListener(v -> increaseQuantity());
        decreaseButton.setOnClickListener(v -> decreaseQuantity());
        checkoutButton.setOnClickListener(v -> performCheckout());
    }


    private void updatePriceAndQuantity() {
        quantityTextView.setText(String.valueOf(currentQuantity));
        double totalPrice = currentQuantity * pricePerUnit;
        priceTextView.setText(String.format("$%.2f", totalPrice));
    }

    private void increaseQuantity() {
        if (currentQuantity < getMaxQuantity()) {
            currentQuantity++;
            updatePriceAndQuantity();
        }
    }

    private void decreaseQuantity() {
        if (currentQuantity > 1) {
            currentQuantity--;
            updatePriceAndQuantity();
        }
    }

    private void performCheckout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Checkout : "+priceTextView.getText().toString());

        // Inflate a custom view for the AlertDialog
        View checkoutView = LayoutInflater.from(this).inflate(R.layout.dialog_checkout, null);
        builder.setView(checkoutView);
        builder.setCancelable(false);

        // Set up the buttons
        builder.setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Access the EditText fields in the custom view
                EditText addressLine1EditText = checkoutView.findViewById(R.id.addressLine1EditText);
                EditText addressLine2EditText = checkoutView.findViewById(R.id.addressLine2EditText);
                EditText cityEditText = checkoutView.findViewById(R.id.cityEditText);
                EditText stateEditText = checkoutView.findViewById(R.id.stateEditText);
                EditText countryEditText = checkoutView.findViewById(R.id.countryEditText);
                EditText pincodeEditText = checkoutView.findViewById(R.id.pincodeEditText);

                EditText cardHolderNameEditText = checkoutView.findViewById(R.id.cardHolderNameEditText);
                EditText cardNumberEditText = checkoutView.findViewById(R.id.cardNumberEditText);
                EditText expiryMonthEditText = checkoutView.findViewById(R.id.expiryMonthEditText);
                EditText expiryYearEditText = checkoutView.findViewById(R.id.expiryYearEditText);
                EditText cvvEditText = checkoutView.findViewById(R.id.cvvEditText);

                // Validate the input
                if (validateInput(addressLine1EditText, addressLine2EditText, cityEditText, stateEditText,
                        countryEditText, pincodeEditText, cardHolderNameEditText, cardNumberEditText,
                        expiryMonthEditText, expiryYearEditText, cvvEditText)) {

                    // If validation is successful, proceed to PaymentSuccess activity
                    String addressLine1 = addressLine1EditText.getText().toString();
                    String addressLine2 = addressLine2EditText.getText().toString();
                    String city = cityEditText.getText().toString();
                    String state = stateEditText.getText().toString();
                    String country = countryEditText.getText().toString();
                    String pincode = pincodeEditText.getText().toString();

                    String cardHolderName = cardHolderNameEditText.getText().toString();
                    String cardNumber = cardNumberEditText.getText().toString();
                    String expiryMonth = expiryMonthEditText.getText().toString();
                    String expiryYear = expiryYearEditText.getText().toString();
                    String cvv = cvvEditText.getText().toString();

                    // Pass the data to PaymentSuccess activity
                    PaymentSuccess.startActivity(BuyProduct.this, addressLine1, addressLine2, city, state,
                            country, pincode, cardHolderName, cardNumber, expiryMonth, expiryYear, cvv);

                    // Display a success message
                    Toast.makeText(BuyProduct.this, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();

                    // Dismiss the dialog
                    dialogInterface.dismiss();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    private boolean validateInput(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private int getMaxQuantity() {
        return quantity;
    }
}
