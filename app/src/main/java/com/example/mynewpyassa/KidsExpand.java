package com.example.mynewpyassa;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynewpyassa.adapter.ProductAdapter;
import com.example.mynewpyassa.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewpyassa.databinding.ActivityKidsExpandBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class KidsExpand extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    ImageView categoryBtn;


    private AppBarConfiguration appBarConfiguration;
    private ActivityKidsExpandBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_expand);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get reference to Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get reference to the "wydadCateg" collection
        CollectionReference kidsCateg = db.collection("kidsCateg");

        // Query the collection to get all documents
        kidsCateg.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Create a list to hold the products
                List<ProductModel> products = new ArrayList<>();

                // Loop through the documents in the query result
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    // Convert each document to a ProductModel object
                    ProductModel product = document.toObject(ProductModel.class);

                    // Add the product to the list
                    products.add(product);
                }

                // Create and set the adapter on the RecyclerView
                adapter = new ProductAdapter(products);
                recyclerView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error getting products", e);
                Toast.makeText(KidsExpand.this, "Error loading products", Toast.LENGTH_SHORT).show();
            }
        });
    }


}


