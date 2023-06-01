package com.example.mynewpyassa;

import androidx.annotation.NonNull;
import androidx.annotation.ReturnThis;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class CategoryActivity extends AppCompatActivity{
        ImageView backpressed;
        CardView wydad,womenExpandBtn,kidsExpandBts,menExpandBtn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_category);
                kidsExpandBts = findViewById(R.id.kidsWidget);
                menExpandBtn = findViewById(R.id.menWidget);
                wydad = findViewById(R.id.wydadWidget);
                womenExpandBtn=findViewById(R.id.womenWidget);

                backpressed = findViewById(R.id.back_pressed);
                backpressed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(CategoryActivity.this, HomePageActivity.class);
                                startActivity(intent);
                        }
                });
                wydad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(CategoryActivity.this, WydadExpand.class);
                                startActivity(intent);

                        }
                });
                womenExpandBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(CategoryActivity.this, womencategExpand.class);
                                startActivity(intent);
                        }
                });
                menExpandBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(CategoryActivity.this, MenExpand.class);
                                startActivity(intent);
                        }
                });
                kidsExpandBts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(CategoryActivity.this, KidsExpand.class);
                                startActivity(intent);
                        }
                });

        }
}