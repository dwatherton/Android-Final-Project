package com.ualr.androidfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ShoppingCartActivity extends AppCompatActivity {

    private static final String TAG = "FinalProject";
    private ShoppingCartListFragment shoppingCartListFragment;
    private TextView mSubTotal;
    private TextView mTax;
    private TextView mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        // Set Toolbar to include my custom menu
        Toolbar toolbar = findViewById(R.id.shopping_cart_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorSecondary));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the Item List Fragment
        shoppingCartListFragment = ((ShoppingCartListFragment) getSupportFragmentManager().findFragmentById(R.id.shopping_cart_list_fragment));

        // Get Views
        mSubTotal = findViewById(R.id.shopping_cart_sub_total);
        mTax = findViewById(R.id.shopping_cart_tax);
        mTotal = findViewById(R.id.shopping_cart_total);

        if (shoppingCartListFragment.getItems() != null) {
            double subTotal = 0;
            for (int i = 0; i < shoppingCartListFragment.getItems().size(); i++) {
                // Add The List Price For Each Item To Sub Total
                subTotal += shoppingCartListFragment.getItems().get(i).getPrice();
            }

            // Set Totals
            if (subTotal <= 9.99) {
                mSubTotal.setText(String.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(subTotal))));
                mTax.setText(String.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(subTotal * 0.09))));
                mTotal.setText(String.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(subTotal + (subTotal * 0.09)))));
            }
            else
            {
                mSubTotal.setText(String.valueOf(Double.parseDouble(new DecimalFormat("##.##").format(subTotal))));
                mTax.setText(String.valueOf(Double.parseDouble(new DecimalFormat("##.##").format(subTotal * 0.09))));
                mTotal.setText(String.valueOf(Double.parseDouble(new DecimalFormat("##.##").format(subTotal + (subTotal * 0.09)))));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "Clicked on menu option: " + item.getTitle() + ".");
        switch (item.getItemId()) {
            case R.id.view_shopping_cart:
                // Empty Out The Shopping Cart List
                shoppingCartListFragment.clearShoppingCart();

                // Clear The Values For Sub-Total, Tax, And Total TextView's
                mSubTotal.setText("");
                mTax.setText("");
                mTotal.setText("");

                // Create An Intent For Showing The Browse Items View, And Start The Browse Items Activity
                Intent intent = new Intent(this, BrowseItemsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onProceedToCheckoutButtonClicked(View view) {
        // Show A Toast Explaining Why The Proceed To Checkout Button Does Nothing Further
        Toast.makeText(this, "Proceed To Checkout Exceeds The Scope Of This Project!!!", Toast.LENGTH_LONG).show();
    }
}
