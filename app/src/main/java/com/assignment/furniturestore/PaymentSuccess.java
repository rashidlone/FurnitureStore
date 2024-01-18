package com.assignment.furniturestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuccess extends AppCompatActivity {

    private static final String EXTRA_ADDRESS_LINE1 = "extra_address_line1";
    private static final String EXTRA_ADDRESS_LINE2 = "extra_address_line2";
    private static final String EXTRA_CITY = "extra_city";
    private static final String EXTRA_STATE = "extra_state";
    private static final String EXTRA_COUNTRY = "extra_country";
    private static final String EXTRA_PINCODE = "extra_pincode";
    private static final String EXTRA_CARD_HOLDER_NAME = "extra_card_holder_name";
    private static final String EXTRA_CARD_NUMBER = "extra_card_number";
    private static final String EXTRA_EXPIRY_MONTH = "extra_expiry_month";
    private static final String EXTRA_EXPIRY_YEAR = "extra_expiry_year";
    private static final String EXTRA_CVV = "extra_cvv";

    public static void startActivity(Context context, String addressLine1, String addressLine2,
                                     String city, String state, String country, String pincode,
                                     String cardHolderName, String cardNumber, String expiryMonth,
                                     String expiryYear, String cvv) {
        Intent intent = new Intent(context, PaymentSuccess.class);
        intent.putExtra(EXTRA_ADDRESS_LINE1, addressLine1);
        intent.putExtra(EXTRA_ADDRESS_LINE2, addressLine2);
        intent.putExtra(EXTRA_CITY, city);
        intent.putExtra(EXTRA_STATE, state);
        intent.putExtra(EXTRA_COUNTRY, country);
        intent.putExtra(EXTRA_PINCODE, pincode);
        intent.putExtra(EXTRA_CARD_HOLDER_NAME, cardHolderName);
        intent.putExtra(EXTRA_CARD_NUMBER, cardNumber);
        intent.putExtra(EXTRA_EXPIRY_MONTH, expiryMonth);
        intent.putExtra(EXTRA_EXPIRY_YEAR, expiryYear);
        intent.putExtra(EXTRA_CVV, cvv);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        TextView addressTextView = findViewById(R.id.addressTextView);
        TextView cardDetailsTextView = findViewById(R.id.cardDetailsTextView);

        // Retrieve data from the intent
        String addressLine1 = getIntent().getStringExtra(EXTRA_ADDRESS_LINE1);
        String addressLine2 = getIntent().getStringExtra(EXTRA_ADDRESS_LINE2);
        String city = getIntent().getStringExtra(EXTRA_CITY);
        String state = getIntent().getStringExtra(EXTRA_STATE);
        String country = getIntent().getStringExtra(EXTRA_COUNTRY);
        String pincode = getIntent().getStringExtra(EXTRA_PINCODE);

        String cardHolderName = getIntent().getStringExtra(EXTRA_CARD_HOLDER_NAME);
        String cardNumber = getIntent().getStringExtra(EXTRA_CARD_NUMBER);
        String expiryMonth = getIntent().getStringExtra(EXTRA_EXPIRY_MONTH);
        String expiryYear = getIntent().getStringExtra(EXTRA_EXPIRY_YEAR);
        String cvv = getIntent().getStringExtra(EXTRA_CVV);

        // Display the data in TextViews
        String addressText = "Address: " + addressLine1 + ", " + addressLine2 + ", " +
                city + ", " + state + ", " + country + ", " + pincode;
        String cardDetailsText = "Card Details: " + cardHolderName + ", " + cardNumber + ", " +
                expiryMonth + "/" + expiryYear + ", CVV: " + cvv;

        addressTextView.setText(addressText);
        cardDetailsTextView.setText(cardDetailsText);
    }
}
