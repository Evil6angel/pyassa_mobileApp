package com.example.mynewpyassa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView orderConfirmationTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);


        // Get the total amount from the intent

        // Set the click listener for the done button
        Button doneButton = findViewById(R.id.order_confirmation_done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderConfirmationActivity.this, "Your order is placed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrderConfirmationActivity.this, HomePageActivity.class));
                finish();
            }
        });
    }
}
