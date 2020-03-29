package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Retrofit.Constants;
import com.example.myapplication.Retrofit.RequestInterface;
import com.example.myapplication.Retrofit.ServerRequest;
import com.example.myapplication.Retrofit.ServerResponse;
import com.example.myapplication.adapters.AdapterMedi;
import com.example.myapplication.model_classes.mPic;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order_image extends AppCompatActivity {

    private static final int IMG_REQUEST = 777;
    String file_name;
    private Bitmap bitmap;
    int checkStatus = 0;

    Button btn_order;
    ImageButton order_image;
    EditText et_address, et_extras;

    String user_uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initView();

        UserShared userShared = new UserShared(Order_image.this);
        user_uid = userShared.getUser_uid();

        order_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });


        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_address.getText().toString().length() == 0) {
                    et_address.setError("Address is required");
                } else {
                    if (checkStatus == 1) {

                        addOrder();

                    } else if (checkStatus == 0) {
                        Toast.makeText(Order_image.this, "Please select image first!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    public void goBack(View view) {
        Intent intent = new Intent(Order_image.this, navigation_activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        AdapterMedi.listofId.clear();
        Intent intent = new Intent(Order_image.this, navigation_activity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {

        order_image = findViewById(R.id.order_pic);
        btn_order = findViewById(R.id.send_order);
        et_address = findViewById(R.id.et_address);
        et_extras = findViewById(R.id.et_extra);




    }

    private void openFileChooser() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                order_image.setImageURI(resultUri);
                file_name = getFileName(resultUri);

                int pos = file_name.lastIndexOf(".");
                if (pos >= 0) {
                    file_name = file_name.substring(0, pos);
                }

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    order_image.setImageBitmap(bitmap);
                    order_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    checkStatus = 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
            }
        }


    }


    private String getFileName(Uri uri) {

        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);


    }

    private void addOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final mPic mpic = new mPic();
        final String image = imageToString();


        mpic.setUser_uid(user_uid);
        mpic.setAddress(et_address.getText().toString());
        mpic.setExtras(et_extras.getText().toString());
        mpic.setPicName(file_name);
        mpic.setImage_code(image);


        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPLOAD_PIC);
        request.setMpic(mpic);

        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                mPic mpic = new mPic();
                ServerResponse resp = response.body();

                Toast.makeText(Order_image.this, resp.getMessage(), Toast.LENGTH_LONG).show();


                mpic = resp.getM();

                setUpIntent();


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(Order_image.this, "Connection Failure " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setUpIntent() {
        Intent intent = new Intent(Order_image.this, final_activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        finish();
    }

}
