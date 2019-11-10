package com.ualr.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ualr.androidfinalproject.model.User;
import com.ualr.androidfinalproject.model.UserViewModel;
import com.ualr.androidfinalproject.repository.Repository;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "FinalProject";
    private TextInputLayout emailTIL;
    private TextInputLayout passwordTIL;
    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get The User View Model
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // Create A User Repository And Pass It The User View Model (For Populating View Model With A List Of Users From Database)
        Repository repository = new Repository(userViewModel);
        // Make The Call To Populate View Model List Of Users From Database
        repository.getUserList();

        // Get The Email And Password EditText's
        emailET = findViewById(R.id.login_email);
        passwordET = findViewById(R.id.login_password);
    }

    public void onForgotPasswordClicked(View view) {
        Log.d(TAG, "User Clicked On Forgot Password Link!");
        // TODO: Implement Firebase Password Reset Link Sent To Currently Entered Email Address
    }

    public void onLoginButtonClicked(View view) {
        Log.d(TAG, "User Clicked On Login Button!");

        // Get Email And Password
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        // Handle Empty Required Field Scenario's
        if (email.isEmpty() || password.isEmpty()) {
            Log.d(TAG, "User left a required login field empty! Retry!");
            if (email.isEmpty()) {
                emailTIL = findViewById(R.id.login_email_text_input_layout);
                emailTIL.setError("Email field is Required!");
            } else {
                passwordTIL = findViewById(R.id.login_password_text_input_layout);
                passwordTIL.setError("Password field is Required!");
            }
            // Don't Even Bother Checking Data Entered Unless Required Fields Are NOT Empty
            return;
        }

        // Check If A User With Specified Email Exists In Our User View Model
        if (userViewModel.getUser(email) != null) {
            Log.d(TAG, "Found an account for a User with the email: " + email + "!");
            // User Exists, Get The User
            User user2 = userViewModel.getUser(email);
            // Check The User Entered Password With Password In View Model
            if (user2.getPassword().equals(password)) {
                Log.d(TAG, "The password the user entered was correct! Logging In!");

                // Password Correct! Create An Intent For Showing The Browse Items View, And Start The Browse Items Activity
                Intent intent = new Intent(this, BrowseItemsActivity.class);
                startActivity(intent);
            }
            else {
                // Password Incorrect! Display A Toast To Alert User
                Log.d(TAG, "The password the user entered was incorrect! Retry!");
                Toast.makeText(this, "Incorrect Password! Please try again...", Toast.LENGTH_LONG).show();
            }
        }
        else {
            // No User With Specified Email Exists In Our View Model (Populated From Jexia Web Service API)
            Log.d(TAG, "No User account matching the email: " + email + "! Retry");
            Toast.makeText(this, "Incorrect Email! Please try again...", Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateAccountClicked(View view) {
        Log.d(TAG, "User Clicked On Create Account Link!");
        // Clicked To Create Account! Create An Intent For Showing The Register Activity, And Start The Register Activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
