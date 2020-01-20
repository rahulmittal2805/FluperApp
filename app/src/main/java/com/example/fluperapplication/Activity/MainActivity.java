package com.example.fluperapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.example.fluperapplication.R;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout llshowProduct,llcreateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    public void initView(){
        llshowProduct=findViewById(R.id.llshowProduct);
        llcreateProduct=findViewById(R.id.llcreateProduct);

        llcreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,CreateProduct.class);
                startActivity(i);

            }
        });

        llshowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ShowProduct.class);
                startActivity(i);
            }
        });
    }
}
