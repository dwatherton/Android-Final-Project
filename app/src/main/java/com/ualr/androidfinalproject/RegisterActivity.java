package com.ualr.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ualr.androidfinalproject.model.User;
import com.ualr.androidfinalproject.model.UserViewModel;
import com.ualr.androidfinalproject.repository.Repository;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "FinalProject";
    private static final String VALID_EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String STRONG_PASSWORD_REGEX = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+<>~`|?,.])(?=.*).{8,})";
    private TextInputLayout emailTIL;
    private TextInputLayout passwordTIL;
    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private UserViewModel userViewModel;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get The User View Model
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // Create A User Repository And Pass It The User View Model (For Populating View Model With A List Of Users From Database)
        repository = new Repository(userViewModel);
        // Make The Call To Populate View Model List Of Users From Database
        repository.getUserList();

        // Get The Email And Password EditText's
        emailET = findViewById(R.id.registration_email);
        passwordET = findViewById(R.id.registration_password);

        // Get The Email And Password Text Input Layout (For Updating Color Based On Relevance/Strength)
        emailTIL = findViewById(R.id.registration_email_text_input_layout);
        passwordTIL = findViewById(R.id.registration_password_text_input_layout);

        emailET.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String email = emailET.getText().toString();
                // Check If The Email Entered Doesn't Match The Valid Email Regex
                if (!email.matches(VALID_EMAIL_REGEX)) {
                    emailTIL.setBoxStrokeColor(getResources().getColor(R.color.negativeFeedbackColor));
                }
                // Otherwise The Email Is A Valid Email Address
                else {
                    emailTIL.setBoxStrokeColor(getResources().getColor(R.color.positiveFeedbackColor));
                }
                return false;
            }
        });

        passwordET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String password = passwordET.getText().toString();
                // Whatever The Password Contains, If It's Less Than 4 Characters, It's Weak!
                if (password.length() < 4) {
                    passwordTIL.setBoxStrokeColor(getResources().getColor(R.color.negativeFeedbackColor));
                }
                // Otherwise If The Password Is Not Less Than 4 Characters, But It Is Less Than 8, It's Medium!
                else if (password.length() < 8){
                    passwordTIL.setBoxStrokeColor(getResources().getColor(R.color.moderateFeedbackColor));
                }
                else {
                    // Finally, If The Password Is Greater Than Or Equal To 8 Characters, Check Against RegEx For A Strong Password!
                    if (password.matches(STRONG_PASSWORD_REGEX)) {
                        passwordTIL.setBoxStrokeColor(getResources().getColor(R.color.positiveFeedbackColor));
                    }
                }
                return false;
            }
        });
    }

    public void onRegisterButtonClicked(View view) {
        Log.d(TAG, "User Clicked On Register Button!");

        // Get Email And Password
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        // Handle Empty Required Field Scenario's
        if (email.isEmpty() || password.isEmpty()) {
            Log.d(TAG, "User left a required registration field empty! Retry!");
            if (email.isEmpty()) {
                emailTIL.setError("Email field is Required!");
            } else {
                passwordTIL.setError("Password field is Required!");
            }
            // Don't Even Bother Checking Data Entered Unless Required Fields Are NOT Empty
            return;
        }

        // Make Sure A User With Specified Email Doesn't Exist In Our User View Model
        if (userViewModel.getUser(email) == null) {
            Log.d(TAG, "Checking if Email Address is a valid email!");
            // Check Email Validity
            if (email.matches(VALID_EMAIL_REGEX)) {
                Log.d(TAG, "Email is a valid Email Address!");
            }
            else {
                Toast.makeText(this, "Email Address Invalid! Must contain 1 or more characters, @ character, 1 or more characters, . character, 1 or more characters", Toast.LENGTH_LONG).show();
                // Don't Allow Registration With Invalid Email Address
                return;
            }

            Log.d(TAG, "Checking Password strength before creating account!");
            // Check Password Strength
            if (password.matches(STRONG_PASSWORD_REGEX)) {
                Log.d(TAG, "Password is strong enough to register!");
            }
            else {
                Toast.makeText(this, "Password is too Weak! Must contain 1 lowercase, 1 uppercase, 1 number, 1 special character, and be at least 8 characters", Toast.LENGTH_LONG).show();
                // Don't Allow Registration With Weak Passwords
                return;
            }

            // User Does NOT Exist Yet, And The Email And Password Are Sufficient, Create A User!
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            Log.d(TAG, "Attempting to register an account with Email: " + user.getEmail() + " and Password: " + user.getPassword() + "!");
            // Register The User With The Database
            repository.register(user);

            // Create An Intent For Showing The Browse Items View, And Start The Browse Items Activity
            Intent intent = new Intent(this, BrowseItemsActivity.class);
            startActivity(intent);
        }
        else {
            // User Already Exists! Display A Toast To Alert User
            Log.d(TAG, "An account already exists for a User with the email: " + email + "!");
            Toast.makeText(this, "Email Address is already registered to an account! Please try again...", Toast.LENGTH_LONG).show();
        }
    }

}
