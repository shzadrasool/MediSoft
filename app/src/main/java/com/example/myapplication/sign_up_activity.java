package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class sign_up_activity extends AppCompatActivity {

    TextView signin;
    Button btn_signup;
    EditText et_name;
    EditText et_contact;
    EditText et_pass;
    String nam, con, user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        signin = findViewById(R.id.link_signin);
        btn_signup = findViewById(R.id.btn_signup);
        et_name = findViewById(R.id.input_username);
        et_contact = findViewById(R.id.input_phoneNo);
        et_pass = findViewById(R.id.input_password);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String contact = et_contact.getText().toString().trim();
                String password = et_pass.getText().toString().trim();

                if (name.isEmpty()) {
                    et_name.setError("Enter Username");
                } else if (contact.isEmpty()) {

                    et_contact.setError("Enter Contact");
                } else if (password.isEmpty()) {

                    et_pass.setError("Enter Password");
                } else {
                    SignUpProcess(name, contact, password);

                }
            }


        });
    }

    private void SignUpProcess(final String name, final String contact, final String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setUserName(name);
        user.setUserContact(contact);
        user.setUserPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.SIGN_UP);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                Toast.makeText(sign_up_activity.this, "" + resp.getMessage(), Toast.LENGTH_SHORT).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    UserShared user = new UserShared(sign_up_activity.this);
                    user.setName(nam);
                    user.setContact(con);
                    user.setUser_uid(user_uid);

                    Intent intent = new Intent(sign_up_activity.this, navigation_activity.class);
                    intent.putExtra("name", nam);
                    intent.putExtra("con", con);
                    intent.putExtra("user_uid", user_uid);
                    startActivity(intent);
                    finish();

//                    startActivity(new Intent(getApplicationContext(), TeacherPanel.class));

                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {


                Log.d(Constants.TAG, "failed");
                Toast.makeText(sign_up_activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}