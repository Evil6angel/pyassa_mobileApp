package com.example.mynewpyassa;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.mynewpyassa.adapter.ProductAdapter;
import com.example.mynewpyassa.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    LinearLayout sellerView,settings,share,aboutUs,logout;
    ImageView womenVintage, menVintage, kidsVintage, wacVintage;
    private ProductAdapter adapter;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Button searchBtn;
    private FirebaseFirestore db;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //search btn


        //Display products section
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("products");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CollectionReference productsRef = FirebaseFirestore.getInstance().collection("products");

        // Query the products collection to get all documents
        productsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Create a new list to hold the products
                List<ProductModel> products = new ArrayList<>();

                // Loop through the documents in the query result
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    // Convert each document to a ProductModel object
                    ProductModel product = document.toObject(ProductModel.class);

                    // Add the product to the list
                    products.add(product);
                }

                // Pass the list of products to the ProductAdapter and set it on the RecyclerView
                ProductAdapter adapter = new ProductAdapter(products);
                recyclerView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the error
                Log.e(TAG, "Error getting products", e);
                Toast.makeText(HomePageActivity.this, "Error loading products", Toast.LENGTH_SHORT).show();
            }
        });





        //CATEGORY SECTION
        womenVintage=findViewById(R.id.womenCategPic);
        menVintage=findViewById(R.id.menCategPic);
        kidsVintage=findViewById(R.id.KidsCategPic);
        wacVintage=findViewById(R.id.wydadCategPic);

        womenVintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this,womencategExpand.class));
            }
        });
        menVintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this,MenExpand.class));
            }
        });
        kidsVintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this,KidsExpand.class));
            }
        });
        wacVintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this,WydadExpand.class));
            }
        });
        //TOOLBAR SECTION
        drawerLayout=findViewById(R.id.drawer_layout);

        db = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toolbar Search




        //

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
            navigationView.setCheckedItem(R.id.nav_category);
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
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
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search query on Firestore database
                db.collection("products")
                        .orderBy("name")
                        .startAt(query.toLowerCase())
                        .endAt(query.toLowerCase() + "\uf8ff")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<ProductModel> products = new ArrayList<>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    ProductModel product = document.toObject(ProductModel.class);
                                    products.add(product);
                                }
                                // Handle search results
                                // For example, update the UI with the search results
                                updateSearchResultsUI(products);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle search failure
                                Log.e(TAG, "Error searching products", e);
                            }
                        });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Not used in this example
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity,Class secondAvtivity){
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
                startActivity(new Intent(HomePageActivity.this, CategoryActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(HomePageActivity.this, ContactActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(HomePageActivity.this, ShareActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(HomePageActivity.this, AboutActivity.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(HomePageActivity.this, SecondMainActivity.class));
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
    private void updateSearchResultsUI(List<ProductModel> products) {
        // Get a reference to the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Create a new instance of the ProductAdapter and pass in the list of products
        ProductAdapter adapter = new ProductAdapter(products);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(adapter);

        // Set the layout manager on the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}