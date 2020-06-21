package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Retrofit.Constants;
import com.example.myapplication.Retrofit.RequestInterface;
import com.example.myapplication.Retrofit.ServerRequest;
import com.example.myapplication.Retrofit.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class My_Profile extends AppCompatActivity {

    String user_name, user_contact, user_pass, uid, update_name, update_contact, update_pass;
    EditText name, contact, pass;
    ImageView img_edit;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__profile);

        name = findViewById(R.id.user_name);
        contact = findViewById(R.id.user_con);
        pass = findViewById(R.id.user_pass);
        btn_update = findViewById(R.id.btn_update);

        name.setEnabled(false);
        contact.setEnabled(false);
        pass.setEnabled(false);
        img_edit = findViewById(R.id.img_edit);

        UserShared userShared = new UserShared(My_Profile.this);
        uid = userShared.getUser_uid();

        getInfo(uid);


        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                name.setEnabled(true);
                                contact.setEnabled(true);
                                pass.setEnabled(true);
                                btn_update.setVisibility(View.VISIBLE);
                                img_edit.setVisibility(View.GONE);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(My_Profile.this);
                builder.setMessage("Are you sure you want to edit?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getvalues();
                updateInfo(update_name, update_contact, update_pass);
                getInfo(uid);
                name.setEnabled(false);
                contact.setEnabled(false);
                pass.setEnabled(false);
                btn_update.setVisibility(View.GONE);
                img_edit.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getvalues() {

        update_name = name.getText().toString();
        update_contact = contact.getText().toString();
        update_pass = pass.getText().toString();
    }

    private void getInfo(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final User user = new User();
        user.setUid(id);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_INFO);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                assert resp != null;
                if (resp.getResult().equals(Constants.SUCCESS)) {
                    User user = resp.getUser();
                    user_name = user.getUserName();
                    user_contact = user.getUserContact();
                    user_pass = user.getUserPassword();
                    name.setText(user_name);
                    contact.setText(user_contact);
                    pass.setText(user_pass);


                } else {
                    Toast.makeText(My_Profile.this, resp.getResult(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {


                Log.d(Constants.TAG, "failed");
                Toast.makeText(My_Profile.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

//                 Snackbar.make(MainActivity.this, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void updateInfo(String nameVr, String contactVr, String passwordVr) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final User user = new User();
        user.setUid(uid);
        user.setUserName(nameVr);
        user.setUserContact(contactVr);
        user.setUserPassword(passwordVr);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_INFO);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();


                Toast.makeText(My_Profile.this, "" + resp.getMessage(), Toast.LENGTH_SHORT).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(My_Profile.this, resp.getResult(), Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(My_Profile.this, resp.getResult(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {


                Log.d(Constants.TAG, "failed");
                Toast.makeText(My_Profile.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
