package com.example.fluperapplication.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fluperapplication.R;
import com.example.fluperapplication.Utils.DbBitmapUtility;

import java.io.IOException;


import static com.example.fluperapplication.Activity.MainActivity.DATABASE_NAME;
import static com.example.fluperapplication.Activity.MainActivity.DB_TABLE;

public class UpdateProduct extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llBackButton, llAddProduct, llchooseImage;
    TextView tvImagePath, tvHeading, tvAddProduct;
    EditText etproductName, etproductDiscription, etRegularPrice, etSalePrice, etStorename;
    Spinner spinnerColor;
    String productname, productdiscripton, productregularprice, productsaleprice, productStorename, prodctImage, productColor;

    SQLiteDatabase mDatabase;
    Bitmap bitmap;
    private static final int PICK_FILE_REQUEST = 234;
    private Uri filePath;
    Bundle bundle;
    String ProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        initView();
    }

    public void initView() {

        llBackButton = findViewById(R.id.llBackButton);
        llAddProduct = findViewById(R.id.llAddProduct);
        llchooseImage = findViewById(R.id.llchooseImage);

        tvImagePath = findViewById(R.id.tvImagePath);
        tvHeading = findViewById(R.id.tvHeading);
        tvAddProduct = findViewById(R.id.tvAddProduct);

        spinnerColor = findViewById(R.id.spinnerColor);

        etproductName = findViewById(R.id.etproductName);
        etproductDiscription = findViewById(R.id.etproductDiscription);
        etRegularPrice = findViewById(R.id.etRegularPrice);
        etSalePrice = findViewById(R.id.etSalePrice);
        etStorename = findViewById(R.id.etStorename);

        tvHeading.setText("Update Product");
        tvAddProduct.setText("Update Product");

        bundle = getIntent().getExtras();
        ProductName=bundle.getString("Name");
        etproductName.setText(ProductName);
        etproductDiscription.setText(bundle.getString("Desc"));
        etRegularPrice.setText(bundle.getString("RP"));
        etSalePrice.setText(bundle.getString("SP"));
        etStorename.setText(bundle.getString("Store"));

        llBackButton.setOnClickListener(this);
        llAddProduct.setOnClickListener(this);
        llchooseImage.setOnClickListener(this);

        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    //In this method we will do the create operation
    private void addProduct(String name, String discription, String regularprice, String saleprise, String color, byte[] image, String store) {


        ContentValues cv = new ContentValues();
        cv.put(MainActivity.NAME, name);
        cv.put(MainActivity.DISCRIPTION, discription);
        cv.put(MainActivity.REGULARPRICE, regularprice);
        cv.put(MainActivity.SALEPRICE, saleprise);
        cv.put(MainActivity.COLOR, color);
        cv.put(MainActivity.IMAGE, image);
        cv.put(MainActivity.STORE, store);
        String where = "Pname=?";
        String[] whereArgs = new String[]{ProductName};
        mDatabase.update(DB_TABLE, cv, where, whereArgs);
        //  mDatabase.insert(DB_TABLE, null, cv);
        Toast.makeText(this, "Product Update Successfully", Toast.LENGTH_SHORT).show();

        etproductName.setText(null);
        etproductDiscription.setText(null);
        etRegularPrice.setText(null);
        etSalePrice.setText(null);
        etStorename.setText(null);
        tvImagePath.setText(null);
        Intent i = new Intent(UpdateProduct.this, ShowProduct.class);
        startActivity(i);
        finish();



    }

    public void mFilechosser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            System.out.println("File PAth========" + filePath);
            if (filePath != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    ///imageView.setImageBitmap(bitmap);
                    String imagename = filePath.getLastPathSegment();
                    tvImagePath.setText(imagename);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBackButton:

                onBackPressed();

                break;
            case R.id.llchooseImage:
                mFilechosser();

                break;

            case R.id.llAddProduct:
                productname = etproductName.getText().toString();
                productdiscripton = etproductDiscription.getText().toString();
                productregularprice = etRegularPrice.getText().toString();
                productsaleprice = etSalePrice.getText().toString();
                productColor = spinnerColor.getSelectedItem().toString();
                prodctImage = tvImagePath.getText().toString();
                productStorename = etStorename.getText().toString();

                byte[] image = DbBitmapUtility.getBytes(bitmap);
                long length = image.length;
                System.out.println("Sizeeeeeeeeeeeeeeeee=" + length);


                if (TextUtils.isEmpty(productname)) {
                    Toast.makeText(this, "Enter Product Name", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(productdiscripton)) {
                    Toast.makeText(this, "Enter Product Discription", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(productregularprice)) {
                    Toast.makeText(this, "Enter Regular Price", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(productsaleprice)) {
                    Toast.makeText(this, "Enter Sale Prise", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(productStorename)) {
                    Toast.makeText(this, "Enter Product store", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(prodctImage)) {
                    Toast.makeText(this, "Select Image", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(productColor)) {
                    Toast.makeText(this, "Select Color", Toast.LENGTH_LONG).show();
                } else if (length / 1000 >= 500) {
                    Toast.makeText(this, "Select another image less than 500 kb", Toast.LENGTH_LONG).show();
                    tvImagePath.setText(null);
                    mFilechosser();
                } else {

                    addProduct(productname, productdiscripton, productregularprice, productsaleprice, productColor, image, productStorename);
                }
                break;


        }
    }

}
