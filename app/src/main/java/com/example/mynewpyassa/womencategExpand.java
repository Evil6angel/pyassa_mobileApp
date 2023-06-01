package com.example.mynewpyassa;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynewpyassa.adapter.ProductAdapter;
import com.example.mynewpyassa.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class womencategExpand extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ImageView categoryBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_womencateg_expand);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get reference to Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get reference to the "wydadCateg" collection
        CollectionReference womenCateg = db.collection("womenCateg");

        // Query the collection to get all documents
        womenCateg.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                Toast.makeText(womencategExpand.this, "Error loading products", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
