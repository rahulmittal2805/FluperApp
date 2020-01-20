package com.example.fluperapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fluperapplication.Model.Product;
import com.example.fluperapplication.R;
import com.example.fluperapplication.Utils.DbBitmapUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateProduct extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llBackButton, llAddProduct, llchooseImage;
    TextView tvImagePath;
    EditText etproductName, etproductDiscription, etRegularPrice, etSalePrice, etStorename;
    Spinner spinnerColor;
    String productname, productdiscripton, productregularprice, productsaleprice, productStorename, prodctImage, productColor;
    public static final String DATABASE_NAME = "myproductsdatabase";

    Bitmap bitmap;

    // Table Names
    static final String DB_TABLE = "product10";

    // column names
    static final String NAME = "Pname";
    static final String DISCRIPTION = "Pdiscription";
    static final String REGULARPRICE = "Pregularprice";
    static final String SALEPRICE = "Psaleprise";
    static final String COLOR = "Pcolor";
    static final String IMAGE = "Pimage";
    static final String STORE = "Pstore";

    static SQLiteDatabase mDatabase;

    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};
    private int STORAGE_PERMISSION_CODE = 23;

    private static final int PICK_FILE_REQUEST = 234;
    private Uri filePath;

    ArrayList<Product> productList = new ArrayList<>();

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

        spinnerColor = findViewById(R.id.spinnerColor);

        etproductName = findViewById(R.id.etproductName);
        etproductDiscription = findViewById(R.id.etproductDiscription);
        etRegularPrice = findViewById(R.id.etRegularPrice);
        etSalePrice = findViewById(R.id.etSalePrice);
        etStorename = findViewById(R.id.etStorename);

        llBackButton.setOnClickListener(this);
        llAddProduct.setOnClickListener(this);
        llchooseImage.setOnClickListener(this);

        //creating a database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createEmployeeTable();
    }


    private void createEmployeeTable() {

        String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + "(" +
                NAME + " TEXT," +
                DISCRIPTION + " TEXT," +
                REGULARPRICE + " TEXT," +
                SALEPRICE + " TEXT," +
                COLOR + " TEXT," +
                IMAGE + " BLOB," +
                STORE + " TEXT);";

        mDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    //In this method we will do the create operation
    private void addProduct(String name, String discription, String regularprice, String saleprise, String color, byte[] image, String store) {


        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(DISCRIPTION, discription);
        cv.put(REGULARPRICE, regularprice);
        cv.put(SALEPRICE, saleprise);
        cv.put(COLOR, color);
        cv.put(IMAGE, image);
        cv.put(STORE, store);
        mDatabase.insert(DB_TABLE, null, cv);
        Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show();

        etproductName.setText(null);
        etproductDiscription.setText(null);
        etRegularPrice.setText(null);
        etSalePrice.setText(null);
        etStorename.setText(null);
        tvImagePath.setText(null);
        Intent i = new Intent(CreateProduct.this, ShowProduct.class);
        startActivity(i);


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
                checkPermission();

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
                long length= image.length;
                System.out.println("Sizeeeeeeeeeeeeeeeee="+length);



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
                }else if(length/1000>=500){
                    Toast.makeText(this, "Select another image less than 500 kb", Toast.LENGTH_LONG).show();
                    tvImagePath.setText(null);
                    mFilechosser();
                } else {

                    addProduct(productname, productdiscripton, productregularprice, productsaleprice, productColor, image, productStorename);
                }
                break;


        }
    }


    public void checkPermission() {
        int ResultStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ResultStorage1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (ResultStorage != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else if (ResultStorage1 != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            mFilechosser();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, STORAGE_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mFilechosser();
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else
                checkPermission();
        }
    }

}
