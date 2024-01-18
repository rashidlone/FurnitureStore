package com.assignment.furniturestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        if (isUserLoggedIn()) {
            // User is logged in, open MainActivity directly
            finish();
            Launch.openActivity(LoginActivity.this, MainActivity.class);
            return;
        }

        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateInput(username, password)) {
                    loginUser(username, password);
                }
            }
        });
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            showToast("Please enter your username");
            return false;
        }

        if (password.isEmpty()) {
            showToast("Please enter your password");
            return false;
        }

        // Add more validation rules if needed

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loginUser(final String username, final String password) {
        progressDialog.show();

        try {
            // Replace "YOUR_LOGIN_URL" with the actual URL of your login.php
            String loginUrl = MainActivity.API_URL+"login.php";

            // Initialize Volley RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this);

            // Create a StringRequest for the login request using POST method
            StringRequest request = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            handleLoginResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            showToast("Error: " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            // Add the request to the RequestQueue
            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            showToast("Error: " + e.getMessage());
        }
    }

    private void handleLoginResponse(String response) {
        if (response.equals("success")) {
            // Successful login

            // Save user login state
            setLoggedIn(true);

            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            finish();
            Launch.openActivity(LoginActivity.this, MainActivity.class);
        } else {
            // Failed login
            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    private void setLoggedIn(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.apply();
    }

    public void newUser(View view) {
        finish();
        Launch.openActivity(LoginActivity.this, RegisterActivity.class);
    }
}
