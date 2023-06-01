package com.example.mynewpyassa;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynewpyassa.adapter.CartAdapter;
import com.example.mynewpyassa.model.CartItem;
import com.example.mynewpyassa.model.CartModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    LinearLayout sellerView,settings,share,aboutUs,logout;
    RecyclerView recyclerView;
    TextView totalPriceTextView;
CheckoutActivity checkoutActivity;
Button checkoutButton;

    ArrayList<CartItem> cartItems;
    CartAdapter cartAdapter;
    CartModel cartModel;
    private LinearLayout parentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //Cart Display items

        TextView totalPriceTextView = findViewById(R.id.total_price_text_view);
        totalPriceTextView.setText("0");

        recyclerView = findViewById(R.id.cart_recycler_view);
        checkoutButton = findViewById(R.id.checkout_button);

        cartItems = CartModel.getInstance().getCartItems();
        cartAdapter = new CartAdapter(cartItems);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cartAdapter);

        // Update the total price TextView
        updateCartUI();

        // Set the checkout button click listener
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Intent to navigate to the checkout activity/fragment
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                double totalAmount = calculateTotalPrice();
                intent.putExtra("totalAmount", totalAmount);
                startActivity(intent);
            }
        });

        //

        drawerLayout=findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView userEmailTextView = headerView.findViewById(R.id.user_email);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            userEmailTextView.setText(email);
        }
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            //    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SellerView()).commit();
            navigationView.setCheckedItem(R.id.nav_about);
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_cart);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide__out_left);
                    finish();
                    return true;
                case R.id.bottom_cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide__out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide__out_left);
                    finish();
                    return true;
                case R.id.bottom_help:
                    startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide__out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondAvtivity){
        Intent intent = new Intent(activity, secondAvtivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_category:
                startActivity(new Intent(CartActivity.this, CategoryActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(CartActivity.this, ContactActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(CartActivity.this, ShareActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(CartActivity.this, AboutActivity.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(CartActivity.this, SecondMainActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    private AppCompatTextView total_price;

    private void updateCartUI() {
        // Update the cart items list view
        cartAdapter.notifyDataSetChanged();
        // Update the total price text view
        TextView totalPriceTextView = findViewById(R.id.total_price_text_view);
        double totalPrice = calculateTotalPrice();
        totalPriceTextView.setText(String.format(Locale.getDefault(), "Total Price: MAD%.2f", totalPrice));
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }




}