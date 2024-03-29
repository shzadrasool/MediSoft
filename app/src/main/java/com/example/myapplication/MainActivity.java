package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Retrofit.Constants;
import com.example.myapplication.Retrofit.RequestInterface;
import com.example.myapplication.Retrofit.ServerRequest;
import com.example.myapplication.Retrofit.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView signup;
    Button btn_login;
    EditText et_contact;
    EditText et_pass;
    LinearLayout linearLayout;
    String name, con, user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.link_signup);
        btn_login = findViewById(R.id.btn_login);
        et_contact = findViewById(R.id.input_phoneNo);
        et_pass = findViewById(R.id.input_password);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sign_up_activity.class);
                startActivity(intent);
                finish();
            }
        });

        if (isNetwork(getApplicationContext())) {

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Internet Is Not Connected", Toast.LENGTH_SHORT).show();
        }

        //validateUser();
        final UserShared userShared = new UserShared(MainActivity.this);


        if (!userShared.getContact().equals("")) {
            // loginProcess(em,pass);
            Intent intent = new Intent(MainActivity.this, navigation_activity.class);
            intent.putExtra("contact", userShared.getContact());
            startActivity(intent);
            finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_contact.getText().toString().trim();
                String password = et_pass.getText().toString().trim();

                if (email.isEmpty()) {
                    et_contact.setError("Enter Contact");
                } else if (password.isEmpty()) {

                    et_pass.setError("Enter Password");
                } else {
                    loginProcess(email, password);
                }
            }
        });





    }

    public boolean isNetwork(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void loginProcess(final String contact, final String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final User user = new User();
        user.setUserContact(contact);
        user.setUserPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOG_IN);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();



                Toast.makeText(MainActivity.this, "" + resp.getMessage(), Toast.LENGTH_SHORT).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    User user = resp.getUser();
                    name = user.getUserName();
                    con = user.getUserContact();
                    user_uid = user.getUid();
                    UserShared userShared = new UserShared(MainActivity.this);
                    userShared.setName(name);
                    userShared.setContact(con);
                    userShared.setUser_uid(user_uid);
                    Toast.makeText(MainActivity.this, user_uid, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, navigation_activity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("con", con);
                    intent.putExtra("user_uid", user_uid);
                    startActivity(intent);
                    finish();

                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {


                btn_login.setVisibility(View.VISIBLE);
                Log.d(Constants.TAG, "failed");
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

//                 Snackbar.make(MainActivity.this, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }


}