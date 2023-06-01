package com.example.mynewpyassa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    private EditText fnameEditText, lnameEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText cardNumberText, cardHolderText, ccvText;
    private EditText zipEditText;
    private TextView totalAmount;


    private ImageView backpressedBtn, cartpressedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize the EditText views

                fnameEditText = findViewById(R.id.etFirstName);
                lnameEditText = findViewById(R.id.etLastName);
                emailEditText = findViewById(R.id.etEmail);
                addressEditText = findViewById(R.id.editTextAddress);
                cityEditText = findViewById(R.id.editTextCity);
                zipEditText = findViewById(R.id.editTextZipCode);
                cardNumberText = findViewById(R.id.editTextCardNumber);
                cardHolderText = findViewById(R.id.editTextCardHolder);
                ccvText = findViewById(R.id.editTextCvv);
                TextView totalAmountTextView = findViewById(R.id.textViewTotalAmount);
                double totalAmount = getIntent().getDoubleExtra("totalAmount", 0);
                totalAmountTextView.setText("Total Amount: MAD" + totalAmount);




                // Get the user input from the EditText views
                String fname = fnameEditText.getText().toString();
                String lname = lnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String zip = zipEditText.getText().toString();
                String cardnum= cardNumberText.getText().toString();
                String cardholder = cardHolderText.getText().toString();
                String ccv = ccvText.getText().toString();


                // Validate the user input
                if (TextUtils.isEmpty(fname)) {
                    fnameEditText.setError("First Name is required");
                    fnameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(lname)) {
                    lnameEditText.setError("Last Name is required");
                    lnameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Invalid email format");
                    emailEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    addressEditText.setError("Address is required");
                    addressEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    cityEditText.setError("City is required");
                    cityEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(zip)) {
                    zipEditText.setError("ZIP code is required");
                    zipEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cardnum)) {
                    cardNumberText.setError("Missing Information");
                    cardNumberText.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(cardnum)) {
                    cardNumberText.setError("Missing Information");
                    cardNumberText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cardnum)) {
                    cardNumberText.setError("Missing Information");
                    cardNumberText.requestFocus();
                    return;
                }

                // If all inputs are valid, create an Intent to confirm the order
                Intent intent = new Intent(CheckoutActivity.this, OrderConfirmationActivity.class);
                intent.putExtra("first name",fname);
                intent.putExtra("last name",lname);
                intent.putExtra("email", email);
                intent.putExtra("address", address);
                intent.putExtra("city", city);
                intent.putExtra("zip", zip);
                intent.putExtra("card holder name", cardholder);
                intent.putExtra("card holder number", cardnum);
                intent.putExtra("card holder cvv", ccv);

                startActivity(intent);
            }
        });
        backpressedBtn=findViewById(R.id.back_pressed);
        backpressedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, HomePageActivity.class));
            }
        });
        cartpressedBtn = findViewById(R.id.cart);
        cartpressedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, CartActivity.class));
            }
        });


    }
}
